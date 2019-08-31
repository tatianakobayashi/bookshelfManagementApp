package atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseUser;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class ChangePasswordActivity extends AppCompatActivity {
    private static final String TAG = "ChangePassword";
    private EditText edtPasswordChange;
    private EditText edtPasswordChange2;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        user = (User) getIntent().getExtras().getSerializable("logged_user");

        if(user == null){
            finish();
        }

        edtPasswordChange = findViewById(R.id.edtPasswordChange);
        edtPasswordChange2 = findViewById(R.id.edtPasswordChange2);
        Button btnChangePassword = findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = edtPasswordChange.getText().toString();
                if (password.equals(edtPasswordChange2.getText().toString())){
                    user.setPassword(password);
                    DatabaseUser userDb = new DatabaseUser(ChangePasswordActivity.this);
                    boolean b = userDb.updateUser(user);
                    if (b) {
                        Toast.makeText(ChangePasswordActivity.this, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Senha alterada com sucesso!");
                        Intent intent = new Intent();
                        intent.putExtra("logged_user", user);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
                else {
                    Log.e(TAG, "Senhas diferentes. Limpando campos");
                    edtPasswordChange.setText("");
                    edtPasswordChange2.setText("");
                    Toast.makeText(ChangePasswordActivity.this, "Senhas são diferentes! Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}