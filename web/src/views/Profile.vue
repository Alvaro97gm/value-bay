<template>
  <div id="container">
    
  </div>
</template>
<script>
export default {
  name: 'Profile',
  data: function(){
    return {
      userInfo: null
    }
  },
  methods: {

  },
  mounted: function(){
    this.userInfo = JSON.parse(localStorage.getItem('userData'))
  }   
}
</script>
<style scoped>
#container {
  margin-top: 3em;
  display: flex;
  justify-content: center;
  width: 100%;
}
#card {
  align-self: center;
}
</style>