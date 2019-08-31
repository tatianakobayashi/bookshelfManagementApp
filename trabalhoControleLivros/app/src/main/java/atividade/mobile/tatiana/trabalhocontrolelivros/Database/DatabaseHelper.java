package atividade.mobile.tatiana.trabalhocontrolelivros.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Informações do banco
    private SQLiteDatabase database;
    private static DatabaseHelper singleInstance = null;
    private static final String DATABASE_NAME = "book_database.db";
    private static final int DATABASE_VERSION = 9;

    private static final String TAG = "DBHelper";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseAuthor.createTable());
        db.execSQL(DatabaseBook.createTable());
        db.execSQL(DatabaseSeries.createTable());
        db.execSQL(DatabasePublisher.createTable());
        db.execSQL(DatabaseSeriesRelations.createTable());
        db.execSQL(DatabaseAuthorRelations.createTable());
        db.execSQL(DatabaseStatus.createTable());
        db.execSQL(DatabaseUser.createTable());
        db.execSQL(DatabaseExemplar.createTable());

        Log.i(TAG, "Criando banco de dados");
        this.database = db;

        initialize(db);
    }

    private void initialize(SQLiteDatabase db){
        Log.i(TAG, "Inicializando banco de dados");
        for (ContentValues values: DatabaseStatus.initialize()) {
            db.insert(DatabaseStatus.getTableName(), null, values);
        }

        db.insert(DatabaseUser.getTableName(), null, DatabaseUser.initialize());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            Log.i(TAG, "Fazendo upgrade do banco de dados");

            db.execSQL(DatabaseExemplar.dropTable());
            db.execSQL(DatabaseSeriesRelations.dropTable());
            db.execSQL(DatabaseAuthorRelations.dropTable());
            db.execSQL(DatabaseSeries.dropTable());
            db.execSQL(DatabaseBook.dropTable());
            db.execSQL(DatabaseStatus.dropTable());
            db.execSQL(DatabaseAuthor.dropTable());
            db.execSQL(DatabaseUser.dropTable());
            db.execSQL(DatabasePublisher.dropTable());

            this.database = db;
            this.onCreate(db);
        }
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        Log.i(TAG, "Passando instância do banco de dados");
        if(singleInstance == null){
            singleInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return singleInstance;
    }

    /*
     *  CRUD Base
     */

    public long insert(ContentValues values, String tableName){
        database = this.getWritableDatabase();

        long i = database.insert(tableName, null, values);

        database.close();

        return i;
    }

    public int update(ContentValues values, String tableName, String idColumn, int id){
        int rowsUpdated;
        database = this.getWritableDatabase();

        String[] args = {String.valueOf(id)};

        rowsUpdated = database.update(tableName, values, idColumn + "=?", args);
        database.close();

        return rowsUpdated;
    }

    public int delete(String tableName, String idColumn, int id){
        int rowsDeleted;
        database = this.getWritableDatabase();

        String[] args = {String.valueOf(id)};

        rowsDeleted = database.delete(tableName, idColumn + "=?", args);
        database.close();

        return rowsDeleted;
    }

    public int updateRelationshipTable(ContentValues values, String tableName, String idColumnTableA, String idColumnTableB, int idA, int idB){
        int rowsUpdated;
        database = this.getWritableDatabase();

        String[] args = {String.valueOf(idA), String.valueOf(idB)};

        rowsUpdated = database.update(tableName, values, idColumnTableA + "=? and " + idColumnTableB + "=?", args);
        database.close();

        return rowsUpdated;
    }

    public int deleteRelationshipTable(String tableName,String idColumnTableA, String idColumnTableB, int idA, int idB){
        int rowsDeleted;
        database = this.getWritableDatabase();

        String[] args = {String.valueOf(idA), String.valueOf(idB)};

        rowsDeleted = database.delete(tableName, idColumnTableA + "=? and " + idColumnTableB + "=?", args);
        database.close();

        return rowsDeleted;
    }

    /*
     *  Get By Id
     */

    public Cursor getById(String tableName, String column, int id){
        database = this.getReadableDatabase();
        // String query = "SELECT * FROM " + tableName + " WHERE " + column + " = " + id + ";";
        String args[] = new String[]{String.valueOf(id)};
        // return database.rawQuery(query,null);
        return database.query(tableName, null, column + " =?", args, null, null, column);
    }

    public Cursor getByName(String tableName, String column, String name){
        database = this.getReadableDatabase();
        // String query = "SELECT * FROM " + tableName + " WHERE " + column + " = " + name + ";";
        String args[] = new String[]{name};
        // return database.rawQuery(query,null);
        return database.query(tableName, null, column + " =?", args, null, null, column);
    }

    public void closeDatabase(){
        database.close();
    }



    /*
     * Get All
     */

    public Cursor getAllByRawQuery(String query, String[] args){
        database = this.getReadableDatabase();
        return database.rawQuery(query, args);
    }

    public Cursor getAllByQuery(String tableName, String[] columns, String selection, String[] args, String group, String having, String order){
        database = this.getReadableDatabase();
        return database.query(tableName, columns, selection, args, group, having, order);
    }

}