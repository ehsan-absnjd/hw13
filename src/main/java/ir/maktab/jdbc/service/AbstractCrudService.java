package ir.maktab.jdbc.service;

import ir.maktab.jdbc.dao.core.BaseDao;
import ir.maktab.jdbc.entity.base.BaseEntity;
import ir.maktab.jdbc.exception.DataNotFoundException;
import ir.maktab.jdbc.exception.ModificationDataException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AbstractCrudService<T extends BaseEntity<ID>, ID extends Number> {
    private BaseDao<T, ID> baseDao;

    public void saveOrUpdate(T entity) {
        try {
            baseDao.startTransaction();
            if (entity.getId() == null) {
                baseDao.save(entity);
            } else {
                baseDao.update(entity.getId(), entity);
            }
            baseDao.commit();
        }catch (DataNotFoundException | ModificationDataException e){
            try {
                baseDao.rollBack();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            try {
                baseDao.rollBack();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println("sql error");
        }
    }

    public void deleteById(ID id){
        try {
            baseDao.startTransaction();
            baseDao.delete(id);
            baseDao.commit();
        }catch (DataNotFoundException | ModificationDataException e){
            try {
                baseDao.rollBack();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            try {
                baseDao.rollBack();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println("sql error");
        }
    }

    public Optional<T> loadById(ID id){
        Optional<T> result = Optional.empty();
        try {
            result=Optional.of( baseDao.loadById(id));
        }catch (DataNotFoundException | ModificationDataException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public Optional<List<T>> loadAall(){
        Optional<List<T>> results = Optional.empty();
        try {
            results=Optional.of( baseDao.loadAll ());
        }catch (DataNotFoundException | ModificationDataException e){
            System.out.println(e.getMessage());
        }
        return results;
    }

    public void setBaseDao(BaseDao<T, ID> baseDao) {
        this.baseDao = baseDao;
    }

    public BaseDao<T, ID> getBaseDao() {
        return baseDao;
    }
}
