@extends("admin.layout.app")
@section("title")
    <title>Admin - Danh sách người dùng</title>
@endsection
@section("content")
    <div>
        <div id="app">
            <admin-list-user/>
        </div>
    </div>
@endsection
@section("js_location")
    <script src="{{asset('js/admin-list-user.js?t='.Carbon\Carbon::now()->timestamp)}}"></script>
@endsection
