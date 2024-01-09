window.Vue = require('vue');
Vue.component('admin-list-class', require('./components/admin/class/index').default);
new Vue({
    el: '#app'
});

