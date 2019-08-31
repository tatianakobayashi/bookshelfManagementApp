package atividade.mobile.tatiana.trabalhocontrolelivros.activities.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseUser;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class MainMenuActivity extends AppCompatActivity {
    private static final int ACTION_SETTINGS = 1;
    private Button btnToListMenu;
    private Button btnAddNewItem;
    private Button btnToUserMenu;
    private Button btnToSettings;
    private Button btnToSearch;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnToListMenu = findViewById(R.id.btnToListMenu);
        btnAddNewItem = findViewById(R.id.btnAddNewItems);
        btnToUserMenu = findViewById(R.id.btnToUserMenu);
        btnToSettings = findViewById(R.id.btnToSettings);
        btnToSearch = findViewById(R.id.btnToSearch);

        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("logged_user");
        if(user == null){
            finish();
        }

        btnToListMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, ListActivity.class);
                intent.putExtra("logged_user", user);
                startActivity(intent);
            }
        });

        btnAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, NewItemMenuActivity.class);
                intent.putExtra("logged_user", user);
                startActivity(intent);
            }
        });

        btnToUserMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, UserMenuActivity.class);
                intent.putExtra("logged_user", user);
                startActivity(intent);
            }
        });

        btnToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
                intent.putExtra("logged_user", user);
                startActivityForResult(intent, ACTION_SETTINGS);
            }
        });

        btnToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, SearchActivity.class);
                intent.putExtra("logged_user", user);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            user = null;
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            user = (User) data.getSerializableExtra("logged_user");
        }
    }

    @Override
    protected void onResume() {
        DatabaseUser helper = new DatabaseUser(this);
        user = helper.getUserById(user.getId());
        super.onResume();
    }
}