package com.cpacitti.castify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrackAdapter extends ArrayAdapter<JSONObject> {
    Context context;
    JSONObject[] tracks = null;
    int position;
    static class TrackHolder {
        TextView title;
    }

    public TrackAdapter(Context context, int position, JSONObject[] tracks){
        super(context, position, tracks);
        this.context = context;
        this.position = position;
        this.tracks = tracks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TrackHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.tracklayout, parent, false);
            holder = new TrackHolder();
            holder.title = (TextView) row.findViewById(R.id.title);
            row.setTag(holder);
        } else {
            holder = (TrackHolder) row.getTag();
        }

        try {
            holder.title.setText(tracks[position].getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return row;
    }
}
