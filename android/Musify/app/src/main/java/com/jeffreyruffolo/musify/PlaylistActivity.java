package com.jeffreyruffolo.musify;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class PlaylistActivity extends AppCompatActivity {
    private String room_name;

    private CardArrayAdapter playlist_cardArrayAdapter;
    private ListView playlist_listView;


    private CurrentArrayAdapter current_cardArrayAdapter;
    private ListView current_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        room_name = getIntent().getExtras().getString("room_name");

        current_listView = (ListView) findViewById(R.id.current_song_listView);

        current_listView.addHeaderView(new View(this));
        current_listView.addFooterView(new View(this));


        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.search_button);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this, SearchActivity.class);
                intent.putExtra("room_name", room_name);

                startActivity(intent);
            }
        });

        playlist_listView = (ListView) findViewById(R.id.card_listView);

        playlist_listView.addHeaderView(new View(this));
        playlist_listView.addFooterView(new View(this));

        ListView current_card = (ListView) findViewById(R.id.current_song_listView);
        current_card.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                refreshPlaylist();
            }
        });
        refreshPlaylist();
    }

    public void refreshPlaylist(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST,"http://ec2-52-42-199-153.us-west-2.compute.amazonaws.com/api/getPlaylist",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray reader = new JSONArray(response);

                            current_cardArrayAdapter = new CurrentArrayAdapter(getApplicationContext(), R.layout.current_song_card, room_name);
                            playlist_cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.playlist_item_card,
                                    room_name, PlaylistActivity.this);

                            for(int i = 0; i < reader.length(); i++){
                                JSONObject item = reader.getJSONObject(i);

                                String song_name = item.getString("vcName");
                                String artist_name = item.getString("vcArtist");
                                String image_url = item.getString("vcCoverArt");
                                String song_id = item.getString("nSongID");


                                if(i == 0){
                                    add_current_card(current_cardArrayAdapter, song_name, artist_name, image_url, song_id);
                                }
                                else{
                                    add_playlist_card(playlist_cardArrayAdapter, song_name, artist_name, image_url, song_id);
                                }
                            }

                            current_listView.setAdapter(current_cardArrayAdapter);
                            playlist_listView.setAdapter(playlist_cardArrayAdapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        System.out.println("oh no error");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("roomID", room_name);

                return params;
            }
        };
        queue.add(sr);

    }

    void add_current_card(CurrentArrayAdapter a, String song_name, String artist_name, String image_url, String song_id){
        Card card = new Card(song_name, artist_name, image_url, song_id);
        a.add(card);
    }

    void add_playlist_card(CardArrayAdapter a, String song_name, String artist_name, String image_url, String song_id){
        Card card = new Card(song_name, artist_name, image_url, song_id);
        a.add(card);
    }
}
