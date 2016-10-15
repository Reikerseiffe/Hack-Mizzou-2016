<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| This file is where you may define all of the routes that are handled
| by your application. Just tell Laravel the URIs it should respond
| to using a Closure or controller method. Build something great!
|
*/

Route::get('/', function () {
    return view('hostJoin');
});

Route::get('/test', function () {
    return view('welcome');
});

Route::get('/nameRoom', function () {
    return view('nameRoom');
});

Route::get('/joinRoom', function(){
	return view('joinRoom');
});

Route::get('/songSearch', function(){
	return view('songSearch');
});

Route::get('/hostJoin', function(){
	return view('hostJoin');
});

Route::get('/index', "RoomController@index");
Route::get('/auth', "RoomController@create");
Route::post('/createRoom', "RoomController@store");
Route::get('/playlist', "RoomController@show");
Route::post('/joinRoom', "RoomController@joinRoom");
