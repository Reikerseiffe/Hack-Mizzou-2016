<?php

namespace App\Jobs;

use App\Room;
use Carbon\Carbon;
use Illuminate\Bus\Queueable;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Queue\SerializesModels;
use SpotifyWebAPI\Session;
use SpotifyWebAPI\SpotifyWebAPI;

class RemoveTopSong implements ShouldQueue
{
    use InteractsWithQueue, Queueable, SerializesModels;

    protected $room;

    /**
     * Create a new job instance.
     *
     * @return void
     */
    public function __construct(Room $room)
    {
        $this->room = $room;
    }

    /**
     * Execute the job.
     *
     * @return void
     */
    public function handle()
    {
        $session = new Session(env('CLIENT_ID'), env('CLIENT_SECRET'), 'http://ec2-52-42-199-153.us-west-2.compute.amazonaws.com/auth');
        $api = new SpotifyWebAPI();
        $api->setAccessToken($this->room->vcAccessToken);

        $songs = $this->room->songs()->orderBy('nRepScore', 'desc')->get();
        $songs[1]->nRepScore = 999999;
        $songs[1]->save();
        $duration = (int)$songs[1]->nDuration;
        $seconds = $duration/1000;
       

        $tracks = [
            ['id' => $songs[0]->vcSpotifySongID],
        ];
        $snapshot = $api->deleteUserPlaylistTracks($this->room->vcUsername, $this->room->vcPlaylistID, $tracks, $this->room->vcSnapshotID);
        if($snapshot){
            $this->room->vcSnapshotID = $snapshot;
            $this->room->save();
        }
        $songs[0]->delete();

        //$job = (new RemoveTopSong($this->room))->delay(Carbon::now()->addSeconds($seconds));

        //dispatch($job);
    }
}
