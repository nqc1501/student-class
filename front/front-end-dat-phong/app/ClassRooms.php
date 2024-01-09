<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ClassRooms extends Model
{
    protected $fillable = [
        'name', 'status','start_time','end_time','owner','note','location','image'
    ];

    protected $table = 'classrooms';
    public function ownerInfo(){
        return $this->belongsTo(StudentList::class,'owner','id');
    }
}
