package es.cice.moviedbfinalproject.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import es.cice.moviedbfinalproject.MainActivity;
import es.cice.moviedbfinalproject.model.setupDB;

/**
 * Created by Carmen on 14/02/2017.
 */

//En el primer parámetro le pasamos la URL para obtener el Json con la url base para las imágenes
public class TheMovieDBUrlBaseAsynTask extends AsyncTask<String, Void, String> {
    private String UrlBaseImage;
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected String doInBackground(String... urls) {
        BufferedReader in = null;
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer data = new StringBuffer();
            //Insertar los datos obtenidos con in en el StringBuffer
            String line = null;
            while ((line = in.readLine()) != null) {
                data.append(line);
            }
            Gson gson = new Gson();
            String json=data.toString();

            setupDB setupdb = gson.fromJson(json, setupDB.class);
            UrlBaseImage = setupdb.getImages().getBaseUrl();
            Log.d(TAG, "URL BASE ...." + UrlBaseImage);

            return UrlBaseImage;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

}
}
