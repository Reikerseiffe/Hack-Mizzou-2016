<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Song extends Model
{

	protected $primaryKey = 'nSongID';

    protected $fillable = [
        'vcName', 'vcArtist', 'vcAlbum', 'nDuration', 'vcCoverArt', 'vcSpotifySongID', 'nRepScore'
    ];
}
