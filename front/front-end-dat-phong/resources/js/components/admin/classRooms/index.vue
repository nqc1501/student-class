<template>
    <el-row :gutter="24" v-loading.fullscreen.lock="loading.status">
        <el-col :xs="12" :sm="12" :md="6" :lg="4">
            <label>Từ khoá</label>
            <el-input placeholder="Tên hoặc vị trí phòng..." v-model="dataSearch.key"></el-input>
        </el-col>
        <el-col :xs="12" :sm="12" :md="8" :lg="8">
            <label>Học sinh</label>
            <el-select style="width: 100%" filterable v-model="dataSearch.owner" placeholder="Chọn">
                <el-option
                    v-for="item in list_students"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value">
                </el-option>
            </el-select>
        </el-col>
        <el-col :xs="12" :sm="12" :md="8" :lg="8">
            <label for="" style="color:transparent">Tìm kiếm</label>
            <el-button class="d-block" @click.prevent="getListClassRoom" type="primary">Tìm kiếm</el-button>
        </el-col>
        <el-col :span="24" class="mt-4">
            <div class="card">
                <div class="card-header">
                    <el-row :gutter="24">
                        <el-col :span="12"><h5>Danh sách phòng học cho mượn</h5></el-col>
                        <el-col :span="12" class="text-right">
                            <el-button type="primary" size="mini" @click.prevent="showAddClassRoom()">Thêm mới
                            </el-button>
                        </el-col>
                    </el-row>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="datatable table-bordered table hover-table">
                            <thead class="thead-light">
                            <tr>
                                <th>STT</th>
                                <th>Tên phòng học</th>
                                <th>Vị trí</th>
                                <th>Hình ảnh</th>
                                <th>Ghi chú</th>
                                <th>Trạng thái</th>
                                <th>Người sử dụng</th>
                                <th>Thời gian đăng ký sử dụng</th>
                                <th>Hành động</th>
                            </tr>
                            </thead>
                            <tbody v-if="list_data&&list_data.length">
                            <tr v-for="(item,index) in list_data" :key="index">
                                <td class="text-center">{{ index + 1 }}</td>
                                <td>{{ item.name }}</td>
                                <td>{{ item.location }}</td>
                                <td>
                                    <el-card class="box-card" style="min-width: 150px">
                                        <img v-if="item.image" style="width:150px;height:120px" :src="item.image"
                                             :alt="item.name">
                                    </el-card>
                                </td>
                                <td>{{ item.note }}</td>
                                <td class="text-center">
                                    <el-button v-if="item.status==0" size="mini" type="success">Phòng trống</el-button>
                                    <el-button v-if="item.status==1" size="mini" type="primary">Đã đăng ký</el-button>
                                    <el-button v-if="item.status==2" size="mini" type="info">Đang sử dụng</el-button>
                                </td>
                                <td>
                                    <el-card v-if="item.owner_info" class="box-card" style="min-width:300px">
                                        <el-row :gutter="24">
                                            <el-col :span="8">
                                                <img style="width:80px;height:100px" :src="item.owner_info.image"
                                                     alt="">
                                            </el-col>
                                            <el-col :span="16">
                                                <p style="margin:0">{{ item.owner_info.name }} -
                                                    [{{ item.owner_info.student_code }}]</p>
                                                <span>Lớp học: {{ item.owner_info.student_class.name }}</span>
                                                <p>Sđt: {{ item.owner_info.phone }}</p>
                                            </el-col>
                                        </el-row>
                                    </el-card>
                                </td>
                                <td class="text-center">{{ item.startTime ? item.startTime : '[Trống]' }} -
                                    {{ item.endTime ? item.endTime : '[Trống]' }}
                                </td>
                                <td class="text-center">
                                    <el-button size="mini" type="info"
                                               :disabled="item.status!=1"
                                               @click.prevent="upDateStatusRoom(item,0)">Từ chối
                                    </el-button>
                                    <el-button size="mini" type="primary"
                                               :disabled="item.status!=1"
                                               @click.prevent="upDateStatusRoom(item,2)">Chấp nhận
                                    </el-button>
                                    <el-button size="mini" type="success"
                                               :disabled="item.status!=2"
                                               @click.prevent="upDateStatusRoom(item,0)">Trả phòng
                                    </el-button>
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
                            <label>Tên phòng:</label>
                            <el-input v-model="infoUpdate.name" placeholder="Nhập..."></el-input>
                        </el-col>
                        <el-col :sm="24" :md="12" class="text-left">
                            <label>Vị trí:</label>
                            <el-input v-model="infoUpdate.location" placeholder="Nhập..."></el-input>
                        </el-col>
                        <el-col :sm="24" :md="12" class="text-left mt-3">
                            <label>Ghi chú:</label>
                            <el-input v-model="infoUpdate.note" placeholder="Nhập..."></el-input>
                        </el-col>
                        <el-col :sm="24" :md="12" class="text-left mt-3">
                            <span>Hình ảnh phòng học</span>
                            <div class="source d-flex">
                                <ul class="el-upload-list el-upload-list--picture-card " style="display: contents">
                                    <template v-for="(link,i) in listFileUpload">
                                        <li tabindex="0" class="el-upload-list__item is-ready">
                                            <div>
                                                <img v-if="link.type == 1" :src="link.link" alt=""
                                                     class="el-upload-list__item-thumbnail">
                                                <span class="el-upload-list__item-actions">
                                        <span class="el-upload-list__item-delete" @click.prevent="removeUpload(link,i)">
                                                                                              <i class="el-icon-delete"/>
                                                                                            </span>
                                        <span class="el-upload-list__item-delete" @click.prevent="previewLink(link)">
                                          <i class="el-icon-view"/>
                                        </span>
                                      </span>
                                            </div>
                                        </li>
                                    </template>
                                    <li>
                                        <div class="">
                                            <el-upload ref="uploadShop" :show-file-list="false" :on-change="uploadFile"
                                                       accept=".jpg,.png,.jpeg,.jfif" action="/" :auto-upload="false">
                                                <div tabindex="0" class="el-upload el-upload--picture-card"><i
                                                    class="el-icon-plus"/></div>
                                            </el-upload>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <span slot="footer" class="dialog-footer">
    <el-button @click="show_update = false">Đóng</el-button>
    <el-button type="warning" @click="confirmUpdate()">Cập nhật</el-button>
  </span>
            </el-dialog>
            <el-dialog
                title="Thêm mới phòng học"
                :visible.sync="show_add"
                custom-class="minWidth375"
                width="50%"
                :before-close="handleClose">
                <div>
                    <el-row :gutter="24">
                        <el-col :sm="24" :md="12" class="text-left">
                            <label>Tên phòng:</label>
                            <el-input v-model="infoAdd.name" placeholder="Nhập..."></el-input>
                        </el-col>
                        <el-col :sm="24" :md="12" class="text-left">
                            <label>Vị trí:</label>
                            <el-input v-model="infoAdd.location" placeholder="Nhập..."></el-input>
                        </el-col>
                        <el-col :sm="24" :md="12" class="text-left mt-3">
                            <label>Ghi chú:</label>
                            <el-input v-model="infoAdd.note" placeholder="Nhập..."></el-input>
                        </el-col>
                        <el-col :sm="24" :md="12" class="text-left mt-3">
                            <span>Hình ảnh phòng học</span>
                            <div class="source d-flex">
                                <ul class="el-upload-list el-upload-list--picture-card " style="display: contents">
                                    <template v-for="(link,i) in listFileUpload">
                                        <li tabindex="0" class="el-upload-list__item is-ready">
                                            <div>
                                                <img v-if="link.type == 1" :src="link.link" alt=""
                                                     class="el-upload-list__item-thumbnail">
                                                <span class="el-upload-list__item-actions">
                                        <span class="el-upload-list__item-delete" @click.prevent="removeUpload(link,i)">
                                                                                              <i class="el-icon-delete"/>
                                                                                            </span>
                                        <span class="el-upload-list__item-delete" @click.prevent="previewLink(link)">
                                          <i class="el-icon-view"/>
                                        </span>
                                      </span>
                                            </div>
                                        </li>
                                    </template>
                                    <li>
                                        <div class="">
                                            <el-upload ref="uploadShop" :show-file-list="false" :on-change="uploadFile"
                                                       accept=".jpg,.png,.jpeg,.jfif" action="/" :auto-upload="false">
                                                <div tabindex="0" class="el-upload el-upload--picture-card"><i
                                                    class="el-icon-plus"/></div>
                                            </el-upload>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <span slot="footer" class="dialog-footer">
    <el-button @click="show_add = false" size="mini">Đóng</el-button>
    <el-button type="primary" size="mini" @click="confirmAdd()">Thêm</el-button>
  </span>
            </el-dialog>

            <el-dialog
                append-to-body
                top="10vh"
                title="Thông tin hình ảnh"
                custom-class="minWidth375"
                :visible.sync="showImg"
                width="20%"
                :before-close="()=>{showImg=false}">
                <img style="width:100%" :src="linkImgView" alt="">
                <span slot="footer" class="dialog-footer">
                <el-button type="danger" size="mini" @click="showImg = false">Đóng</el-button>
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
            listFileUpload: [],
            list_data: [],
            loading: {
                status: false,
                text: 'Loading...'
            },
            show_update: false,
            show_add: false,
            infoUpdate: {},
            infoAdd: {
                name: '',
                location: '',
                note: ''
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
            list_students:[],
            dataSearch:{
                key:'',
                owner:''
            }

        }
    },
    mounted() {
        this.getListStudent();
        this.getListClassRoom()
    },
    methods: {
        upDateStatusRoom(item,status){
            this.infoUpdate = JSON.parse(JSON.stringify(item))
            this.$confirm('Xác nhận hành động?', 'Thông báo', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
            })
                .then(_ => {

                    this.loading.status = true;
                    this.loading.text = 'Loading...'
                    var dataForm = new FormData()
                    dataForm.append('id', this.infoUpdate.id)
                    dataForm.append('name', this.infoUpdate.name)
                    dataForm.append('location', this.infoUpdate.location)
                    dataForm.append('note', this.infoUpdate.note)
                    dataForm.append('status', status)

                    var updateClassroomParam = {
                        id: this.infoUpdate.id,
                        name: this.infoUpdate.name,
                        location: this.infoUpdate.location,
                        note: this.infoUpdate.note,
                        status: status
                    }

                    rest_api.post('/admin/update-class-room', updateClassroomParam).then(
                        response => {
                            if (response && response.data.rc == 0) {
                                this.thongBao('success', 'Chỉnh sửa dữ liệu thành công')
                                this.getListClassRoom();
                            } else {
                                this.thongBao('error', 'Hệ thống bận. Vui lòng thử lại sau ít phút.')
                            }
                            this.loading.status = false;
                        }
                    ).catch((e) => {
                    })
                })
                .catch(_ => {
                });
        },
        getListStudent(){
            let params = {
                all:true
            }
            var url = '/admin/list-student'
            this.loading.status = true;
            this.loading.text = 'Loading...'
            this.list_students  = [];
            rest_api.post(url, params).then(
                response => {
                    console.log('Dữ liệu học sinh:')
                    console.log(response)
                    if (response.data.rc == 0) {
                        let arr = response.data.data;
                        for (let i = 0; i < arr.length; i++) {
                            let obj = {
                                name:arr[i].name + ' - [Lớp: '+arr[i].student_class.name +' - Mã:'+arr[i].student_code+']',
                                value:arr[i].id
                            }
                            this.list_students.push(obj);
                        }
                        console.log(this.list_students)
                    } else {
                    }
                    this.loading.status = false;
                }
            ).catch((e) => {
            })
        },
        previewLink(item) {
            this.linkImgView = item.link
            this.showImg = true;
        },
        removeUpload(item, index) {
            this.$refs.uploadShop.clearFiles()
            this.$refs.uploadShop.value = null
            this.listFileUpload.splice(index, 1)
            this.dataForm.splice(index, 1)
        },
        uploadFile(file, fileList) {
            fileList.forEach((item, x) => {
                const typeImage = /(png|jpg|jfif|jpeg)$/i
                if (item.raw) {
                    this.listFileUpload = [];
                    this.dataForm = [];
                    if (typeImage.exec(item.raw.type)) {
                        this.listFileUpload.push({
                            type: 1,
                            link: URL.createObjectURL(item.raw)
                        })
                        this.dataForm.push(item.raw)
                    }
                } else {
                    this.thongBao('error', 'Vui lòng chọn đúng định dạng hình ảnh')
                }
            })
            this.$refs.uploadShop.clearFiles()
            this.$refs.uploadShop.value = null
        },
        showAddClassRoom() {
            this.show_add = true;
        },
        showUpdate(item) {
            this.show_update = true;
            this.infoUpdate = JSON.parse(JSON.stringify(item));
            this.dataForm = [];
            this.listFileUpload = [];
            if (this.infoUpdate.image) {
                this.listFileUpload = [{
                    type: 1,
                    link: this.infoUpdate.image
                }]
            }
        },
        layLai(e) {
            console.log('onPagingClick')
            this.paging.start = e.start;
            this.paging.limit = e.limit;
            this.paging.currentPage = e.currentPage;
            this.getListClassRoom()
        },
        confirmUpdate() {
            this.$confirm('Xác nhận cập nhật thông tin ?', 'Thông báo', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
            })
                .then(_ => {

                    this.loading.status = true;
                    this.loading.text = 'Loading...'
                    var dataForm = new FormData()
                    dataForm.append('id', this.infoUpdate.id)
                    dataForm.append('name', this.infoUpdate.name)
                    dataForm.append('location', this.infoUpdate.location)
                    dataForm.append('note', this.infoUpdate.note)

                    var updateClassroomParam = {
                        id: this.infoUpdate.id,
                        name: this.infoUpdate.name,
                        location: this.infoUpdate.location,
                        note: this.infoUpdate.note,
                    }

                    if (this.dataForm && this.dataForm.length) {
                        Array
                            .from(Array(this.dataForm.length).keys())
                            .map(x => {
                                dataForm.append('hinhAnh', this.dataForm[x], this.dataForm[x].name)
                            })
                    }
                    rest_api.post('/admin/update-class-room', updateClassroomParam).then(
                        response => {
                            if (response && response.data.rc == 0) {
                                this.thongBao('success', 'Chỉnh sửa dữ liệu thành công')
                                this.getListClassRoom();
                            } else {
                                this.thongBao('error', 'Hệ thống bận. Vui lòng thử lại sau ít phút.')
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
                    var url = '/admin/delete-class-room'
                    this.loading.status = true;
                    this.loading.text = 'Loading...'
                    rest_api.post(url, params).then(
                        response => {
                            if (response.data.rc == 0) {
                                this.getListClassRoom()
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

            this.$confirm('Xác nhận thêm mới thông tin phòng học?', 'Thông báo', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
            })
                .then(_ => {
                    this.loading.status = true;
                    this.loading.text = 'Loading...'
                    var dataForm = new FormData()
                    dataForm.append('name', this.infoAdd.name)
                    dataForm.append('location', this.infoAdd.location)
                    dataForm.append('note', this.infoAdd.note)

                    var updateClassroomParam = {
                        name: this.infoAdd.name,
                        location: this.infoAdd.location,
                        note: this.infoAdd.note,
                    }
                    if (this.dataForm && this.dataForm.length) {
                        Array
                            .from(Array(this.dataForm.length).keys())
                            .map(x => {
                                dataForm.append('hinhAnh', this.dataForm[x], this.dataForm[x].name)
                            })
                    }
                    rest_api.post('/admin/add-class-room', updateClassroomParam).then(
                        response => {
                            if (response && response.data.rc == 0) {
                                this.thongBao('success', 'Thêm mới thành công')
                                this.getListClassRoom();
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
            this.showImg = false;
        },
        getListClassRoom() {
            let params = {
                start: this.paging.start,
                limit: this.paging.limit,
                key: this.dataSearch.key,
                owner: this.dataSearch.owner
            }
            var url = '/user/list-class-room'
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
