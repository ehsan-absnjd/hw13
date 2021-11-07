package ir.maktab.jdbc.dao;

import ir.maktab.jdbc.config.DataSourceConfig;
import ir.maktab.jdbc.dao.core.BaseDao;
import ir.maktab.jdbc.entity.Major;
import ir.maktab.jdbc.exception.DataNotFoundException;
import ir.maktab.jdbc.exception.ModificationDataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MajorDao implements BaseDao<Major, Integer> {

    private Connection connection;

    public MajorDao() {
        try {
            this.connection = DataSourceConfig.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Major entity) {
        final String QUERY = "INSERT INTO majors (name) VALUES(?)";

        try (PreparedStatement ps = statementForVarArgs(QUERY , entity.getName() )) {
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not insert data to db");
        }


    }

    @Override
    public void update(Integer id, Major newEntity) {
        final String QUERY ="UPDATE majors SET name=? WHERE id= ?";
        try ( PreparedStatement ps = statementForVarArgs(QUERY , newEntity.getName() , id)){
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not update data to db");
        }
    }

    @Override
    public void delete(Integer id) {
        final String QUERY = "DELETE FROM majors WHERE id=?";
        try (PreparedStatement ps = statementForVarArgs(QUERY , id)){
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not update data to db");
        }

    }

    @Override
    public Major loadById(Integer id) {
        final String QUERY ="SELECT * FROM majors WHERE id = ?";
        try (PreparedStatement ps = statementForVarArgs(QUERY,id)){
            try (ResultSet resultSet = ps.executeQuery()) {
                Major major = null;
                while (resultSet.next()) {
                    major= entityByResultset(resultSet);
                }
                return major;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find data from db");
        }
    }

    @Override
    public List<Major> loadAll() {
        final String QUERY ="SELECT * FROM majors";
        try (PreparedStatement ps = connection.prepareStatement(QUERY);
             ResultSet resultSet = ps.executeQuery();){
            List<Major> majors = new ArrayList<>();
            while (resultSet.next()) {
                majors.add( entityByResultset(resultSet));
            }
            return majors;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find data from db");
        }
    }

    @Override
    public Major entityByResultset(ResultSet resultSet) throws SQLException {
        int majorId = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Major(majorId , name );
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
