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

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseBook;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.view.BookViewActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.form.BookFormActivity;

public class BookListFragment extends Fragment {
    private static final String TAG = "BookList";
    private View view;
    private ListView listView;
    private List<Book> books;
    private DatabaseBook helper;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wish_exemplar_list,container,false);

        Log.i(TAG, "Criando view");

        user = (User) getArguments().getSerializable("logged_user");

        listView = view.findViewById(R.id.wishExemplarListView);

        helper = new DatabaseBook(getContext());

        books = helper.getAllBooks();

        ListAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, books);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getActivity(), BookViewActivity.class);
                it.putExtra("logged_user", user);
                it.putExtra("Book", ((Book)parent.getItemAtPosition(position)));
                startActivity(it);
            }
        });

        registerForContextMenu(listView);

        return view;
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
        Book selectedBook = books.get(position);

        switch (item.getItemId()){
            case R.id.action_context_edit:
                Intent it = new Intent(getActivity(), BookFormActivity.class);
//                it.putExtra("New", false);
                it.putExtra("logged_user", user);
                it.putExtra("Book", selectedBook);
                startActivity(it);
                return true;
            case R.id.action_context_delete:
                boolean b = helper.deleteBook(selectedBook);
                if (b){
                    Log.i(TAG, "Livro deletada com sucesso!");
                    fillList();
                }
                else {
                    Log.e(TAG, "Erro ao deletar livro! Tente novamente");
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

    private void fillList(){
        books = helper.getAllBooks();
        ArrayAdapter<Book> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, books);
        listView.setAdapter(adapter);
    }
}
