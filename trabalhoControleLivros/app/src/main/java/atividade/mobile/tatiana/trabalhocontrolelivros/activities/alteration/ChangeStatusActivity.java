package atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseExemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseStatus;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Exemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Status;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class ChangeStatusActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private User user;
    private Exemplar exemplar;
    private Status status;
//    private DatabaseHelper helper;
    private DatabaseStatus statusDb;
    private DatabaseExemplar exemplarDb;
    private boolean changed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("logged_user");
        if(user == null){
            finish();
        }

        exemplar = (Exemplar) bundle.getSerializable("Exemplar");
        status = exemplar.getStatus();

        Spinner spinner = findViewById(R.id.spnStatusChange);
        Button button = findViewById(R.id.btnStatusChange);

        statusDb = new DatabaseStatus(this);
        exemplarDb = new DatabaseExemplar(this);
        List<Status> statuses = statusDb.getAllStatus();

        ArrayAdapter<Status> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changed) {
                    exemplar.setStatus(status);
                    if(status.getId() == 4){
                        exemplar.setTimesLent(exemplar.getTimesLent()+1);
                    }
                    else if(status.getId() == 2){
                        exemplar.setTimesRead(exemplar.getTimesRead()+1);
                    }
                    exemplarDb.updateExemplar(exemplar);
                }
                Intent intent = new Intent();
                intent.putExtra("logged_user", user);
                intent.putExtra("Exemplar", exemplar);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        status = (Status) parent.getItemAtPosition(pos);
        changed = true;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        status = exemplar.getStatus();
        changed = false;
    }
}