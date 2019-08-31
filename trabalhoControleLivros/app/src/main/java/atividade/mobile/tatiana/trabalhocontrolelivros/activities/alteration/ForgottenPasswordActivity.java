package atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseUser;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class ForgottenPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgottenPassword";
    private EditText edtName;
    private EditText edtUsername;
    private EditText edtEmail;
    private boolean fromLogin;

    private TextView txtTitleForgPass;

    private DatabaseUser userDb;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);

        edtName = findViewById(R.id.edtNameForgPass);
        edtUsername = findViewById(R.id.edtUsernameForgPass);
        edtEmail = findViewById(R.id.edtEmailForgPass);
        txtTitleForgPass = findViewById(R.id.txtTitleForgPass);

        Button btnConfirm = findViewById(R.id.btnForgottenPassword);

        fromLogin = getIntent().getExtras().getBoolean("fromLogin");

        if (fromLogin) {
            txtTitleForgPass.setText(R.string.title_activity_forgotten_password);
        }
        else{
            user = (User) getIntent().getExtras().getSerializable("logged_user");
            txtTitleForgPass.setText("Editar informações de usuário");
            edtName.setText(user.getName());
            edtUsername.setText(user.getUsername());
            edtEmail.setText(user.getEmail());
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDb = new DatabaseUser(ForgottenPasswordActivity.this);
                String name = edtName.getText().toString();
                String username = edtUsername.getText().toString();
                String email = edtEmail.getText().toString();
                if(fromLogin) {
                    user = userDb.getUserByUsername(username);
                    if (user.getName().equals(name) && user.getEmail().equals(email)) {
                        Log.i(TAG, "Usuário existe! Avançando para próxima fase de recuperação do login");
                        Intent intent = new Intent(ForgottenPasswordActivity.this, ChangePasswordActivity.class);
                        intent.putExtra("logged_user", user);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e(TAG, "Dados não conferem. Limpando campos");
                        edtName.setText("");
                        edtEmail.setText("");
                        edtUsername.setText("");
                        Toast.makeText(ForgottenPasswordActivity.this, "Dados não conferem. Tente novamente", Toast.LENGTH_SHORT).show();
                    }
                }
                else{

                    user.setName(name);
                    user.setUsername(username);
                    user.setEmail(email);

                    boolean b = userDb.updateUser(user);

                    if(b){
                        Log.i(TAG, "Usuário atualizado com sucesso!");
                        Intent intent = new Intent();
                        intent.putExtra("logged_user", user);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else{
                        Log.e(TAG, "Erro ao atualizar usuário");
                    }
                }
            }
        });
    }
}