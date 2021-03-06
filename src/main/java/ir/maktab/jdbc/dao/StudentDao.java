package ir.maktab.jdbc.dao;

import ir.maktab.jdbc.config.DataSourceConfig;
import ir.maktab.jdbc.dao.core.BaseDao;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.entity.Student;
import ir.maktab.jdbc.exception.DataNotFoundException;
import ir.maktab.jdbc.exception.ModificationDataException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentDao implements BaseDao<Student, Integer> {

    private Connection connection;

    private CourseDao courseDao = new CourseDao();
    private MajorDao majorDao = new MajorDao();

    public StudentDao() {
        try {
            this.connection = DataSourceConfig.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Student entity) {
        final String QUERY = "INSERT INTO students (name, family_name, major_id) VALUES(?, ?, ?)";
        final String MIDDLE_TABLE_QUERY = "INSERT INTO students_courses (student_id , course_id) " +
                "values(? , ?)";
        int studentId;
        try (PreparedStatement ps = statementForVarArgs(QUERY , entity.getName() ,
                entity.getFamilyName() , entity.getMajor().getId()) ) {
            ps.executeUpdate();
            ResultSet resultSet =ps.getGeneratedKeys();
            resultSet.next();
            studentId =resultSet.getInt(1);
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
            throw new ModificationDataException("Can not insert student " + entity + " into database.");
        }
        for (Course course : entity.getCourses()){
            try(PreparedStatement ps = statementForVarArgs(MIDDLE_TABLE_QUERY ,
                     studentId , course.getId() )){
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
                throw new ModificationDataException("Can not insert student course data to db.");
            }
        }

    }

    @Override
    public void update(Integer id, Student newEntity) {
        final String QUERY ="UPDATE students SET name=?, family_name=?, major_id=? WHERE id= ?";
        final String MIDDLE_TABLE_DELETE = "DELETE FROM students_courses WHERE student_id = ?";
        final String MIDDLE_TABLE_QUERY = "INSERT INTO students_courses (student_id , course_id) " +
                "values(? , ?)";
        try ( PreparedStatement ps = statementForVarArgs(QUERY , newEntity.getName() ,
                newEntity.getFamilyName() , newEntity.getMajor().getId() , id)){
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not update student " + newEntity + " into database.");
        }
        try ( PreparedStatement ps = statementForVarArgs(MIDDLE_TABLE_DELETE , id )){
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not delete previous courses for student " + newEntity);
        }
        for (Course course : newEntity.getCourses()){
            try(PreparedStatement ps = statementForVarArgs(MIDDLE_TABLE_QUERY ,id , course.getId() )){
                ps.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
                throw new ModificationDataException("Can not insert new courses for student " + newEntity);
            }
        }

    }

    @Override
    public void delete(Integer id) {
        final String QUERY = "DELETE FROM students WHERE id=?";
        try (PreparedStatement ps = statementForVarArgs(QUERY , id)){
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("can not delete student with id: " + id);
        }
    }

    @Override
    public Student loadById(Integer id) {
        final String QUERY ="SELECT * FROM students WHERE id = ?";
        final String MIDDLE_TABLE_QUERY = "SELECT course_id FROM students_courses WHERE student_id = ?";
        Student student = null;
        try (
             PreparedStatement ps = statementForVarArgs(QUERY,id);
             PreparedStatement courses = statementForVarArgs(MIDDLE_TABLE_QUERY , id);
             ResultSet resultSet = ps.executeQuery();
             ResultSet courseResults = courses.executeQuery()){
                if (resultSet.next()) {
                   student = entityByResultset(resultSet);
                   Set<Course> courseSet = new HashSet<>();
                   while(courseResults.next()){
                       courseSet.add(courseDao.loadById(courseResults.getInt("course_id")) );
                   }
                   student.setCourses(courseSet);
                }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("error happened while finding student with id: " + id);
        }
        if (student==null){
            throw new DataNotFoundException("Can not find student with id: " + id);
        }else {
            return student;
        }
    }

    @Override
    public List<Student> loadAll() {
        final String QUERY ="SELECT * FROM students";
        final String MIDDLE_TABLE_QUERY = "SELECT course_id FROM students_courses WHERE student_id = ?";
        List<Student> students = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(QUERY);
            ResultSet resultSet = ps.executeQuery();){
            while (resultSet.next()) {
                Student student = entityByResultset(resultSet);
                try (PreparedStatement courseStatement = statementForVarArgs(MIDDLE_TABLE_QUERY , student.getId());
                ResultSet courseResults = courseStatement.executeQuery()){
                    Set<Course> courseSet = new HashSet<>();
                    while(courseResults.next()){
                        courseSet.add(courseDao.loadById(courseResults.getInt("course_id")) );
                    }
                    student.setCourses(courseSet);
                }catch (SQLException e) {
                    e.printStackTrace();
                    throw new DataNotFoundException("error happened while finding students.");
                }
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("error happened while finding students.");
        }
        if (students.isEmpty()){
            throw new DataNotFoundException("Can not find any students.");
        }else {
            return students;
        }
    }

    @Override
    public Student entityByResultset(ResultSet resultSet) throws SQLException {
        int studentId = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String familyName = resultSet.getString("family_name");
        int majorId = resultSet.getInt("major_id");
        Student student = Student.builder()
                .id(studentId)
                .name(name)
                .familyName(familyName)
                .major(majorDao.loadById(majorId))
                .build();
        return student;
    }

    @Override
    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    @Override
    public void commit() throws SQLException {
        connection.commit();
    }

    @Override
    public void rollBack() throws SQLException {
        connection.rollback();
    }

    private PreparedStatement statementForVarArgs(String query, Object... params){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for(int i=1; i<=params.length; i++){
                Object param = params[i - 1];
                Class paramClass = param.getClass();
                if (Integer.class.equals(paramClass)) {
                    preparedStatement.setInt(i , (int)param);
                } else if (Double.class.equals(paramClass)) {
                    preparedStatement.setDouble(i , (double)param);
                } else if (String.class.equals(paramClass)) {
                    preparedStatement.setString(i , (String)param);
                } else if (Boolean.class.equals(paramClass)) {
                    preparedStatement.setBoolean(i , (Boolean) param); ;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }
}
