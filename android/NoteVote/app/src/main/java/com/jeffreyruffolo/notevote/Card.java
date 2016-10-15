package com.jeffreyruffolo.notevote;

/**
 * Created by Jeffrey on 10/14/2016.
 */

public class Card {
    private String song_name;
    private String artist_name;
    private String image_url;
    private String song_id;

    public Card(String song_name, String artist_name, String image_url, String song_id) {
        this.song_name = song_name;
        this.artist_name = artist_name;
        this.image_url = image_url;
        this.song_id = song_id;
    }

    public String getSong() {
        return song_name;
    }

    public String getArtist() {
        return artist_name;
    }

    public String getURL_string() {
        return image_url;
    }

    public String get_song_id(){
        return song_id;
    }
}