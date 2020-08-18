<template>
  <div id="root">
    <div id="left-side">
      <img id="logo" src="../../public/logo.png" alt="Logo">
      <LoginCard id="login-card"/>
    </div>
    <div id="right-side">
      <img id="img-background" src="../../public/login-background.jpg" alt="Bills and coins">
    </div>
  </div>
</template>

<script>
import LoginCard from '../components/LoginCard';

export default {
  name: "Access",
  components: {
    LoginCard
  }
}
</script>

<style scoped>
#root {
  display: flex;
  height: 100vh;
  width: 100%;
}
.screen-section {
  width: 50%;
  height: 100vh;
}

#left-side {
  min-width: 65%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: rgba(16, 167, 41, 0.65);
  box-shadow: inset -5px 5px 80px black;
}

#logo {
  margin-top: 3em;
  align-self: center;
  max-width: 200px;
  max-height: 200px;
}

#login-card {
  width: 55%;
  margin-top: 2em;
  padding: 2em 2.5em 2em 2.5em;
  display: flex;
  flex-direction: column;
  align-self: center;  
  background-color: white;
  border-radius: 10px;
  box-shadow: -5px 5px 35px black;
}

#right-side {
  max-width: 35%;
  height: 100vh;
  background-color: rgb(105, 156, 53);
}

#img-background {
  max-width: 100%;
  height: 100vh;
  object-fit: cover;
}

hr {
  width: 100%;
  border: 1px solid rgba(0, 0, 0, 0.5);
}

a {
  color: rgba(16, 167, 41, 0.65);
}
</style>