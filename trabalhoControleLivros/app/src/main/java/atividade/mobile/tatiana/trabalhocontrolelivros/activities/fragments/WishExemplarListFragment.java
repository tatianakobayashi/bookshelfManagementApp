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
import android.widget.ListView;

import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseExemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseStatus;
import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.ActivityType;
import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.OrderType;
import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.StatusBaseTypes;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Exemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.ChangeStatusActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.EditCommentExemplarActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.alteration.GiveNewClassificationActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.form.ExemplarFormActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.view.ExemplarViewActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.view.ShowExemplarCoverActivity;


public class WishExemplarListFragment extends Fragment {
    private static final String TAG = "WishList";
    private View view;
    private ListView listView;
    private List<Exemplar> exemplars;
    private DatabaseExemplar databaseHelper;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wish_exemplar_list,container,false);

        user = (User) getArguments().getSerializable("logged_user");

        listView = view.findViewById(R.id.wishExemplarListView);

        databaseHelper = new DatabaseExemplar(getContext());

        fillList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getActivity(), ExemplarViewActivity.class);
                it.putExtra("logged_user", user);
                it.putExtra("Exemplar", ((Exemplar)parent.getItemAtPosition(position)));
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
            inflater.inflate(R.menu.exemplar_list_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        int position = info.position;
        Exemplar selectedExemplar = exemplars.get(position);

        Intent intent;
        switch (item.getItemId()){
            case R.id.action_exemplar_context_edit:
                intent = new Intent(getActivity(), ExemplarFormActivity.class);
//                it.putExtra("New", false);
                intent.putExtra("logged_user", user);
                intent.putExtra("Exemplar", selectedExemplar);
                startActivity(intent);
                return true;
            case R.id.action_exemplar_context_delete:
                boolean b = databaseHelper.deleteExemplar(selectedExemplar);
                if (b){
                    Log.i(TAG, "Exemplar deletada com sucesso!");
                    fillList();
                }
                else {
                    Log.e(TAG, "Erro ao deletar exemplar! Tente novamente");
                }
                return true;
            case R.id.action_exemplar_change_status:
                intent = new Intent(getActivity(), ChangeStatusActivity.class);
                intent.putExtra("logged_user", user);
                intent.putExtra("Exemplar", selectedExemplar);
                startActivity(intent);
                return true;
            case R.id.action_exemplar_add_comment:
                intent = new Intent(getActivity(), EditCommentExemplarActivity.class);
                intent.putExtra("logged_user", user);
                intent.putExtra("Exemplar", selectedExemplar);
                startActivity(intent);
                return true;
            case R.id.action_exemplar_view_cover:
                intent = new Intent(getActivity(), ShowExemplarCoverActivity.class);
                intent.putExtra("logged_user", user);
                intent.putExtra("Exemplar", selectedExemplar);
                startActivity(intent);
                return true;
            case R.id.action_exemplar_classificate:
                intent = new Intent(getActivity(), GiveNewClassificationActivity.class);
                intent.putExtra("logged_user", user);
                intent.putExtra("Exemplar", selectedExemplar);
                startActivity(intent);
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
        DatabaseStatus statusDb = new DatabaseStatus(getContext());
        exemplars = databaseHelper.getAllExemplars_ByStatus(user, statusDb.getStatusById(StatusBaseTypes.WISH.value()));
        ArrayAdapter<Exemplar> adapter = new ArrayAdapter<Exemplar>(getActivity(), android.R.layout.simple_list_item_1, exemplars);
        listView.setAdapter(adapter);
    }
}
