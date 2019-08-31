package atividade.mobile.tatiana.trabalhocontrolelivros.activities.form;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseUser;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class NewUserActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        edtName = findViewById(R.id.edtNewName);
        edtEmail = findViewById(R.id.edtNewEmail);
        edtUsername = findViewById(R.id.edtNewUsername);
        edtPassword = findViewById(R.id.edtNewPassword);
        edtPassword2 = findViewById(R.id.edtNewPassword2);

        Button btnNewUser = findViewById(R.id.btnCreateNewUser);

        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String password2 = edtPassword2.getText().toString();

                if(password.equals(password2)){
                    User user = new User(name, email, username, password);
                    DatabaseUser helper = new DatabaseUser(NewUserActivity.this);

                    boolean x =  helper.insertUser(user);
                    if (x){
                        Log.i("NewUser", "Usuário criado com sucesso!");
                        // Toast.makeText(NewUserActivity.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Log.e("NewUser", "Erro ao criar usuário!");
                        // Toast.makeText(NewUserActivity.this, "Erro ao criar usuário. Tente novamente", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    edtPassword.setText("");
                    edtPassword2.setText("");
                    Log.e("NewUser", "Senhas diferentes!");
                    // Toast.makeText(NewUserActivity.this, "Senhas não são iguais!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}