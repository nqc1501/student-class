window.Vue = require('vue');
Vue.component('user-list-class-rooms', require('./components/user/home/listRooms').default);
new Vue({
    el: '#app'
});

