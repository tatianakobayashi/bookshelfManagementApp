package atividade.mobile.tatiana.trabalhocontrolelivros.activities.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseExemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Exemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.ChangeStatusActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.EditCommentExemplarActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.GiveNewClassificationActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.form.ExemplarFormActivity;

public class ExemplarViewActivity extends AppCompatActivity {
    private User user;
    private Exemplar exemplar;

    private static final int ACTION_STATUS = 1;
    private static final int ACTION_COMMENT = 2;
    private static final int ACTION_CLASSIFICATE = 3;

    private TextView txtId;
    private TextView txtBookTitle;
    private TextView txtStatus;
    private TextView txtEditor;
    private TextView txtEdition;
    private TextView txtLanguage;
    private TextView txtPages;
    private TextView txtCurrentPage;
    private TextView txtBookType;
    private TextView txtTimesRead;
    private TextView txtTimesLent;
    private TextView txtLastClassification;
    private TextView txtClassificationMean;
    private TextView txtComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exemplar_view);

        user = (User) getIntent().getExtras().getSerializable("logged_user");

        if(user == null){
            finish();
        }
        exemplar = (Exemplar) getIntent().getExtras().getSerializable("Exemplar");
        if(exemplar == null){
            finish();
        }

        txtId = findViewById(R.id.txtViewExemplarId);
        txtBookTitle = findViewById(R.id.txtViewExemplarBookTitle);
        txtStatus = findViewById(R.id.txtViewExemplarStatus );
        txtEditor = findViewById(R.id.txtViewExemplarEditor);
        txtEdition = findViewById(R.id.txtViewExemplarEdition);
        txtLanguage = findViewById(R.id.txtViewExemplarLanguage);
        txtPages = findViewById(R.id.txtViewExemplarPages);
        txtCurrentPage = findViewById(R.id.txtViewExemplarCurrentPage);
        txtBookType = findViewById(R.id.txtViewExemplarBookType);
        txtTimesRead = findViewById(R.id.txtViewExemplarTimesRead);
        txtTimesLent = findViewById(R.id.txtViewExemplarTimesLent);
        txtLastClassification = findViewById(R.id.txtViewExemplarLastClassification);
        txtClassificationMean = findViewById(R.id.txtViewExemplarClassificationMean);
        txtComments = findViewById(R.id.txtViewExemplarComments);

        setText();
    }

    private void setText(){
        txtId.setText(String.valueOf(exemplar.getId()));
        if (exemplar.getBook() != null)
            txtBookTitle.setText(exemplar.getBook().getTitle());
        if(exemplar.getStatus() != null)
            txtStatus.setText(exemplar.getStatus().getName());
        if (exemplar.getPublisher() != null)
            txtEditor.setText(exemplar.getPublisher().getName());
        txtEdition.setText(String.valueOf(exemplar.getEdition()));
        txtLanguage.setText(exemplar.getLanguage());
        txtPages.setText(String.valueOf(exemplar.getPages()));
        txtCurrentPage.setText(String.valueOf(exemplar.getCurrentPage()));
        txtBookType.setText(exemplar.getBookType().typeName());
        txtTimesRead.setText(String.valueOf(exemplar.getTimesRead()));
        txtTimesLent.setText(String.valueOf(exemplar.getTimesLent()));
        txtLastClassification.setText(String.valueOf(exemplar.getClassificationLast()));
        txtClassificationMean.setText(String.valueOf(exemplar.getClassificationMean()));
        txtComments.setText(exemplar.getComments());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_exemplar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exemplar_options_edit) {
            Intent intent = new Intent(this, ExemplarFormActivity.class);
            intent.putExtra("logged_user", user);
            intent.putExtra("Exemplar", exemplar);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_exemplar_options_delete) {
            DatabaseExemplar helper = new DatabaseExemplar(this);
            helper.deleteExemplar(exemplar);
            finish();
            return true;
        }
        else if (id == R.id.action_view_change_status) {
            Intent intent = new Intent(this, ChangeStatusActivity.class);
            intent.putExtra("logged_user", user);
            intent.putExtra("Exemplar", exemplar);
            startActivityForResult(intent, ACTION_STATUS);
            return true;
        }
        else if (id == R.id.action_view_add_comment) {
            Intent intent = new Intent(this, EditCommentExemplarActivity.class);
            intent.putExtra("logged_user", user);
            intent.putExtra("Exemplar", exemplar);
            startActivityForResult(intent, ACTION_COMMENT);
            return true;
        }
        else if (id == R.id.action_view_cover) {
            Intent intent = new Intent(this, ShowExemplarCoverActivity.class);
            intent.putExtra("logged_user", user);
            intent.putExtra("Exemplar", exemplar);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_view_classificate) {
            Intent intent = new Intent(this, GiveNewClassificationActivity.class);
            intent.putExtra("logged_user", user);
            intent.putExtra("Exemplar", exemplar);
            startActivityForResult(intent, ACTION_CLASSIFICATE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            exemplar = (Exemplar) data.getSerializableExtra("Exemplar");
            setText();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseExemplar helper = new DatabaseExemplar(this);
        exemplar = helper.getExemplarById(exemplar.getId());
        setText();
    }
}