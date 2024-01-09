window.Vue = require('vue');
Vue.component('admin-list-student', require('./components/admin/student/index').default);
new Vue({
    el: '#app'
});

