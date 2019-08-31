package atividade.mobile.tatiana.trabalhocontrolelivros.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Author;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Series;

public class DatabaseBook {
    private static final String TABLE_BOOK = "books";
    private static final String TAG = "BookDB";

    // campos da tabela LIVRO
    private static final String TABLE_BOOK_COLUMN_ID = "id"; // PK
    private static final String TABLE_BOOK_COLUMN_TITLE = "title";
    private static final String TABLE_BOOK_COLUMN_GENRE = "genre";

    private static final String[] COLUMNS = {TABLE_BOOK_COLUMN_ID, TABLE_BOOK_COLUMN_TITLE, TABLE_BOOK_COLUMN_GENRE};

    private DatabaseHelper helper;
    private Context context;

    public DatabaseBook(Context context){
        this.context = context;
        helper = DatabaseHelper.getInstance(context);
    }

    public static String getTableName(){
        return TABLE_BOOK;
    }

    public static String createTable(){
        return "CREATE TABLE " + TABLE_BOOK +
                "(" +
                TABLE_BOOK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_BOOK_COLUMN_TITLE + " TEXT, " +
                TABLE_BOOK_COLUMN_GENRE + " TEXT" +
                ")";
    }

    public static String dropTable(){
        return "DROP TABLE IF EXISTS " + TABLE_BOOK;
    }

    public static String getIdColumn() {
        return TABLE_BOOK_COLUMN_ID;
    }

    /*
     *  CRUD Livro
     */

    public boolean insertBook(Book book){
        ContentValues values = new ContentValues();
        values.put(TABLE_BOOK_COLUMN_TITLE, book.getTitle());
        values.put(TABLE_BOOK_COLUMN_GENRE, book.getGenre());

        long i = helper.insert(values, TABLE_BOOK);

        if(i > 0){
            Log.i(TAG,"Sucesso ao inserir livro no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao inserir livro no banco de dados");
            return false;
        }
    }

    public boolean updateBook(Book book){
        int r;
        ContentValues values = new ContentValues();

        values.put(TABLE_BOOK_COLUMN_TITLE, book.getTitle());
        values.put(TABLE_BOOK_COLUMN_GENRE, book.getGenre());

        r = helper.update(values, TABLE_BOOK, TABLE_BOOK_COLUMN_ID, book.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao atualizar livro no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao atualizar livro no banco de dados");
            return false;
        }
    }

    public boolean deleteBook(Book book){
        int r;

        deleteAllRelationsbyBook(book);

        r = helper.delete(TABLE_BOOK, TABLE_BOOK_COLUMN_ID, book.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao excluir livro do banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao excluir livro do banco de dados");
            return false;
        }
    }

    public void deleteAllRelationsbyBook(Book book){
        DatabaseAuthor authorDb = new DatabaseAuthor(context);
        DatabaseSeries seriesDb = new DatabaseSeries(context);

        DatabaseAuthorRelations authorRelationDb = new DatabaseAuthorRelations(context);
        DatabaseSeriesRelations seriesRelationDb = new DatabaseSeriesRelations(context);

        List<Series> series = seriesDb.getAllSeriesByBook(book);
        List<Author> authors = authorDb.getAllAuthors_ByBook(book);

        Log.i(TAG,"Excluindo relacionamentos com series");
        for (Series serie : series) {
            seriesRelationDb.deleteSeriesRelationship(serie, book);
        }

        Log.i(TAG,"Excluindo relacionamentos com autores");
        for (Author author : authors) {
            authorRelationDb.deleteAuthorRelationship(author, book);
        }
    }

    public Book getBookById(int id){
        Book book = null;
        Log.i(TAG,"Procurando livro por id");
        Cursor cursor = helper.getById(TABLE_BOOK, TABLE_BOOK_COLUMN_ID, id);
        if(cursor.moveToFirst()){
            Log.i(TAG,"Livro encontrado");
            String title = cursor.getString(cursor.getColumnIndex(TABLE_BOOK_COLUMN_TITLE));
            String genre = cursor.getString(cursor.getColumnIndex(TABLE_BOOK_COLUMN_GENRE));
            book =  new Book(id, title, genre);
        }else{
            Log.e(TAG,"Livro não encontrado");
        }
        cursor.close();
        helper.closeDatabase();
        return  book;
    }

    private List<Book> listAllBooks(Cursor cursor){
        ArrayList<Book> books = new ArrayList<Book>();
        try{
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(TABLE_BOOK_COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(TABLE_BOOK_COLUMN_TITLE));
                String genre = cursor.getString(cursor.getColumnIndex(TABLE_BOOK_COLUMN_GENRE));

                books.add(new Book(id, title, genre));
            }
        }finally {
            cursor.close();
        }

        helper.closeDatabase();
        return books;
    }

    public List<Book> getAllBooks(){
        Cursor cursor = helper.getAllByQuery(TABLE_BOOK, COLUMNS ,null, null, null, null, TABLE_BOOK_COLUMN_ID);
        return listAllBooks(cursor);
    }

    public List<Book> getAllBooks_OrderByTitle(){
        Cursor cursor = helper.getAllByQuery(TABLE_BOOK, COLUMNS ,null, null, null, null, TABLE_BOOK_COLUMN_TITLE +" ASC");
        return listAllBooks(cursor);
    }

    public List<Book> getAllBooks_OrderByGenre(){
        Cursor cursor = helper.getAllByQuery(TABLE_BOOK, COLUMNS ,null, null, null, null, TABLE_BOOK_COLUMN_GENRE +" ASC");
        return listAllBooks(cursor);
    }

    public List<Book> getAllBooks_WithTitleLike(String title){
        String[] args = {"%" + title + "%"};
        Cursor cursor = helper.getAllByQuery(TABLE_BOOK, COLUMNS ,TABLE_BOOK_COLUMN_TITLE + " like ? ", args,
                null, null, TABLE_BOOK_COLUMN_ID);
        return listAllBooks(cursor);
    }

    public List<Book> getAllBooks_OrderByTitle_WithTitleLike(String title){
        String[] args = {title};
        Cursor cursor = helper.getAllByQuery(TABLE_BOOK, COLUMNS ,TABLE_BOOK_COLUMN_TITLE + " like ? ", args,
                null, null, TABLE_BOOK_COLUMN_TITLE +" ASC");
        return listAllBooks(cursor);
    }

    public List<Book> getAllBooks_OrderByGenre_WithTitleLike(String title){
        String[] args = {title};
        Cursor cursor = helper.getAllByQuery(TABLE_BOOK, COLUMNS ,TABLE_BOOK_COLUMN_TITLE + " like ? ", args,
                null, null, TABLE_BOOK_COLUMN_GENRE +" ASC");
        return listAllBooks(cursor);
    }
    public List<Book> getAllBooks_WithGenreLike(String genre){
        String[] args = {"%" + genre + "%"};
        Cursor cursor = helper.getAllByQuery(TABLE_BOOK, COLUMNS ,TABLE_BOOK_COLUMN_GENRE + " like ? ", args,
                null, null, TABLE_BOOK_COLUMN_ID);
        return listAllBooks(cursor);
    }

    public List<Book> getAllBooks_OrderByTitle_WithGenreLike(String genre){
        String[] args = {genre};
        Cursor cursor = helper.getAllByQuery(TABLE_BOOK, COLUMNS ,TABLE_BOOK_COLUMN_GENRE + " like ? ", args,
                null, null, TABLE_BOOK_COLUMN_TITLE +" ASC");
        return listAllBooks(cursor);
    }

    public List<Book> getAllBooks_OrderByGenre_WithGenreLike(String genre){
        String[] args = {genre};
        Cursor cursor = helper.getAllByQuery(TABLE_BOOK, COLUMNS ,TABLE_BOOK_COLUMN_GENRE + " like ? ", args,
                null, null, TABLE_BOOK_COLUMN_GENRE +" ASC");
        return listAllBooks(cursor);
    }

    public List<Book> getAllBooks_BySeries(int seriesId){
        String query = "SELECT B.* from " + TABLE_BOOK + " as B JOIN " + DatabaseSeriesRelations.getTableName() + " as S on B." + TABLE_BOOK_COLUMN_ID + " = S." + DatabaseSeriesRelations.getBookColumn() +
                " WHERE S." + DatabaseSeriesRelations.getSeriesColumn() + " =?;";
        String[] args = {String.valueOf(seriesId)};
        Cursor cursor = helper.getAllByRawQuery(query,args);
        return listAllBooks(cursor);
    }


    public List<Book> getAllBooks_ByAuthor(int authorId){
        String query = "SELECT B.* from " + TABLE_BOOK + " as B JOIN " + DatabaseAuthorRelations.getTableName() + " as A on B." + TABLE_BOOK_COLUMN_ID + " = A." + DatabaseAuthorRelations.getBookColumn() +
                " WHERE A." + DatabaseAuthorRelations.getAuthorColumn() + " =?;";
        String[] args = {String.valueOf(authorId)};
        Cursor cursor = helper.getAllByRawQuery(query,args);
        return listAllBooks(cursor);
    }
}