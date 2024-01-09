<template>
    <el-row :gutter="24" v-loading.fullscreen.lock="loading.status">
        <el-col :sm="8" :md="4" class="text-left">
            <label>Tên người dùng</label>
            <el-input placeholder="Nhập..." v-model="dataSearch.name"></el-input>
        </el-col>
        <el-col :sm="8" :md="4" class="text-left">
            <label>Email người dùng</label>
            <el-input placeholder="Nhập..." v-model="dataSearch.email"></el-input>
        </el-col>
        <el-col :sm="8" :md="4" class="text-left">
            <span style="color: transparent">Tìm kiếm</span>
            <el-button type="success" @click.prevent="getListUser()" class="d-block">Tìm kiếm</el-button>
        </el-col>
        <el-col class="mt-3" :span="24">
            <div class="card">
                <div class="card-header">
                    <el-row :gutter="24">
                        <el-col :span="12"><h5>Danh sách người dùng</h5></el-col>
                        <el-col :span="12" class="text-right">
                            <el-button type="primary" size="mini" @click.prevent="showAddClass()">Thêm mới</el-button>
                        </el-col>
                    </el-row>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="datatable table-bordered table hover-table">
                            <thead class="thead-light">
                            <tr>
                                <th>STT</th>
                                <th>Bí danh</th>
                                <th>Email</th>
                                <th>Vai trò</th>
                                <th>Mã học sinh</th>
                                <th>Tên học sinh</th>
                                <th>Hình ảnh</th>
                                <th>Lớp học</th>
                                <th>Ngày sinh</th>
                                <th>Số điện thoại</th>
                                <th>Ngày đăng ký</th>
                                <th>Trạng thái mượn phòng học</th>
                                <th>Hành động</th>
                            </tr>
                            </thead>
                            <tbody v-if="list_data&&list_data.length">
                            <tr v-for="(item,index) in list_data" :key="index">
                                <td class="text-center">{{ index + 1 }}</td>
                                <td>{{ item.name }}</td>
                                <td>{{ item.email }}</td>
                                <td>
                                    <el-button size="mini" v-if="item.role==1" type="primary">Quản trị viên</el-button>
                                    <el-button size="mini" v-else type="warning">Học sinh/Sinh viên</el-button>
                                </td>
                                <td>
                                    <div v-if="item.student_info">
                                        {{ item.student_info.student_code }}
                                    </div></td>
                                <td>
                                    <div v-if="item.student_info">
                                        {{ item.student_info.name }}
                                    </div>
                                </td>
                                <td class="text-center">
                                    <div v-if="item.student_info">
                                        <img style="width: 120px; height: 150px;padding: 10px 0" :src="item.student_info.image"
                                             class="image">
                                    </div>
                                </td>
                                <td class="text-center">
                                    <div v-if="item.student_info">
                                        {{ item.student_info.student_class.name }}
                                    </div>
                                </td>
                                <td class="text-center">
                                    <div v-if="item.student_info">
                                        {{ item.student_info.birthday }}
                                    </div>
                                </td>
                                <td class="text-center">
                                    <div v-if="item.student_info">
                                        {{ item.student_info.phone }}
                                    </div>
                                </td>
                                <td class="text-center"> {{item.created_at}}</td>
                                <td class="text-center">
                                    <div v-if="item.student_info">
                                        <el-button size="mini" type="success" v-if="item.student_info.abilityToBorrow==1">Có thể</el-button>
                                        <el-button size="mini" type="info" v-else>Đang tạm khoá</el-button>
                                    </div>
                                </td>
                                <td class="text-center">
                                    <el-button size="mini" type="danger"
                                               @click.prevent="confirmDel(item.id)">Xoá
                                    </el-button>
                                </td>
                            </tr>
                            </tbody>
                            <tbody v-else>
                            <tr>
                                <td colspan="10" class="text-center">
                                    <p>Không có dữ liệu</p>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <PhanTrang v-show="paging.total>0" :tongbanghi="paging.total"
                                   :batdau="trangbatdau"
                                   @pageChange="layLai($event)">
                        </PhanTrang>
                    </div>
                </div>
            </div>
        </el-col>
    </el-row>
</template>
<script>
import rest_api from "../../../api/rest_api";
import Vue from 'vue';
import ElementUI from 'element-ui';
import PhanTrang from "../../Ui/phanTrang";
import locale from 'element-ui/lib/locale/lang/vi'
import {
    Icon
} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI, {locale})
Vue.use(Icon);
export default {
    components: {
        PhanTrang
    },
    data() {
        return {
            dataSearch: {
                name: '',
                email: '',
            },

            list_data: [],
            loading: {
                status: false,
                text: 'Loading...'
            },
            trangbatdau: false,
            paging: {
                total: 0,
                start: 0,
                limit: 10,
                currentPage: 1
            },
        }
    },
    mounted() {
        this.getListUser()
    },
    methods: {
        confirmDel(id){
            this.$confirm('Xác nhận xoá thông tin người dùng?', 'Thông báo', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
            })
                .then(_ => {
                    let params = {
                        id:id,
                    }
                    console.log(params)
                    var url = 'admin/delete-user'
                    this.loading.status = true;
                    this.loading.text = 'Loading...'
                    rest_api.post(url, params).then(
                        response => {
                            if (response.data.rc == 0) {
                                this.getListUser()
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
        getListUser() {
            let params = {
                start: this.paging.start,
                limit: this.paging.limit,
                email: this.dataSearch.email,
                name: this.dataSearch.name,
            }
            var url = '/admin/list-user'
            this.loading.status = true;
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
        layLai(e) {
            console.log('onPagingClick')
            this.paging.start = e.start;
            this.paging.limit = e.limit;
            this.paging.currentPage = e.currentPage;
            this.getListClass()
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
<style scoped="scoped">
label {
    margin-bottom: 0;
}

td {
    text-align: left;
}

th {
    text-align: center;
}
</style>
