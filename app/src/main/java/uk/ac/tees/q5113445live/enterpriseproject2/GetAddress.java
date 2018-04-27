package uk.ac.tees.q5113445live.enterpriseproject2;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Luke on 26/04/2018.
 */

public class GetAddress extends AsyncTask<String[], Void, String[]>
{

    private String postcode;
    private String[] addresses;
    GetAddress(String pc)
    {
        postcode = pc;
    }

    @Override
    protected String[] doInBackground(String[]... strings)
    {

        URL url = null;
        try
        {
            url = new URL("https://api.getAddress.io/find/"+postcode+"?api-key=l9q-TMEtpUO1FNIO_NU8Ew13273&sort=True");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("addresses","application/json");
            urlConnection.setRequestMethod("GET");
            try
            {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
//                String inputLine;
//
//                StringBuffer content = new StringBuffer();
////                while ((inputLine = in.readLine()) != null)
////                {
////                    content.append(inputLine);
////                }
                JSONObject jsonObject = new JSONObject(in.readLine());
                JSONArray jsonData = jsonObject.getJSONArray("addresses");

                addresses = new String[jsonData.length()];
                for(int i = 0; i < jsonData.length(); i++)
                {
                    addresses[i] = jsonData.getString(i).substring(jsonData.getString(i).indexOf(", , , ,"), jsonData.getString(i).indexOf(","));
                }

                in.close();
                System.out.println();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally
            {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return addresses;
    }

}
