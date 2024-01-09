window.Vue = require('vue');
Vue.component('admin-list-user', require('./components/admin/user/index').default);
new Vue({
    el: '#app'
});

