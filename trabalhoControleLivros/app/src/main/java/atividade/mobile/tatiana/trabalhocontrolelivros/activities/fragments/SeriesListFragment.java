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

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseSeries;
import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.ActivityType;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Series;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.form.StandardFormActivity;
import atividade.mobile.tatiana.trabalhocontrolelivros.activities.view.ViewObjectActivity;

public class SeriesListFragment extends Fragment {
    private static final String TAG = "SeriesList";
    private View view;
    private ListView listView;
    private List<Series> series;
    private DatabaseSeries databaseHelper;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_series_list,container,false);

        Log.i(TAG, "Criando view");

        user = (User) getArguments().getSerializable("logged_user");

        listView = view.findViewById(R.id.seriesListView);

        databaseHelper = new DatabaseSeries(getContext());

        fillList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "Indo para ViewObject");
                Intent it = new Intent(getActivity(), ViewObjectActivity.class);
                it.putExtra("logged_user", user);
                it.putExtra("Type", ActivityType.SERIES);
                it.putExtra("Series", ((Series)parent.getItemAtPosition(position)));
                startActivity(it);
            }
        });

        registerForContextMenu(listView);

        return view;
    }

    private void fillList(){
        series = databaseHelper.getAllSeries();

        ListAdapter adapter = new ArrayAdapter<Series>(getContext(), android.R.layout.simple_list_item_1, series);
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
        Series selectedSeries = series.get(position);

        switch (item.getItemId()){
            case R.id.action_context_edit:
                Log.i(TAG, "Indo para StandardForm");
                Intent it = new Intent(getActivity(), StandardFormActivity.class);
//                it.putExtra("New", false);
                it.putExtra("logged_user", user);
                it.putExtra("Type", ActivityType.SERIES);
                it.putExtra("Series", selectedSeries);
                startActivity(it);
                return true;
            case R.id.action_context_delete:
                boolean b = databaseHelper.deleteSeries(selectedSeries);
                if (b){
                    Log.i(TAG, "Coleção deletada com sucesso!");
                    fillList();
                }
                else {
                    Log.e(TAG, "Erro ao deletar coleção! Tente novamente");
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