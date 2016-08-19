package org.yapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.yapp.db.Config;
import org.yapp.db.Selector;
import org.yapp.db.sqlite.SqlInfo;
import org.yapp.db.sqlite.WhereBuilder;
import org.yapp.db.table.DbModel;
import org.yapp.db.table.TableEntity;
import org.yapp.ex.DbException;
import org.yapp.utils.KeyValue;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * ClassName: DbManager <br> 
 * Description: 数据库访问接口. <br> 
 * Date: 2015-12-3 上午10:30:03 <br> 
 */
public interface DbManager extends Closeable {

    Config getDaoConfig();

    SQLiteDatabase getDatabase();

    /**
     * 保存实体类或实体类的List到数据库,
     * 如果该类型的id是自动生成的, 则保存完后会给id赋值.
     *
     * @param entity
     * @return
     * @throws DbException
     */
    boolean saveBindingId(Object entity) throws DbException;

    /**
     * 保存或更新实体类或实体类的List到数据库, 根据id对应的数据是否存在.
     *
     * @param entity
     * @throws DbException
     */
    void saveOrUpdate(Object entity) throws DbException;

    /**
     * 保存实体类或实体类的List到数据库
     *
     * @param entity
     * @throws DbException
     */
    void save(Object entity) throws DbException;

    /**
     * 保存或更新实体类或实体类的List到数据库, 根据id和其他唯一索引判断数据是否存在.
     *
     * @param entity
     * @throws DbException
     */
    void replace(Object entity) throws DbException;

    ///////////// delete
    void deleteById(Class<?> entityType, Object idValue) throws DbException;

    void delete(Object entity) throws DbException;

    void delete(Class<?> entityType) throws DbException;

    int delete(Class<?> entityType, WhereBuilder whereBuilder) throws DbException;

    ///////////// update
    void update(Object entity, String... updateColumnNames) throws DbException;

    int update(Class<?> entityType, WhereBuilder whereBuilder, KeyValue... nameValuePairs) throws DbException;

    ///////////// find
    <T> T findById(Class<T> entityType, Object idValue) throws DbException;

    <T> T findFirst(Class<T> entityType) throws DbException;

    <T> List<T> findAll(Class<T> entityType) throws DbException;

    <T> Selector<T> selector(Class<T> entityType) throws DbException;

    DbModel findDbModelFirst(SqlInfo sqlInfo) throws DbException;

    List<DbModel> findDbModelAll(SqlInfo sqlInfo) throws DbException;

    ///////////// table

    /**
     * 获取表信息
     *
     * @param entityType
     * @param <T>
     * @return
     * @throws DbException
     */
    <T> TableEntity<T> getTable(Class<T> entityType) throws DbException;

    /**
     * 删除表
     *
     * @param entityType
     * @throws DbException
     */
    void dropTable(Class<?> entityType) throws DbException;

    /**
     * 添加一列,
     * 新的entityType中必须定义了这个列的属性.
     *
     * @param entityType
     * @param column
     * @throws DbException
     */
    void addColumn(Class<?> entityType, String column) throws DbException;

    ///////////// db

    /**
     * 删除库
     *
     * @throws DbException
     */
    void dropDb() throws DbException;

    /**
     * 关闭数据库,
     * xUtils对同一个库的链接是单实例的, 一般不需要关闭它.
     *
     * @throws IOException
     */
    void close() throws IOException;

    ///////////// custom
    int executeUpdateDelete(SqlInfo sqlInfo) throws DbException;

    int executeUpdateDelete(String sql) throws DbException;
    void execNonQuery(SqlInfo sqlInfo) throws DbException;

    void execNonQuery(String sql) throws DbException;

    Cursor execQuery(SqlInfo sqlInfo) throws DbException;

    Cursor execQuery(String sql) throws DbException;

    public interface DbOpenListener {
        void onDbOpened(DbManager db);
    }

    public interface DbUpgradeListener {
        void onUpgrade(DbManager db, int oldVersion, int newVersion);
    }

    public interface TableCreateListener {
        void onTableCreated(DbManager db, TableEntity<?> table);
    }
}
