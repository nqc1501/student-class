<template>
    <el-row :gutter="24" v-loading.fullscreen.lock="loading.status">
        <el-col :span="24">
            <div class="card">
                <div class="card-header">
                    <el-row :gutter="24">
                        <el-col :span="12"><h5>Danh sách lớp học</h5></el-col>
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
                                <th>Mã lớp</th>
                                <th>Tên lớp</th>
                                <th>Hành động</th>
                            </tr>
                            </thead>
                            <tbody v-if="list_data&&list_data.length">
                            <tr v-for="(item,index) in list_data" :key="index">
                                <td class="text-center">{{ index + 1 }}</td>
                                <td>{{ item.code }}</td>
                                <td>{{ item.name }}</td>
                                <td class="text-center">
                                    <el-button size="mini" type="warning"
                                               @click.prevent="showUpdate(item)">Cập nhật
                                    </el-button>
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
        <el-col :span="24">
            <el-dialog
                title="Cập nhật thông tin"
                :visible.sync="show_update"
                custom-class="minWidth375"
                width="50%"
                :before-close="handleClose">
                <div>
                    <el-row :gutter="24">
                        <el-col :sm="24" :md="12" class="text-left">
                            <label>Mã lớp học:</label>
                            <el-input disabled v-model="infoUpdate.code"  placeholder="Nhập..."></el-input>
                        </el-col>
                        <el-col :sm="24" :md="12" class="text-left">
                            <label>Tên lớp học:</label>
                            <el-input v-model="infoUpdate.name"  placeholder="Nhập..."></el-input>
                        </el-col>
                    </el-row>
                </div>
                <span slot="footer" class="dialog-footer">
    <el-button @click="show_update = false">Đóng</el-button>
    <el-button type="primary" @click="confirmUpdate()">Cập nhật</el-button>
  </span>
            </el-dialog>
            <el-dialog
                title="Thêm mới lớp học"
                :visible.sync="show_add"
                custom-class="minWidth375"
                width="50%"
                :before-close="handleClose">
                <div>
                    <el-row :gutter="24">
                        <el-col :sm="24" :md="12" class="text-left">
                            <label>Mã lớp học:</label>
                            <el-input v-model="infoAdd.code" placeholder="Nhập..."></el-input>
                        </el-col>
                        <el-col :sm="24" :md="12" class="text-left">
                            <label>Tên lớp học:</label>
                            <el-input v-model="infoAdd.name" placeholder="Nhập..."></el-input>
                        </el-col>
                    </el-row>
                </div>
                <span slot="footer" class="dialog-footer">
    <el-button @click="show_add = false" size="mini">Đóng</el-button>
    <el-button type="primary" size="mini" @click="confirmAdd()">Thêm</el-button>
  </span>
            </el-dialog>
        </el-col>
    </el-row>
</template>
<script>
import rest_api from "../../../api/rest_api";
import Vue from 'vue';
import ElementUI from 'element-ui';
import PhanTrang from "../../Ui/phanTrang";
import {
    Icon
} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);
Vue.use(Icon);
export default {
    components: {
        PhanTrang
    },
    data() {
        return {
            list_data: [],
            loading: {
                status: false,
                text: 'Loading...'
            },
            show_update: false,
            show_add: false,
            infoUpdate: {},
            infoAdd: {
                code: '',
                name: ''
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
        this.getListClass()
    },
    methods: {
        showAddClass() {
            this.show_add = true;
        },
        showUpdate(item) {
            this.show_update = true;
            this.infoUpdate = JSON.parse(JSON.stringify(item));
        },
        layLai(e) {
            console.log('onPagingClick')
            this.paging.start = e.start;
            this.paging.limit = e.limit;
            this.paging.currentPage = e.currentPage;
            this.getListClass()
        },
        confirmUpdate() {
            this.$confirm('Xác nhận cập nhật thông tin ?', 'Thông báo', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
            })
                .then(_ => {
                    let params = {
                        id: this.infoUpdate.id,
                        name: this.infoUpdate.name
                    }
                    var url = '/admin/update-class'
                    this.loading.status = true;
                    this.loading.text = 'Loading...'
                    rest_api.post(url, params).then(
                        response => {
                            if (response.data.rc == 0) {
                                this.getListClass()
                                this.thongBao('success', 'Cập nhật dữ liệu thành công')
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
        confirmDel(id) {
            this.$confirm('Xác nhận xoá dữ liệu lớp học này?', 'Thông báo', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
            })
                .then(_ => {
                    let params = {
                        id: id,
                    }
                    console.log(params)
                    var url = '/admin/delete-class'
                    this.loading.status = true;
                    this.loading.text = 'Loading...'
                    rest_api.post(url, params).then(
                        response => {
                            if (response.data.rc == 0) {
                                this.getListClass()
                                this.thongBao('success', 'Xoá dữ liệu thành công')
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
        confirmAdd() {

            this.$confirm('Xác nhận thêm mới thông tin lớp học?', 'Thông báo', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
            })
                .then(_ => {
                    let params = {
                        code: this.infoAdd.code,
                        name: this.infoAdd.name
                    }
                    var url = '/admin/add-class'
                    this.loading.status = true;
                    this.loading.text = 'Loading...'
                    rest_api.post(url, params).then(
                        response => {
                            if (response.data.rc == 0) {
                                this.getListClass()
                                this.thongBao('success', 'Thêm dữ liệu thành công')
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
        handleClose() {
            this.show_update = false;
            this.show_add = false;
        },
        getListClass() {
            let params = {
                start: this.paging.start,
                limit: this.paging.limit,
                key: ''
            }
            var url = '/admin/list-class'
            this.loading.status = true;
            this.show_update = false;
            this.show_add = false;
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
