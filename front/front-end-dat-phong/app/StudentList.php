<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class StudentList extends Model
{
    protected $fillable = [
        'name', 'birthday','email','phone','student_code','class','image','gender','ability_to_borrow'
    ];

    protected $table = 'students';
    public function StudentClass(){
        return $this->belongsTo(ClassList::class,'class','id');
    }
}
