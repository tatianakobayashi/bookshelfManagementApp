package atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseExemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Exemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class EditCommentExemplarActivity extends AppCompatActivity {
    private User user;
    private Exemplar exemplar;
    private EditText edtComment;
    private DatabaseExemplar exemplarDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment_exemplar);

        user = (User) getIntent().getExtras().getSerializable("logged_user");

        if(user == null){
            finish();
        }
        exemplar = (Exemplar) getIntent().getExtras().getSerializable("Exemplar");
        if(exemplar == null){
            finish();
        }

        edtComment = findViewById(R.id.edtAddComments);
        if(exemplar.getComments() != null) {
            edtComment.setText(exemplar.getComments());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            Intent intent = new Intent();
            intent.putExtra("logged_user", user);
            intent.putExtra("Exemplar", exemplar);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        else if (id == R.id.action_save) {
            exemplarDb = new DatabaseExemplar(this);
            exemplar.setComments(edtComment.getText().toString());
            exemplarDb.updateExemplar(exemplar);
            Intent intent = new Intent();
            intent.putExtra("logged_user", user);
            intent.putExtra("Exemplar", exemplar);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}