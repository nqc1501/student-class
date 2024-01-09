window.Vue = require('vue');
Vue.component('admin-list-class-rooms', require('./components/admin/classRooms/index').default);
new Vue({
    el: '#app'
});

