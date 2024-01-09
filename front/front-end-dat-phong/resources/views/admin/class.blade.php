@extends("admin.layout.app")
@section("title")
    <title>Admin - Danh sách lớp học</title>
@endsection
@section("content")
    <div>
        <div id="app">
            <admin-list-class/>
        </div>
    </div>
@endsection
@section("js_location")
    <script src="{{asset('js/admin-list-class.js?t='.Carbon\Carbon::now()->timestamp)}}"></script>
@endsection
