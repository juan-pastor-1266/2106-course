package com.example.japf.coursejun16;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class Utils{

    /**
     * Connect to a resource (URL) and returns an object representing the connection.
     * @param baseUrl url to connect (complete path to the resource)
     * @return a connection on success, null otherwise.
     */
	public static HttpURLConnection connect(String baseUrl) {
        HttpURLConnection urlConnection = null;

        try {
                URL url = new URL(baseUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
            } catch (IOException e) {
                e.printStackTrace();
                urlConnection = null;
            }
            return urlConnection;
        }

    /**
     * Use a connection (to a resource) and gets the data.
     * @param urlConnection connection to use
     * @return Retrieved data on success, null otherwise.
     */
    public static String getDataAndCloseConnection(HttpURLConnection urlConnection) {
        String rvcMsg = null;
        BufferedReader reader = null;

        try {
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() != 0) {
                    rvcMsg = buffer.toString();
                }
            }
        } catch (IOException e) {
            Log.e("Utils:getDataAndC", "Error accesing stream", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("Utils:getDataAndC", "Error closing stream", e);
                }
            }
		}
        try {
            String [] data = Utils.getCountryNamesFromJSON(rvcMsg);
            StringBuffer bData = new StringBuffer();
            for (int i = 0; i < data.length - 1; i++){
                bData.append(data[i] + ", ");
            }
            bData.append(data[data.length-1] + "\n");
            Log.e("Utils", bData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rvcMsg;
    }

    /**
     * Extract an array of country names from the result of the query
     * "http://api.geonames.org/countryInfoJSON?formatted=true&lang=it&country=&username=aname&style=full
     * @param jsonResponse JSON string provided by geonames.
     * @return Array of Strings conatining the country names.
     * @throws JSONException
     */
	public static String[] getCountryNamesFromJSON(String jsonResponse) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray jsonArray = jsonObject.getJSONArray("geonames");

        if (jsonArray == null){
            Log.w("MainActivityFragment", "getCountryNamesFromJSON ---> jsonArray is null");
            return null;
        }

        String[] countryNames = new String[jsonArray.length()];
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jCountry = jsonArray.getJSONObject(i);
            String name = jCountry.getString("countryName");
            countryNames[i] = name;
        }
        return countryNames;
    }

    /*
    public static String getDataForCountryJSON(String jsonResponse, String countryName, String fieldName) throws JSONException{

        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray jsonArray = jsonObject.getJSONArray("geonames");

        if (jsonArray == null){
            Log.w("MainActivityFragment", "getDataForCountryJSON ---> jsonArray is null");
            return null;
        }

        String[] countryNames = new String[jsonArray.length()];
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jCountry = jsonArray.getJSONObject(i);
            String name = jCountry.getString("countryName");
            if (name.equals(countryName)){
                String data = jCountry.getString(fieldName);
                return data;
            }
        }
        return null;
    }
    */

    /**
     * Gets the information corresponding to a given country from the
     * countries information returned by geonames
     * @param jsonResponse complete JSON response.
     * @param countryName  country name
     * @return Information of the requested country.
     * @throws JSONException
     */
    public static String getDataForCountryJSON(String jsonResponse, String countryName) throws JSONException{

        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray jsonArray = jsonObject.getJSONArray("geonames");

        if (jsonArray == null){
            Log.w("MainActivityFragment", "getDataForCountryJSON ---> jsonArray is null");
            return null;
        }

        String[] countryNames = new String[jsonArray.length()];
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jCountry = jsonArray.getJSONObject(i);
            String name = jCountry.getString("countryName");
            if (name.equals(countryName)){
                return jCountry.toString();
            }
        }
        return null;
    }

    /**
     * Extracts a given field from the information of a country
     * @param jCountry string corresponding to the country infromation (json fromat)
     * @param key key of the field to get.
     * @return the infromation associated to the key.
     */
    public static String getFieldForCountryJSON(String jCountry, String key){
        try {
            JSONObject jsonObject = new JSONObject(jCountry);
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
	
