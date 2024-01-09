<template>
    <el-row :gutter="24" v-loading.fullscreen.lock="loading.status">
        <el-col :sm="8" :md="4" class="text-left">
            <label>Mã học sinh</label>
            <el-input placeholder="Nhập..." v-model="dataSearch.code"></el-input>
        </el-col>
        <el-col :sm="8" :md="4" class="text-left">
            <label>Tên học sinh</label>
            <el-input placeholder="Nhập..." v-model="dataSearch.name"></el-input>
        </el-col>
        <el-col :sm="8" :md="4" class="text-left">
            <label>Lớp học</label>
            <el-select style="width: 100%" v-model="dataSearch.class" placeholder="Chọn">
                <el-option
                    v-for="item in list_class"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value">
                </el-option>
            </el-select>
        </el-col>
        <el-col :sm="8" :md="4" class="text-left">
            <span style="color: transparent">Tìm kiếm</span>
            <el-button type="success" @click.prevent="getListStudent()" class="d-block">Tìm kiếm</el-button>
        </el-col>
        <el-col class="mt-3" :span="24">
            <div class="card">
                <div class="card-header">
                    <el-row :gutter="24">
                        <el-col :span="12"><h5>Danh sách học sinh</h5></el-col>
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
                                <th>Tên học sinh</th>
                                <th>Mã học sinh</th>
                                <th>Hình ảnh</th>
                                <th>Giới tính</th>
                                <th>Ngày sinh</th>
                                <th>Lớp học</th>
                                <th>Email</th>
                                <th>Số điện thoại</th>
                                <th>Khả năng mượn phòng</th>
                                <th>Hành động</th>
                            </tr>
                            </thead>
                            <tbody v-if="list_data&&list_data.length">
                            <tr v-for="(item,index) in list_data" :key="index">
                                <td class="text-center">{{ index + 1 }}</td>
                                <td>{{ item.name }}</td>
                                <td>{{ item.student_code }}</td>
                                <td class="text-center">
                                    <img style="width: 120px; height: 150px;padding: 10px 0" :src="item.image"
                                         class="image">
                                </td>
                                <td class="text-center">{{ item.gender == 1 ? 'Nam' : 'Nữ' }}</td>
                                <td class="text-center">{{ item.birthday }}</td>
                                <td class="text-center">{{ item.student_class.name }}</td>
                                <td>{{ item.email }}</td>
                                <td>{{ item.phone }}</td>
                                <td class="text-center">
                                    <el-button v-if="item.abilityToBorrow==1" size="mini" type="primary">Được phép
                                    </el-button>
                                    <el-button v-else size="mini" type="info">Không được phép</el-button>
                                </td>
                                <td class="text-center">
                                    <el-button size="mini" type="warning"
                                               @click.prevent="showUpdate(item)">Cập nhật thông tin
                                    </el-button>
                                    <el-button size="mini" type="danger"
                                               @click.prevent="confirmDel(item.id)">Xoá dữ liệu
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
                title="Thêm mới học sinh"
                :visible.sync="show_add"
                custom-class="minWidth375"
                width="75%"
                top="5vh"
                :before-close="handleClose">
                <div>
                    <el-row :gutter="24">
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Mã học sinh</label>
                            <el-input placeholder="Nhập..." v-model="infoAdd.student_code"></el-input>
                        </el-col>
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Tên học sinh</label>
                            <el-input placeholder="Nhập..." v-model="infoAdd.name"></el-input>
                        </el-col>
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Giới tính</label>
                            <el-select style="width: 100%" v-model="infoAdd.gender" placeholder="Chọn">
                                <el-option
                                    v-for="item in list_gender"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Ngày sinh</label>
                            <el-date-picker
                                v-model="infoAdd.birthday"
                                format="dd/MM/yyyy"
                                value-format="dd/MM/yyyy"
                                style="height: 40px; width: 100%"
                                type="date"
                                placeholder="Chọn ngày sinh"
                                :picker-options="datePickerOptions"
                            />
                        </el-col>
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Lớp học</label>
                            <el-select style="width: 100%" v-model="infoAdd.class" placeholder="Chọn">
                                <el-option
                                    v-for="item in list_class"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Email</label>
                            <el-input placeholder="Nhập..." v-model="infoAdd.email"></el-input>
                        </el-col>
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Số điện thoại</label>
                            <el-input placeholder="Nhập..." v-model="infoAdd.phone"></el-input>
                        </el-col>
                        <el-col :span="24" class="mt-3">
                            <span>Hình ảnh</span>
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
    <el-button @click="show_add = false">Đóng</el-button>
    <el-button type="primary" @click="confirmAdd()">Thêm mới</el-button>
  </span>
            </el-dialog>
            <el-dialog
                title="Chỉnh sửa thông tin học sinh"
                :visible.sync="show_update"
                custom-class="minWidth375"
                width="75%"
                top="5vh"
                :before-close="handleClose">
                <div>
                    <el-row :gutter="24">
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Mã học sinh</label>
                            <el-input disabled placeholder="Nhập..." v-model="infoUpdate.student_code"></el-input>
                        </el-col>
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Tên học sinh</label>
                            <el-input placeholder="Nhập..." v-model="infoUpdate.name"></el-input>
                        </el-col>
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Giới tính</label>
                            <el-select style="width: 100%" v-model="infoUpdate.gender" placeholder="Chọn">
                                <el-option
                                    v-for="item in list_gender"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Ngày sinh</label>
                            <el-date-picker
                                v-model="infoUpdate.birthday"
                                format="dd/MM/yyyy"
                                value-format="dd/MM/yyyy"
                                style="height: 40px; width: 100%"
                                type="date"
                                placeholder="Chọn ngày sinh"
                                :picker-options="datePickerOptions"
                            />
                        </el-col>
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Lớp học</label>
                            <el-select style="width: 100%" v-model="infoUpdate.class" placeholder="Chọn">
                                <el-option
                                    v-for="item in list_class"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Email</label>
                            <el-input placeholder="Nhập..." v-model="infoUpdate.email"></el-input>
                        </el-col>
                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Số điện thoại</label>
                            <el-input placeholder="Nhập..." v-model="infoUpdate.phone"></el-input>
                        </el-col>

                        <el-col :sm="12" :md="6" class="text-left mt-3">
                            <label>Khả năng mượn phòng học</label>
                            <el-select style="width: 100%" v-model="infoUpdate.abilityToBorrow" placeholder="Chọn">
                                <el-option
                                    v-for="item in list_status_aboliti_to_borrow"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="24" class="mt-3">
                            <span>Hình ảnh</span>
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
                code: '',
                class: '',
                name: '',
            },
            show_add: false,
            show_update: false,
            infoUpdate: {},
            infoAdd: {
                name: '',
                student_code: '',
                email: '',
                phone: '',
                class: '',
                gender: '',
                birthday: ''
            },
            listFileUpload: [],
            dataForm: [],
            list_gender: [
                {name: 'Chọn', value: ''},
                {name: 'Nam', value: 1},
                {name: 'Nữ', value: 2},
            ],
            list_status_aboliti_to_borrow:[
                {name: 'Được phép', value: 1},
                {name: 'Không được phép', value: 0},
            ],
            list_data: [],
            list_class: [],
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
            datePickerOptions: {
                disabledDate(date) {
                    return date > new Date()
                }
            },

        }
    },
    mounted() {
        this.getListClass()
        this.getListStudent()
    },
    methods: {
        confirmDel(id) {
            this.$confirm('Xác nhận xoá dữ liệu học sinh. Việc xoá này sẽ xoá cả tài khoản tương ứng đi kèm ?', 'Thông báo', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
            })
                .then(_ => {
                    let params = {
                        id:id,
                    }
                    console.log(params)
                    var url = 'admin/delete-student'
                    this.loading.status = true;
                    this.loading.text = 'Loading...'
                    rest_api.post(url, params).then(
                        response => {
                            if (response.data.rc == 0) {
                                this.getListStudent()
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
        confirmUpdate() {
            this.$confirm('Xác nhận cập nhật thông tin?', 'Thông báo', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
            })
                .then(_ => {
                    this.loading.status = true;
                    this.loading.text = 'Loading...'
                    var dataForm = new FormData()
                    dataForm.append('id', this.infoUpdate.id)
                    dataForm.append('name', this.infoUpdate.name)
                    dataForm.append('student_code', this.infoUpdate.student_code)
                    dataForm.append('gender', this.infoUpdate.gender)
                    dataForm.append('birthday', this.infoUpdate.birthday)
                    dataForm.append('class', this.infoUpdate.class)
                    dataForm.append('email', this.infoUpdate.email)
                    dataForm.append('phone', this.infoUpdate.phone)
                    dataForm.append('abilityToBorrow', this.infoUpdate.abilityToBorrow)
                    if (this.dataForm && this.dataForm.length) {
                        Array
                            .from(Array(this.dataForm.length).keys())
                            .map(x => {
                                dataForm.append('hinhAnh', this.dataForm[x], this.dataForm[x].name)
                            })
                    }
                    rest_api.post('/admin/update-student', dataForm).then(
                        response => {
                            if (response && response.data.rc == 0) {
                                this.thongBao('success', 'Chỉnh sửa dữ liệu thành công')
                                this.getListStudent();
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
        confirmAdd() {
            this.$confirm('Xác nhận thêm mới thông tin?', 'Thông báo', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Hủy',
            })
                .then(_ => {
                    this.loading.status = true;
                    this.loading.text = 'Loading...'
                    var dataForm = new FormData()
                    dataForm.append('name', this.infoAdd.name)
                    dataForm.append('student_code', this.infoAdd.student_code)
                    dataForm.append('gender', this.infoAdd.gender)
                    dataForm.append('birthday', this.infoAdd.birthday)
                    dataForm.append('class', this.infoAdd.class)
                    dataForm.append('email', this.infoAdd.email)
                    dataForm.append('phone', this.infoAdd.phone)
                    if (this.dataForm && this.dataForm.length) {
                        Array
                            .from(Array(this.dataForm.length).keys())
                            .map(x => {
                                dataForm.append('hinhAnh', this.dataForm[x], this.dataForm[x].name)
                            })
                    }
                    rest_api.post('/admin/add-student', dataForm).then(
                        response => {
                            if (response && response.data.rc == 0) {
                                this.thongBao('success', 'Thêm mới thành công')
                                this.getListStudent();
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
        getListStudent() {
            this.handleClose();
            let params = {
                start: this.paging.start,
                limit: this.paging.limit,
                name: this.dataSearch.name,
                code: this.dataSearch.code,
                class: this.dataSearch.class,
            }
            var url = '/admin/list-student'
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
        handleClose() {
            this.show_add = false;
            this.show_update = false;
        },
        showUpdate(item) {
            this.show_update = true;
            this.infoUpdate = JSON.parse(JSON.stringify(item));
            this.dataForm = [];
            this.listFileUpload = [{
                type: 1,
                link: this.infoUpdate.image
            }]
        },
        showAddClass() {
            this.show_add = true;
        },
        getListClass() {
            let params = {
                start: 0,
                limit: 9999,
                key: ''
            }
            var url = '/admin/list-class'
            this.loading.status = true;
            this.loading.text = 'Loading...'
            this.list_class = [{name: 'Chọn', value: ''}];
            rest_api.post(url, params).then(
                response => {
                    if (response.data.rc == 0) {
                        let list_class = response.data.data;
                        for (let i = 0; i < list_class.length; i++) {
                            this.list_class.push({
                                name: list_class[i].name + ' - [' + list_class[i].code + ']',
                                value: list_class[i].id
                            })
                        }
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
