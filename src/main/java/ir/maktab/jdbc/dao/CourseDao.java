package ir.maktab.jdbc.dao;

import ir.maktab.jdbc.config.DataSourceConfig;
import ir.maktab.jdbc.dao.core.BaseDao;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.exception.DataNotFoundException;
import ir.maktab.jdbc.exception.ModificationDataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao implements BaseDao<Course, Integer> {

    private Connection connection;

    public CourseDao() {
        try {
            this.connection = DataSourceConfig.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Course entity) {
        final String QUERY = "INSERT INTO courses (name, units) VALUES(?, ?)";

        try (PreparedStatement ps = statementForVarArgs(QUERY , entity.getName() ,
                entity.getUnit() )) {
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not insert data to db");
        }


    }

    @Override
    public void update(Integer id, Course newEntity) {
        final String QUERY ="UPDATE courses SET name=?, units=? WHERE id= ?";
        try ( PreparedStatement ps = statementForVarArgs(QUERY , newEntity.getName() ,
                newEntity.getUnit (), id)){
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not update data to db");
        }
    }

    @Override
    public void delete(Integer id) {
        final String QUERY = "DELETE FROM courses WHERE id=?";
        try (PreparedStatement ps = statementForVarArgs(QUERY , id)){
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not update data to db");
        }
    }

    @Override
    public Course loadById(Integer id) {
        final String QUERY ="SELECT * FROM courses WHERE id = ?";
        try (PreparedStatement ps = statementForVarArgs(QUERY,id)){
            try (ResultSet resultSet = ps.executeQuery()) {
                Course course = null;
                while (resultSet.next()) {
                    course = entityByResultset(resultSet);
                }
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find data from db");
        }
    }

    @Override
    public List<Course> loadAll() {
        final String QUERY ="SELECT * FROM courses";
        try (PreparedStatement ps = connection.prepareStatement(QUERY);
            ResultSet resultSet = ps.executeQuery();){
            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                courses.add( entityByResultset(resultSet));
            }
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find data from db");
        }
    }

    @Override
    public Course entityByResultset(ResultSet resultSet) throws SQLException {
        int courseId = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int unit = resultSet.getInt("units");
        return new Course(courseId,name,unit);
    }


    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connection.commit();
    }


    private PreparedStatement statementForVarArgs(String query, Object... params){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
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
