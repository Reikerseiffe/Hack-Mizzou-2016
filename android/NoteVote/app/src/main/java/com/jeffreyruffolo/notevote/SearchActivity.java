package com.jeffreyruffolo.notevote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {
    String room_name;

    private SearchArrayAdapter searchArrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        room_name = getIntent().getExtras().getString("room_name");

        listView = (ListView) findViewById(R.id.card_listView);

        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));

        final EditText search_text = (EditText) findViewById(R.id.song_search_text);
        search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(search_text.getText().length() > 0) {
                        String query = search_text.getText().toString().replace(' ', '+');
                        refreshSearch(query, 10);
                    }

                    return true;
                }
                return false;
            }
        });
    }

    public void refreshSearch(String query, int num){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.spotify.com/v1/search?q=" + query +
                "&type=track&market=US&limit=" + Integer.toString(num);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject reader = new JSONObject(response);

                            searchArrayAdapter = new SearchArrayAdapter(getApplicationContext(), R.layout.search_item_card,
                                    room_name, SearchActivity.this);

                            for (int i = 0; i < 10; i++) {
                                JSONObject item = reader.getJSONObject("tracks").getJSONArray("items").getJSONObject(i);
                                String song = item.getString("name");
                                String artist = item.getJSONArray("artists").getJSONObject(0).getString("name");
                                String image_url = item.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url");
                                String song_id = item.getString("id");

                                Card card = new Card(song, artist, image_url, song_id);
                                searchArrayAdapter.add(card);
                            }
                            listView.setAdapter(searchArrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);




    }
}
