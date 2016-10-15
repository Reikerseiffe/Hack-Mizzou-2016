<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateSongsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('songs', function (Blueprint $table) {
            $table->engine = 'InnoDB';
            $table->increments("nSongID");
            $table->string("vcName");
            $table->string("vcArtist");
            $table->string("vcAlbum");
            $table->integer("nDuration");
            $table->string("vcCoverArt");
            $table->string("vcSpotifySongID");
            $table->integer("nRepScore");
            $table->integer("nRoomID")->length(10)->unsigned();
            $table->timestamps();
        });

         Schema::table('songs', function($table) {
            $table->foreign('nRoomID')->references('nRoomID')->on('rooms');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('songs');
    }
}
