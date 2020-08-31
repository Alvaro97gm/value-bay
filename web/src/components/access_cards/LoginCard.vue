<template>
  <div id="login-card">
    <h2 id="login-title">Acceso</h2>
    <hr>
    <b-form id="input-login-group">
      <b-input-group class="input-group mb2">
        <b-input-group-prepend is-text>
          <b-icon icon="envelope-fill"></b-icon>
        </b-input-group-prepend>
        <b-form-input
          id="input-login-email"
          v-model="userEmail"
          type="email"
          placeholder="Ejemplo@mail.com"
          v-b-tooltip.hover.left title="Por favor, introduce tu correo electrónico/e-mail."
          required>
        </b-form-input> 
      </b-input-group>
      <b-input-group class="input-group mb2">
        <b-input-group-prepend is-text>
          <b-icon icon="lock-fill"></b-icon>
        </b-input-group-prepend>
        <b-form-input
          id="input-login-password"
          v-model="userPassword"
          :type="inputPasswordType"
          placeholder="Contraseña"
          v-b-tooltip.hover.left title="Por favor, introduce tu contraseña."
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
    <p id="register-text">¿No tienes cuenta? <a href="#">Regístrate</a></p>         
  </div>
</template>

<script>
import axios from 'axios';
import config from '../../config';

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
      axios({
        method: 'post',
        url: config.serverURL + config.APIEndpoints.Security.login,
        params: this.createPathVariables()
      }).then(res => {console.log(res.headers)})
    },
    isValidInfo: function(){
      return this.userEmail != null && this.userPassword != null         
    },

    createPathVariables: function(){
      if(this.isValidInfo())
        return { email: this.userEmail, password: this.userPassword }
    },
    tooglePasswordVisibility: function(){
      this.$data.inputPasswordType = this.$data.inputPasswordType === 'password' ? 'text' : 'password';
      this.$data.isPasswordHidden = !this.$data.isPasswordHidden;
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

#register-text {
  margin-top: .75em;
  align-self: center;
}

hr {
  width: 100%;
  border: 1px solid rgba(0, 0, 0, 0.5);
}

a {
  color: rgba(16, 167, 41, 0.65);
}
</style>