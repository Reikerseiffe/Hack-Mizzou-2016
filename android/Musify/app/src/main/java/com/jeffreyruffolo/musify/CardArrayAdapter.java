package com.jeffreyruffolo.musify;

/**
 * Created by Jeffrey on 10/14/2016.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardArrayAdapter  extends ArrayAdapter<Card> {
    private String room_name;
    private PlaylistActivity pla;

    private static final String TAG = "CardArrayAdapter";
    private List<Card> cardList = new ArrayList<Card>();

    static class CardViewHolder {
        String sond_id;
        TextView song_name;
        TextView artist_name;
        ImageButton up_vote;
        ImageButton down_vote;
    }

    public CardArrayAdapter(Context context, int textViewResourceId, String room_name, PlaylistActivity pla) {
        super(context, textViewResourceId);
        this.room_name = room_name;
        this.pla = pla;
    }

    @Override
    public void add(Card object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Card getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.playlist_item_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.song_name = (TextView) row.findViewById(R.id.song_name);
            viewHolder.artist_name = (TextView) row.findViewById(R.id.artist_name);
            viewHolder.up_vote = (ImageButton) row.findViewById(R.id.song_up_vote);
            viewHolder.down_vote = (ImageButton) row.findViewById(R.id.song_down_vote);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        Card card = getItem(position);
        viewHolder.sond_id = card.get_song_id();
        viewHolder.song_name.setText(card.getSong());
        viewHolder.artist_name.setText(card.getArtist());

        viewHolder.up_vote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pla.refreshPlaylist();
            }
        });
        viewHolder.down_vote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pla.refreshPlaylist();
            }
        });
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
