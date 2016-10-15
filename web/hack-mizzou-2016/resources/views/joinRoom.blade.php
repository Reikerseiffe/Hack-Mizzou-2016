@extends('index') @section('mainContent')

<div class="container">
    <div class="text-center"><img src="IMG/NoteVoteIcon1.png" alt="LogoImage" width="243" height="236.5"></div>
    <h2 class="text-center">Enter A Room ID:</h2>


    <br>

    <form action="joinRoom" method="post">
       {{csrf_field()}}
        <input type="text" name="vcRoomName" class="col-md-2 col-md-offset-5">
        <br>
        <br>
        <button type="submit" class="col-md-2 col-md-offset-5 btnColor btn">Join Room</button>
    </form>

    <br>

</div>
@endsection