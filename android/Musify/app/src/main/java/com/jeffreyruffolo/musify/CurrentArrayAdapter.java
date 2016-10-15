package com.jeffreyruffolo.musify;

/**
 * Created by Jeffrey on 10/15/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CurrentArrayAdapter  extends ArrayAdapter<Card> {
    private String room_name;
    Context context;

    private static final String TAG = "CardArrayAdapter";
    private List<Card> cardList = new ArrayList<Card>();

    static class CardViewHolder {
        TextView song_name;
        TextView artist_name;
        ImageView album_art;
    }

    public CurrentArrayAdapter(Context context, int textViewResourceId, String room_name) {
        super(context, textViewResourceId);
        this.room_name = room_name;
        this.context = context.getApplicationContext();
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
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.current_song_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.song_name = (TextView) row.findViewById(R.id.song_name);
            viewHolder.artist_name = (TextView) row.findViewById(R.id.artist_name);
            viewHolder.album_art = (ImageView) row.findViewById(R.id.album_art);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        Card card = getItem(position);
        viewHolder.song_name.setText(card.getSong());
        viewHolder.artist_name.setText(card.getArtist());
        Picasso.with(context).load(card.getURL_string()).into(viewHolder.album_art);

        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
