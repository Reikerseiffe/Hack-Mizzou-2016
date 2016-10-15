@extends('index') @section('mainContent')

<div class="container">

    <div class="text-left">
        <a href="playlist"><img class="btn" src="IMG/backArrow.png" alt="Back to Playlist" width="64" height="64"></a>
    </div>

    <div class="text-center"><img src="IMG/NoteVoteIcon1.png" alt="LogoImage" width="243" height="236.5"></div>
    <h2 class="text-center">Search for Song:</h2>

    <br>

    <input id="searchSong" type="text" name="searchSong" class="col-md-2 col-md-offset-5">
    <br>
    <br>
    <button onclick="search();" class="col-md-2 col-md-offset-5 btnColor btn">Search</button>
    <br>
    <br>
    <div id="addedMessage" class="text-center"></div>

    <br>
    <br>
    <br>

    <table class='table' id='songTable'>
        <tr>
            <th>Album:</th>
            <th>Song:</th>
            <th>Artist:</th>
            <th></th>
        </tr>
        <tbody id="tblSearch"></tbody>
    </table>

    <script>
        function search() {
            $.get("https://api.spotify.com/v1/search", {
                q: $('#searchSong').val(),
                type: "track"
            }).done(function (data) {


                $('#tblSearch').empty();

                for (var i = 0; i < data.tracks.items.length; i++) {

                    $('#tblSearch').append("<tr><td><img src=" + data.tracks.items[i].album.images[2].url + "></img></td><td>" + data.tracks.items[i].name + "</td><td>" + data.tracks.items[i].artists[0].name + "</td><td><button class='btn' onclick=add('" + data.tracks.items[i].id + "') >Add Song</button></td></tr>");

                    //+", "+ data.tracks.items[i].name + ", "+ data.tracks.items[i].artists[0].name +

                }

            });
        }

        function add(songID) {

            $.post("/api/addSong", {
                songID: songID
            });
            $('#addedMessage').append("<div>Added song to the playlist</div>");

        }
    </script>

</div>
@endsection