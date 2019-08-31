package atividade.mobile.tatiana.trabalhocontrolelivros.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class DatabaseUser {
    private static final String TABLE_USER = "users";
    private static final String TAG = "UserDB";

    // campos da tabela USUÁRIO
    private static final String TABLE_USER_COLUMN_ID = "id"; // PK
    private static final String TABLE_USER_COLUMN_NAME = "name";
    private static final String TABLE_USER_COLUMN_EMAIL = "email";
    private static final String TABLE_USER_COLUMN_USERNAME = "username";
    private static final String TABLE_USER_COLUMN_PASSWORD = "password";
    private static final String TABLE_USER_COLUMN_STANDARD_ICON = "standardIcon";

    private DatabaseHelper helper;
    private Context context;

    public DatabaseUser(Context context){
        this.context = context;
        helper = DatabaseHelper.getInstance(context);
    }

    public static final String getTableName(){
        return TABLE_USER;
    }

    public static final String createTable(){
        return "CREATE TABLE " + TABLE_USER +
                "(" +
                TABLE_USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_USER_COLUMN_NAME + " TEXT NOT NULL, " +
                TABLE_USER_COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, " +
                TABLE_USER_COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, " +
                TABLE_USER_COLUMN_PASSWORD + " TEXT NOT NULL, " +
                TABLE_USER_COLUMN_STANDARD_ICON + " TEXT" +
                ")";
    }

    public static final String dropTable(){
        return "DROP TABLE IF EXISTS " + TABLE_USER;
    }

    public static ContentValues initialize(){
        ContentValues values = new ContentValues();
        values.put(TABLE_USER_COLUMN_NAME, "Admin");
        values.put(TABLE_USER_COLUMN_USERNAME, "admin");
        values.put(TABLE_USER_COLUMN_EMAIL, "admin@gmail.com");
        values.put(TABLE_USER_COLUMN_PASSWORD, "1234");
        return values;
    }

    /*
     *   CRUD Usuário
     */

    public boolean insertUser(User user){
        ContentValues values = new ContentValues();
        values.put(TABLE_USER_COLUMN_NAME, user.getName());
        values.put(TABLE_USER_COLUMN_USERNAME, user.getUsername());
        values.put(TABLE_USER_COLUMN_EMAIL, user.getEmail());
        values.put(TABLE_USER_COLUMN_PASSWORD, user.getPassword());

        long i = helper.insert(values, TABLE_USER);

        if(i > 0){
            Log.i(TAG,"Sucesso ao inserir usuário no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao inserir usuário no banco de dados");
            return false;
        }
    }

    public boolean updateUser(User user){
        int r;
        ContentValues values = new ContentValues();

        values.put(TABLE_USER_COLUMN_NAME, user.getName());
        values.put(TABLE_USER_COLUMN_USERNAME, user.getUsername());
        values.put(TABLE_USER_COLUMN_EMAIL, user.getEmail());
        values.put(TABLE_USER_COLUMN_PASSWORD, user.getPassword());
        values.put(TABLE_USER_COLUMN_STANDARD_ICON, user.getStandardIcon());

        r = helper.update(values, TABLE_USER, TABLE_USER_COLUMN_ID, user.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao atualizar usuário no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao atualizar usuário no banco de dados");
            return false;
        }
    }

    public boolean deleteUser(User user){
        int r;

        r = helper.delete(TABLE_USER, TABLE_USER_COLUMN_ID, user.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao excluir usuário do banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao excluir usuário do banco de dados");
            return false;
        }
    }

    public User getUserById(int id){
        User user = null;
        Log.i(TAG, "Procurando usuário por id");
        Cursor cursor = helper.getById(TABLE_USER, TABLE_USER_COLUMN_ID, id);
        if(cursor.moveToFirst()){
            Log.i(TAG, "Usuário encontrado");
            String name = cursor.getString(cursor.getColumnIndex(TABLE_USER_COLUMN_NAME));
            String username = cursor.getString(cursor.getColumnIndex(TABLE_USER_COLUMN_USERNAME));
            String email = cursor.getString(cursor.getColumnIndex(TABLE_USER_COLUMN_EMAIL));
            String password = cursor.getString(cursor.getColumnIndex(TABLE_USER_COLUMN_PASSWORD));
            String standardIcon = cursor.getString(cursor.getColumnIndex(TABLE_USER_COLUMN_STANDARD_ICON));

            user =  new User(id, name, email, username, password, standardIcon);
        }else{
            Log.e(TAG, "Usuário não encontrado");
        }
        cursor.close();
        helper.closeDatabase();
        return  user;
    }

    public User getUserByUsername(String username){
        User user = null;
        Log.i(TAG, "Procurando usuário por username");
        Cursor cursor = helper.getByName(TABLE_USER, TABLE_USER_COLUMN_USERNAME, username);
        if(cursor.moveToFirst()){
            Log.i(TAG, "Usuário encontrado");
            int id = cursor.getInt(cursor.getColumnIndex(TABLE_USER_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(TABLE_USER_COLUMN_NAME));
            String email = cursor.getString(cursor.getColumnIndex(TABLE_USER_COLUMN_EMAIL));
            String password = cursor.getString(cursor.getColumnIndex(TABLE_USER_COLUMN_PASSWORD));
            String standardIcon = cursor.getString(cursor.getColumnIndex(TABLE_USER_COLUMN_STANDARD_ICON));
            user = new User(id, name, email, username, password, standardIcon);
        }else{
            Log.e(TAG, "Usuário não encontrado");
        }
        cursor.close();
        helper.closeDatabase();
        return  user;
    }
}
