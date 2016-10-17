<?php

namespace App\Http\Controllers;

use App\Http\Requests;
use App\Room;
use Illuminate\Http\Request;
use SpotifyWebAPI\Session;
use SpotifyWebAPI\SpotifyWebAPI;

class RoomController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $session = new Session(env('CLIENT_ID'), env('CLIENT_SECRET'), 'http://ec2-52-25-40-198.us-west-2.compute.amazonaws.com/auth');

        $scopes = ['playlist-read-private', 'playlist-modify-private'];
        $state = 'authState';

        $authorizeUrl = $session->getAuthorizeUrl(array(
            'scope' => $scopes
        ));

        return redirect($authorizeUrl);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('nameRoom');
    }

    public function joinRoom(Request $request){
        //TODO add redirect for invalid room number
        $roomID = $request->vcRoomName;

        $request->session()->put('roomID', $roomID);
        $request->session()->save();
        return redirect("playlist");
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $session = new Session(env('CLIENT_ID'), env('CLIENT_SECRET'), 'http://ec2-52-25-40-198.us-west-2.compute.amazonaws.com/auth');
        $api = new SpotifyWebAPI();

        // Request a access token using the code from Spotify
        $session->requestAccessToken($request->code);
        $accessToken = $session->getAccessToken();

        // Set the access token on the API wrapper
        $api->setAccessToken($accessToken);

        try{
            $user = $api->me();
        }catch (SpotifyWebAPIException $e){
            $session->refreshAccessToken($room->vcRefreshToken);
            $accessToken = $session->getAccessToken();
            $api->setAccessToken($accessToken);
            $room->vcAccessToken = $accessToken;
            //$room->vcRefreshToken = $session->getRefreshToken();
            $room->save();
            $user = $api->me();
        }
        $playlist = $api->createUserPlaylist($user->id, [
            'name' => 'NoteVote',
            'public' => false,
        ]);

        $room = new Room;
        $room->vcRoomName = "Party";
        $room->vcAccessToken = $accessToken;
        $room->vcRefreshToken = $session->getRefreshToken();
        $room->vcUsername = $user->id;
        $room->vcPlaylistID = $playlist->id;
        $room->vcSnapshotID = $playlist->snapshot_id;
        $room->save();

        $request->session()->put('roomID', $room->nRoomID);
        $request->session()->save();
        return redirect("playlist");
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show(Request $request)
    {

        $session = new Session(env('CLIENT_ID'), env('CLIENT_SECRET'), 'http://ec2-52-25-40-198.us-west-2.compute.amazonaws.com/auth');
        $api = new SpotifyWebAPI();


        $roomID = session('roomID');
        $room = Room::find($roomID);
        $api->setAccessToken($room->vcAccessToken);
         try{
            $playlist = $api->getUserPlaylist($room->vcUsername, $room->vcPlaylistID);
        }catch (SpotifyWebAPIException $e){
            $session->refreshAccessToken($room->vcRefreshToken);
            $accessToken = $session->getAccessToken();
            $api->setAccessToken($accessToken);
            $room->vcAccessToken = $accessToken;
            //$room->vcRefreshToken = $session->getRefreshToken();
            $room->save();
            $playlist = $api->getUserPlaylist($room->vcUsername, $room->vcPlaylistID);
        }
        //$tracks = $playlist->tracks;
        $room->vcSnapshotID = $playlist->snapshot_id;
        $room->save();
        $tracks = $room->songs()->orderBy('nRepScore', 'desc')->get();
        $uri = $playlist->uri;
        return view('playlist', compact('tracks', 'uri'));
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
