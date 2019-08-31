package atividade.mobile.tatiana.trabalhocontrolelivros.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Series;

public class DatabaseSeries {

    private static final String TAG = "SeriesDB";
    private static final String TABLE_SERIES = "series";
    // campos da tabela COLEÇÃO
    private static final String TABLE_SERIES_COLUMN_ID = "id"; // PK
    private static final String TABLE_SERIES_COLUMN_NAME = "name";

    private static final String[] COLUMNS = {TABLE_SERIES_COLUMN_ID, TABLE_SERIES_COLUMN_NAME};


    private DatabaseHelper helper;
    private Context context;

    public DatabaseSeries(Context context){
        this.context = context;
        helper = DatabaseHelper.getInstance(context);
    }

    public static final String getTableName(){
        return TABLE_SERIES;
    }

    public static final String createTable(){
        return "CREATE TABLE " + TABLE_SERIES +
                "(" +
                TABLE_SERIES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_SERIES_COLUMN_NAME + " TEXT" +
                ")";
    }

    public static final String dropTable(){
        return "DROP TABLE IF EXISTS " + TABLE_SERIES;
    }

    public static String getIdColumn() {
        return TABLE_SERIES_COLUMN_ID;
    }

    /*
     *   CRUD Coleção
     */

    public boolean insertSeries(Series series){
        ContentValues values = new ContentValues();
        values.put(TABLE_SERIES_COLUMN_NAME, series.getName());

        long i = helper.insert(values, TABLE_SERIES);

        if(i > 0){
            Log.i(TAG,"Sucesso ao inserir coleção no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao inserir coleção no banco de dados");
            return false;
        }
    }

    public boolean updateSeries(Series series){
        int r;
        ContentValues values = new ContentValues();

        values.put(TABLE_SERIES_COLUMN_NAME, series.getName());

        r = helper.update(values, TABLE_SERIES, TABLE_SERIES_COLUMN_ID, series.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao atualizar coleção no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao atualizar coleção no banco de dados");
            return false;
        }
    }

    public boolean deleteSeries(Series series){
        int r;

        deleteAllRelationsbySeries(series);

        r = helper.delete(TABLE_SERIES, TABLE_SERIES_COLUMN_ID, series.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao excluir coleção do banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao excluir coleção do banco de dados");
            return false;
        }
    }

    public void deleteAllRelationsbySeries(Series series){
        DatabaseSeriesRelations relationsDb = new DatabaseSeriesRelations(context);
        DatabaseBook bookDb = new DatabaseBook(context);
        List<Book> books = bookDb.getAllBooks_BySeries(series.getId());

        Log.i(TAG, "Excluindo relacionamentos com livros");
        for (Book book : books) {
            relationsDb.deleteSeriesRelationship(series, book);
        }
    }

    public Series getSeriesById(int id){
        Series series = null;
        Log.i(TAG, "Procurando coleção por id");
        Cursor cursor = helper.getById(TABLE_SERIES, TABLE_SERIES_COLUMN_ID, id);
        if(cursor.moveToFirst()){
            Log.i(TAG, "Coleção encontrada");
            String name = cursor.getString(cursor.getColumnIndex(TABLE_SERIES_COLUMN_NAME));
            series = new Series(id, name);
        }else{
            Log.e(TAG, "Coleção não encontrada");
        }
        helper.closeDatabase();
        cursor.close();
        return series;
    }

    private List<Series> listAllSeries(Cursor cursor){
        ArrayList<Series> series = new ArrayList<Series>();

        try{
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(TABLE_SERIES_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(TABLE_SERIES_COLUMN_NAME));

                series.add(new Series(id, name));
            }
        }finally {
            cursor.close();
        }
        helper.closeDatabase();
        return series;
    }

    public List<Series> getAllSeries(){
        Log.i(TAG, "Buscando todas as coleções");
        Cursor cursor = helper.getAllByQuery(TABLE_SERIES, COLUMNS ,null, null, null, null, TABLE_SERIES_COLUMN_ID);
        return listAllSeries(cursor);
    }

    public List<Series> getAllSeries_OrderByName(){
        Log.i(TAG, "Buscando todas as coleções e ordenando pelo nome");
        Cursor cursor = helper.getAllByQuery(TABLE_SERIES, COLUMNS ,null, null, null, null, TABLE_SERIES_COLUMN_NAME +" ASC");
        return listAllSeries(cursor);
    }

    public List<Series> getAllSeries_WithNameLike(String name){
        Log.i(TAG, "Buscando todas as coleções");
        String[] args = {"%"+name+"%"};
        Cursor cursor = helper.getAllByQuery(TABLE_SERIES, COLUMNS,TABLE_SERIES_COLUMN_NAME + " like ? ", args, null, null, TABLE_SERIES_COLUMN_ID);
        return listAllSeries(cursor);
    }

    public List<Series> getAllSeries_OrderByName_WithNameLike(String name){
        Log.i(TAG, "Buscando todas as coleções e ordenando pelo nome");
        String[] args = {"%"+name+"%"};
        Cursor cursor = helper.getAllByQuery(TABLE_SERIES, COLUMNS, TABLE_SERIES_COLUMN_NAME + " like ? ", args, null, null, TABLE_SERIES_COLUMN_NAME +" ASC");
        return listAllSeries(cursor);
    }

    public List<Series> getAllSeriesByBook(Book book){
        String query = "SELECT S."+TABLE_SERIES_COLUMN_ID + ", S." + TABLE_SERIES_COLUMN_NAME +
                " from " + TABLE_SERIES + " AS S" +
                " JOIN " + DatabaseSeriesRelations.getTableName() + " AS R ON S." + TABLE_SERIES_COLUMN_ID +
                " = R." + DatabaseSeriesRelations.getSeriesColumn() +
                " WHERE R." + DatabaseSeriesRelations.getBookColumn() + " =?;";
        String[] args = {String.valueOf(book.getId())};
        Cursor cursor = helper.getAllByRawQuery(query, args);

        return listAllSeries(cursor);
    }


}
