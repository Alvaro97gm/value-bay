<template>
  <div id="login-card">
    <h2 id="login-title">Acceso</h2>
    <hr>
    <b-form id="input-login-group">
      <b-input-group class="input-group mb2">
        <b-input-group-prepend 
          v-b-tooltip.hover.left title="Por favor, introduce tu correo electrónico/e-mail."
          is-text>
          <b-icon icon="envelope-fill"></b-icon>
        </b-input-group-prepend>
        <b-form-input
          id="input-login-email"
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
          id="input-login-password"
          v-model="userPassword"
          :type="inputPasswordType"
          placeholder="Contraseña"
          @keyup.enter="login()"
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
    <b-button id="access-button" variant="success" @click="login()">Acceder</b-button>
    <p id="signin-text">¿No tienes cuenta?
      <b-button 
        id="signin-button"
        variant="outline-success" 
        size="sm"
        @click="showSignin()">
        Regístrate
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
  name: 'LoginCard',
  data: function() {
    return {
      isPasswordHidden: true,
      inputPasswordType: 'password',
      userEmail: '',
      userPassword: ''
    }
  },
  methods: {
    login: function(){
      var reqParams = this.createPathVariables()
      if(reqParams === null){
        this.$root.customAlert(configAlert.FORM_NOT_COMPLETE)
        return
      }
      // ===== LOG-IN REQUEST =====
      axios({
        method: 'post',
        url: config.serverURL + config.APIEndpoints.Security.login,
        params: reqParams
      }).then(res => {
        if(res.status == 200)
          // Login succeed
          this.$root.customAlert(configAlert.ACCESS_GRANTED)
          EventBus.$emit('STORE_TOKEN', res.headers.authorization)
      }).catch(err => {
        if(err.response){
          // Request send, error's present in response.
          this.$root.customAlert(configAlert.ACCESS_DENIED)
        }else{
          // Error during request.
          this.$root.customAlert(configAlert.GENERIC_ERROR)
          console.log(err)
        }
      })
    },

    isValidInfo: function(){
      return this.userEmail != "" && this.userPassword != ""         
    },

    createPathVariables: function(){
      if(this.isValidInfo())
        return { email: this.userEmail, password: this.userPassword }
      return null
    },

    tooglePasswordVisibility: function(){
      this.$data.inputPasswordType = this.$data.inputPasswordType === 'password' ? 'text' : 'password'
      this.$data.isPasswordHidden = !this.$data.isPasswordHidden
    },

    showSignin: function() {
      EventBus.$emit('SHOW_SIGNIN');
    }
  }
}
</script>

<style scoped>
#login-title {
  align-self: center;
}

#input-login-group {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.input-group {
  margin: 1em 0 .5em 0;
}

#access-button {
  width: 30%;
  margin-top: 1.5em;
  margin-bottom: 1em;
  align-self: center;
}

#signin-text {
  margin-top: .75em;
  align-self: center;
}

#signin-button {
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