package atividade.mobile.tatiana.trabalhocontrolelivros.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.BookType;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Author;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Exemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Publisher;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Series;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Status;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class DatabaseExemplar {
    private static final String TABLE_EXEMPLAR = "exemplars";
    // campos da tabela EXEMPLAR
    private static final String TABLE_EXEMPLAR_COLUMN_ID = "id"; // PK
    private static final String TABLE_EXEMPLAR_COLUMN_BOOK_ID = "bookId"; // FK
    private static final String TABLE_EXEMPLAR_COLUMN_USER_ID = "userId"; // FK
    private static final String TABLE_EXEMPLAR_COLUMN_STATUS_ID = "statusId"; // FK
    private static final String TABLE_EXEMPLAR_COLUMN_PUBLISHER_ID = "publisherId";
    private static final String TABLE_EXEMPLAR_COLUMN_EDITION = "edition";
    private static final String TABLE_EXEMPLAR_COLUMN_PAGES = "pages";
    private static final String TABLE_EXEMPLAR_COLUMN_CURRENT_PAGE = "currentPage";
    private static final String TABLE_EXEMPLAR_COLUMN_BOOK_TYPE = "bookType";
    private static final String TABLE_EXEMPLAR_COLUMN_TIME_READ = "timesRead";
    private static final String TABLE_EXEMPLAR_COLUMN_TIME_LENT = "timesLent";
    private static final String TABLE_EXEMPLAR_COLUMN_COVER_IMAGE = "coverImage";
    private static final String TABLE_EXEMPLAR_COLUMN_LANGUAGE = "language";
    private static final String TABLE_EXEMPLAR_COLUMN_COMMENTS = "comments";
    private static final String TABLE_EXEMPLAR_COLUMN_TIME_CLASSIFICATED = "timesClassificated";
    private static final String TABLE_EXEMPLAR_COLUMN_CLASSIFICATION_LAST = "classificationLast";
    private static final String TABLE_EXEMPLAR_COLUMN_CLASSIFICATION_MEAN = "classificationMean";

    private static final String ORDER_BY_CLASSIFICATION_MEAN = TABLE_EXEMPLAR_COLUMN_CLASSIFICATION_MEAN + ", " + TABLE_EXEMPLAR_COLUMN_ID;
    private static final String ORDER_BY_CLASSIFICATION_LAST = TABLE_EXEMPLAR_COLUMN_CLASSIFICATION_LAST + ", " + TABLE_EXEMPLAR_COLUMN_ID;
    private static final String ORDER_BY_PAGES = TABLE_EXEMPLAR_COLUMN_PAGES + ", " + TABLE_EXEMPLAR_COLUMN_ID;
    private static final String ORDER_BY_TIMES_READ = TABLE_EXEMPLAR_COLUMN_TIME_READ + ", " + TABLE_EXEMPLAR_COLUMN_ID;
    private static final String ORDER_BY_TIMES_LENT = TABLE_EXEMPLAR_COLUMN_TIME_LENT + ", " + TABLE_EXEMPLAR_COLUMN_ID;


    private static final String TAG = "ExemplarDb";

    private DatabaseHelper helper;
    private Context context;

    public DatabaseExemplar(Context context) {
        this.context = context;
        helper = DatabaseHelper.getInstance(context);
    }

    public static final String getTableName() {
        return TABLE_EXEMPLAR;
    }

    public static final String createTable() {
        return "CREATE TABLE " + TABLE_EXEMPLAR +
                "(" +
                TABLE_EXEMPLAR_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_EXEMPLAR_COLUMN_BOOK_ID + " INTEGER NOT NULL REFERENCES " + DatabaseBook.getTableName() + ", " +
                TABLE_EXEMPLAR_COLUMN_USER_ID + " INTEGER NOT NULL REFERENCES " + DatabaseUser.getTableName() + ", " +
                TABLE_EXEMPLAR_COLUMN_STATUS_ID + " INTEGER NOT NULL REFERENCES " + DatabaseStatus.getTableName() + ", " +
                TABLE_EXEMPLAR_COLUMN_PUBLISHER_ID + " INTEGER NOT NULL REFERENCES " + DatabasePublisher.getTableName() + ", " +
                TABLE_EXEMPLAR_COLUMN_LANGUAGE + " TEXT NOT NULL, " +
                TABLE_EXEMPLAR_COLUMN_EDITION + " INTEGER NOT NULL, " +
                TABLE_EXEMPLAR_COLUMN_TIME_READ + " INTEGER, " +
                TABLE_EXEMPLAR_COLUMN_TIME_LENT + " INTEGER, " +
                TABLE_EXEMPLAR_COLUMN_PAGES + " INTEGER NOT NULL, " +
                TABLE_EXEMPLAR_COLUMN_CURRENT_PAGE + " INTEGER, " +
                TABLE_EXEMPLAR_COLUMN_BOOK_TYPE + " INTEGER NOT NULL, " +
                TABLE_EXEMPLAR_COLUMN_COVER_IMAGE + " TEXT, " +
                TABLE_EXEMPLAR_COLUMN_TIME_CLASSIFICATED + " INTEGER, " +
                TABLE_EXEMPLAR_COLUMN_CLASSIFICATION_LAST + " REAL, " +
                TABLE_EXEMPLAR_COLUMN_CLASSIFICATION_MEAN + " REAL, " +
                TABLE_EXEMPLAR_COLUMN_COMMENTS + " TEXT" +
                ")";
    }

    public static final String dropTable() {
        return "DROP TABLE IF EXISTS " + TABLE_EXEMPLAR;
    }

    public boolean insertExemplar(Exemplar exemplar){
        ContentValues values = new ContentValues();
        values.put(TABLE_EXEMPLAR_COLUMN_BOOK_ID,   exemplar.getBook().getId());
        values.put(TABLE_EXEMPLAR_COLUMN_USER_ID,   exemplar.getUser().getId());
        values.put(TABLE_EXEMPLAR_COLUMN_STATUS_ID, exemplar.getStatus().getId());
        values.put(TABLE_EXEMPLAR_COLUMN_PUBLISHER_ID,    exemplar.getPublisher().getId());
        values.put(TABLE_EXEMPLAR_COLUMN_EDITION,   exemplar.getEdition());
        values.put(TABLE_EXEMPLAR_COLUMN_PAGES,     exemplar.getPages());
        if (exemplar.getBookType() == BookType.EBOOK){
            values.put(TABLE_EXEMPLAR_COLUMN_BOOK_TYPE, 0);
        }
        else{
            values.put(TABLE_EXEMPLAR_COLUMN_BOOK_TYPE, 1);
        }
        values.put(TABLE_EXEMPLAR_COLUMN_LANGUAGE,  exemplar.getLanguage());
        values.put(TABLE_EXEMPLAR_COLUMN_LANGUAGE,  exemplar.getLanguage());

        long i = helper.insert(values, TABLE_EXEMPLAR);

        if(i > 0){
            Log.i(TAG,"Sucesso ao inserir exemplar no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao inserir exemplar no banco de dados");
            return false;
        }
    }

    public boolean updateExemplar(Exemplar exemplar){
        int r;
        ContentValues values = new ContentValues();
        // NOT NULL
        Log.i(TAG, "Livro: " + exemplar.getBook().getTitle());
        values.put(TABLE_EXEMPLAR_COLUMN_BOOK_ID,   exemplar.getBook().getId());
        values.put(TABLE_EXEMPLAR_COLUMN_USER_ID,   exemplar.getUser().getId());
        values.put(TABLE_EXEMPLAR_COLUMN_STATUS_ID, exemplar.getStatus().getId());
        values.put(TABLE_EXEMPLAR_COLUMN_PUBLISHER_ID,    exemplar.getPublisher().getId());
        values.put(TABLE_EXEMPLAR_COLUMN_EDITION,   exemplar.getEdition());
        values.put(TABLE_EXEMPLAR_COLUMN_PAGES,     exemplar.getPages());

        if (exemplar.getBookType() == BookType.EBOOK){
            values.put(TABLE_EXEMPLAR_COLUMN_BOOK_TYPE, 0);
        }
        else{
            values.put(TABLE_EXEMPLAR_COLUMN_BOOK_TYPE, 1);
        }
        values.put(TABLE_EXEMPLAR_COLUMN_LANGUAGE,  exemplar.getLanguage());

        values.put(TABLE_EXEMPLAR_COLUMN_CURRENT_PAGE,  exemplar.getCurrentPage());
        values.put(TABLE_EXEMPLAR_COLUMN_TIME_READ,  exemplar.getTimesRead());
        values.put(TABLE_EXEMPLAR_COLUMN_TIME_LENT,  exemplar.getTimesLent());
        values.put(TABLE_EXEMPLAR_COLUMN_COMMENTS,  exemplar.getComments());
        values.put(TABLE_EXEMPLAR_COLUMN_TIME_CLASSIFICATED,  exemplar.getTimesClassificated());
        values.put(TABLE_EXEMPLAR_COLUMN_CLASSIFICATION_LAST,  exemplar.getClassificationLast());
        values.put(TABLE_EXEMPLAR_COLUMN_CLASSIFICATION_MEAN,  exemplar.getClassificationMean());
        values.put(TABLE_EXEMPLAR_COLUMN_COVER_IMAGE,  exemplar.getCoverImage());

        r = helper.update(values, TABLE_EXEMPLAR, TABLE_EXEMPLAR_COLUMN_ID, exemplar.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao atualizar exemplar no banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao atualizar exemplar no banco de dados");
            return false;
        }
    }

    public boolean deleteExemplar(Exemplar exemplar){
        int r;

        r = helper.delete(TABLE_EXEMPLAR, TABLE_EXEMPLAR_COLUMN_ID, exemplar.getId());

        if(r > 0){
            Log.i(TAG,"Sucesso ao excluir exemplar do banco de dados");
            return true;
        }else {
            Log.e(TAG,"Erro ao excluir exemplar do banco de dados");
            return false;
        }
    }

    public Exemplar getExemplarById(int id) {
        DatabaseBook bookDb = new DatabaseBook(context);
        DatabaseStatus statusDb = new DatabaseStatus(context);
        DatabaseUser userDb = new DatabaseUser(context);
        DatabasePublisher publisherDb = new DatabasePublisher(context);
        Exemplar exemplar = null;
        Log.i(TAG, "Procurando exemplar por id");
        Cursor cursor = helper.getById(TABLE_EXEMPLAR, TABLE_EXEMPLAR_COLUMN_ID, id);

        if (cursor.moveToFirst()) {
            Log.i(TAG, "Exemplar encontrado");
            Book book = bookDb.getBookById(cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_BOOK_ID)));
            Log.i(TAG, "Livro: " + book.getTitle());
            Status status = statusDb.getStatusById(cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_STATUS_ID)));
            User user = userDb.getUserById(cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_USER_ID)));
            Publisher publisher = publisherDb.getPublisherById(cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_PUBLISHER_ID)));
            int edition = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_EDITION));
            int pages = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_PAGES));
            int currentPage = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_CURRENT_PAGE));
            int timesRead = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_TIME_READ));
            int timesLent = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_TIME_LENT));
            String coverImage = cursor.getString(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_COVER_IMAGE));
            String language = cursor.getString(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_LANGUAGE));
            String comments = cursor.getString(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_COMMENTS));
            int timesClassificated = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_TIME_CLASSIFICATED));
            float classificationLast = cursor.getFloat(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_CLASSIFICATION_LAST));
            float classificationMean = cursor.getFloat(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_CLASSIFICATION_MEAN));
            int x = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_BOOK_TYPE));

            BookType bookType;
            if (x == 0) {
                bookType = BookType.EBOOK;
            } else {
                bookType = BookType.PHYSICAL_COPY;
            }

            exemplar = new Exemplar(id, book, user, status, publisher, edition, pages, currentPage, bookType, timesRead, timesLent, coverImage,
                    language, comments, classificationLast, classificationMean, timesClassificated);
        } else {
            Log.e(TAG, "Exemplar não encontrado");
        }
        cursor.close();
        helper.closeDatabase();
        return exemplar;
    }

    public List<Exemplar> listAllExemplars(Cursor cursor){
        DatabaseBook bookDb = new DatabaseBook(context);
        DatabaseStatus statusDb = new DatabaseStatus(context);
        DatabaseUser userDb = new DatabaseUser(context);
        DatabasePublisher publisherDb = new DatabasePublisher(context);
        ArrayList<Exemplar> exemplars = new ArrayList<>();
        try{
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_ID));
                Book book                = bookDb.getBookById(cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_BOOK_ID)));
                Status status            = statusDb.getStatusById(cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_STATUS_ID)));
                User userB               = userDb.getUserById(cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_USER_ID)));
                Publisher publisher      = publisherDb.getPublisherById(cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_PUBLISHER_ID)));
                int edition              = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_EDITION));
                int pages                = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_PAGES));
                int currentPage          = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_CURRENT_PAGE));
                int x = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_BOOK_TYPE));
                int timesRead            = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_TIME_READ));
                int timesLent            = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_TIME_LENT));
                String coverImage        = cursor.getString(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_COVER_IMAGE));
                String language          = cursor.getString(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_LANGUAGE));
                String comments          = cursor.getString(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_COMMENTS));
                float classificationLast = cursor.getFloat(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_CLASSIFICATION_LAST));
                float classificationMean = cursor.getFloat(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_CLASSIFICATION_MEAN));
                int timesClassificated   = cursor.getInt(cursor.getColumnIndex(TABLE_EXEMPLAR_COLUMN_TIME_CLASSIFICATED));

                BookType bookType;
                if (x == 0)
                    bookType = BookType.EBOOK;
                else
                    bookType = BookType.PHYSICAL_COPY;

                exemplars.add(new Exemplar(id, book, userB, status, publisher, edition, pages, currentPage, bookType, timesRead, timesLent, coverImage,
                        language, comments, classificationLast, classificationMean, timesClassificated));
            }
        }finally {
            cursor.close();
        }

        helper.closeDatabase();
        return exemplars;
    }

    private List<Exemplar> getAllExemplarsQuery(String selection, String[] args, String order){
        Cursor cursor = helper.getAllByQuery(TABLE_EXEMPLAR, null, selection, args, null, null, order);
        return listAllExemplars(cursor);
    }

    public List<Exemplar> getAllExemplars(User user){
        Log.i(TAG, "Buscando todos os exemplares");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())}, TABLE_EXEMPLAR_COLUMN_ID);
    }

    public List<Exemplar> getAllExemplars_ByStatus(User user, Status status){
        String[] args = {String.valueOf(user.getId()), String.valueOf(status.getId())};
        String condition;
        if(status.getId() <= 4) {
            condition = " AND " + TABLE_EXEMPLAR_COLUMN_STATUS_ID + " =?";
        }
        else{
            condition = " AND " +  TABLE_EXEMPLAR_COLUMN_STATUS_ID + " >=?";
        }

        Log.i(TAG, "Buscando todos os exemplares por Status");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ?" + condition, args, TABLE_EXEMPLAR_COLUMN_ID);
    }

    public List<Exemplar> getAllExemplars_ByBook(User user, Book book){
        String[] args = {String.valueOf(user.getId()), String.valueOf(book.getId())};
        Log.i(TAG, "Buscando todos os exemplares por Livro");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_BOOK_ID + " =?", args, TABLE_EXEMPLAR_COLUMN_ID);
    }

    public List<Exemplar> getAllExemplars_ByLanguage(User user, String language){
        String[] args = {String.valueOf(user.getId()), "%"+language+"%"};
        Log.i(TAG, "Buscando todos os exemplares por Lingua");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_LANGUAGE + " =?", args, TABLE_EXEMPLAR_COLUMN_ID);
    }

    public List<Exemplar> getAllExemplars_ByBookType(User user, BookType type){
        String[] args = {String.valueOf(user.getId()), String.valueOf(type.getValue())};
        Log.i(TAG, "Buscando todos os exemplares por tipo");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_LANGUAGE + " =?", args, TABLE_EXEMPLAR_COLUMN_ID);
    }

    public List<Exemplar> getAllExemplarsRaw_ByAuthor(int userId, int authorId, String order){
        String[] args = {String.valueOf(authorId), String.valueOf(userId)};
        String query = "SELECT E.* from " + TABLE_EXEMPLAR + " AS E " +
                " JOIN " + DatabaseBook.getTableName() + " ON " + DatabaseBook.getIdColumn() + " = " + TABLE_EXEMPLAR_COLUMN_BOOK_ID +
                " JOIN " + DatabaseAuthorRelations.getTableName() + " ON " + DatabaseBook.getIdColumn()  + " = " + DatabaseAuthorRelations.getBookColumn() +
                " JOIN " + DatabaseAuthor.getTableName() + " ON " + DatabaseAuthor.getIdColumn() + " = " + DatabaseAuthorRelations.getAuthorColumn() +
                " WHERE " + DatabaseAuthor.getIdColumn()  + " = ? AND " + TABLE_EXEMPLAR_COLUMN_USER_ID + " = ?" + order;
        Cursor cursor = helper.getAllByRawQuery(query, args);
        return listAllExemplars(cursor);
    }

    public List<Exemplar> getAllExemplars_ByAuthor(User user, Author author){
        Log.i(TAG, "Buscando todos os exemplares por autor");
        return getAllExemplarsRaw_ByAuthor(user.getId(), author.getId(), "");
    }

    public List<Exemplar> getAllExemplarsRaw_BySeries(int userId, int seriesId, String order){
        String[] args = {String.valueOf(seriesId), String.valueOf(userId)};
        String query = "SELECT E.* from " + TABLE_EXEMPLAR + " AS E " +
                " JOIN " + DatabaseBook.getTableName() + " ON " + DatabaseBook.getIdColumn() + " = " + TABLE_EXEMPLAR_COLUMN_BOOK_ID +
                " JOIN " + DatabaseSeriesRelations.getTableName() + " ON " + DatabaseBook.getIdColumn() + " = " + DatabaseSeriesRelations.getBookColumn() +
                " JOIN " + DatabaseSeries.getTableName() + " ON " + DatabaseSeries.getIdColumn() + " = " + DatabaseSeriesRelations.getSeriesColumn() +
                " WHERE " + DatabaseSeries.getIdColumn() + " = ? AND " + TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? " + order;
        Cursor cursor = helper.getAllByRawQuery(query, args);
        return listAllExemplars(cursor);
    }

    public List<Exemplar> getAllExemplars_BySeries(User user, Series series){
        Log.i(TAG, "Buscando todos os exemplares por coleção");
        return getAllExemplarsRaw_BySeries(user.getId(), series.getId(), "");
    }

    public List<Exemplar> getAllExemplars_OrderByClassificationMean(User user){
        Log.i(TAG, "Buscando todos os exemplares, ordenando pela média das classificações");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())}, ORDER_BY_CLASSIFICATION_MEAN);
    }

    public List<Exemplar> getAllExemplars_ByStatus_OrderByClassificationMean(User user, Status status){
        String[] args = {String.valueOf(user.getId()), String.valueOf(status.getId())};
        String condition;
        if(status.getId() <= 4) {
            condition = " AND " + TABLE_EXEMPLAR_COLUMN_STATUS_ID + " =?";
        }
        else{
            condition = " AND " +  TABLE_EXEMPLAR_COLUMN_STATUS_ID + " >=?";
        }

        Log.i(TAG, "Buscando todos os exemplares por Status, ordenando pela média das classificações");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ?" + condition, args, ORDER_BY_CLASSIFICATION_MEAN);
    }

    public List<Exemplar> getAllExemplars_ByBook_OrderByClassificationMean(User user, Book book){
        String[] args = {String.valueOf(user.getId()), String.valueOf(book.getId())};
        Log.i(TAG, "Buscando todos os exemplares por Livro, ordenando pela média das classificações");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_BOOK_ID + " =?", args, ORDER_BY_CLASSIFICATION_MEAN);
    }

    public List<Exemplar> getAllExemplars_ByLanguage_OrderByClassificationMean(User user, String language){
        String[] args = {String.valueOf(user.getId()), "%"+language+"%"};
        Log.i(TAG, "Buscando todos os exemplares por Lingua, ordenando pela média das classificações");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_LANGUAGE + " =?", args, ORDER_BY_CLASSIFICATION_MEAN);
    }

    public List<Exemplar> getAllExemplars_ByBookType_OrderByClassificationMean(User user, BookType type){
        String[] args = {String.valueOf(user.getId()), String.valueOf(type.getValue())};
        Log.i(TAG, "Buscando todos os exemplares por tipo, ordenando pela média das classificações");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_LANGUAGE + " =?", args, ORDER_BY_CLASSIFICATION_MEAN);
    }

    public List<Exemplar> getAllExemplars_ByAuthor_OrderByClassificationMean(User user, Author author){
        Log.i(TAG, "Buscando todos os exemplares por autor, ordenando pela média das classificações");
        return getAllExemplarsRaw_ByAuthor(user.getId(), author.getId(), " ORDER BY " + ORDER_BY_CLASSIFICATION_MEAN);
    }

    public List<Exemplar> getAllExemplars_BySeries_OrderByClassificationMean(User user, Series series){
        Log.i(TAG, "Buscando todos os exemplares por coleção, ordenando pela média das classificações");
        return getAllExemplarsRaw_BySeries(user.getId(), series.getId(), " ORDER BY " + ORDER_BY_CLASSIFICATION_MEAN);
    }

    public List<Exemplar> getAllExemplars_OrderByClassificationLast(User user){
        Log.i(TAG, "Buscando todos os exemplares ordenando pela última classificação");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())}, ORDER_BY_CLASSIFICATION_LAST);
    }

    public List<Exemplar> getAllExemplars_ByStatus_OrderByClassificationLast(User user, Status status){
        String[] args = {String.valueOf(user.getId()), String.valueOf(status.getId())};
        String condition;
        if(status.getId() <= 4) {
            condition = " AND " + TABLE_EXEMPLAR_COLUMN_STATUS_ID + " =?";
        }
        else{
            condition = " AND " +  TABLE_EXEMPLAR_COLUMN_STATUS_ID + " >=?";
        }

        Log.i(TAG, "Buscando todos os exemplares por Status, ordenando pela última classificação");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ?" + condition, args, ORDER_BY_CLASSIFICATION_LAST);
    }

    public List<Exemplar> getAllExemplars_ByBook_OrderByClassificationLast(User user, Book book){
        String[] args = {String.valueOf(user.getId()), String.valueOf(book.getId())};
        Log.i(TAG, "Buscando todos os exemplares por Livro, ordenando pela última classificação");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_BOOK_ID + " =?", args, ORDER_BY_CLASSIFICATION_LAST);
    }

    public List<Exemplar> getAllExemplars_ByLanguage_OrderByClassificationLast(User user, String language){
        String[] args = {String.valueOf(user.getId()), "%"+language+"%"};
        Log.i(TAG, "Buscando todos os exemplares por Língua, ordenando pela última classificação");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_LANGUAGE + " =?", args, ORDER_BY_CLASSIFICATION_LAST);
    }

    public List<Exemplar> getAllExemplars_ByBookType_OrderByClassificationLast(User user, BookType type){
        String[] args = {String.valueOf(user.getId()), String.valueOf(type.getValue())};
        Log.i(TAG, "Buscando todos os exemplares por tipo, ordenando pela última classificação");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_LANGUAGE + " =?", args, ORDER_BY_CLASSIFICATION_LAST);
    }

    public List<Exemplar> getAllExemplars_ByAuthor_OrderByClassificationLast(User user, Author author){
        Log.i(TAG, "Buscando todos os exemplares por autor, ordenando pela última classificação");
        return getAllExemplarsRaw_ByAuthor(user.getId(), author.getId(), " ORDER BY " + ORDER_BY_CLASSIFICATION_LAST);
    }

    public List<Exemplar> getAllExemplars_BySeries_OrderByClassificationLast(User user, Series series){
        Log.i(TAG, "Buscando todos os exemplares por coleção, ordenando pela última classificação");
        return getAllExemplarsRaw_BySeries(user.getId(), series.getId(), " ORDER BY " + ORDER_BY_CLASSIFICATION_LAST);
    }

    public List<Exemplar> getAllExemplars_OrderByPages(User user){
        Log.i(TAG, "Buscando todos os exemplares, ordenando pela quantidade de páginas");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())}, ORDER_BY_PAGES);
    }

    public List<Exemplar> getAllExemplars_ByStatus_OrderByPages(User user, Status status){
        String[] args = {String.valueOf(user.getId()), String.valueOf(status.getId())};
        String condition;
        if(status.getId() <= 4) {
            condition = " AND " + TABLE_EXEMPLAR_COLUMN_STATUS_ID + " =?";
        }
        else{
            condition = " AND " +  TABLE_EXEMPLAR_COLUMN_STATUS_ID + " >=?";
        }

        Log.i(TAG, "Buscando todos os exemplares por Status, ordenando pela quantidade de páginas");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ?" + condition, args, ORDER_BY_PAGES);
    }

    public List<Exemplar> getAllExemplars_ByBook_OrderByPages(User user, Book book){
        String[] args = {String.valueOf(user.getId()), String.valueOf(book.getId())};
        Log.i(TAG, "Buscando todos os exemplares por Livro, ordenando pela quantidade de páginas");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_BOOK_ID + " =?", args, ORDER_BY_PAGES);
    }

    public List<Exemplar> getAllExemplars_ByLanguage_OrderByPages(User user, String language){
        String[] args = {String.valueOf(user.getId()), "%"+language+"%"};
        Log.i(TAG, "Buscando todos os exemplares por Língua, ordenando pela quantidade de páginas");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_LANGUAGE + " =?", args, ORDER_BY_PAGES);
    }

    public List<Exemplar> getAllExemplars_ByBookType_OrderByPages(User user, BookType type){
        String[] args = {String.valueOf(user.getId()), String.valueOf(type.getValue())};
        Log.i(TAG, "Buscando todos os exemplares por tipo, ordenando pela quantidade de páginas");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_LANGUAGE + " =?", args, ORDER_BY_PAGES);
    }

    public List<Exemplar> getAllExemplars_ByAuthor_OrderByPages(User user, Author author){
        Log.i(TAG, "Buscando todos os exemplares por autor, ordenando pela quantidade de páginas");
        return getAllExemplarsRaw_ByAuthor(user.getId(), author.getId(), " ORDER BY " + ORDER_BY_PAGES);
    }

    public List<Exemplar> getAllExemplars_BySeries_OrderByPages(User user, Series series){
        Log.i(TAG, "Buscando todos os exemplares por coleção, ordenando pela quantidade de páginas");
        return getAllExemplarsRaw_BySeries(user.getId(), series.getId(), " ORDER BY " + ORDER_BY_PAGES);
    }

    public List<Exemplar> getAllExemplars_OrderByTimesRead(User user){
        Log.i(TAG, "Buscando todos os exemplares, ordenando pela quantidade de vezes que foi lido");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())}, ORDER_BY_TIMES_READ);
    }

    public List<Exemplar> getAllExemplars_ByStatus_OrderByTimesRead(User user, Status status){
        String[] args = {String.valueOf(user.getId()), String.valueOf(status.getId())};
        String condition;
        if(status.getId() <= 4) {
            condition = " AND " + TABLE_EXEMPLAR_COLUMN_STATUS_ID + " =?";
        }
        else{
            condition = " AND " +  TABLE_EXEMPLAR_COLUMN_STATUS_ID + " >=?";
        }

        Log.i(TAG, "Buscando todos os exemplares por Status, ordenando pela quantidade de vezes que foi lido");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ?" + condition, args, ORDER_BY_TIMES_READ);
    }

    public List<Exemplar> getAllExemplars_ByBook_OrderByTimesRead(User user, Book book){
        String[] args = {String.valueOf(user.getId()), String.valueOf(book.getId())};
        Log.i(TAG, "Buscando todos os exemplares por Livro, ordenando pela quantidade de vezes que foi lido");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_BOOK_ID + " =?", args, ORDER_BY_TIMES_READ);
    }

    public List<Exemplar> getAllExemplars_ByLanguage_OrderByTimesRead(User user, String language){
        String[] args = {String.valueOf(user.getId()), "%"+language+"%"};
        Log.i(TAG, "Buscando todos os exemplares por Língua, ordenando pela quantidade de vezes que foi lido");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_LANGUAGE + " =?", args, ORDER_BY_TIMES_READ);
    }

    public List<Exemplar> getAllExemplars_ByBookType_OrderByTimesRead(User user, BookType type){
        String[] args = {String.valueOf(user.getId()), String.valueOf(type.getValue())};
        Log.i(TAG, "Buscando todos os exemplares por tipo, ordenando pela quantidade de vezes que foi lido");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_LANGUAGE + " =?", args, ORDER_BY_TIMES_READ);
    }

    public List<Exemplar> getAllExemplars_ByAuthor_OrderByTimesRead(User user, Author author){
        Log.i(TAG, "Buscando todos os exemplares por autor, ordenando pela quantidade de vezes que foi lido");
        return getAllExemplarsRaw_ByAuthor(user.getId(), author.getId(), " ORDER BY " + ORDER_BY_TIMES_READ);
    }

    public List<Exemplar> getAllExemplars_BySeries_OrderByTimesRead(User user, Series series){
        Log.i(TAG, "Buscando todos os exemplares por coleção, ordenando pela quantidade de vezes que foi lido");
        return getAllExemplarsRaw_BySeries(user.getId(), series.getId(), " ORDER BY " + ORDER_BY_TIMES_READ);
    }

    public List<Exemplar> getAllExemplars_OrderByTimesLent(User user){
        Log.i(TAG, "Buscando todos os exemplares, ordenando pela quantidade de vezes que foi emprestado");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())}, ORDER_BY_TIMES_LENT);
    }

    public List<Exemplar> getAllExemplars_ByStatus_OrderByTimesLent(User user, Status status){
        String[] args = {String.valueOf(user.getId()), String.valueOf(status.getId())};
        String condition;
        if(status.getId() <= 4) {
            condition = " AND " + TABLE_EXEMPLAR_COLUMN_STATUS_ID + " =?";
        }
        else{
            condition = " AND " +  TABLE_EXEMPLAR_COLUMN_STATUS_ID + " >=?";
        }

        Log.i(TAG, "Buscando todos os exemplares por Status, ordenando pela quantidade de vezes que foi emprestado");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ?" + condition, args, ORDER_BY_TIMES_LENT);
    }

    public List<Exemplar> getAllExemplars_ByBook_OrderByTimesLent(User user, Book book){
        String[] args = {String.valueOf(user.getId()), String.valueOf(book.getId())};
        Log.i(TAG, "Buscando todos os exemplares por Livro, ordenando pela quantidade de vezes que foi emprestado");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_BOOK_ID + " =?", args, ORDER_BY_TIMES_LENT);
    }

    public List<Exemplar> getAllExemplars_ByLanguage_OrderByTimesLent(User user, String language){
        String[] args = {String.valueOf(user.getId()), "%"+language+"%"};
        Log.i(TAG, "Buscando todos os exemplares por Língua, ordenando pela quantidade de vezes que foi emprestado");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_LANGUAGE + " =?", args, ORDER_BY_TIMES_LENT);
    }

    public List<Exemplar> getAllExemplars_ByBookType_OrderByTimesLent(User user, BookType type){
        String[] args = {String.valueOf(user.getId()), String.valueOf(type.getValue())};
        Log.i(TAG, "Buscando todos os exemplares por tipo, ordenando pela quantidade de vezes que foi emprestado");
        return getAllExemplarsQuery(TABLE_EXEMPLAR_COLUMN_USER_ID + " = ? AND " + TABLE_EXEMPLAR_COLUMN_LANGUAGE + " =?", args, ORDER_BY_TIMES_LENT);
    }

    public List<Exemplar> getAllExemplars_ByAuthor_OrderByTimesLent(User user, Author author){
        Log.i(TAG, "Buscando todos os exemplares por autor, ordenando pela quantidade de vezes que foi emprestado");
        return getAllExemplarsRaw_ByAuthor(user.getId(), author.getId(), " ORDER BY " + ORDER_BY_TIMES_LENT);
    }

    public List<Exemplar> getAllExemplars_BySeries_OrderByTimesLent(User user, Series series){
        Log.i(TAG, "Buscando todos os exemplares por coleção, ordenando pela quantidade de vezes que foi emprestado");
        return getAllExemplarsRaw_BySeries(user.getId(), series.getId(), " ORDER BY " + ORDER_BY_TIMES_LENT);
    }
}
