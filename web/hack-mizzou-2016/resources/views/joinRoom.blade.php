@extends('index') @section('mainContent')

<div class="container">
    <div class="text-center"><img src="IMG/NoteVoteIcon1.png" alt="LogoImage" width="243" height="236.5"></div>
    <h2 class="text-center">Enter A Room ID:</h2>


    <br>

    <form style="text-align:center" action="joinRoom" method="post">
       {{csrf_field()}}
        <input type="text" name="vcRoomName">
        <br>
        <br>
        <button type="submit" class="btnColor btn">Join Room</button>
    </form>

    <br>

</div>
@endsection