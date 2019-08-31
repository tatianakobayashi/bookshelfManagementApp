package atividade.mobile.tatiana.trabalhocontrolelivros.activities.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.form.StandardFormActivity;

public class ViewObjectActivity extends AppCompatActivity {
    private TextView txtViewObjectId;
    private TextView txtViewObjectName;
    private ActivityType type;
    private Author author;
    private Series series;
    private Status status;
    private Publisher publisher;
    private User user;

    private static final String TAG = "ViewObject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_object);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("logged_user");
        type = (ActivityType) bundle.getSerializable("Type");
        // Autor, Coleção, Status, Editora

        if (user == null || type == null) {
            finish();
        }

        txtViewObjectId = findViewById(R.id.txtViewObjectId);
        txtViewObjectName = findViewById(R.id.txtViewObjectName);

        switch (type) {
            case AUTHOR:
                author = (Author) bundle.getSerializable("Author");
                if (author == null) finish();
                txtViewObjectId.setText(String.valueOf(author.getId()));
                txtViewObjectName.setText(author.getName());
                break;
            case SERIES:
                series = (Series) bundle.getSerializable("Series");
                if (series == null) finish();
                txtViewObjectId.setText(String.valueOf(series.getId()));
                txtViewObjectName.setText(series.getName());
                break;
            case STATUS:
                status = (Status) bundle.getSerializable("Status");
                if (status == null) finish();
                txtViewObjectId.setText(String.valueOf(status.getId()));
                txtViewObjectName.setText(status.getName());
                break;
            case EDITOR:
                publisher = (Publisher) bundle.getSerializable("Publisher");
                if (publisher == null) finish();
                txtViewObjectId.setText(String.valueOf(publisher.getId()));
                txtViewObjectName.setText(publisher.getName());
                break;
            default:
                finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
//            DatabaseHelper helper  = DatabaseHelper.getInstance(this);
            switch (type) {
                case AUTHOR:
                    DatabaseAuthor authorDb = new DatabaseAuthor(this);
                    authorDb.deleteAuthor(author);
                    break;
                case SERIES:
                    DatabaseSeries seriesDb = new DatabaseSeries(this);
                    seriesDb.deleteSeries(series);
                    break;
                case STATUS:
                    DatabaseStatus statusDb = new DatabaseStatus(this);
                    statusDb.deleteStatus(status);
                    break;
                case EDITOR:
                    DatabasePublisher publisherDb = new DatabasePublisher(this);
                    publisherDb.deletePublisher(publisher);
                    break;
            }
            finish();
            return true;
        } else if (id == R.id.action_edit) {
            Log.i(TAG, "Indo para StandardForm");
            Intent intent = new Intent(this, StandardFormActivity.class);
            intent.putExtra("logged_user", user);
            intent.putExtra("Type", type);
            switch (type) {
                case AUTHOR:
                    intent.putExtra("Author", author);
                    break;
                case SERIES:
                    intent.putExtra("Series", series);
                    break;
                case STATUS:
                    intent.putExtra("Status", status);
                    break;
                case EDITOR:
                    intent.putExtra("Publisher", publisher);
                    break;
            }
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (type) {
            case AUTHOR:
                DatabaseAuthor authorDb = new DatabaseAuthor(this);
                author = authorDb.getAuthorById(author.getId());
                txtViewObjectId.setText(String.valueOf(author.getId()));
                txtViewObjectName.setText(author.getName());
                break;
            case SERIES:
                DatabaseSeries seriesDb = new DatabaseSeries(this);
                series = seriesDb.getSeriesById(series.getId());
                txtViewObjectId.setText(String.valueOf(series.getId()));
                txtViewObjectName.setText(series.getName());
                break;
            case STATUS:
                DatabaseStatus statusDb = new DatabaseStatus(this);
                status = statusDb.getStatusById(status.getId());
                txtViewObjectId.setText(String.valueOf(status.getId()));
                txtViewObjectName.setText(status.getName());
                break;
            case EDITOR:
                DatabasePublisher publisherDb = new DatabasePublisher(this);
//                Log.i(TAG, "buscando editora de id " + publisher.getId());
                publisher = publisherDb.getPublisherById(publisher.getId());
//                Log.i(TAG, "Editora: " + publisher);
                txtViewObjectId.setText(String.valueOf(publisher.getId()));
                txtViewObjectName.setText(publisher.getName());
                break;
        }
    }
}