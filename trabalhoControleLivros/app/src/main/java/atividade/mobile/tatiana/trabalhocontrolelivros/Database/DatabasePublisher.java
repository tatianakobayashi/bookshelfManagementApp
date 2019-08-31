package atividade.mobile.tatiana.trabalhocontrolelivros.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Publisher;

public class DatabasePublisher {
    private static final String TAG = "PublishersDB";
    private static final String TABLE_PUBLISHER = "publishers";
    // campos da tabelas EDITORA
    private static final String TABLE_PUBLISHER_COLUMN_ID = "id"; // PK
    private static final String TABLE_PUBLISHER_COLUMN_NAME = "name";

    private static final String[] COLUMNS = {TABLE_PUBLISHER_COLUMN_ID, TABLE_PUBLISHER_COLUMN_NAME};

    private DatabaseHelper helper;
    private Context context;

    public DatabasePublisher(Context context){
        this.context = context;
        helper = DatabaseHelper.getInstance(context);
    }

    public static final String getTableName(){
        return TABLE_PUBLISHER;
    }

    public static final String createTable(){
        return "CREATE TABLE " + TABLE_PUBLISHER +
                "(" +
                TABLE_PUBLISHER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_PUBLISHER_COLUMN_NAME + " TEXT" +
                ")";
    }

    public static final String dropTable(){
        return "DROP TABLE IF EXISTS " + TABLE_PUBLISHER;
    }

    /*
     *   CRUD Publisher/Editor
     */

    public boolean insertPublisher(Publisher publisher){
        ContentValues values = new ContentValues();
        values.put(TABLE_PUBLISHER_COLUMN_NAME, publisher.getName());

        long i = helper.insert(values, TABLE_PUBLISHER);

        if(i > 0){
            Log.i(TAG,"Sucesso ao inserir editora no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao inserir editora no banco de dados");
            return false;
        }
    }

    public boolean updatePublisher(Publisher publisher){
        int r;
        ContentValues values = new ContentValues();

        values.put(TABLE_PUBLISHER_COLUMN_NAME, publisher.getName());

        r = helper.update(values, TABLE_PUBLISHER, TABLE_PUBLISHER_COLUMN_ID, publisher.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao atualizar editora no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao atualizar editora no banco de dados");
            return false;
        }
    }

    public boolean deletePublisher(Publisher publisher){
        int r;

        r = helper.delete(TABLE_PUBLISHER, TABLE_PUBLISHER_COLUMN_ID, publisher.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao excluir editora do banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao excluir editora do banco de dados");
            return false;
        }
    }

    public Publisher getPublisherById(int id){
        Publisher publisher = null;
        Log.i(TAG, "Procurando editora por id");
        Cursor cursor = helper.getById(TABLE_PUBLISHER, TABLE_PUBLISHER_COLUMN_ID, id);
        if(cursor.moveToFirst()){
            Log.i(TAG, "Editora encontrada");
            String name = cursor.getString(cursor.getColumnIndex(TABLE_PUBLISHER_COLUMN_NAME));
            publisher = new Publisher(id, name);
        }else{
            Log.e(TAG, "Editora não encontrada");
        }
        cursor.close();
        helper.closeDatabase();
        return  publisher;
    }

    private List<Publisher> listAllPublishers(Cursor cursor){
        ArrayList<Publisher> publishers = new ArrayList<>();
        try{
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(TABLE_PUBLISHER_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(TABLE_PUBLISHER_COLUMN_NAME));

                publishers.add(new Publisher(id, name));
            }
        }finally {
            cursor.close();
        }
        helper.closeDatabase();
        return publishers;
    }

    public List<Publisher> getAllPublishers(){
        Log.i(TAG, "Buscando todas as editoras");
        Cursor cursor = helper.getAllByQuery(TABLE_PUBLISHER, COLUMNS ,null, null,
                null, null, TABLE_PUBLISHER_COLUMN_ID);

        return listAllPublishers(cursor);
    }

    public List<Publisher> getAllPublishers_OrderByName(){
        Log.i(TAG, "Buscando todas as editoras e ordenando pelo nome");
        Cursor cursor = helper.getAllByQuery(TABLE_PUBLISHER, COLUMNS ,null, null,
                null, null, TABLE_PUBLISHER_COLUMN_NAME +" ASC");

        return listAllPublishers(cursor);
    }


    public List<Publisher> getAllPublishers_WithNameLike(String name){
        String[] args = {"%" + name + "%"};

        Log.i(TAG, "Buscando todas as editoras com nome que contenha "+ name);
        Cursor cursor = helper.getAllByQuery(TABLE_PUBLISHER, COLUMNS ,TABLE_PUBLISHER_COLUMN_NAME + " like ?", args,
                null, null, TABLE_PUBLISHER_COLUMN_ID);

        return listAllPublishers(cursor);
    }

    public List<Publisher> getAllPublishers_OrderByName_WithNameLike(String name){
        String[] args = {"%" + name + "%"};

        Log.i(TAG, "Buscando todas as editoras com nome que contenha "+ name+" e ordenando por nome");
        Cursor cursor = helper.getAllByQuery(TABLE_PUBLISHER, COLUMNS ,TABLE_PUBLISHER_COLUMN_NAME + " like ?", args,
                null, null, TABLE_PUBLISHER_COLUMN_NAME +" ASC");

        return listAllPublishers(cursor);
    }
}
