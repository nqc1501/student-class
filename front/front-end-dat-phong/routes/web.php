<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

Auth::routes();

Route::get('/home', 'HomeController@index')->name('home');

Route::group(['prefix' => 'admin','middleware' => ['checkLogin', 'checkAdmin']], function(){
    Route::get('/', 'AdminController@getHome')->name("admin.home");
    Route::get('/class-room', 'AdminController@listClassRoom')->name("admin.classRoom");
    Route::get('/class', 'AdminController@listClass')->name("admin.listClass");
    Route::get('/user', 'AdminController@listUser')->name("admin.user");
    Route::get('/student', 'AdminController@listStudent')->name("admin.listStudent");
    Route::post('/list-class', 'AdminController@getListClass')->name("admin.getListClass");
    Route::post('/list-student', 'AdminController@getListStudent')->name("admin.getListStudent");
    Route::post('/list-user', 'AdminController@getListUser')->name("admin.getListUser");
    Route::post('/add-class', 'AdminController@addClass')->name("admin.addClass");
    Route::post('/add-class-room', 'AdminController@addClassRoom')->name("admin.addClassRoom");
    Route::post('/add-student', 'AdminController@addStudent')->name("admin.addStudent");
    Route::post('/update-class', 'AdminController@updateClass')->name("admin.updateClass");
    Route::post('/update-class-room', 'AdminController@updateClassRoom')->name("admin.updateClassRoom");
    Route::post('/update-student', 'AdminController@updateStudent')->name("admin.updateStudent");
    Route::post('/delete-class', 'AdminController@deleteClass')->name("admin.deleteClass");
    Route::post('/delete-class-room', 'AdminController@deleteClassRoom')->name("admin.deleteClassRoom");
    Route::post('/delete-student', 'AdminController@deleteStudent')->name("admin.deleteStudent");
    Route::post('/delete-user', 'AdminController@deleteUser')->name("admin.deleteUser");
});
Route::get('/', 'UserController@getUserHome')->middleware('checkLogin')->name('getHome');
Route::group(['prefix' => 'user','middleware' => ['checkLogin']], function(){
    Route::post('/request-room', 'UserController@requestRoom')->name("user.requestRoom");//
    Route::get('/my-request', 'UserController@myRequest')->name("user.myRequest");
    Route::post('/cancel-room', 'UserController@cancelRoom')->name("user.cancelRoom");//
    Route::post('/list-class-room', 'AdminController@getListClassRoom')->name("user.getListClassRoom");//
});

