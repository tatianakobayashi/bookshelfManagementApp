package atividade.mobile.tatiana.trabalhocontrolelivros.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Author;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;

public class DatabaseAuthor {

    private static final String TABLE_AUTHOR = "authors";
    private static final String TAG = "AuthorDB";
    // campos da tabelas AUTOR
    private static final String TABLE_AUTHOR_COLUMN_ID = "id"; // PK
    private static final String TABLE_AUTHOR_COLUMN_NAME = "name";

    private static final String[] COLUMNS = {TABLE_AUTHOR_COLUMN_ID, TABLE_AUTHOR_COLUMN_NAME};

    private DatabaseHelper helper;
    private Context context;

    public DatabaseAuthor(Context context){
        this.context = context;
        helper = DatabaseHelper.getInstance(context);
    }

    public static String getTableName(){
        return TABLE_AUTHOR;
    }


    public static String createTable(){
        return "CREATE TABLE " + TABLE_AUTHOR +
                "(" +
                TABLE_AUTHOR_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_AUTHOR_COLUMN_NAME + " TEXT" +
                ")";
    }

    public static String dropTable(){
        return "DROP TABLE IF EXISTS " + TABLE_AUTHOR;
    }

    public static String getIdColumn() {
        return TABLE_AUTHOR_COLUMN_ID;
    }

    /*
     *  CRUD Autor
     */

    public boolean insertAuthor(Author author){
        ContentValues values = new ContentValues();
        values.put(TABLE_AUTHOR_COLUMN_NAME, author.getName());

        long i = helper.insert(values, TABLE_AUTHOR);

        if(i > 0){
            Log.i(TAG,"Sucesso ao inserir autor no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao inserir autor no banco de dados");
            return false;
        }
    }

    public boolean updateAuthor(Author author){
        int r;
        ContentValues values = new ContentValues();

        values.put(TABLE_AUTHOR_COLUMN_NAME, author.getName());

        r = helper.update(values, TABLE_AUTHOR, TABLE_AUTHOR_COLUMN_ID, author.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao atualizar autor no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao atualizar autor no banco de dados");
            return false;
        }
    }

    public boolean deleteAuthor(Author author){
        int r;

        deleteAllRelationsbyAuthor(author);

        r = helper.delete(TABLE_AUTHOR, TABLE_AUTHOR_COLUMN_ID, author.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao excluir autor do banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao excluir autor do banco de dados");
            return false;
        }
    }

    public void deleteAllRelationsbyAuthor(Author author){
        DatabaseBook bookDb = new DatabaseBook(context);
        DatabaseAuthorRelations relationDb = new DatabaseAuthorRelations(context);
        List<Book> books = bookDb.getAllBooks_ByAuthor(author.getId());

        Log.i(TAG,"Excluindo relacionamentos com livros");
        for (Book book : books) {
            relationDb.deleteAuthorRelationship(author, book);
        }
    }

    public Author getAuthorById(int id){
        Author author = null;
        Log.i(TAG,"Procurando autor por id");
        Cursor cursor = helper.getById(TABLE_AUTHOR, TABLE_AUTHOR_COLUMN_ID, id);
        if(cursor.moveToFirst()){
            Log.i(TAG,"Autor encontrado");
            String name = cursor.getString(cursor.getColumnIndex(TABLE_AUTHOR_COLUMN_NAME));
            author = new Author(id, name);
        }else{
            Log.e(TAG,"Autor não encontrado");
        }
        cursor.close();
        helper.closeDatabase();
        return author;
    }

    private List<Author> listAllAuthors(Cursor cursor){
        ArrayList<Author> authors = new ArrayList<Author>();

        Author author = null;

        try{
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(TABLE_AUTHOR_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(TABLE_AUTHOR_COLUMN_NAME));
                author = new Author(id, name);

                authors.add(author);
            }
        }finally {
            cursor.close();
        }
        helper.closeDatabase();
        return authors;
    }

    public List<Author> getAllAuthors(){
        Log.i(TAG, "Buscando todos os autores");
        Cursor cursor = helper.getAllByQuery(TABLE_AUTHOR, COLUMNS, null, null, null, null, TABLE_AUTHOR_COLUMN_ID);
        return listAllAuthors(cursor);
    }

    public List<Author> getAllAuthorsOrderByName(){
        Log.i(TAG, "Buscando todos os autores e ordenando por nome");
        Cursor cursor = helper.getAllByQuery(TABLE_AUTHOR, COLUMNS, null, null, null, null, TABLE_AUTHOR_COLUMN_NAME + " ASC");
        return listAllAuthors(cursor);
    }

    public List<Author> getAllAuthors_WithNameLike(String name){
        String[] args = {"%" + name + "%"};

        Log.i(TAG, "Buscando todos os autores com nome que contenha " + name );
        Cursor cursor = helper.getAllByQuery(TABLE_AUTHOR, COLUMNS, TABLE_AUTHOR_COLUMN_NAME + " like ? ",
                args, null, null, TABLE_AUTHOR_COLUMN_ID);
        return listAllAuthors(cursor);
    }

    public List<Author> getAllAuthors_OrderByName_WithNameLike(String name){
        String[] args = {"%" + name + "%"};

        Log.i(TAG, "Buscando todos os autores com nome que contenha " + name + " e ordenando por nome");
        Cursor cursor = helper.getAllByQuery(TABLE_AUTHOR, COLUMNS, TABLE_AUTHOR_COLUMN_NAME + " like ? ",
                args, null, null, TABLE_AUTHOR_COLUMN_NAME + " ASC");
        return listAllAuthors(cursor);
    }

    public List<Author> getAllAuthors_ByBook(Book book){

        Log.i(TAG, "Buscando todos os autores relacionados com o livro " + book.getTitle());
        String query = "SELECT A."+ TABLE_AUTHOR_COLUMN_ID + ", A." + TABLE_AUTHOR_COLUMN_NAME +
                " FROM " + TABLE_AUTHOR + " AS A JOIN " + DatabaseAuthorRelations.getTableName() + " AS R " +
                " ON A." + TABLE_AUTHOR_COLUMN_ID + " = R." + DatabaseAuthorRelations.getAuthorColumn() +
                " WHERE R." + DatabaseAuthorRelations.getBookColumn() + " =?;";
        String[] args = {String.valueOf(book.getId())};
        Cursor cursor = helper.getAllByRawQuery(query, args);

        return listAllAuthors(cursor);
    }
}
