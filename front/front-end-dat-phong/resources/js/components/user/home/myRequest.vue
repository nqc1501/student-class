<template>
    <div class="appointments">
        <!-- Appointment List -->
        <div v-if="list_data&&list_data.length">
            <div v-for="item in list_data" class="appointment-list">
                <div class="profile-info-widget">
                    <div class="booking-doc-img">
                        <img style="width: 150px;height: 180px" :src="item.image" :alt="item.name">
                    </div>
                    <div class="profile-det-info">
                        <h2 style="color:orange">{{ item.name }}</h2>
                        <div class="x">
                            <h5>Trạng thái:
                                <b>
                                    <span style="color: dodgerblue" v-if="item.status==1">Chờ duyệt <i class="el-icon-loading"></i></span>
                                    <span style="color: indianred" v-if="item.status==2">Đang sử dụng</span>
                                </b>
                            </h5>
                            <h5>Vị trí: <b>{{item.location}}</b></h5>
                            <h5>Ghi chú: <b>{{item.note}}</b></h5>
                            <h5>Thời gian sử dụng: <b style="color:red">{{item.startTime}} </b>đến <b style="color:red">{{item.endTime}}</b></h5>
                            <div v-if="item.status==1" class="mt-2">
                                <el-button type="danger" size="mini" @click.prevent="showCancelRoom(item)">Huỷ đăng ký</el-button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div v-else class="text-center">
            <p>Chưa có dữ liệu</p>
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
    props:['user','mydata'],
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
            trangbatdau: false,
            paging: {
                total: 0,
                start: 0,
                limit: 1000,
                currentPage: 1
            },
            showImg: false,
            linkImgView: '',
            show_reg:false,
            dataReg:{}

        }
    },
    mounted() {
        console.log('Danh sách yêu cầu của tôi:')
        console.log(this.user)
        this.getListClassRoom();
    },
    methods: {
        showCancelRoom(item){
            console.log('Thông tin phòng huỷ:')
            console.log(item)
            this.$confirm('Xác nhận huỷ đăng ký của bạn?', 'Thông báo', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
            })
                .then(_ => {
                    let params = {
                        id:item.id,
                    }
                    console.log(params)
                    var url = 'user/cancel-room'
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

        getListClassRoom() {
            let params = {
                start: this.paging.start,
                limit: this.paging.limit,
                key: '',
                owner:this.user.id
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
