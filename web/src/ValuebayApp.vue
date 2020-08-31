<template>
  <Access v-if="jwt === ''"/>
  <Home :jwt="jwt" v-else/>
</template>

<script>
import EventBus from './util/eventBus';
import Access from './views/Access';
import Home from './views/Home';

export default {
  name: 'App',
  components: {
    Access,
    Home
  },
  data: function() {
    return {
      jwt: ""
    }
  },
  methods: {
    setToken: function(token){
      this.$data.jwt = token
    }
  },
  mounted: function() {
    var appContext = this;
    EventBus.$on('STORE_TOKEN', function(payload){
      appContext.$data.jwt = payload
    })
  } 
}
</script>

<style>
body {
  margin: 0;
  padding: 0;
}
</style>
