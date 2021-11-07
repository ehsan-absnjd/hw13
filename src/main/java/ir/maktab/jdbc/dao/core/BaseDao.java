package ir.maktab.jdbc.dao.core;

import ir.maktab.jdbc.entity.base.BaseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// CRUD => Create, Read, Update, Delete
public interface BaseDao<T extends BaseEntity<ID>, ID extends Number> {
    void save(T entity);
    void update(ID id, T newEntity);
    void delete(ID id);
    T loadById(ID id);
    List<T> loadAll();
    T entityByResultset(ResultSet resultSet) throws SQLException;
}
