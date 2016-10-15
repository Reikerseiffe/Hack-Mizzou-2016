package com.jeffreyruffolo.musify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "CardListActivity";
    private SearchArrayAdapter searchArrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = (ListView) findViewById(R.id.card_listView);

        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));

        searchArrayAdapter = new SearchArrayAdapter(getApplicationContext(), R.layout.search_item_card);

        for (int i = 0; i < 10; i++) {
            Card card = new Card("Card " + (i+1) + " Line 1", "Card " + (i+1) + " Line 2");
            searchArrayAdapter.add(card);
        }
        listView.setAdapter(searchArrayAdapter);
    }
}
