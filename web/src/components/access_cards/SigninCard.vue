<template>
  <div id="signin-card">
    <h2 id="signin-title">Registro</h2>
    <hr>
    <b-form id="input-signin-group">

      <b-input-group class="input-group mb2">
        <b-input-group-prepend 
          v-b-tooltip.hover.left title="Por favor, introduce tu nombre y apellidos."
          is-text>
          <b-icon icon="person-fill"></b-icon>
        </b-input-group-prepend>
        <b-form-input
          id="input-name"
          v-model="userName"
          type="text"
          placeholder="Nombre"
          required>
        </b-form-input>

        <b-form-input
          id="input-lastname"
          v-model="userLastname"
          type="text"
          placeholder="Apellidos"
          required>
        </b-form-input>
      </b-input-group>

      <b-input-group class="input-group mb2">
        <b-input-group-prepend 
          v-b-tooltip.hover.left title="Por favor, introduce tu correo electrónico/e-mail."
          is-text>
          <b-icon icon="envelope-fill"></b-icon>
        </b-input-group-prepend>
        <b-form-input
          id="input-signin-email"
          v-model="userEmail"
          type="email"
          placeholder="Ejemplo@mail.com"
          required>
        </b-form-input> 
      </b-input-group>
      
      <b-input-group class="input-group mb2">
        <b-input-group-prepend
          v-b-tooltip.hover.left title="Por favor, introduce tu contraseña."
          is-text>
          <b-icon icon="lock-fill"></b-icon>
        </b-input-group-prepend>
        <b-form-input
          id="input-signin-password"
          v-model="userPassword"
          :type="inputPasswordType"
          placeholder="Contraseña"
          
          required>
        </b-form-input>
        <b-input-group-prepend
          v-b-tooltip.hover.left title="Por favor, repite tu contraseña."
          is-text>
          <b-icon icon="shield-fill-check"></b-icon>
        </b-input-group-prepend>
        <b-form-input
          id="input-repeat-password"
          v-model="repeatPassword"
          :type="inputPasswordType"
          placeholder="Contraseña"
          @keyup.enter="signin()"
          required>
        </b-form-input>
        <b-input-group-prepend
          is-text
          v-b-tooltip.hover.right title="Mostrar/Ocultar contraseña."
          @click="tooglePasswordVisibility()">
          <b-icon v-if="this.$data.isPasswordHidden" icon="eye-slash-fill"></b-icon>
          <b-icon v-else icon="eye-fill"></b-icon>
        </b-input-group-prepend>
      </b-input-group>            
    </b-form>
    <b-button id="signin-button" variant="success" @click="signin()">Enviar</b-button>
    <p id="access-text">¿Ya tienes cuenta?
      <b-button 
        id="login-button"
        variant="outline-success" 
        size="sm"
        @click="showLogin()">
        Accede
      </b-button>
    </p>         
  </div>
</template>

<script>
import axios from 'axios';
import EventBus from '../../util/eventBus';
import config from '../../util/config';
import configAlert from '../../util/configAlert';

export default {
  name: "SigninCard",
  data: function() {
    return {
      isPasswordHidden: true,
      inputPasswordType: 'password',
      userName: "",
      userLastname: "",
      userEmail: "",
      userPassword: "",
      repeatPassword: ""
    }
  },

  methods: {
    signin: function(){
      var currentContext = this;
      var reqParams = this.createPathVariables();
      if(reqParams === null){
        this.$root.customAlert(configAlert.FORM_NOT_COMPLETE)
        return
      }else if (reqParams.error != null){
        this.$root.customAlert(configAlert.NOT_MATCHING_PASS)
        return
      }
      // ===== SIGN-IN REQUEST =====
      axios({
        method: 'post',
        url: config.serverURL + config.APIEndpoints.Security.signin,
        params: reqParams
      }).then(res => {
        if(res.status === 200)
          // Signin succed
          currentContext.getTokenByLogin(reqParams.email, reqParams.password);
      }).catch(err => {
        if(err.response){
          // Request send, error's present in response.
          if(err.status === 301)
            this.$root.customAlert(configAlert.ACCESS_DENIED)
          else
            this.$root.customAlert(configAlert.EMAIL_IN_USE)
        }else{
          // Error during request.
          this.$root.customAlert(configAlert.GENERIC_ERROR)
          console.log(err)
        }
      })
    },

    getTokenByLogin: function(userEmail, userPassword){
      // ===== LOG-IN REQUEST =====
      axios({
        method: 'post',
        url: config.serverURL + config.APIEndpoints.Security.login,
        params: {email: userEmail, password: userPassword}
      }).then(res => {
        if(res.status == 200)
          // Login succeed
          this.$root.customAlert(configAlert.ACCESS_GRANTED)
          EventBus.$emit('STORE_TOKEN', res.headers.authorization)
      }).catch(err => {
        // Error during request.
        this.$root.customAlert(configAlert.GENERIC_ERROR)
        console.log(err)
      })
    },

    isValidInfo: function(){
      return (
        this.userName != ""           &&
        this.userLastname != ""       &&
        this.userEmail != ""          &&
        this.userPassword != ""
      )   
    },

    createPathVariables: function(){
      if(this.isValidInfo()){
        if(this.userPassword === this.repeatPassword){
          return {
            email: this.userEmail,
            firstName: this.userName,
            lastName: this.userLastname,
            password: this.userPassword
          }
        }else{
          return {error: 'NOT_MATCHING_PASS'}
        }
      }
      return null;
    },

    tooglePasswordVisibility: function(){
      this.$data.inputPasswordType = this.$data.inputPasswordType === "password" ? "text" : "password";
      this.$data.isPasswordHidden = !this.$data.isPasswordHidden;
    },

    showLogin: function(){
      EventBus.$emit('SHOW_LOGIN')
    }
  }
}
</script>

<style scoped>
#signin-title {
  align-self: center;
}

#input-signin-group {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.input-group {
  margin: 1em 0 .5em 0;
}

#signin-button {
  width: 30%;
  margin-top: 1.5em;
  margin-bottom: 1em;
  align-self: center;
}

#access-text {
  margin-top: .75em;
  align-self: center;
}

#login-button {
  margin-left: 1em; 
}

hr {
  width: 100%;
  border: 1px solid rgba(0, 0, 0, 0.5);
}

a {
  color: rgba(16, 167, 41, 0.65);
}
</style>