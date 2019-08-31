package atividade.mobile.tatiana.trabalhocontrolelivros.activities.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;

public class UserMenuActivity extends AppCompatActivity {
    private User user;
    private ImageView imgAvatar;
    private TextView txtNameUserMenu;
    private TextView txtUsernameUserMenu;
    private TextView txtEmailUserMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("logged_user");
        if(user == null){
            finish();
        }

        imgAvatar = findViewById(R.id.imgAvatar);
        txtNameUserMenu = findViewById(R.id.txtNameUserMenu);
        txtUsernameUserMenu = findViewById(R.id.txtUsernameUserMenu);
        txtEmailUserMenu = findViewById(R.id.txtEmailUserMenu);

        txtNameUserMenu.setText(user.getName());
        txtUsernameUserMenu.setText(user.getUsername());
        txtEmailUserMenu.setText(user.getEmail());

        if(user.getStandardIcon() == null){
            user.setStandardIcon("Android");
        }

        if (user.getStandardIcon().equals("Snow")){
            imgAvatar.setImageDrawable(getDrawable(R.drawable.snow_avatar));
        }
        else if (user.getStandardIcon().equals("Sun")){
            imgAvatar.setImageDrawable(getDrawable(R.drawable.sun_avatar));
        }
        else if (user.getStandardIcon().equals("Flower")){
            imgAvatar.setImageDrawable(getDrawable(R.drawable.flower_avatar));
        }
        else if (user.getStandardIcon().equals("Car")){
            imgAvatar.setImageDrawable(getDrawable(R.drawable.car_avatar));
        }
        else {
            imgAvatar.setImageDrawable(getDrawable(R.drawable.android_avatar));
        }
    }
}
