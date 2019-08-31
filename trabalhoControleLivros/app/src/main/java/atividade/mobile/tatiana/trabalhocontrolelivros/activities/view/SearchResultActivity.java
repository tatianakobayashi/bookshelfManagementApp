package atividade.mobile.tatiana.trabalhocontrolelivros.activities.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseAuthor;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseBook;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseExemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabasePublisher;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseSeries;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseStatus;
import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.ActivityType;
import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.BookSearchType;
import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.OrderType;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Author;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Exemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Publisher;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Series;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Status;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

import static atividade.mobile.tatiana.trabalhocontrolelivros.Enums.ActivityType.STATUS;

public class SearchResultActivity extends AppCompatActivity {
    private User user;
    private String search;
    private ActivityType searchType;
    private String type;

    private ListView searchListView;

    //    private DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        user = (User) getIntent().getExtras().getSerializable("logged_user");
        search = getIntent().getExtras().getString("search");
        type = getIntent().getExtras().getString("type");

        if(user == null){
            finish();
        }

//        helper = DatabaseHelper.getInstance(this);

        searchListView = findViewById(R.id.searchListView);

        if(type.equals("Exemplar")){
            searchType = ActivityType.EXEMPLAR;
            DatabaseExemplar helper = new DatabaseExemplar(this);
            List<Exemplar> exemplars;
            if(search.equals("")) {
                exemplars = helper.getAllExemplars(user);
            }else{
                exemplars = helper.getAllExemplars(user);
            }
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exemplars);
            searchListView.setAdapter(adapter);
        }else if (type.equals("Livro")){
            searchType = ActivityType.BOOK;
            DatabaseBook helper = new DatabaseBook(this);
            BookSearchType searchOption = (BookSearchType)getIntent().getExtras().getSerializable("searchOption");
            List<Book> books;
            switch (searchOption) {
                case AUTHOR:
                    Author author = (Author) getIntent().getExtras().getSerializable("Author");
                    books = helper.getAllBooks_ByAuthor(author.getId());
                    break;
                case SERIES:
                    Series series = (Series)getIntent().getExtras().getSerializable("Series");
                    books = helper.getAllBooks_BySeries(series.getId());
                    break;
                case TITLE:
                    books = helper.getAllBooks_WithTitleLike(search);
                    break;
                case GENRE:
                    books = helper.getAllBooks_WithGenreLike(search);
                    break;
                default:
                    books = helper.getAllBooks();
                    break;
            }
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, books);
            searchListView.setAdapter(adapter);
        }else if (type.equals("Autor")){
            searchType = ActivityType.AUTHOR;
            DatabaseAuthor helper = new DatabaseAuthor(this);
            List<Author> authors;
            if(search.equals("")) {
                authors = helper.getAllAuthors();
            }
            else{
                authors = helper.getAllAuthors_WithNameLike(search);
            }
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, authors);
            searchListView.setAdapter(adapter);
        }else if (type.equals("Coleção")){
            searchType = ActivityType.SERIES;
            ActivityType searchOption = (ActivityType) getIntent().getExtras().getSerializable("searchOption");
            DatabaseSeries helper = new DatabaseSeries(this);
            List<Series> series;
            switch (searchOption) {
                case BOOK:
                    Book book = (Book) getIntent().getExtras().getSerializable("Book");
                    series = helper.getAllSeriesByBook(book);
                    break;
                case NAME:
                    series = helper.getAllSeries_WithNameLike(search);
                    break;
                default:
                    series = helper.getAllSeries();
                    break;
            }
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, series);
            searchListView.setAdapter(adapter);
        }else if (type.equals("Status")){
            searchType = STATUS;
            DatabaseStatus helper = new DatabaseStatus(this);
            List<Status> statuses;
            if (search.equals("")) {
                statuses = helper.getAllStatus();
            }
            else{
                statuses = helper.getAllStatus_WithNameLike(search);
            }
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, statuses);
            searchListView.setAdapter(adapter);
        }else {
            searchType = ActivityType.EDITOR;
            DatabasePublisher helper = new DatabasePublisher(this);
            List<Publisher> publishers;
            if (search.equals("")) {
                publishers = helper.getAllPublishers();
            }
            else {
                publishers = helper.getAllPublishers_WithNameLike(search);
            }
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, publishers);
            searchListView.setAdapter(adapter);
        }

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch(searchType){
                    case BOOK:
                        intent = new Intent(SearchResultActivity.this, BookViewActivity.class);
                        intent.putExtra("logged_user", user);
                        intent.putExtra("Book", ((Book)parent.getItemAtPosition(position)));
                        startActivity(intent);
                        break;
                    case AUTHOR:
                        intent = new Intent(SearchResultActivity.this, ViewObjectActivity.class);
                        intent.putExtra("logged_user", user);
                        intent.putExtra("Type", ActivityType.AUTHOR);
                        intent.putExtra("Author", ((Author)parent.getItemAtPosition(position)));
                        startActivity(intent);
                        break;
                    case EXEMPLAR:
                        intent = new Intent(SearchResultActivity.this, ExemplarViewActivity.class);
                        intent.putExtra("logged_user", user);
                        intent.putExtra("Exemplar", ((Exemplar)parent.getItemAtPosition(position)));
                        startActivity(intent);
                        break;
                    case EDITOR:
                        intent = new Intent(SearchResultActivity.this, ViewObjectActivity.class);
                        intent.putExtra("logged_user", user);
                        intent.putExtra("Type", ActivityType.EDITOR);
                        intent.putExtra("Publisher", ((Publisher)parent.getItemAtPosition(position)));
                        startActivity(intent);
                        break;
                    case SERIES:
                        intent = new Intent(SearchResultActivity.this, ViewObjectActivity.class);
                        intent.putExtra("logged_user", user);
                        intent.putExtra("Type", ActivityType.SERIES);
                        intent.putExtra("Series", ((Series)parent.getItemAtPosition(position)));
                        startActivity(intent);
                        break;
                    case STATUS:
                        intent = new Intent(SearchResultActivity.this, ViewObjectActivity.class);
                        intent.putExtra("logged_user", user);
                        intent.putExtra("Type", STATUS);
                        intent.putExtra("Status", ((Status)parent.getItemAtPosition(position)));
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}