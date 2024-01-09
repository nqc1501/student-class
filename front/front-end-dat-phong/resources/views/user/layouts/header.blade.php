<header class="header">
    <nav class="navbar navbar-expand-lg header-nav">
        <div class="navbar-header">
            <a id="mobile_btn" href="javascript:void(0);">
							<span class="bar-icon">
								<span></span>
								<span></span>
								<span></span>
							</span>
            </a>
            <a href="index.html" class="navbar-brand logo">
                <img src="assets/img/logo.png" class="img-fluid" alt="Logo">
            </a>
        </div>
        <div class="main-menu-wrapper">
            <div class="menu-header">
                <a href="index.html" class="menu-logo">
                    <img src="assets/img/logo.png" class="img-fluid" alt="Logo">
                </a>
                <a id="menu_close" class="menu-close" href="javascript:void(0);">
                    <i class="fas fa-times"></i>
                </a>
            </div>
        </div>
        <ul class="nav header-navbar-rht">
            <li class="nav-item contact-item">
                <div class="header-contact-img">
                    <i class="fas fa-user-injured"></i>
                </div>
                <div class="header-contact-detail">
                    <p class="contact-header">{{Auth::user()->name}}</p>
                    @if (Auth()->user()->role == 1)
                        <p class="contact-info-header"></p>
                    @else
                        <p class="contact-info-header">{{$student->student_code}}</p>
                    @endif
                </div>
            </li>
            <!-- User Menu -->
            <li class="nav-item dropdown has-arrow logged-item">
                <a href="#" class="dropdown-toggle nav-link" data-bs-toggle="dropdown">
                    <span class="user-img">
                        @if (Auth()->user()->role == 1)
                        @else
                            <img class="rounded-circle" src="{{$student->image}}" width="31"
                                 alt="{{$student->name}}">
                        @endif
                    </span>
                </a>
                <div class="dropdown-menu dropdown-menu-end">
                    <div class="user-header">
                        @if (Auth()->user()->role == 1)
                        @else
                            <div class="avatar avatar-sm">
                                <img src="{{$student->image}}" alt="User Image"
                                     class="avatar-img rounded-circle">
                            </div>
                        @endif
                        <div class="user-text">
                            @if (Auth()->user()->role == 1)
                                {{Auth()->user()->name}}
                            @else
                                <h6>{{$student->name}}</h6>
                            @endif
                            <p class="text-muted mb-0">{{Auth::user()->role==1?'Quản trị viên':'Sinh viên'}}</p>
                        </div>
                    </div>
                    <a class="dropdown-item" href="{{ route('logout') }}"
                       onclick="event.preventDefault();
                                                     document.getElementById('logout-form').submit();">
                        Đăng xuất
                    </a>

                    <form id="logout-form" action="{{ route('logout') }}" method="POST"
                          style="display: none;">
                        @csrf
                    </form>


                </div>
            </li>
            <!-- /User Menu -->

        </ul>
    </nav>
</header>
<div class="breadcrumb-bar">
    <div class="container-fluid">
        <div class="row align-items-center">
            <div class="col-md-12 col-12">
                <h2 class="breadcrumb-title">Hệ thống đăng ký mượn phòng học</h2>
            </div>
        </div>
    </div>
</div>
