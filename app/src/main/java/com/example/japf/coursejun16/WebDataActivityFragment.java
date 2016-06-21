package com.example.japf.coursejun16;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.net.HttpURLConnection;

/**
 * A placeholder fragment containing a simple view.
 */
public class WebDataActivityFragment extends Fragment {

    String countryQuery ="http://api.geonames.org/countryInfoJSON?formatted=true&lang=it&country=&username=coursejun2016&style=full";
    String jsonCountryData = "Empty Data";
    String countryName = "No Country";

    public WebDataActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_web_data, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String data = intent.getStringExtra(Intent.EXTRA_TEXT);
            countryName = data;
            Log.e("WebDataActivityFragment",
                    "onCreateView ---> countryName = "
                            + countryName +
                            "************************");
            ((TextView) rootView.findViewById(R.id.textData)).setText(data);

            FetchCountryDataTask getDataTask = new FetchCountryDataTask();
            getDataTask.execute();
        }
        return rootView;
    }

    class FetchCountryDataTask extends AsyncTask<Void, Void, String> {
        private final String LOG_TAG = FetchCountryDataTask.class.getSimpleName();

        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            String jsonMsg = null;
            String baseUrl = countryQuery;
            urlConnection = Utils.connect(baseUrl);
            jsonMsg = Utils.getDataAndCloseConnection(urlConnection);
            return jsonMsg;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                jsonCountryData = result;

                try {
                    String data = Utils
                            .getDataForCountryJSON(
                                    jsonCountryData, countryName);
                    Log.e("WebDataActivityFragment",
                            "FetchCountryDataTask:onPostExecute ---> countryName = "
                                    + data
                                    + "************************");
                    ((TextView)getView().findViewById(R.id.textData)).setText(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
