<template>
    <div class="appointments">
        <!-- Appointment List -->
        <el-row :gutter="24" class="mb-4" >
            <el-col :sm="16" :md="12" :span="16" class="text-left">
                <label>Từ khoá</label>
                <el-input placeholder="Nhập tên phòng hoặc vị trí phòng..." v-model="dataSearch.key"></el-input>
            </el-col>
            <el-col :span="8">
                <label for="" style="color:transparent">Tìm kiếm</label>
                <el-button class="d-block" @click.prevent="getListClassRoom" type="primary">Tìm kiếm</el-button>
            </el-col>
        </el-row>
        <div v-if="list_data&&list_data.length">
            <div v-for="item in list_data" class="appointment-list">
                <div class="profile-info-widget">
                    <a href="patient-profile.html" class="booking-doc-img">
                        <img :src="item.image" :alt="item.name">
                    </a>
                    <div class="profile-det-info">
                        <h2 style="color:orange">{{ item.name }}</h2>
                        <div class="x">
                            <h5>Trạng thái:
                                <b>
                                    <span style="color:green" v-if="item.status==0">Trống</span>
                                    <span style="color: dodgerblue" v-if="item.status==1">Đã có người đăng ký</span>
                                    <span style="color: indianred" v-if="item.status==2">Đang sử dụng</span>
                                </b>
                            </h5>
                            <h5>Vị trí: <b>{{item.location}}</b></h5>
                            <h5>Ghi chú: <b>{{item.note}}</b></h5>
                            <h5 v-if="item.status!=0">
                                {{item.status==1?'Người đăng ký:':'Người sử dụng:'}}
                                <b>
                                    <span style="color:green" >{{item.owner_info.name}} </span>
                                </b>
                                <ul class="mt-2">
                                    <li> Mã HS/SV: {{item.owner_info.student_code}}</li>
                                    <li>Lớp: {{item.owner_info.student_class.name}}</li>
                                    <li> Sđt: {{item.owner_info.phone}}</li>
                                    <li> Email: {{item.owner_info.email}}</li>
                                    <li>Thời gian sử dụng: Từ <span style="color: red">{{item.startTime}}</span> đến <span style="color: red">{{item.endTime}}</span></li>
                                </ul>
                            </h5>
                            <h5 v-else>
                                <el-button v-if="role==0" class="mt-3" type="primary" @click.prevent="showRegRoom(item)">Đăng ký mượn phòng</el-button>
                            </h5>
                        </div>
                    </div>
                </div>
            </div>
            <div>

                <PhanTrang v-show="paging.total>0" :tongbanghi="paging.total"
                           :batdau="trangbatdau"
                           @pageChange="layLai($event)">
                </PhanTrang>
            </div>
        </div>
        <div v-else class="text-center">
            <p>Chưa có dữ liệu</p>
        </div>
        <div v-if="role==0">
            <el-dialog
                title="Thông tin đăng ký mượn phòng"
                :visible.sync="show_reg"
                custom-class="minWidth375"
                width="50%"
                :before-close="()=>{show_reg=false}">
                <div>
                    <el-row :gutter="24">
                        <el-col :sm="24" :md="12" class="text-left mt-3">
                            <label>Người đăng ký:</label>
                            <el-input disabled v-model="studentInfo.name" placeholder="Nhập..."></el-input>
                        </el-col>
                        <el-col :sm="24" :md="12" class="text-left mt-3">
                            <label>Mã sinh viên:</label>
                            <el-input disabled v-model="studentInfo.student_code" placeholder="Nhập..."></el-input>
                        </el-col>
                        <el-col :sm="24" :md="12" class="text-left mt-3">
                            <label>Lớp học:</label>
                            <el-input disabled v-model="studentInfo.student_class.name" placeholder="Nhập..."></el-input>
                        </el-col>
                        <el-col :sm="24" :md="12" class="text-left mt-3">
                            <label>Số điện thoại:</label>
                            <el-input disabled v-model="studentInfo.phone" placeholder="Nhập..."></el-input>
                        </el-col>
                        <el-col :sm="24" :md="12" class="text-left mt-3">
                            <label>Email:</label>
                            <el-input disabled v-model="studentInfo.email" placeholder="Nhập..."></el-input>
                        </el-col>
                        <el-col :sm="24" :md="12" class="text-left mt-3">
                            <label>Thời gian sử dụng:</label>
                            <el-time-picker
                                style="width:100%"
                                is-range
                                v-model="useTimes"
                                range-separator="-"
                                format="HH:mm:ss"
                                value-format="HH:mm:ss"
                                start-placeholder="Bắt đầu"
                                end-placeholder="Kết thúc">
                            </el-time-picker>
                        </el-col>
                    </el-row>
                </div>
                <span slot="footer" class="dialog-footer">
    <el-button @click="show_reg = false">Đóng</el-button>
    <el-button type="warning" @click="confirmReg()">Đăng ký</el-button>
  </span>
            </el-dialog>
        </div>
    </div>
</template>
<script>
import PhanTrang from "../../Ui/phanTrang";
import rest_api from "../../../api/rest_api";
import Vue from 'vue';
import ElementUI from 'element-ui';
import locale from 'element-ui/lib/locale/lang/vi'
import {
    Icon
} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI, {locale})
Vue.use(Icon);
export default {
    props:['user','role'],
    components: {
        PhanTrang
    },
    data() {
        return {
            useTimes:[ "08:00:00", "17:00:00" ],
            list_data: [],
            loading: {
                status: false,
                text: 'Loading...'
            },
            dataSearch:{
                key:''
            },
            trangbatdau: false,
            paging: {
                total: 0,
                start: 0,
                limit: 10,
                currentPage: 1
            },
            showImg: false,
            linkImgView: '',
            show_reg:false,
            dataReg:{},
            studentInfo:{},

        }
    },
    mounted() {
        console.log('Thông tin đăng nhập:')
        this.studentInfo = JSON.parse(JSON.stringify(this.user))
        console.log('Thông tin:')
        console.log(this.studentInfo)
        this.getListClassRoom()
    },
    methods: {
        layLai(e){
            console.log('onPagingClick')
            this.paging.start = e.start;
            this.paging.limit = e.limit;
            this.paging.currentPage = e.currentPage;
            this.getListClassRoom()},
        showRegRoom(item){
            this.dataReg = item;
            this.show_reg = true;
        },
        getListClassRoom() {
            let params = {
                start: this.paging.start,
                limit: this.paging.limit,
                key: this.dataSearch.key,
                owner:''
            }
            var url = '/user/list-class-room'
            this.loading.status = true;
            this.show_reg = false;
            this.loading.text = 'Loading...'
            this.list_data = [];
            this.paging.total = 0;
            rest_api.post(url, params).then(
                response => {
                    if (response.data.rc == 0) {
                        this.list_data = response.data.data;
                        this.paging.total = response.data.total
                        this.thongBao('success', 'Lấy dữ liệu thành công')
                    } else {
                        this.thongBao('error', response.data.rd)
                    }
                    this.loading.status = false;
                }
            ).catch((e) => {
            })
        },
        confirmReg(){
            console.log('Phòng học:')
            console.log(JSON.stringify(this.dataReg));
            console.log('Người mượn:')
            console.log(JSON.stringify(this.studentInfo))
            console.log('Thời gian:')
            console.log(this.useTimes)
            if(!this.useTimes||this.useTimes.length==0){
                this.thongBao('error','Vui lòng bổ sung thời gian sử dụng');
                return;
            }
            this.$confirm('Xác nhận đăng ký mượn ' + this.dataReg.name+ ', sử dụng từ '+ this.useTimes[0] +' đến '+  this.useTimes[1] +'?', 'Thông báo', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
            })
                .then(_ => {
                    let params = {
                        startTime:this.useTimes[0],
                        endTime:this.useTimes[1],
                        room:this.dataReg.id,
                        owner:this.studentInfo.id
                    }
                    console.log(params)
                    var url = 'user/request-room'
                    this.loading.status = true;
                    this.loading.text = 'Loading...'
                    rest_api.post(url, params).then(
                        response => {
                            if (response.data.rc == 0) {
                                this.getListClassRoom()
                                this.thongBao('success', response.data.rd)
                            } else {
                                this.thongBao('error', response.data.rd)
                            }
                            this.loading.status = false;
                        }
                    ).catch((e) => {
                    })
                })
                .catch(_ => {
                });
        },
        thongBao(typeNoty, msgNoty) {
            let msg = "";
            let cl = "";
            if (msgNoty) {
                msg = msgNoty;
            }
            let type = "success";
            if (typeNoty) {
                type = typeNoty
            }
            if (type == "success") {
                cl = "dts-noty-success"
            }
            if (type == "warning") {
                cl = "dts-noty-warning"
            }
            if (type == "error") {
                cl = "dts-noty-error"
            }
            if (type == "info") {
                cl = "dts-noty-info"
            }
            this.$message({
                customClass: cl,
                showClose: true,
                message: msg,
                type: type,
                duration: 3000
            });
        },
    }
}
</script>
