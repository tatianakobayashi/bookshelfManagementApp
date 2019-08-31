package atividade.mobile.tatiana.trabalhocontrolelivros.activities.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.AlterAvatarActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.ChangePasswordActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.ForgottenPasswordActivity;

public class SettingsActivity extends AppCompatActivity {
    private User user;
    private int AVATAR_REQUEST = 1;
    private int USER_REQUEST = 2;
    private int PASSWORD_REQUEST = 3;
    // private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("logged_user");

        if(user == null){
            finish();
        }

        Button btnAlterUser = findViewById(R.id.btnAlterUser);
        Button btnChangePassword = findViewById(R.id.btnChangePassword);
        Button btnAlterAvatar = findViewById(R.id.btnAlterAvatar);

        btnAlterAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, AlterAvatarActivity.class);
                intent.putExtra("logged_user", user);
                startActivityForResult(intent, AVATAR_REQUEST);
            }
        });

        btnAlterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ForgottenPasswordActivity.class);
                intent.putExtra("logged_user", user);
                startActivityForResult(intent, USER_REQUEST);
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
                intent.putExtra("logged_user", user);
                startActivityForResult(intent, PASSWORD_REQUEST);
            }
        });
    }

    @Override
    protected void onDestroy(){
        Intent intent = new Intent();
        intent.putExtra("logged_user", user);
        setResult(RESULT_OK, intent);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            user = (User) data.getSerializableExtra("logged_user");
        }
    }
}