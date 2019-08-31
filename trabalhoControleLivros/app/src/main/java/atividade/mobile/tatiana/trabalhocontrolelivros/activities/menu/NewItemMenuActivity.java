package atividade.mobile.tatiana.trabalhocontrolelivros.activities.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.ActivityType;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Author;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Exemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Publisher;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Series;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.form.StandardFormActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Status;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.form.BookFormActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.form.ExemplarFormActivity;

public class NewItemMenuActivity extends AppCompatActivity {
    private Button btnNewExemplar;
    private Button btnNewBook;
    private Button bntNewAuthor;
    private Button btnNewSeries;
    private Button btnNewStatus;
    private Button btnNewPublisher;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_menu);

        user = (User) getIntent().getExtras().getSerializable("logged_user");

        if(user == null){
            finish();
        }

        btnNewExemplar = findViewById(R.id.btnNewExemplar);
        btnNewBook = findViewById(R.id.btnNewBook);
        bntNewAuthor = findViewById(R.id.bntNewAuthor);
        btnNewSeries = findViewById(R.id.btnNewSeries);
        btnNewStatus = findViewById(R.id.btnNewStatus);
        btnNewPublisher = findViewById(R.id.btnNewPublisher);

        btnNewExemplar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewItemMenuActivity.this, ExemplarFormActivity.class);
                intent.putExtra("logged_user", user);
                Exemplar exemplar = null;
                intent.putExtra("Exemplar", exemplar);
                startActivity(intent);
            }
        });
        btnNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewItemMenuActivity.this, BookFormActivity.class);
                intent.putExtra("logged_user", user);
                Book book = null;
                intent.putExtra("Book", book);
                startActivity(intent);
            }
        });
        bntNewAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewItemMenuActivity.this, StandardFormActivity.class);
                intent.putExtra("logged_user", user);
                intent.putExtra("Type", ActivityType.AUTHOR);
                Author author = null;
                intent.putExtra("Author", author);
                startActivity(intent);
            }
        });
        btnNewSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewItemMenuActivity.this, StandardFormActivity.class);
                intent.putExtra("logged_user", user);
                intent.putExtra("Type", ActivityType.SERIES);
                Series series = null;
                intent.putExtra("Series", series);
                startActivity(intent);
            }
        });
        btnNewStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewItemMenuActivity.this, StandardFormActivity.class);
                intent.putExtra("logged_user", user);
                intent.putExtra("Type", ActivityType.STATUS);
                Status status = null;
                intent.putExtra("Status", status);
                startActivity(intent);
            }
        });
        btnNewPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewItemMenuActivity.this, StandardFormActivity.class);
                intent.putExtra("logged_user", user);
                intent.putExtra("Type", ActivityType.EDITOR);
                Publisher publisher = null;
                intent.putExtra("Publisher", publisher);
                startActivity(intent);
            }
        });
    }



}