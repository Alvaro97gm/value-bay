<template>
  <div id="root">
    <div id="left-side">
      <img id="logo" src="../../public/logo.png" alt="Logo">
      <LoginCard id="login-card"/>
    </div>
    <div id="middle-side">
      <b-button id="register-nav-button" variant="success" @click="showRegister()">Registro</b-button>
      <InfoCard id="info-card"/>      
      <b-button id="login-nav-button" variant="success" @click="showLogin()">Acceso</b-button>      
    </div>
    <div id="right-side" class="hidden">
      <img id="logo" src="../../public/logo.png" alt="Logo">
      <RegisterCard id="register-card"/>
    </div>
  </div>
</template>

<script>
import LoginCard from '../components/access_cards/LoginCard';
import RegisterCard from '../components/access_cards/RegisterCard';
import InfoCard from '../components/access_cards/InfoCard';

export default {
  name: "Access",
  components: {
    LoginCard,
    RegisterCard,
    InfoCard
  },
  methods: {
    showRegister: function(){
      document.getElementById("left-side").style.display = "none";
      document.getElementById("right-side").style.display = "flex";
      document.getElementById("register-nav-button").style.display="none";
      document.getElementById("login-nav-button").style.display="inherit";
    },
    showLogin: function(){
      document.getElementById("right-side").style.display = "none";
      document.getElementById("left-side").style.display = "flex";
      document.getElementById("login-nav-button").style.display = "none";
      document.getElementById("register-nav-button").style.display = "inherit";      
    }
  }
}
</script>

<style scoped>
#root {
  display: flex;
  height: 100vh;
  width: 100%;
}

/* LEFT_SIDE */

#left-side {
  width: 65%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: rgba(16, 167, 41, 0.65);
  box-shadow: inset -5px 5px 80px black;
}

#logo {
  margin-top: 1.5em;
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

/* MIDDLE_SIDE */

#middle-side {
  display: flex;
  justify-content: center;
  width: 35%;
  height: 100vh;
  background-image: url('../../public/login-background.jpg');
  background-repeat: no-repeat;
  background-size: cover;
}

#login-nav-button {
  display: none;
  position: fixed;
  justify-content: center;
  width: 10%;
  top: 3vh;
  right: 52%;
  transition: all 200ms ease-in;
  transform: scale(1);
  box-shadow: 2px 5px 35px black;  
}

.icon-composer {
  position: sticky;
  top: 0;
  left: 0;
  margin: 0;
  padding: 0;
}

#register-nav-button {
  display: flex;
  position: fixed;
  justify-content: center;
  width: 10%;
  top: 3vh;
  left: 52%;
  transition: all 200ms ease-in;
  transform: scale(1);
  box-shadow: -2px 5px 35px black;  
}

#register-nav-button:hover, #login-nav-button:hover {
  transition: all 200ms ease-in;
  transform: scale(1.1);
  cursor: pointer;
}

/* RIGHT_SIDE */

#right-side {
  display: none;
  flex-direction: column;
  width: 65%;
  background-color: rgba(16, 167, 41, 0.65);
  box-shadow: inset 5px 5px 80px black;
}

#register-card {
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
</style>