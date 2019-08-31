package atividade.mobile.tatiana.trabalhocontrolelivros.activities.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseAuthor;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseBook;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseSeries;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.RemoveAuthorRelationshipActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.RemoveSeriesRelationshipActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Series;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.AddAuthorRelationshipActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.AddSeriesRelationshipActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Author;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.form.BookFormActivity;

public class BookViewActivity extends AppCompatActivity {
    private TextView txtViewBookId;
    private TextView txtViewBookTitle;
    private TextView txtViewBookGenre;
    private ListView lvViewBookAuthorList;
    private ListView lvViewBookSeriesList;
    private User user;
    private Book book;
    private DatabaseBook helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("logged_user");
        book = (Book) bundle.getSerializable("Book");

        if(user == null || book == null){
            finish();
        }

        helper = new DatabaseBook(this);

        txtViewBookId = findViewById(R.id.txtViewBookId);
        txtViewBookTitle = findViewById(R.id.txtViewBookTitle);
        txtViewBookGenre = findViewById(R.id.txtViewBookGenre);
        lvViewBookAuthorList = findViewById(R.id.lvViewBookAuthorList);
        lvViewBookSeriesList = findViewById(R.id.lvViewBookSeriesList);

        txtViewBookId.setText(String.valueOf(book.getId()));
        txtViewBookTitle.setText(book.getTitle());
        txtViewBookGenre.setText(book.getGenre());

        fillList();
    }

    private void fillList(){
        DatabaseAuthor authorDb = new DatabaseAuthor(this);
        DatabaseSeries seriesDb = new DatabaseSeries(this);
        List<Author> authors = authorDb.getAllAuthors_ByBook(book);
        List<Series> series = seriesDb.getAllSeriesByBook(book);

        ArrayAdapter adapterA = new ArrayAdapter<Author>(this, android.R.layout.simple_list_item_1, authors);
        lvViewBookAuthorList.setAdapter(adapterA);

        ArrayAdapter adapterS = new ArrayAdapter<Series>(this, android.R.layout.simple_list_item_1, series);
        lvViewBookSeriesList.setAdapter(adapterS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_book_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_book_delete) {
            helper.deleteBook(book);
            return true;
        }
        else if (id == R.id.action_book_edit) {
            Intent intent = new Intent(this, BookFormActivity.class);
            intent.putExtra("logged_user", user);
            intent.putExtra("Book", book);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_book_add_author) {
            Intent intent = new Intent(this, AddAuthorRelationshipActivity.class);
            intent.putExtra("logged_user", user);
            intent.putExtra("Book", book);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_book_add_series) {
            Intent intent = new Intent(this, AddSeriesRelationshipActivity.class);
            intent.putExtra("logged_user", user);
            intent.putExtra("Book", book);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_book_del_author) {
            Intent intent = new Intent(this, RemoveAuthorRelationshipActivity.class);
            intent.putExtra("logged_user", user);
            intent.putExtra("Book", book);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_book_del_series) {
            Intent intent = new Intent(this, RemoveSeriesRelationshipActivity.class);
            intent.putExtra("logged_user", user);
            intent.putExtra("Book", book);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        fillList();
        super.onResume();
    }
}