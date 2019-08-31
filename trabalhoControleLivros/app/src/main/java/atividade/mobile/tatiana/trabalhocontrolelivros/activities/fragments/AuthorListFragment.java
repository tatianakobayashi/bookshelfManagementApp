package atividade.mobile.tatiana.trabalhocontrolelivros.activities.fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseAuthor;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.ActivityType;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Author;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.form.StandardFormActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.view.ViewObjectActivity;

public class AuthorListFragment extends Fragment {
    private static final String TAG = "AuthorList";
    private View view;
    private ListView listView;
    private List<Author> authors;
    private DatabaseAuthor databaseHelper;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_author_list,container,false);

        Log.i(TAG, "Criando view");

        user = (User) getArguments().getSerializable("logged_user");

        listView = view.findViewById(R.id.authorListView);

        databaseHelper = new DatabaseAuthor(getContext());

        fillList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "Indo para ViewObject");
                Intent it = new Intent(getActivity(), ViewObjectActivity.class);
                it.putExtra("logged_user", user);
                it.putExtra("Type", ActivityType.AUTHOR);
                it.putExtra("Author", ((Author)parent.getItemAtPosition(position)));
                startActivity(it);
            }
        });

        registerForContextMenu(listView);

        return view;
    }

    private void fillList(){
        authors = databaseHelper.getAllAuthors();

        ListAdapter adapter = new ArrayAdapter<Author>(getContext(), android.R.layout.simple_list_item_1, authors);
        listView.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == listView.getId()) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.list_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        int position = info.position;
        Author selectedAuthor = authors.get(position);

        switch (item.getItemId()){
            case R.id.action_context_edit:
                Log.i(TAG, "Indo para StandardForm");
                Intent it = new Intent(getActivity(), StandardFormActivity.class);
//                it.putExtra("New", false);
                it.putExtra("logged_user", user);
                it.putExtra("Type", ActivityType.AUTHOR);
                it.putExtra("Author", selectedAuthor);
                startActivity(it);
                return true;
            case R.id.action_context_delete:
                boolean b = databaseHelper.deleteAuthor(selectedAuthor);
                if (b){
                    Log.i(TAG, "Autor deletado com sucesso!");
                    fillList();
                }
                else {
                    Log.e(TAG, "Erro ao deletar autor! Tente novamente");
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fillList();
    }
}