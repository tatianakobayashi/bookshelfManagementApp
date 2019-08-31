package atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseUser;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class AlterAvatarActivity extends AppCompatActivity {

    private User user;
//    private DatabaseHelper helper;
    private DatabaseUser userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_avatar);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("logged_user");

        if(user == null){
            finish();
        }

        Button btnAndroidAv = findViewById(R.id.btnAndroidAv);
        Button btnSnowAv = findViewById(R.id.btnSnowAv);
        Button btnSunAv = findViewById(R.id.btnSunAv);
        Button btnFlowerAv = findViewById(R.id.btnFlowerAv);
        Button btnCarAv = findViewById(R.id.btnCarAv);

        userDb = new DatabaseUser(this);


        btnAndroidAv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity("Android");
            }
        });

        btnSnowAv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity("Snow");
            }
        });

        btnSunAv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity("Sun");
            }
        });

        btnFlowerAv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity("Flower");
            }
        });

        btnCarAv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity("Car");
            }
        });
    }

    private void finishActivity(String avatar){
        user.setStandardIcon(avatar);
        userDb.updateUser(user);
        Intent intent = new Intent();
        intent.putExtra("logged_user", user);
        setResult(RESULT_OK, intent);
        finish();
    }
}