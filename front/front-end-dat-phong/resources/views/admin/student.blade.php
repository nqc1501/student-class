@extends("admin.layout.app")
@section("title")
    <title>Admin - Danh sách học sinh</title>
@endsection
@section("content")
    <div>
        <div id="app">
            <admin-list-student/>
        </div>
    </div>
@endsection
@section("js_location")
    <script src="{{asset('js/admin-list-student.js?t='.Carbon\Carbon::now()->timestamp)}}"></script>
@endsection
