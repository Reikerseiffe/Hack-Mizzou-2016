<?php

namespace App\Http\Controllers;

use App\Http\Requests;
use App\Jobs\RemoveTopSong;
use App\Room;
use App\Song;
use Carbon\Carbon;
use Illuminate\Http\Request;
use SpotifyWebAPI\Session;
use SpotifyWebAPI\SpotifyWebAPI;
use SpotifyWebAPI\SpotifyWebAPIException;

class ApiController extends Controller
{
    public function upvote(Request $request){

    	$session = new Session(env('CLIENT_ID'), env('CLIENT_SECRET'), 'http://ec2-52-42-199-153.us-west-2.compute.amazonaws.com/auth');
        $api = new SpotifyWebAPI();

    	//$roomID = $request->roomID;
    	$roomID = (int)$request->session()->get('roomID');
    	if(!$roomID){
    		$roomID = $request->roomID;
    	}
    	$songID = $request->songID;
    	//dd($roomID);

    	$room = Room::find($roomID);

    	$api->setAccessToken($room->vcAccessToken);

    	$songs = $room->songs()->orderBy('nRepScore', 'desc')->get();
    	$songs[$songID]->nRepScore++;
    	$songs[$songID]->save();
    	$keyed = $room->songs()->orderBy('nRepScore', 'desc')->get()->map(function($item, $key){ return['id'=>$item['vcSpotifySongID']];});
    	$keyed->shift();
    	try{
    		$snapshot_id = $api->deleteUserPlaylistTracks($room->vcUsername, $room->vcPlaylistID, $keyed, $room->vcSnapshotID);
    	}catch (SpotifyWebAPIException $e){
    		$session->refreshAccessToken($room->vcRefreshToken);
    		$accessToken = $session->getAccessToken();
    		$api->setAccessToken($accessToken);
    		$room->vcAccessToken = $accessToken;
    		//$room->vcRefreshToken = $session->getRefreshToken();
    		$room->save();
    		$snapshot_id = $api->deleteUserPlaylistTracks($room->vcUsername, $room->vcPlaylistID, $keyed, $room->vcSnapshotID);
    	}

    	$songs2 = $room->songs()->orderBy('nRepScore', 'desc')->get()->pluck('vcSpotifySongID');
    	$songs2->shift();
    	$songs2 = $songs2->toArray();

    	$api->addUserPlaylistTracks($room->vcUsername, $room->vcPlaylistID, $songs2);

    	$playlist = $api->getUserPlaylist($room->vcUsername, $room->vcPlaylistID);
    	$room->vcSnapshotID = $playlist->snapshot_id;
    	$room->save();

    	return response('', 200);
    }


    public function downvote(Request $request){

    	$session = new Session(env('CLIENT_ID'), env('CLIENT_SECRET'), 'http://ec2-52-42-199-153.us-west-2.compute.amazonaws.com/auth');
        $api = new SpotifyWebAPI();

    	$roomID = (int)$request->session()->get('roomID');
    	if(!$roomID){
    		$roomID = $request->roomID;
    	}
    	$songID = $request->songID;

    	$room = Room::find($roomID);

    	$api->setAccessToken($room->vcAccessToken);
    	$songs = $room->songs()->orderBy('nRepScore', 'desc')->get();
    	$songs[$songID]->nRepScore--;
    	$songs[$songID]->save();
    	$keyed = $room->songs()->orderBy('nRepScore', 'desc')->get()->map(function($item, $key){ return['id'=>$item['vcSpotifySongID']];});
    	$keyed->shift();
    	try{
    		$snapshot_id = $api->deleteUserPlaylistTracks($room->vcUsername, $room->vcPlaylistID, $keyed, $room->vcSnapshotID);
    	}catch (SpotifyWebAPIException $e){
    		$session->refreshAccessToken($room->vcRefreshToken);
    		$accessToken = $session->getAccessToken();
    		$api->setAccessToken($accessToken);
    		$room->vcAccessToken = $accessToken;
    		//$room->vcRefreshToken = $session->getRefreshToken();
    		$room->save();
    		$snapshot_id = $api->deleteUserPlaylistTracks($room->vcUsername, $room->vcPlaylistID, $keyed, $room->vcSnapshotID);
    	}

    	$songs2 = $room->songs()->orderBy('nRepScore', 'desc')->get()->pluck('vcSpotifySongID');
    	$songs2->shift();
    	$songs2 = $songs2->toArray();

    	$api->addUserPlaylistTracks($room->vcUsername, $room->vcPlaylistID, $songs2);

    	$playlist = $api->getUserPlaylist($room->vcUsername, $room->vcPlaylistID);
    	$room->vcSnapshotID = $playlist->snapshot_id;
    	$room->save();
    	return response('', 200);
    }

    public function getPlaylist(Request $request){
        $roomID = (int)$request->session()->get('roomID');
    	if(!$roomID){
    		$roomID = (int)$request->roomID;
    	}
        $room = Room::find($roomID);

        return $room->songs()->orderBy('nRepScore', 'desc')->get();
    }

    public function addSong(Request $request){

    	$session = new Session(env('CLIENT_ID'), env('CLIENT_SECRET'), 'http://ec2-52-42-199-153.us-west-2.compute.amazonaws.com/auth');
        $api = new SpotifyWebAPI();


    	$roomID = (int)$request->session()->get('roomID');
    	if(!$roomID){
    		$roomID = $request->roomID;
    	}
    	$songID = $request->songID;
    	$room = Room::find($roomID);
    	$api->setAccessToken($room->vcAccessToken);
    	$track;
    	try{
    		$track = $api->getTrack($songID);
    	}catch (SpotifyWebAPIException $e){
    		$session->refreshAccessToken($room->vcRefreshToken);
    		$accessToken = $session->getAccessToken();
    		$api->setAccessToken($accessToken);
    		$room->vcAccessToken = $accessToken;
    		//$room->vcRefreshToken = $session->getRefreshToken();
    		$room->save();
    		$track = $api->getTrack($songID);
    	}

    	$song = new Song;
    	$song->vcName = $track->name;
    	$song->vcArtist = $track->artists[0]->name;
    	$song->vcAlbum = $track->album->name;
    	$song->nDuration = $track->duration_ms;
    	$song->vcCoverArt = $track->album->images[2]->url;
    	$song->vcSpotifySongID = $songID;
    	$song->nRepScore = 0;

 		$room->songs()->save($song);

    	$api->addUserPlaylistTracks($room->vcUsername, $room->vcPlaylistID, [
    		$songID,
		]);
    	$playlist = $api->getUserPlaylist($room->vcUsername, $room->vcPlaylistID);
    	//dd($playlist->snapshot_id);
    	$room->vcSnapshotID = $playlist->snapshot_id;
    	$room->save();
		return response('', 200);
    }

    public function startParty(Request $request){
    	$roomID = (int)$request->session()->get('roomID');
    	if(!$roomID){
    		$roomID = $request->roomID;
    	}
    	$room = Room::find($roomID);
    	$song = $room->songs()->orderBy('nRepScore', 'desc')->get()[0];
    	$song->nRepScore = 999999;
    	$song->save();
    	$duration = (int)$song->nDuration;
        $seconds = $duration/1000;
    	$job = (new RemoveTopSong($room))->delay(Carbon::now()->addSeconds($seconds));
    	dispatch($job);
    }
}
