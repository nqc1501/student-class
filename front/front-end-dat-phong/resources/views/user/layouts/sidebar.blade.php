<div class="profile-sidebar">
    <div class="widget-profile pro-widget-content">
        <div class="profile-info-widget">
            <a href="#" class="booking-doc-img">

                @if(Auth()->user()->role==1)
                @else
                    <img src="{{$student->image}}" alt="User Image">
                @endif
            </a>

            <div class="profile-det-info">
                @if(Auth()->user()->role==1)
                    <h3>{{Auth()->user()->name }}</h3>
                    <div class="patient-details">
                        <h5 class="mb-0">Quản trị viên</h5>
                    </div>
                @else
                    <h3>{{$student->name }} - [{{$student->student_code}}]</h3>
                    <div class="patient-details">
                        <h5 class="mb-0">Lớp: {{$student->StudentClass->name}}</h5>
                    </div>
                @endif
            </div>
        </div>
    </div>
    <div class="dashboard-widget">
        <nav class="dashboard-menu">
            <ul>
                <li class=" {{ Request::routeIs('getHome')? 'active' : '' }}">
                    <a href="{{route('getHome')}}">
                        <i class="fas fa-calendar-check"></i>
                        <span>Tổng quan</span>
                    </a>
                </li>

                @if(Auth()->user()->role==1)
                    <li >
                        <a href="{{route('admin.home')}}">
                            <i class="fas fa-calendar-check"></i>
                            <span>Trang quản trị</span>
                        </a>
                    </li>
                @else
                    <li class=" {{ Request::routeIs('user.myRequest')? 'active' : '' }}">
                        <a href="{{route('user.myRequest')}}">
                            <i class="fas fa-calendar-check"></i>
                            <span>Phòng học của tôi</span>
                        </a>
                    </li>
                @endif

                <li>

                    <a title="Đăng xuất" href="{{ route('logout') }}"
                       onclick="event.preventDefault();
                                                     document.getElementById('logout-form').submit();">

                        <i class="fas fa-sign-out-alt"></i>
                        Đăng xuất
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>
