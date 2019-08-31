package atividade.mobile.tatiana.trabalhocontrolelivros.activities.form;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseBook;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class BookFormActivity extends AppCompatActivity {
    private User user;
    private Book book;
    private DatabaseBook helper;
    private LinearLayout linLayBookFormID;
    private TextView edtIdBookForm;
    private EditText edtTitleBookForm;
    private EditText edtGenreBookForm;
    private boolean newBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_form);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("logged_user");
        book = (Book) bundle.getSerializable("Book");

        if(user == null){
            finish();
        }

        helper = new DatabaseBook(this);

        linLayBookFormID = findViewById(R.id.linLayBookFormID);
        edtIdBookForm = findViewById(R.id.edtIdBookForm);
        edtTitleBookForm = findViewById(R.id.edtTitleBookForm);
        edtGenreBookForm = findViewById(R.id.edtGenreBookForm);

        if(book == null){
            book = new Book("", "");
            newBook = true;
            linLayBookFormID.setVisibility(View.GONE);
        }
        else{
            linLayBookFormID.setVisibility(View.VISIBLE);
            edtIdBookForm.setText(String.valueOf(book.getId()));
            edtTitleBookForm.setText(book.getTitle());
            edtGenreBookForm.setText(book.getGenre());
        }
    }

    private void saveBook(){
        String title = edtTitleBookForm.getText().toString();
        String genre = edtGenreBookForm.getText().toString();

        book.setTitle(title);
        book.setGenre(genre);

        if(newBook){
            helper.insertBook(book);
        }
        else{
            helper.updateBook(book);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancel) {
            finish();
            return true;
        }
        else if (id == R.id.action_save) {
            saveBook();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}