@extends("user.layouts.app")
@section("title")
    <title>Yêu cầu của tôi - Hệ thống đăng ký mượn phòng học</title>
@endsection
@section("content")
    <div id="app">
        <user-my-request :user="{{json_encode($student)}}" :mydata="{{json_encode($myClass)}}"/>
    </div>
@endsection
@section("js_location")
    <script src="{{asset('js/user-my-request.js?t='.Carbon\Carbon::now()->timestamp)}}"></script>
@endsection
