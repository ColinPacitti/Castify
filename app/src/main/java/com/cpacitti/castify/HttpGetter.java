package com.cpacitti.castify;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpGetter extends AsyncTask<String, Void, String>{

    @Override
    public String doInBackground(String...token) {
        String result = null;
        HttpClient client = new DefaultHttpClient();
        HttpEntity httpentity = null;
        HttpResponse response = null;
        HttpGet httpGet = new HttpGet("https://api.spotify.com/v1/me/tracks");
        httpGet.setHeader("Authorization", "Bearer " + token[0]);

        try {
            response = client.execute(httpGet);
            httpentity = response.getEntity();
            if(httpentity != null){
                result = EntityUtils.toString(httpentity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
