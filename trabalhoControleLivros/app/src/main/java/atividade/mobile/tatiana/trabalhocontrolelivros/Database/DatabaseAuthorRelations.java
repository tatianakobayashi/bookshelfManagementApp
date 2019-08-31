package atividade.mobile.tatiana.trabalhocontrolelivros.Database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Author;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;

public class DatabaseAuthorRelations {
    private static final String TAG = "AuthorRelationsDB";
    private static final String TABLE_AUTHOR_RELATIONS = "authorRelations";
    // campos da tabela de relacionamento LIVRO-AUTOR
    private static final String TABLE_AUTHOR_RELATIONS_COLUMN_AUTHOR_ID = "authorId";  // FK
    private static final String TABLE_AUTHOR_RELATIONS_COLUMN_BOOK_ID = "bookId"; // FK

    private DatabaseHelper helper;
    private Context context;

    public DatabaseAuthorRelations(Context context){
        this.context = context;
        helper = DatabaseHelper.getInstance(context);
    }

    static String getTableName(){
        return TABLE_AUTHOR_RELATIONS;
    }
    static String getAuthorColumn(){
        return TABLE_AUTHOR_RELATIONS_COLUMN_AUTHOR_ID;
    }
    static String getBookColumn(){
        return TABLE_AUTHOR_RELATIONS_COLUMN_BOOK_ID;
    }

    static String createTable(){
        return "CREATE TABLE " + TABLE_AUTHOR_RELATIONS +
                "(" +
                TABLE_AUTHOR_RELATIONS_COLUMN_BOOK_ID + " INTEGER REFERENCES " + DatabaseBook.getTableName() + ", " +
                TABLE_AUTHOR_RELATIONS_COLUMN_AUTHOR_ID + " INTEGER REFERENCES " + DatabaseAuthor.getTableName() + ", " +
                "PRIMARY KEY (" + TABLE_AUTHOR_RELATIONS_COLUMN_BOOK_ID + ", " + TABLE_AUTHOR_RELATIONS_COLUMN_AUTHOR_ID + ")" +
                ")";
    }

    static String dropTable(){
        return "DROP TABLE IF EXISTS " + TABLE_AUTHOR_RELATIONS;
    }

    /*
     *   CRUD Relacionamento Autor-Livro
     */

    public boolean insertAuthorRelationship(Author author, Book book){
        ContentValues values = new ContentValues();
        values.put(TABLE_AUTHOR_RELATIONS_COLUMN_AUTHOR_ID, author.getId());
        values.put(TABLE_AUTHOR_RELATIONS_COLUMN_BOOK_ID, book.getId());

        long i = helper.insert(values, TABLE_AUTHOR_RELATIONS);

        if(i > 0){
            Log.i(TAG,"Sucesso ao inserir relacionamento livro-autor no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao inserir relacionamento livro-autor no banco de dados");
            return false;
        }
    }

    public boolean updateAuthorRelationship(Author author, Book book){
        int r;
        ContentValues values = new ContentValues();

        values.put(TABLE_AUTHOR_RELATIONS_COLUMN_AUTHOR_ID, author.getId());
        values.put(TABLE_AUTHOR_RELATIONS_COLUMN_BOOK_ID, book.getId());

        r = helper.updateRelationshipTable(values, TABLE_AUTHOR_RELATIONS, TABLE_AUTHOR_RELATIONS_COLUMN_BOOK_ID, TABLE_AUTHOR_RELATIONS_COLUMN_AUTHOR_ID, book.getId(), author.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao atualizar relacionamento livro-autor no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao atualizar relacionamento livro-autor no banco de dados");
            return false;
        }
    }

    public boolean deleteAuthorRelationship(Author author, Book book){
        int r;

        r = helper.deleteRelationshipTable(TABLE_AUTHOR_RELATIONS, TABLE_AUTHOR_RELATIONS_COLUMN_BOOK_ID, TABLE_AUTHOR_RELATIONS_COLUMN_AUTHOR_ID, book.getId(), author.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao excluir relacionamento livro-autor do banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao excluir relacionamento livro-autor do banco de dados");
            return false;
        }
    }


}
