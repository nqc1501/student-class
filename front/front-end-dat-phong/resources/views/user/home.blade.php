@extends("user.layouts.app")
@section("title")
    <title>Tổng quan - Hệ thống đăng ký mượn phòng học</title>
@endsection
@section("content")
    <div id="app">
        <user-list-class-rooms :user="{{json_encode($student)}}" :role="{{json_encode(Auth()->user()->role)}}"/>
    </div>
@endsection
@section("js_location")
    <script src="{{asset('js/user-list-class-rooms.js?t='.Carbon\Carbon::now()->timestamp)}}"></script>
@endsection
