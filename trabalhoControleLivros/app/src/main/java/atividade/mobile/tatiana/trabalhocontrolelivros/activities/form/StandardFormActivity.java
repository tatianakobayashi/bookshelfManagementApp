package atividade.mobile.tatiana.trabalhocontrolelivros.activities.form;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseAuthor;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabasePublisher;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseSeries;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseStatus;
import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.ActivityType;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Author;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Publisher;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Series;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Status;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;

public class StandardFormActivity extends AppCompatActivity {
    private LinearLayout linLayStandardFormID;
    private TextView edtIdStandardForm;
    private EditText edtStandardFormName;
    private ActivityType type;
    private User user;

    private Author author;
    private Series series;
    private Status status;
    private Publisher publisher;

    private boolean newObj;
//    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_form);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("logged_user");
        type = (ActivityType) bundle.getSerializable("Type");

        if(user == null){
            finish();
        }

//        helper = DatabaseHelper.getInstance(this);

        linLayStandardFormID = findViewById(R.id.linLayStandardFormID);
        edtIdStandardForm = findViewById(R.id.edtIdStandardForm);
        edtStandardFormName = findViewById(R.id.edtStandardFormName);

        switch(type){
            case AUTHOR:
                author = (Author) bundle.getSerializable("Author");
                if(author == null){
                    newObj = true;
                    linLayStandardFormID.setVisibility(View.GONE);
                    author = new Author("");
                }
                else{
                    newObj = false;
                    linLayStandardFormID.setVisibility(View.VISIBLE);
                    edtIdStandardForm.setText(String.valueOf(author.getId()));
                    edtStandardFormName.setText(author.getName());
                }
                break;
            case SERIES:
                series = (Series) bundle.getSerializable("Series");
                if(series == null){
                    newObj = true;
                    linLayStandardFormID.setVisibility(View.GONE);
                    series = new Series("");
                }
                else{
                    newObj = false;
                    linLayStandardFormID.setVisibility(View.VISIBLE);
                    edtIdStandardForm.setText(String.valueOf(series.getId()));
                    edtStandardFormName.setText(series.getName());
                }
                break;
            case STATUS:
                status = (Status) bundle.getSerializable("Status");
                if(status == null){
                    newObj = true;
                    linLayStandardFormID.setVisibility(View.GONE);
                    status = new Status("");
                }
                else{
                    newObj = false;
                    linLayStandardFormID.setVisibility(View.VISIBLE);
                    edtIdStandardForm.setText(String.valueOf(status.getId()));
                    edtStandardFormName.setText(status.getName());
                }
                break;
            case EDITOR:
                publisher = (Publisher) bundle.getSerializable("Publisher");
                if(publisher == null){
                    newObj = true;
                    linLayStandardFormID.setVisibility(View.GONE);
                    publisher = new Publisher("");
                }
                else{
                    newObj = false;
                    linLayStandardFormID.setVisibility(View.VISIBLE);
                    edtIdStandardForm.setText(String.valueOf(publisher.getId()));
                    edtStandardFormName.setText(publisher.getName());
                }
                break;
            default:
                finish();
                break;
        }
    }

    private void saveObject(){
        String name = edtStandardFormName.getText().toString();

        switch(type){
            case AUTHOR:
                DatabaseAuthor authorDb = new DatabaseAuthor(this);
                author.setName(name);
                if(newObj){
                    authorDb.insertAuthor(author);
                }
                else{
                    authorDb.updateAuthor(author);
                }
                break;
            case SERIES:
                DatabaseSeries seriesDb = new DatabaseSeries(this);
                series.setName(name);
                if(newObj){
                    seriesDb.insertSeries(series);
                }
                else{
                    seriesDb.updateSeries(series);
                }
                break;
            case STATUS:
                DatabaseStatus statusDb = new DatabaseStatus(this);
                status.setName(name);
                if(newObj){
                    statusDb.insertStatus(status);
                }
                else{
                    statusDb.updateStatus(status);
                }
                break;
            case EDITOR:
                DatabasePublisher publisherDb = new DatabasePublisher(this);
                publisher.setName(name);
                if(newObj){
                    publisherDb.insertPublisher(publisher);
                }
                else{
                    publisherDb.updatePublisher(publisher);
                }
                break;
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
            saveObject();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}