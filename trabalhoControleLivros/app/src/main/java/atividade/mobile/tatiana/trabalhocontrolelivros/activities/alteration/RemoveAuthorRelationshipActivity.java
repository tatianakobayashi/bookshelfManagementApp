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
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Author;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class RemoveAuthorRelationshipActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private User user;
    private Book book;
    private Author author;
    private DatabaseAuthor helper;
    private boolean changed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_author_relationship);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("logged_user");
        book = (Book) bundle.getSerializable("Book");
        if(user == null || book == null){
            finish();
        }

        Spinner spinner = findViewById(R.id.spnRemoveAuthorRelationship);
        Button button = findViewById(R.id.btnRemoveAuthorRelationship);

        helper = new DatabaseAuthor(this);
        List<Author> authors = helper.getAllAuthors_ByBook(book);

        ArrayAdapter<Author> adapter = new ArrayAdapter<Author>(this, android.R.layout.simple_spinner_item, authors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changed) {
                    DatabaseAuthorRelations relations = new DatabaseAuthorRelations(RemoveAuthorRelationshipActivity.this);
                    relations.deleteAuthorRelationship(author, book);
                }
                finish();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        author = (Author) parent.getItemAtPosition(pos);
        changed = true;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        changed = false;
    }
}