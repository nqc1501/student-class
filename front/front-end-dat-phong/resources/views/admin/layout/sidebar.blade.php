<div class="sidebar" id="sidebar">
    <div class="sidebar-inner slimscroll">
        <div id="sidebar-menu" class="sidebar-menu">
            <ul>
                <li class="menu-title"><span>Hệ thống</span></li>
                <li class=" {{ Request::routeIs('admin.home')? 'active' : '' }}">
                    <a href="{{route('admin.home')}}"><i class="feather-grid"></i> <span>Tổng quan</span></a>
                </li>
                <li class=" {{ Request::routeIs('admin.user')? 'active' : '' }}">
                    <a href="{{route('admin.user')}}"><i class="feather-users"></i> <span>Người dùng</span></a>
                </li>
                <li class=" {{ Request::routeIs('admin.listClass')? 'active' : '' }}">
                    <a href="{{route('admin.listClass')}}"><i class="feather-calendar"></i> <span>Lớp học</span></a>
                </li>
                <li class=" {{ Request::routeIs('admin.listStudent')? 'active' : '' }}">
                    <a href="{{route('admin.listStudent')}}"><i class="feather-user"></i> <span>Học sinh</span></a>
                </li>

                <li class="menu-title">
                    <span>Nghiệp vụ</span>
                </li>
                <li class=" {{ Request::routeIs('admin.classRoom')? 'active' : '' }}">
                    <a href="{{route('admin.classRoom')}}"><i class="feather-calendar"></i> <span>Phòng học</span></a>
                </li>
                <li>
                    <a href="/logout" onclick="event.preventDefault();
                                                     document.getElementById('logout-form').submit();"><i
                            class="feather-log-out me-1"></i> <span>Đăng xuất</span></a>
                    <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">
                        @csrf
                    </form>
                </li>
            </ul>
        </div>
    </div>
</div>
