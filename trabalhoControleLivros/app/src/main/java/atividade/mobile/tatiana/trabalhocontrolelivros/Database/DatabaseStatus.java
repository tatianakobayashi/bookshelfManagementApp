package atividade.mobile.tatiana.trabalhocontrolelivros.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Status;

public class DatabaseStatus {
    private static final String TABLE_STATUS = "status";
    private static final String TAG = "StatusDB";
    // campos da tabela STATUS
    private static final String TABLE_STATUS_COLUMN_ID = "id"; // PK
    private static final String TABLE_STATUS_COLUMN_NAME = "name";

    private static final String[] COLUMNS = {TABLE_STATUS_COLUMN_ID, TABLE_STATUS_COLUMN_NAME};

    private DatabaseHelper helper;
    private Context context;

    public DatabaseStatus(Context context){
        this.context = context;
        helper = DatabaseHelper.getInstance(context);
    }

    public static final String getTableName(){
        return TABLE_STATUS;
    }

    public static final String createTable(){
        return "CREATE TABLE " + TABLE_STATUS +
                "(" +
                TABLE_STATUS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_STATUS_COLUMN_NAME + " TEXT" +
                ")";
    }

    public static final String dropTable(){
        return "DROP TABLE IF EXISTS " + TABLE_STATUS;
    }

    public static List<ContentValues> initialize(){
        List<ContentValues> values = new ArrayList<>();
        ContentValues v = new ContentValues();
        v.put(TABLE_STATUS_COLUMN_NAME, "Desejado");
        values.add(v);

        v = new ContentValues();
        v.put(TABLE_STATUS_COLUMN_NAME, "Lendo");
        values.add(v);

        v = new ContentValues();
        v.put(TABLE_STATUS_COLUMN_NAME, "Arquivado");
        values.add(v);

        v = new ContentValues();
        v.put(TABLE_STATUS_COLUMN_NAME, "Emprestado");
        values.add(v);

        return values;
    }

    /*
     *   CRUD Status
     */

    public boolean insertStatus(Status status){
        ContentValues values = new ContentValues();
        values.put(TABLE_STATUS_COLUMN_NAME, status.getName());

        long i = helper.insert(values, TABLE_STATUS);

        if(i > 0){
            Log.i(TAG,"Sucesso ao inserir status no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao inserir status no banco de dados");
            return false;
        }
    }

    public boolean updateStatus(Status status){
        int r;
        ContentValues values = new ContentValues();

        values.put(TABLE_STATUS_COLUMN_NAME, status.getName());

        r = helper.update(values, TABLE_STATUS, TABLE_STATUS_COLUMN_ID, status.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao atualizar status no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao atualizar status no banco de dados");
            return false;
        }
    }

    public boolean deleteStatus(Status status){
        int r;

        r = helper.delete(TABLE_STATUS, TABLE_STATUS_COLUMN_ID, status.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao excluir status do banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao excluir status do banco de dados");
            return false;
        }
    }

    public Status getStatusById(int id){
        Status status = null;
        Log.i(TAG,"Procurando status por id");
        Cursor cursor = helper.getById(TABLE_STATUS, TABLE_STATUS_COLUMN_ID, id);
        if(cursor.moveToFirst()){
            Log.i(TAG,"Status encontrado");
            String name = cursor.getString(cursor.getColumnIndex(TABLE_STATUS_COLUMN_NAME));
            status = new Status(id, name);
        }else{
            Log.e(TAG,"Status não encontrado");
        }
        cursor.close();
        helper.closeDatabase();
        return  status;
    }

    private List<Status> listAllStatus(Cursor cursor){
        ArrayList<Status> statuses = new ArrayList<Status>();

        try{
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(TABLE_STATUS_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(TABLE_STATUS_COLUMN_NAME));

                statuses.add(new Status(id, name));
            }
        }finally {
            cursor.close();
        }
        helper.closeDatabase();
        return statuses;
    }

    public List<Status> getAllStatus(){
        Log.i(TAG,"Buscando todos os status");
        Cursor cursor = helper.getAllByQuery(TABLE_STATUS, COLUMNS ,null, null,
                null, null, TABLE_STATUS_COLUMN_ID);
        return listAllStatus(cursor);
    }

    public List<Status> getAllStatus_OrderByName(){
        Log.i(TAG,"Buscando todos os status e ordenando por nome");
        Cursor cursor = helper.getAllByQuery(TABLE_STATUS, COLUMNS ,null, null,
                null, null, TABLE_STATUS_COLUMN_NAME +" ASC");
        return listAllStatus(cursor);
    }

    public List<Status> getAllStatus_WithNameLike(String name){
        String[] args = {"%" + name + "%"};
        Cursor cursor = helper.getAllByQuery(TABLE_STATUS, COLUMNS ,TABLE_STATUS_COLUMN_NAME + " like ? ", args,
                null, null, TABLE_STATUS_COLUMN_ID);
        return listAllStatus(cursor);
    }

    public List<Status> getAllStatus_OrderByName_WithNameLike(String name){
        Log.i(TAG,"Buscando todos os status com nome que contenha "+ name + " e ordenando por nome");
        String[] args = {"%" + name + "%"};
        Cursor cursor = helper.getAllByQuery(TABLE_STATUS, COLUMNS ,TABLE_STATUS_COLUMN_NAME + " like ? ", args,
                null, null, TABLE_STATUS_COLUMN_NAME +" ASC");
        return listAllStatus(cursor);
    }
}
