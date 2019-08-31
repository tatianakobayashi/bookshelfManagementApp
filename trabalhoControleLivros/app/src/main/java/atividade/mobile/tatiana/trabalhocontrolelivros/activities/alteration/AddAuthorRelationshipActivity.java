package atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseAuthor;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseAuthorRelations;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Author;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class AddAuthorRelationshipActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private User user;
    private Book book;
    private Author author;
//    private DatabaseHelper helper;
    private DatabaseAuthor authorDb;
    private DatabaseAuthorRelations authorRelations;
    private boolean changed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author_relationship);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("logged_user");
        book = (Book) bundle.getSerializable("Book");
        if (user == null || book == null) {
            finish();
        }

        Spinner spinner = findViewById(R.id.spnAddAuthorRelationship);
        Button button = findViewById(R.id.btnAddAuthorRelationship);

        authorDb = new DatabaseAuthor(this);
        authorRelations = new DatabaseAuthorRelations(this);
        List<Author> authors = authorDb.getAllAuthors();

        ArrayAdapter<Author> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, authors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changed) {
                    authorRelations.insertAuthorRelationship(author, book);
                }
                finish();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        author = (Author) parent.getItemAtPosition(pos);
        changed = true;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        changed = false;
    }
}