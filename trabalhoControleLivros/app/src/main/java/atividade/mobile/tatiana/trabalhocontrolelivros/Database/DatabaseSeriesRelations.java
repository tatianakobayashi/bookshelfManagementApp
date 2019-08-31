package atividade.mobile.tatiana.trabalhocontrolelivros.Database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Series;

public class DatabaseSeriesRelations {
    private static final String TAG = "SeriesRelationsDB";
    private static final String TABLE_SERIES_RELATIONS = "seriesRelations";
    // campos da tabela de relacionamento LIVRO-COLEÇÃO
    private static final String TABLE_SERIES_RELATIONS_COLUMN_SERIES_ID = "seriesId";  // FK
    private static final String TABLE_SERIES_RELATIONS_COLUMN_BOOK_ID = "bookId"; // FK

    private DatabaseHelper helper;
    private Context context;

    public DatabaseSeriesRelations(Context context){
        this.context = context;
        helper = DatabaseHelper.getInstance(context);
    }

    public static final String getTableName(){
        return TABLE_SERIES_RELATIONS;
    }
    public static final String getSeriesColumn(){
        return TABLE_SERIES_RELATIONS_COLUMN_SERIES_ID;
    }
    public static final String getBookColumn(){
        return TABLE_SERIES_RELATIONS_COLUMN_BOOK_ID;
    }

    public static final String createTable(){
        return "CREATE TABLE " + TABLE_SERIES_RELATIONS +
                "(" +
                TABLE_SERIES_RELATIONS_COLUMN_BOOK_ID + " INTEGER REFERENCES " + DatabaseBook.getTableName() + ", " +
                TABLE_SERIES_RELATIONS_COLUMN_SERIES_ID + " INTEGER REFERENCES " + DatabaseSeries.getTableName() + ", " +
                "PRIMARY KEY (" + TABLE_SERIES_RELATIONS_COLUMN_BOOK_ID + ", " + TABLE_SERIES_RELATIONS_COLUMN_SERIES_ID + ")" +
                ")";
    }

    public static final String dropTable(){
        return "DROP TABLE IF EXISTS " + TABLE_SERIES_RELATIONS;
    }

    /*
     *   CRUD Relacionamento Coleção-Livro
     */

    public boolean insertSeriesRelationship(Series series, Book book){
        ContentValues values = new ContentValues();
        values.put(TABLE_SERIES_RELATIONS_COLUMN_SERIES_ID, series.getId());
        values.put(TABLE_SERIES_RELATIONS_COLUMN_BOOK_ID, book.getId());

        long i = helper.insert(values, TABLE_SERIES_RELATIONS);

        if(i > 0){
            Log.i(TAG,"Sucesso ao inserir relacionamento livro-coleção no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao inserir relacionamento livro-coleção no banco de dados");
            return false;
        }
    }

    public boolean updateSeriesRelationship(Series series, Book book){
        int r;
        ContentValues values = new ContentValues();

        values.put(TABLE_SERIES_RELATIONS_COLUMN_SERIES_ID, series.getId());
        values.put(TABLE_SERIES_RELATIONS_COLUMN_BOOK_ID, book.getId());

        r = helper.updateRelationshipTable(values, TABLE_SERIES_RELATIONS, TABLE_SERIES_RELATIONS_COLUMN_BOOK_ID, TABLE_SERIES_RELATIONS_COLUMN_SERIES_ID, book.getId(), series.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao atualizar relacionamento livro-coleção no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao atualizar relacionamento livro-coleção no banco de dados");
            return false;
        }
    }

    public boolean deleteSeriesRelationship(Series series, Book book){
        int r;

        r = helper.deleteRelationshipTable(TABLE_SERIES_RELATIONS, TABLE_SERIES_RELATIONS_COLUMN_BOOK_ID, TABLE_SERIES_RELATIONS_COLUMN_SERIES_ID, book.getId(), series.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao excluir relacionamento livro-coleção do banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao excluir relacionamento livro-coleção do banco de dados");
            return false;
        }
    }


}
