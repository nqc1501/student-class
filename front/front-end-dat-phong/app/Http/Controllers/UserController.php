<?php

namespace App\Http\Controllers;

use App\ClassRooms;
use App\StudentList;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Log;

class UserController extends Controller
{
    public function myRequest(){
        $user = Auth::user();
        $student = StudentList::where('email', $user->email)->with('StudentClass')->first();
        $myClass= ClassRooms::where('owner',$student->id)->with('ownerInfo')->get();
        return view('user.my-request', compact('student','myClass'));
    }
    public function getUserHome()
    {
        $user = Auth::user();
        $student = StudentList::where('email', $user->email)->with('StudentClass')->first();
        return view('user.home', compact('student'));
    }
    public function cancelRoom(Request $request){
        $req = $request->all();
        $check = ClassRooms::where('id',$req['id'])->first();
        if(isset($check)){
            $check->owner = null;
            $check->status=0;
            $check->start_time = '00:00:00';
            $check->end_time = '23:59:59';
            $check->save();

            $res = [
                'rc' => 0,
                'rd' => 'Huỷ phòng thành công',
                'data' => $check
            ];
        }else{
            $res = [
                'rc' => -1,
                'rd' => 'Không tìm thấy bản ghi phù hợp',
            ];
        }
        return json_encode($res);
    }
    public function requestRoom(Request $request)
    {
        $req = $request->all();
        $check = ClassRooms::where('id', $req['room'])->first();
        Log::info('Phòng:');
        Log::info($check);
        if ($check) {
            if (isset($req['start_time']) && isset($req['start_time']) && isset($req['room'])) {
                $check->owner = $req['owner'];
                $check->start_time = $req['start_time'];
                $check->end_time = $req['end_time'];
                $check->status = 1;
                $check->save();
                $res = [
                    'rc' => 0,
                    'rd' => 'Thêm dữ liệu thành công.',
                    'data' => $check
                ];
            } else {
                $res = [
                    'rc' => -1,
                    'rd' => 'Thông tin thêm mới thiếu hoặc không hợp lệ.',
                ];
            }
        } else {
            $res = [
                'rc' => -1,
                'rd' => 'Không tìm thấy phòng học đăng ký.',
            ];
        }
        return json_encode($res);
    }
}
