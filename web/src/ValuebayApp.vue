<template>
  <Access v-if="jwt === ''"/>
  <Home v-else/>
</template>

<script>
import jwt from 'jsonwebtoken';
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
      jwt: ''
    }
  },
  methods:{
    extractDataFromJWT: function(rawToken){
      
      var tokenPayload = jwt.decode(rawToken.substring(7), {json: true})
      if(tokenPayload != null)
        return tokenPayload.userData
    },
    storeToken: function(token){
      localStorage.setItem('userData', JSON.stringify(this.extractDataFromJWT(token)))
      localStorage.setItem('jwt', token)
      this.loadToken()
    },
    deleteToken: function(){
      localStorage.setItem('userData', '')
      localStorage.setItem('jwt', '')
      this.$data.jwt = ''      
    },
    loadToken: function(){
      this.$data.jwt = localStorage.getItem('jwt')
    }
  },
  mounted: function() {
  
    if(localStorage.getItem('jwt') != '') this.loadToken(localStorage.getItem('jwt'))
    
    var appContext = this
    EventBus.$on('STORE_TOKEN', (payload) => { appContext.storeToken(payload) })
    EventBus.$on('DELETE_TOKEN', () => { appContext.deleteToken() })
  } 
}
</script>

<!-- Gobal styles -->
<style>
body {
  margin: 0;
  padding: 0;
}

/* Hide numeric input arrows */
input[type=number]::-webkit-inner-spin-button, 
input[type=number]::-webkit-outer-spin-button { 
  -webkit-appearance: none; 
  margin: 0; 
}
</style>
