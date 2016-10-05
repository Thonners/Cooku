package com.thonners.kooku;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * AsyncTask to look up online the addresses from a given postcode.
 *
 * Uses getaddresses.io to look up an address from a postcode, the API-key, url, etc. are in strings.xml
 *
 * getaddresses.io usage:
 *
 *
 * @author M Thomas
 * @since 11/08/16
 */


public class AddressLookupASyncTask extends AsyncTask<String, Void, ArrayList<AddressManager.Address>> {

    private String url ;


    @Override
    protected ArrayList<AddressManager.Address> doInBackground(String... params) {

        ArrayList<AddressManager.Address> addresses = new ArrayList<>();
        return addresses;
    }
        /*
        String apiKey = "";
        String postcode = URLEncoder.encode("ID1 1QD", "UTF-8");
        String baseUrl = "https://api.ideal-postcodes.co.uk/v1/postcodes/";

        URL obj = new URL(baseUrl + postcode + "?api_key=" + apiKey);

        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

    }

    @Override
    protected void onPostExecute(AddressManager.Address result) {

    }

    @Override
    protected void onPreExecute() {

    }  //*/
}
