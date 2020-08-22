import Vue from 'vue';
import ValuebayApp from './ValuebayApp.vue';
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue';
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.min.css";

Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

new Vue({
  BootstrapVue,
  IconsPlugin,
  render: h => h(ValuebayApp)
}).$mount('#root');
