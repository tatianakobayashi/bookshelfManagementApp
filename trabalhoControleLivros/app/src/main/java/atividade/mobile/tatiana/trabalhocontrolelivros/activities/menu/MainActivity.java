package atividade.mobile.tatiana.trabalhocontrolelivros.activities.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseUser;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.form.NewUserActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.ForgottenPasswordActivity;

public class MainActivity extends AppCompatActivity {
    private EditText edtUser;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView txtForgotPassword;
    private TextView txtNewUser;
    private DatabaseUser helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        helper = new DatabaseUser(this);

        edtUser = findViewById(R.id.edtLoginUserName);
        edtPassword = findViewById(R.id.edtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtNewUser = findViewById(R.id.txtNewUser);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Botão apertado!", Toast.LENGTH_LONG);
                Log.i("LOGIN", "Botão apertado");
                User user = helper.getUserByUsername(edtUser.getText().toString());
                if (user.getPassword().equals(edtPassword.getText().toString())){
                    // Se forem iguais, abrir menu principal;
                    Log.i("LOGIN", "Fazendo login");
                    Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                    intent.putExtra("logged_user", user);
//                intent.putExtra("logged_user", new User("", "", edtUser.getText().toString(), edtPassword.getText().toString()));
                    startActivity(intent);
                }
                else{
                    // Senão, mostrar toast e limpar edits
                    Log.e("LOGIN", "Usuário ou senha errados");
                    edtUser.setText("");
                    edtPassword.setText("");
                    Toast.makeText(MainActivity.this, "Usuário ou senha incorreta. Tente novamente!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Esqueci a senha!", Toast.LENGTH_LONG);
                Intent intent = new Intent(MainActivity.this, ForgottenPasswordActivity.class);
                intent.putExtra("fromLogin", true);
                startActivity(intent);
            }
        });

        txtNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Novo usuário!", Toast.LENGTH_LONG);
                startActivity(new Intent(MainActivity.this, NewUserActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit_app) {
            finish();
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
