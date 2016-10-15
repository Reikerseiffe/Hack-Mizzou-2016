package com.jeffreyruffolo.notevote;

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
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchArrayAdapter  extends ArrayAdapter<Card> {
    Context context;
    private String room_name;
    SearchActivity sa;


    private static final String TAG = "CardArrayAdapter";
    private List<Card> cardList = new ArrayList<Card>();

    static class CardViewHolder {
        TextView song_name;
        TextView artist_name;
        ImageButton add_song;
        String song_id;
    }

    public SearchArrayAdapter(Context context, int textViewResourceId, String room_name, SearchActivity sa) {
        super(context, textViewResourceId);
        this.context = context;
        this.room_name = room_name;
        this.sa = sa;
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
            row = inflater.inflate(R.layout.search_item_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.song_name = (TextView) row.findViewById(R.id.song_name);
            viewHolder.artist_name = (TextView) row.findViewById(R.id.artist_name);
            viewHolder.add_song = (ImageButton) row.findViewById(R.id.song_add_button);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        Card card = getItem(position);
        viewHolder.song_name.setText(card.getSong());
        viewHolder.artist_name.setText(card.getArtist());
        viewHolder.song_id = card.get_song_id();

        viewHolder.add_song.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest sr = new StringRequest(Request.Method.POST,"http://ec2-52-42-199-153.us-west-2.compute.amazonaws.com/api/addSong",
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                sa.onBackPressed();
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
                        params.put("songID", viewHolder.song_id);

                        return params;
                    }
                };
                queue.add(sr);
            }
        });
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
