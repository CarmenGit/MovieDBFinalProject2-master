package es.cice.moviedbfinalproject.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import es.cice.moviedbfinalproject.model.Genre;
import es.cice.moviedbfinalproject.model.ListGenres;

/**
 * Created by cice on 15/2/17.
 */

//En el primer parámetro le pasamos la URL para obtener los géneros disponibles
public class TheMovieDBGetGenresAsynTask extends AsyncTask<String, Void, List<Genre>>{

    private Spinner spGenre;
    private Context ctx;

    public void setSpGenre(Spinner spGenre) {
        this.spGenre = spGenre;
        Log.d("Spinner", spGenre.toString());
    }
    public void setContext(Context ctx) {this.ctx=ctx;}


    @Override
    protected List<Genre> doInBackground(String... urls) {
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
            ListGenres lg= gson.fromJson(json, ListGenres.class);
            return lg.getGenres();

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

        }
        return null;
    }


    @Override
    protected void onPostExecute(List<Genre> genres) {
        super.onPostExecute(genres);
       List<String> listag=new ArrayList<>();
        Log.d("Un género", ""+ genres.size());
        for(int i=0;i<genres.size();i++){
            listag.add(genres.get(i).getName());
            Log.d("Un género", ""+ genres.get(i).getName());
        }

//Creamos el adaptador
       // ArrayAdapter spAdapter = new ArrayAdapter(ctx, android.R.layout.simple_spinner_item, genres);
//Añadimos el layout para el menú y se lo damos al spinner
        //spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // ArrayAdapter spAdapter = new ArrayAdapter(ctx, R.layout.genre_layout, genres);
//Añadimos el layout para el menú y se lo damos al spinner
        //spAdapter.setDropDownViewResource(R.layout.genre_layout);
        Log.d("Spinner", spGenre.toString());
        //spGenre.setAdapter(spAdapter);

    }
}
