package atividade.mobile.tatiana.trabalhocontrolelivros.activities.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

import javax.crypto.ExemptionMechanism;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseAuthor;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseBook;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseSeries;
import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.ActivityType;
import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.BookSearchType;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Author;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Series;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.view.SearchResultActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private String search;

    private EditText edtSearch;
    private Spinner spnSearchType;
    private Switch swcFilter;
    private LinearLayout linLayFilterSearch; // Visibility
    private LinearLayout linlayFilterSwitch;
    private Spinner spnFilterType;
    private Spinner spnFilterBy;

    private LinearLayout linLayOrderSearch;
    private Switch swcOrder;

    private LinearLayout linLayOrderSearchOptions;
    private Spinner spnOrderType;
//    private Spinner spnOrderBy;

    private Button btnSearch;
    private User user;

    private ArrayAdapter<CharSequence> adapterSearch;
    private ArrayAdapter<CharSequence> adapterFilterType;
    private ArrayAdapter<Book> adapterFilterByBook;
    private ArrayAdapter<Author> adapterFilterByAuthor;
    private ArrayAdapter<Series> adapterFilterBySeries;
//    private ArrayAdapter<CharSequence> adapterOrderTyper;
    private ArrayAdapter<CharSequence> adapterOrderBy;

    private boolean useFilter = false;
    private boolean useOrder = false;

//    private ActivityType filterType;
    private ActivityType searchType = ActivityType.NONE;
    private ActivityType searchOption = ActivityType.NONE;
    private BookSearchType bookSearchOption = BookSearchType.NONE;

    private ActivityType filterType;
    private String filterBy;
    private String orderBy;

    private DatabaseAuthor authorDb;
    private List<Author> authors;
    private DatabaseSeries seriesDb;
    private List<Series> seriesS;

    private DatabaseBook bookDb;
    private List<Book> books;

    private Book selectedBook = null;
    private Series selectedSeries = null;
    private Author selectedAuthor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("logged_user");

        if(user == null){
            finish();
        }

        authorDb = new DatabaseAuthor(this);
        authors = authorDb.getAllAuthors();
        seriesDb = new DatabaseSeries(this);
        seriesS= seriesDb.getAllSeries();
        bookDb = new DatabaseBook(this);
        books = bookDb.getAllBooks();

        edtSearch = findViewById(R.id.edtSearch);
        spnSearchType = findViewById(R.id.spnSearchType);
        swcFilter = findViewById(R.id.swcFilter);
        linlayFilterSwitch = findViewById(R.id.linLayFilterSwitch);
        linLayFilterSearch = findViewById(R.id.linLayFilterSearch);
        spnFilterType = findViewById(R.id.spnFilterType);
        spnFilterBy = findViewById(R.id.spnFilterBy);

        linLayOrderSearch = findViewById(R.id.linLayOrderSearch);
        swcOrder = findViewById(R.id.swcOrder);

        linLayOrderSearchOptions = findViewById(R.id.linLayOrderSearchOptions);
        spnOrderType = findViewById(R.id.spnOrderType);
//        spnOrderBy = findViewById(R.id.spnOrderBy);

        btnSearch = findViewById(R.id.btnSearch);

        adapterSearch = ArrayAdapter.createFromResource(this, R.array.search_options, android.R.layout.simple_spinner_item);
        adapterSearch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSearchType.setAdapter(adapterSearch);
        spnSearchType.setOnItemSelectedListener(this);

        adapterOrderBy = ArrayAdapter.createFromResource(this, R.array.order_exemplar_options, android.R.layout.simple_spinner_item);
        adapterOrderBy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnOrderType.setAdapter(adapterOrderBy);
        spnOrderType.setOnItemSelectedListener(this);

//        swcFilter.o

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(SearchActivity.this, "Botão apertado", Toast.LENGTH_SHORT).show();
                if (search.equals("") || search.equals("Escolha o tipo de busca")){
                    Toast.makeText(SearchActivity.this, "Escolha o tipo", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("logged_user", user);
                    intent.putExtra("search", edtSearch.getText().toString());
                    intent.putExtra("type", search);
                    if (searchType == ActivityType.SERIES) {
                        intent.putExtra("searchOption", searchOption);
                        if (filterType == ActivityType.BOOK){
                            intent.putExtra("Book", selectedBook);
                        }
                    }
                    else if (searchType == ActivityType.BOOK) {
                        intent.putExtra("searchOption", bookSearchOption);
                        if (filterType == ActivityType.AUTHOR){
                            intent.putExtra("Author", selectedAuthor);
                        }
                        else if (filterType == ActivityType.SERIES){
                            intent.putExtra("Series", selectedSeries);
                        }
                    }
                    startActivity(intent);
                }
            }
        });

        swcFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && linlayFilterSwitch.getVisibility() == View.VISIBLE){
                    linLayFilterSearch.setVisibility(View.VISIBLE);
                }
                else{
                    linLayFilterSearch.setVisibility(View.GONE);
                }
            }
        });

        swcOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && linLayOrderSearch.getVisibility() == View.VISIBLE){
                    linLayOrderSearchOptions.setVisibility(View.VISIBLE);
                }
                else{
                    linLayOrderSearchOptions.setVisibility(View.GONE);
                }
            }
        });
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
//        status = (Status) parent.getItemAtPosition(pos);
//        changed = true;
        switch (parent.getId()){
            case R.id.spnSearchType:
                if (adapterSearch.getPosition("Livro") == pos){
                    linlayFilterSwitch.setVisibility(View.VISIBLE);
                    linLayOrderSearch.setVisibility(View.GONE);

                    adapterFilterType = ArrayAdapter.createFromResource(this, R.array.search_book_options, android.R.layout.simple_spinner_item);
                    adapterFilterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnFilterType.setAdapter(adapterFilterType);
                    spnFilterType.setOnItemSelectedListener(this);

                    searchType = ActivityType.BOOK;
                }
                else if (adapterSearch.getPosition("Coleção") == pos){
                    linlayFilterSwitch.setVisibility(View.VISIBLE);
                    linLayOrderSearch.setVisibility(View.GONE);

                    adapterFilterType = ArrayAdapter.createFromResource(this, R.array.search_series_options, android.R.layout.simple_spinner_item);
                    adapterFilterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnFilterType.setAdapter(adapterFilterType);
                    spnFilterType.setOnItemSelectedListener(this);

                    searchType = ActivityType.SERIES;
                }
                else if (adapterSearch.getPosition("Exemplar") == pos){
                    linLayOrderSearch.setVisibility(View.VISIBLE);
                    linlayFilterSwitch.setVisibility(View.VISIBLE);

                    adapterFilterType = ArrayAdapter.createFromResource(this, R.array.search_exemplar_options, android.R.layout.simple_spinner_item);
                    adapterFilterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnFilterType.setAdapter(adapterFilterType);
                    spnFilterType.setOnItemSelectedListener(this);

                    searchType = ActivityType.EXEMPLAR;
                }
                else{
                    linLayOrderSearch.setVisibility(View.GONE);
                    linlayFilterSwitch.setVisibility(View.GONE);

                    searchType = ActivityType.NONE;
                }
                search = (String)parent.getItemAtPosition(pos);
                break;
            case R.id.spnFilterType:
                switch (searchType) {
                    case EXEMPLAR:
                        break;
                    case BOOK:
                        if (adapterFilterType.getPosition("Autor") == pos) {
                            bookSearchOption = BookSearchType.AUTHOR;

                            spnFilterBy.setVisibility(View.VISIBLE);

                            adapterFilterByAuthor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, authors);
                            adapterFilterByAuthor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnFilterBy.setAdapter(adapterFilterByAuthor);
                            spnFilterBy.setOnItemSelectedListener(this);

                            filterType = ActivityType.AUTHOR;
                        }
                        else if (adapterFilterType.getPosition("Coleção") == pos) {
                            bookSearchOption = BookSearchType.SERIES;

                            spnFilterBy.setVisibility(View.VISIBLE);

                            adapterFilterBySeries = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, seriesS);
                            adapterFilterBySeries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnFilterBy.setAdapter(adapterFilterBySeries);
                            spnFilterBy.setOnItemSelectedListener(this);

                            filterType = ActivityType.SERIES;
                        }
                        else if (adapterFilterType.getPosition("Título") == pos) {
                            bookSearchOption = BookSearchType.TITLE;
                            spnFilterBy.setVisibility(View.GONE);
                        }
                        else if (adapterFilterType.getPosition("Gênero") == pos) {
                            bookSearchOption = BookSearchType.GENRE;
                            spnFilterBy.setVisibility(View.GONE);
                        }
                        else{
                            bookSearchOption = BookSearchType.NONE;
                            spnFilterBy.setVisibility(View.GONE);
                        }
                        break;
                    case SERIES:
                        if (adapterFilterType.getPosition("Livro") == pos) {
                            searchOption = ActivityType.BOOK;

                            spnFilterBy.setVisibility(View.VISIBLE);

                            adapterFilterByBook = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, books);
                            adapterFilterByBook.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnFilterBy.setAdapter(adapterFilterByBook);
                            spnFilterBy.setOnItemSelectedListener(this);

                            filterType = ActivityType.BOOK;
                        }
                        else if (adapterFilterType.getPosition("Nome") == pos) {
                            searchOption = ActivityType.NAME;
                            spnFilterBy.setVisibility(View.GONE);
                        }
                        else{
                            searchOption = ActivityType.NONE;
                            spnFilterBy.setVisibility(View.GONE);
                        }
                        break;
                }
                break;
            case R.id.spnFilterBy:
                switch (searchType) {
                    case BOOK:
                        if (filterType == ActivityType.AUTHOR) {
                            selectedAuthor = (Author)parent.getItemAtPosition(pos);
                        } else if (filterType == ActivityType.SERIES) {
                            selectedSeries = (Series)parent.getItemAtPosition(pos);
                        }
                        break;
                    case SERIES:
                        if (filterType == ActivityType.BOOK) {
                           selectedBook = (Book)parent.getItemAtPosition(pos);
                        }
                        break;
                }
                break;
            case R.id.spnOrderType:
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
//        status = exemplar.getStatus();
//        changed = false;
        switch (parent.getId()){
            case R.id.spnSearchType:
                search = "";
                break;
            case R.id.spnFilterType:
                break;
            case R.id.spnFilterBy:
                break;
            case R.id.spnOrderType:
                break;
        }
    }
}
