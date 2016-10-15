@extends('index') @section('mainContent')

<div class="container">
    <div class="text-center"><img src="IMG/NoteVoteIcon1.png" alt="LogoImage" width="243" height="236.5"></div>
    <h2 class="text-center">Create your room by clicking below:</h2>
    
    <br>


    <form action="createRoom" method="post">
        {{ csrf_field() }}
        <input type="hidden" name="code" value="">
        <button type="submit" class="col-md-2 col-md-offset-5 btnColor btn">Create Room</button>
    </form>

    <br>

</div>

<script>
    var getUrlParameter = function getUrlParameter(sParam) {    
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                    sURLVariables = sPageURL.split('&'),
                    sParameterName,
            i;

            
        for (i = 0; i < sURLVariables.length; i++) {        
            sParameterName = sURLVariables[i].split('=');

                    
            if (sParameterName[0] === sParam) {            
                return sParameterName[1] === undefined ? true : sParameterName[1];        
            }    
        }
    };

    $(document).ready(function () {
        var code = getUrlParameter('code');
        $('input[name=code]').val(code);
    });
</script>
@endsection