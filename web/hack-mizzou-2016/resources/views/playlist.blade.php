@extends('index') @section('mainContent')

<!--TODO display room number, change to db interface-->
<div>
  <img src="IMG/NoteVoteIcon1.png" alt="NoteVote Logo" width="60.75" height="59.125" id="playlistLogo">
  <h3 class="text-right" style="float:right;">Room ID: {{session('roomID')}}</h3>
</div>
<div class="text-left">
    <a href="hostJoin"><img class="btn" src="" alt="End Session"></a>
</div>

<div class="container">
    
    <div class="text-center"><img src="IMG/NOTEVOTE1_name_Tagline.png" alt="NoteVote Logo TagLine" width="359" height="90"></div>

    <h3 class="text-center">Now Playing:</h3>
    <div class="text-center"><img src="" alt="Current Sont Album Art" id="currArt"></div>
    <div class="text-center" id="currSong">Current Song Title Holder</div>
    <div class="text-center" id="currArtist">Current Song Artist Holder</div>

    <br>

    <div class="text-center">
        <a onclick="startParty();"><img class="btn" src="IMG/playArrow.png" alt="Play" width="64" height="52"></a>
        <a href="songSearch"><img class="btn" src="IMG/PlusIconWeb.png" alt="ADD SONG" width="64" height="52"></a>
    </div>

    <br>

    <h3>Playlist</h3>
    <table class="table">
        <tr>
            <th>Album:</th>
            <th>Rep:</th>
            <th>Song:</th>
            <th>Artist:</th>
            <th></th>
        </tr>
        <ul>
            @foreach($tracks as $track)

            <tr>
                <td id="tableElement"><img src="{{$track->vcCoverArt}}" alt="ALBUM ART">
                    <script>
                        if ({{$track->nRepScore}} >= 900000) {
                            $('#currArt').empty();
                            $('#currArt').attr("src",'{{$track->vcCoverArt}}');
                        }
                    </script>
                </td>
                <td id="tableElement">
                    <div id="repScore">{{$track->nRepScore}}</div>
                    <script>
                        if ({{$track->nRepScore}} >= 900000) {
                            $('#repScore').empty();
                            $('#repScore').append("Current");
                        }
                    </script>
                </td>
                <td id="tableElement">
                    <div>{{$track->vcName}}</div>
                    <script>
                        if ({{$track->nRepScore}} >= 900000) {
                            $('#currSong').empty();
                            $('#currSong').append('{{$track->vcName}}');
                        }
                    </script>
                </td>
                <td id="tableElement">
                    <div>{{$track->vcArtist}}</div>
                    <script>
                        if ({{$track->nRepScore}} >= 900000) {
                            $('#currArtist').empty();
                            $('#currArtist').append("By: "+'{{$track->vcArtist}}');
                        }
                    </script>
                </td>
                <td id="tableElement">
                    <a class="btn" onclick="upVote({{$loop->index}});"><img src="IMG/UpVote.png" alt="upvote" width="35.5" height="17.9"></a>
                    <br>
                    <a class="btn" onclick="downVote({{$loop->index}});"><img src="IMG/DownVote.png" alt="downvote" width="35.5" height="17.9"></a>
                </td>

            </tr>
            @endforeach
        </ul>
    </table>
</div>
<script>
    function upVote(index) {
        $.post("/api/upvote", {
            songID: index,
        });
        location.reload();
    }

    function downVote(index) {
        $.post("/api/downvote", {
            songID: index
        });
        location.reload();
    }
    function startParty(){
        $.post("/api/startParty");
        location.reload();
    }
    setTimeout(function(){window.location.reload(1);}, 30000);
</script>
@endsection