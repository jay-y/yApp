package org.yapp.db;

import android.text.TextUtils;

import org.yapp.DbManager.DbOpenListener;
import org.yapp.DbManager.DbUpgradeListener;
import org.yapp.DbManager.TableCreateListener;

import java.io.File;

public class Config{
	private File dbDir;
    private String dbName = "y_utils.db"; // default db name
    private int dbVersion = 1;
    private boolean allowTransaction = true;
    private DbUpgradeListener dbUpgradeListener;
    private TableCreateListener tableCreateListener;
    private DbOpenListener dbOpenListener;

    public Config() {
    }

    public Config setDbDir(File dbDir) {
        this.dbDir = dbDir;
        return this;
    }

    public Config setDbName(String dbName) {
        if (!TextUtils.isEmpty(dbName)) {
            this.dbName = dbName;
        }
        return this;
    }

    public Config setDbVersion(int dbVersion) {
        this.dbVersion = dbVersion;
        return this;
    }

    public Config setAllowTransaction(boolean allowTransaction) {
        this.allowTransaction = allowTransaction;
        return this;
    }

    public Config setDbOpenListener(DbOpenListener dbOpenListener) {
        this.dbOpenListener = dbOpenListener;
        return this;
    }

    public Config setDbUpgradeListener(DbUpgradeListener dbUpgradeListener) {
        this.dbUpgradeListener = dbUpgradeListener;
        return this;
    }

    public Config setTableCreateListener(TableCreateListener tableCreateListener) {
        this.tableCreateListener = tableCreateListener;
        return this;
    }

    public File getDbDir() {
        return dbDir;
    }

    public String getDbName() {
        return dbName;
    }

    public int getDbVersion() {
        return dbVersion;
    }

    public boolean isAllowTransaction() {
        return allowTransaction;
    }

    public DbOpenListener getDbOpenListener() {
        return dbOpenListener;
    }

    public DbUpgradeListener getDbUpgradeListener() {
        return dbUpgradeListener;
    }

    public TableCreateListener getTableCreateListener() {
        return tableCreateListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Config Config = (Config) o;

        if (!dbName.equals(Config.dbName)) return false;
        return dbDir == null ? Config.dbDir == null : dbDir.equals(Config.dbDir);
    }

    @Override
    public int hashCode() {
        int result = dbName.hashCode();
        result = 31 * result + (dbDir != null ? dbDir.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.valueOf(dbDir) + "/" + dbName;
    }
}
