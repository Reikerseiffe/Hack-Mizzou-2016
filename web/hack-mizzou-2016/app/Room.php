<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Room extends Model
{
	protected $primaryKey = "nRoomID";

    protected $fillable = [
        'vcRoomName', 'vcAccessToken', 'vcRefreshToken', 'vcPlaylistID', 'vcSnapshotID', 'vcUsername'
    ];

    public function songs(){
    	return $this->hasMany('App\Song', 'nRoomID', 'nRoomID');
    }
}
