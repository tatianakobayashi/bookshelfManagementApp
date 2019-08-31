package atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseSeries;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseSeriesRelations;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Series;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class AddSeriesRelationshipActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private User user;
    private Book book;
    private Series serie;
//    private DatabaseHelper helper;
    private DatabaseSeries seriesDb;
    private DatabaseSeriesRelations relationsDb;
    private boolean changed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_series_relationship);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("logged_user");
        book = (Book) bundle.getSerializable("Book");
        if(user == null || book == null){
            finish();
        }

        Spinner spinner = findViewById(R.id.spnAddSeriesRelationship);
        Button button = findViewById(R.id.btnAddSeriesRelationship);

        seriesDb = new DatabaseSeries(this);
        relationsDb = new DatabaseSeriesRelations(this);
        List<Series> series = seriesDb.getAllSeries();

        ArrayAdapter<Series> adapter = new ArrayAdapter<Series>(this, android.R.layout.simple_spinner_item, series);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changed) {
                    relationsDb.insertSeriesRelationship(serie, book);
                }
                finish();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        serie = (Series) parent.getItemAtPosition(pos);
        changed = true;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        changed = false;
    }
}