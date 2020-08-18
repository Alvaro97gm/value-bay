<template>
  <div id="register-card">
    <h2 id="register-title">Registro</h2>
    <hr>
    <b-form id="input-register-group">

      <b-input-group class="input-group mb2">
        <b-input-group-prepend is-text>
          <b-icon icon="person-fill"></b-icon>
        </b-input-group-prepend>
        <b-form-input
          id="input-name"
          type="text"
          placeholder="Nombre"
          v-b-tooltip.hover.left title="Por favor, introduce tu nombre."
          required>
        </b-form-input>

        <b-form-input
          id="input-surname"
          type="text"
          placeholder="Apellidos"
          v-b-tooltip.hover.left title="Por favor, introduce tus apellidos."
          required>
        </b-form-input>
      </b-input-group>

      <b-input-group class="input-group mb2">
        <b-input-group-prepend is-text>
          <b-icon icon="envelope-fill"></b-icon>
        </b-input-group-prepend>
        <b-form-input
          id="input-email"
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
          id="input-password"
          :type="inputPasswordType"
          placeholder="Contraseña"
          v-b-tooltip.hover.left title="Por favor, introduce tu contraseña."
          required>
        </b-form-input>
        <b-form-input
          id="input-repeat-password"
          :type="inputPasswordType"
          placeholder="Repetir contraseña"
          v-b-tooltip.hover.left title="Por favor, repite tu contraseña."
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
    <b-button id="register-button" type="submit" variant="success">Enviar</b-button>
    <p id="access-text">¿Ya tienes cuenta? <a href="#">Accede</a></p>         
  </div>
</template>

<script>
export default {
  name: "RegisterCard",
  data: function() {
    return {
      isPasswordHidden: true,
      inputPasswordType: 'password'
    }
  },
  // TODO: Gestionar POST Request a /localhost:8080/users/register
  methods: {
    tooglePasswordVisibility: function(){
      this.$data.inputPasswordType = this.$data.inputPasswordType === "password" ? "text" : "password";
      this.$data.isPasswordHidden = !this.$data.isPasswordHidden;
    }
  }
}
</script>

<style scoped>
#register-title {
  align-self: center;
}

#input-register-group {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.input-group {
  margin: 1em 0 .5em 0;
}

#register-button {
  width: 30%;
  margin-top: 1.5em;
  margin-bottom: 1em;
  align-self: center;
}

#access-text {
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