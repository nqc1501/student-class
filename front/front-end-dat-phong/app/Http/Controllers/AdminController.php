<?php

namespace App\Http\Controllers;

use App\ClassList;
use App\ClassRooms;
use App\StudentList;
use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Log;

class AdminController extends Controller
{
    public function getHome()
    {
        return view('admin.home');
    }

    public function listClass()
    {
        return view('admin.class');
    }

    public function listStudent()
    {
        return view('admin.student');
    }

    public function listClassRoom()
    {
        return view('admin.class-room');
    }

    public function listUser()
    {
        return view('admin.user');
    }

    public function getListUser(Request $request)
    {
        $req = $request->all();
        $list = User::where('name', 'like', '%' . $req['name'] . '%');
        if (isset($req['email'])) {
            $list = $list->where('email', 'like', '%' . $req['email'] . '%');
        }
        $total = $list->orderBy('created_at', 'DESC')->get()->count();
        $data = $list->orderBy('name', 'ASC')->skip($req['start'])->take($req['limit'])->with(['StudentInfo', 'StudentInfo.StudentClass'])->get();
        if (count($data)) {
            $res = [
                'rc' => '0',
                'data' => $data,
                'total' => $total
            ];
        } else {
            $res = [
                'rc' => '1',
                'rd' => 'Không tìm thấy bản ghi nào'
            ];
        }
        return json_encode($res);
    }

    public function getListClassRoom(Request $request)
    {
        $req = $request->all();
        $list = ClassRooms::where('name', 'like', '%' . $req['key'] . '%');
        if(isset($req['owner'])){
            $list = $list->where('owner',$req['owner']);
        }
        if(isset($req['key'])){
            $list = $list->where('name','like','%'.$req['key'].'%')->orWhere('location','like','%'.$req['key'].'%');
        }
        $total = $list->count();
        $data = $list->orderBy('name', 'ASC')->skip($req['start'])->take($req['limit'])->with('ownerInfo', 'ownerInfo.StudentClass')->get();
        if (count($data)) {
            $res = [
                'rc' => '0',
                'data' => $data,
                'total' => $total
            ];
        } else {
            $res = [
                'rc' => '1',
                'rd' => 'Không tìm thấy bản ghi nào'
            ];
        }
        return json_encode($res);
    }

    public function getListClass(Request $request)
    {
        $req = $request->all();
        $list = ClassList::where('name', 'like', '%' . $req['key'] . '%');
        $data = $list->orderBy('name', 'ASC')->skip($req['start'])->take($req['limit'])->get();
        if (count($data)) {
            $res = [
                'rc' => '0',
                'data' => $data,
                'total' => ClassList::where('name', 'like', '%' . $req['key'] . '%')->count()
            ];
        } else {
            $res = [
                'rc' => '1',
                'rd' => 'Không tìm thấy bản ghi nào'
            ];
        }
        return json_encode($res);
    }

    public function getListStudent(Request $request)
    {
        $req = $request->all();
        $total = 0;
        $data = null;
        if(isset($req['all'])){
            if ($req['all']){
                $data = StudentList::with(['StudentClass'])->get();
                $total = count($data);
            }
        }else{

            $list = StudentList::where('name', 'like', '%' . $req['name'] . '%');
            if (isset($req['class'])) {
                $list = $list->where('class', '=', $req['class']);
            }
            if (isset($req['code'])) {
                $list = $list->where('student_code', 'like', '%' . $req['code'] . '%');
            }
            $total = $list->orderBy('created_at', 'DESC')->get()->count();
            $data = $list->orderBy('name', 'ASC')->skip($req['start'])->take($req['limit'])->with(['StudentClass'])->get();

        }

        if (count($data)) {
            $res = [
                'rc' => '0',
                'data' => $data,
                'total' => $total
            ];
        } else {
            $res = [
                'rc' => '1',
                'rd' => 'Không tìm thấy bản ghi nào'
            ];
        }
        return json_encode($res);
    }

    public function addClassRoom(Request $request)
    {
        $req = $request->all();
        $hinhAnh = $request->file('hinhAnh');
        if (isset($req['name']) && isset($req['location']) && isset($hinhAnh)) {
            $filePathHinhAnh = '/classRooms/' . uniqid() . '.' . $hinhAnh->extension();
            $hinhAnh->move(public_path('classRooms'), $filePathHinhAnh);
            $dataCreat = ClassRooms::create([
                'name' => $req['name'],
                'location' => $req['location'],
                'note' => $req['note'],
                'image' => $filePathHinhAnh,
            ]);
            $res = [
                'rc' => 0,
                'rd' => 'Thêm dữ liệu thành công.',
                'data' => $dataCreat
            ];
        } else {
            $res = [
                'rc' => -1,
                'rd' => 'Thông tin thêm mới không đầy đủ.',
                'data' => $req
            ];
        }
        return json_encode($res);
    }

    public function addClass(Request $request)
    {
        $req = $request->all();
        $check = ClassList::where('code', $req['code'])->first();
        if (isset($check)) {
            $res = [
                'rc' => -1,
                'rd' => 'Mã lớp học đã tồn tại trên hệ thống.',
                'data' => $req
            ];
        } else {
            if (isset($req['name']) && isset($req['code'])) {
                $dataCreat = ClassList::create([
                    'name' => $req['name'],
                    'code' => $req['code'],
                ]);
                $res = [
                    'rc' => 0,
                    'rd' => 'Thêm dữ liệu thành công.',
                    'data' => $dataCreat
                ];
            } else {
                $res = [
                    'rc' => -1,
                    'rd' => 'Thông tin thêm mới không đầy đủ.',
                    'data' => $req
                ];
            }
        }
        return json_encode($res);
    }

    public function addStudent(Request $request)
    {
        $req = $request->all();
        if (isset($req['name']) && isset($req['student_code']) && isset($req['gender']) && isset($req['class']) && isset($req['email']) && isset($req['phone']) && isset($req['birthday']) && isset($req['hinhAnh'])) {
            $hinhAnh = $request->file('hinhAnh');
            $filePathHinhAnh = '/students/' . uniqid() . '.' . $hinhAnh->extension();
            $hinhAnh->move(public_path('students'), $filePathHinhAnh);
            $dataCreat = StudentList::create([
                'name' => $req['name'],
                'student_code' => $req['student_code'],
                'gender' => $req['gender'],
                'class' => $req['class'],
                'email' => $req['email'],
                'phone' => $req['phone'],
                'birthday' => $req['birthday'],
                'image' => $filePathHinhAnh,
            ]);
            $res = [
                'rc' => 0,
                'rd' => 'Thêm dữ liệu thành công.',
                'data' => $dataCreat
            ];
        } else {
            $res = [
                'rc' => -1,
                'rd' => 'Thông tin thêm mới thiếu hoặc không hợp lệ.',
            ];
        }
        return json_encode($res);
    }

    public function updateClassRoom(Request $request)
    {
        $id = $request->id;
        $check = ClassRooms::where('id', $id)->first();
        if (isset($check)) {
            if(isset($request->name)&&isset($request->location)){
                if (isset($request->name)) {
                    $check->name = $request->name;
                    $check->location = $request->location;
                    $check->note = $request->note;
                    if ($request->file('hinhAnh')) {
                        $hinhAnh = $request->file('hinhAnh');
                        $filePathHinhAnh = '/classRooms/' . uniqid() . '.' . $hinhAnh->extension();
                        $hinhAnh->move(public_path('classRooms'), $filePathHinhAnh);
                        $check->image = $filePathHinhAnh;
                    }
                    if(isset($request->status)){
                        $stt = $request->status;
                        if($stt==0){
                            $check->owner = null;
                            $check->start_time = '00:00:00';
                            $check->end_time = '23:59:59';
                            $check->status = $stt;
                        }else if ($stt==2){
                            $check->status = $stt;
                        }
                    }
                    $check->save();
                    $res = [
                        'rc' => '0',
                        'rd' => 'Cập nhật thành công'
                    ];
                } else {
                    $res = [
                        'rc' => -1,
                        'rd' => 'Thiếu thông tin tên lớp học.',
                    ];
                }
            }else{
                $res = [
                    'rc' => -1,
                    'rd' => 'Thông tin chỉnh sửa không đầy đủ. Kiểm tra lại.',
                ];
            }
        } else {
            $res = [
                'rc' => -1,
                'rd' => 'Mã lớp học đã tồn tại trên hệ thống.',
            ];
        }
        return json_encode($res);
    }

    public function updateClass(Request $request)
    {
        $id = $request->id;
        $check = ClassList::where('id', $id)->first();
        if (isset($check)) {
            if (isset($request->name)) {

                $check->name = $request->name;
                $check->save();
                $res = [
                    'rc' => '0',
                    'rd' => 'Cập nhật thành công'
                ];
            } else {
                $res = [
                    'rc' => -1,
                    'rd' => 'Thiếu thông tin tên lớp học.',
                ];
            }
        } else {
            $res = [
                'rc' => -1,
                'rd' => 'Mã lớp học đã tồn tại trên hệ thống.',
            ];
        }
        return json_encode($res);
    }

    public function updateStudent(Request $request)
    {
        $req = $request->all();
        if (isset($req['id']) && isset($req['name']) && isset($req['student_code']) && isset($req['gender']) && isset($req['class']) && isset($req['email']) && isset($req['phone']) && isset($req['birthday'])) {
            $check = StudentList::where('id', $req['id'])->first();
            if ($check) {
                $check->name = $req['name'];
                $check->student_code = $req['student_code'];
                $check->gender = $req['gender'];
                $check->birthday = $req['birthday'];
                $check->class = $req['class'];
                $check->email = $req['email'];
                $check->phone = $req['phone'];
                $check->abilityToBorrow = $req['abilityToBorrow'];
                if ($request->file('hinhAnh')) {
                    $hinhAnh = $request->file('hinhAnh');
                    $filePathHinhAnh = '/students/' . uniqid() . '.' . $hinhAnh->extension();
                    $hinhAnh->move(public_path('students'), $filePathHinhAnh);
                    $check->image = $filePathHinhAnh;
                }
                $check->save();
                $res = [
                    'rc' => 0,
                    'rd' => 'Chỉnh sửa thông tin thành công.',
                    'data' => $check
                ];
            } else {
                $res = [
                    'rc' => -1,
                    'rd' => 'Không tìm thấy bản ghi tương ứng.',
                ];
            }
        } else {
            $res = [
                'rc' => -1,
                'rd' => 'Thông tin thêm mới không hợp lệ. Vui lòng kiểm tra lại.',
            ];
        }
        return json_encode($res);
    }

    public function deleteStudent(Request $request)
    {
        $id = $request->id;
        $checkStudent = StudentList::where('id', $id)
            ->first();
        if ($checkStudent) {
            $email = $checkStudent->email;
            $checkUser = User::where('email', $email)->first();
            if ($checkUser) {
                $checkStudent->delete();
                $checkUser->delete();
                $res = [
                    'rc' => '0',
                    'rd' => 'Xoá dữ liệu học sinh và tài khoản người dùng đi kèm với email ' . $email . ' thành công'
                ];
            } else {
                $checkStudent->delete();
                $res = [
                    'rc' => '0',
                    'rd' => 'Xoá dữ liệu học sinh thành công.'
                ];
            }
        } else {
            $res = [
                'rc' => '-1',
                'rd' => 'Không tìm thấy tài khoản tương ứng'
            ];
        }
        return json_encode($res);
    }

    public function deleteUser(Request $request)
    {
        Log::info('Xoá tài khoản người dùng');
        $id = $request->id;
        $checkUser = User::where('id', $id)->first();
        if (isset($checkUser)) {
            Log::info('cas1');
            if ($checkUser->role == 1) {
                Log::info('case 1-1');
                $res = [
                    'rc' => '-1',
                    'rd' => 'Tài khoản quản trị viên, không thể xoá'
                ];
            } else {
                Log::info('Case 1-2');
                $checkUser->delete();
                $res = [
                    'rc' => '0',
                    'rd' => 'Xoá tài khoản người dùng thành công.'
                ];
            }
        } else {
            Log::info('case2');
            $res = [
                'rc' => '-1',
                'rd' => 'Không tìm thấy tài khoản tương ứng'
            ];
        }
        return json_encode($res);
    }

    public function deleteClassRoom(Request $request)
    {
        $id = $request->id;
        $check = ClassRooms::where('id', $id)->first();
        if (isset($check)) {

            $check->delete();
            $res = [
                'rc' => '0',
                'rd' => 'Xoá thành công'
            ];
        } else {
            $res = [
                'rc' => '-1',
                'rd' => 'Không tìm thấy phòng học cần xoá.'
            ];
        }
        return json_encode($res);
    }

    public function deleteClass(Request $request)
    {
        $id = $request->id;
        $check = StudentList::where('class', $id)->count();
        if (isset($check)) {
            $res = [
                'rc' => '-1',
                'rd' => 'Lớp học có học sinh đang học.Không thể xoá.'
            ];
        } else {
            $check = ClassList::where('id', $id)
                ->first();
            $check->delete();
            $res = [
                'rc' => '0',
                'rd' => 'Xoá thành công'
            ];
        }
        return json_encode($res);
    }
}
