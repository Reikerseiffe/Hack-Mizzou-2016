package com.jeffreyruffolo.musify;

/**
 * Created by Jeffrey on 10/14/2016.
 */

public class Card {
    private String song_name;
    private String artist_name;

    public Card(String song_name, String artist_name) {
        this.song_name = song_name;
        this.artist_name = artist_name;
    }

    public String getSong() {
        return song_name;
    }

    public String getArtist() {
        return artist_name;
    }

}