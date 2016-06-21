package com.example.japf.coursejun16;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    List<String> names = new ArrayList<String>();
    ArrayAdapter<String> namesListAdapter;

    public MainActivityFragment() { }

    @Override
    public void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menuInflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            Log.e("MainActivity", " !!!! onOptionsItemSelected: GetWebData Selected  !!!! ");
            String data = "Andorra";
            Intent intent = new Intent(getActivity(), WebDataActivity.class)
                    .putExtra(Intent.EXTRA_TEXT, data);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        for(int i = 0; i < 20; i++){
            names.add("Name_" + i);
        }

        namesListAdapter =
                new ArrayAdapter<String>(
                        getActivity(),          // The current context (this activity)
                        R.layout.my_list_row,   // The name of the layout ID.
                        R.id.a_name,      // The ID of the textview to populate.
                        names);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(namesListAdapter);


        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String name = ((TextView) view.findViewById(R.id.a_name))
                                .getText().toString();
                        Toast.makeText(getActivity(), name, Toast.LENGTH_LONG).show();
                    }
                }
        );

        return rootView;
    }
}
