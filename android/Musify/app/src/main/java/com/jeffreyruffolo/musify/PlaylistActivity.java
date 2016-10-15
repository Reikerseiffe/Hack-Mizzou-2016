package com.jeffreyruffolo.musify;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class PlaylistActivity extends AppCompatActivity {

    private CardArrayAdapter playlist_cardArrayAdapter;
    private ListView playlist_listView;


    private CurrentArrayAdapter current_cardArrayAdapter;
    private ListView current_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);


        current_listView = (ListView) findViewById(R.id.current_song_listView);

        current_listView.addHeaderView(new View(this));
        current_listView.addFooterView(new View(this));

        current_cardArrayAdapter = new CurrentArrayAdapter(getApplicationContext(), R.layout.playlist_item_card);

        for (int i = 0; i < 1; i++) {
            Card card = new Card("Current Song Name", "Current Artist Name");
            current_cardArrayAdapter.add(card);
        }
        current_listView.setAdapter(current_cardArrayAdapter);


        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.search_button);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PlaylistActivity.this, SearchActivity.class));
            }
        });

        playlist_listView = (ListView) findViewById(R.id.card_listView);

        playlist_listView.addHeaderView(new View(this));
        playlist_listView.addFooterView(new View(this));

        playlist_cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.playlist_item_card);

        for (int i = 0; i < 10; i++) {
            Card card = new Card("Card " + (i+1) + " Line 1", "Card " + (i+1) + " Line 2");
            playlist_cardArrayAdapter.add(card);
        }
        playlist_listView.setAdapter(playlist_cardArrayAdapter);
    }
}
