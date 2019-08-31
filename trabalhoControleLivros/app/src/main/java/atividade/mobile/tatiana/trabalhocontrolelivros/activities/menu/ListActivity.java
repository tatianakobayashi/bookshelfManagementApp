package atividade.mobile.tatiana.trabalhocontrolelivros.activities.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import atividade.mobile.tatiana.trabalhocontrolelivros.activities.fragments.LentExemplarListFragment;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.fragments.OtherExemplarListFragment;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.fragments.PublisherListFragment;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.fragments.ReadingExemplarListFragment;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.fragments.SeriesListFragment;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.fragments.StatusListFragment;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.fragments.StoredExemplarListFragment;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.TabAdapter;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.fragments.WishExemplarListFragment;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.fragments.AuthorListFragment;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.fragments.BookListFragment;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";
    private TabAdapter adapter;
    private ViewPager mViewPager;
    private FloatingActionButton fabAdd;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Log.d(TAG, "onCreate: Starting.");
        user = (User) getIntent().getExtras().getSerializable("logged_user");

        adapter = new TabAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.viewPagerList);
        setupViewPager(mViewPager);

        fabAdd = findViewById(R.id.fabList);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, NewItemMenuActivity.class);
                intent.putExtra("logged_user", user);
                startActivity(intent);
            }
        });
    }


    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("logged_user", user);

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        Fragment wish = new WishExemplarListFragment();
        Fragment read = new ReadingExemplarListFragment();
        Fragment stored = new StoredExemplarListFragment();
        Fragment lent = new LentExemplarListFragment();
        Fragment other = new OtherExemplarListFragment();
        Fragment bookTab = new BookListFragment();
        Fragment authorTab = new AuthorListFragment();
        Fragment seriesTab = new SeriesListFragment();
        Fragment statusTab = new StatusListFragment();
        Fragment pubTab = new PublisherListFragment();

        wish.setArguments(bundle);
        read.setArguments(bundle);
        stored.setArguments(bundle);
        lent.setArguments(bundle);
        other.setArguments(bundle);
        bookTab.setArguments(bundle);
        authorTab.setArguments(bundle);
        seriesTab.setArguments(bundle);
        statusTab.setArguments(bundle);
        pubTab.setArguments(bundle);

        adapter.addFragment(bookTab, "Livros");
        adapter.addFragment(authorTab, "Autores");
        adapter.addFragment(seriesTab, "Coleções");
        adapter.addFragment(statusTab, "Tipos de Status");
        adapter.addFragment(pubTab, "Editoras");
        adapter.addFragment(wish, "Desejados");
        adapter.addFragment(read, "Lendo");
        adapter.addFragment(stored, "Parados");
        adapter.addFragment(lent, "Emprestados");
        adapter.addFragment(other, "Outros");
        viewPager.setAdapter(adapter);
    }

}