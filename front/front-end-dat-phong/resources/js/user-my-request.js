window.Vue = require('vue');
Vue.component('user-my-request', require('./components/user/home/myRequest').default);
new Vue({
    el: '#app'
});

