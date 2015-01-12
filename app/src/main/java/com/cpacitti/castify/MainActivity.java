package com.cpacitti.castify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.authentication.SpotifyAuthentication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ConcurrentModificationException;
import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {
    String music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        String token = sharedPref.getString("TOKEN", null);
        if(token == null) {
            Intent myIntent = new Intent(MainActivity.this, SpotifyLogin.class);
            MainActivity.this.startActivity(myIntent);
        } else {
            setContentView(R.layout.activity_main);
            Uri uri = Uri.parse(token);
            AuthenticationResponse response = SpotifyAuthentication.parseOauthResponse(uri);
            try {
                music = new HttpGetter().execute(response.getAccessToken()).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        try {
            JSONObject jObject = new JSONObject(music);
            JSONArray items = jObject.getJSONArray("items");
            JSONObject trackInfo[] = new JSONObject[20];

            for(int i = 1; i < 20; i++) {
                trackInfo[i-1] = items.getJSONObject(i-1).getJSONObject("track");
            }
            TrackAdapter adapter = new TrackAdapter(MainActivity.this, R.layout.tracklayout, trackInfo);
            ListView listView = (ListView) findViewById(R.id.tracklist);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
