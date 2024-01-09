<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">

    @include('user.layouts.css')
    @yield('title')

</head>
<body>
<!-- Main Wrapper -->
<div class="main-wrapper">
    <!-- Header -->
    @include('user.layouts.header')
    <div class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-5 col-lg-4 col-xl-3 theiaStickySidebar">
                    @include('user.layouts.sidebar')
                </div>
                <div class="col-md-7 col-lg-8 col-xl-9">
                        @yield('content')
                </div>
            </div>

        </div>

    </div>
    @include('user.layouts.footer')
</div>
@include('user.layouts.js')
@yield('js_location')
</body>
</html>
