<template>
  <b-navbar id="navbar">
    <div id="logo-compo">
      <b-img id="logo" :src="require('../assets/logo.png')" fluid alt="Logo"></b-img>
      <h1 id="logo-text">Value-bay</h1>
    </div>
    <div id="account-buttons">
      <b-button
        class="margin-right-1em"
        variant="success">
        Inicio
      </b-button>
      <b-button
        class="margin-right-1em"
        variant="success">
        Subastas
      </b-button>
      <b-button
        class="margin-right-1em"
        variant="success">
        Artículos
      </b-button>      
      <b-dropdown
        class="margin-right-1em"
        text="Mi cuenta" 
        variant="success"
        right>
        <b-dropdown-item variant="success"> 
          <b-icon class="margin-right-0_5em" icon="person"></b-icon>
          Mi perfil
        </b-dropdown-item>
        <b-dropdown-item variant="success"> 
          <b-icon class="margin-right-0_5em" icon="award"></b-icon>
          Mis pujas
        </b-dropdown-item>        
        <b-dropdown-item variant="success"> 
          <b-icon class="margin-right-0_5em" icon="arrow-left-right"></b-icon>
          Historial de transacciones
        </b-dropdown-item>
        <b-dropdown-item variant="success"> 
          <b-icon class="margin-right-0_5em" icon="envelope"></b-icon>
          Notificaciones
        </b-dropdown-item>
        <b-dropdown-divider></b-dropdown-divider>
        <b-dropdown-item variant="success" v-if="isAdmin()"> 
          <b-icon class="margin-right-0_5em" icon="lightning"></b-icon>
          Administrador
        </b-dropdown-item>
        <b-dropdown-item variant="success"> 
          <b-icon class="margin-right-0_5em" icon="gear"></b-icon>
          Configuración
        </b-dropdown-item>
      </b-dropdown>
      <b-button
        @click="exit()"
        variant="outline-success">
          Salir 
      </b-button>
    </div>
  </b-navbar>
</template>
<script>
import EventBus from '../util/eventBus';

export default {
   name: "Navbar",
   methods: {
     exit: function() {
       EventBus.$emit('DELETE_TOKEN')
     },
     isAdmin: function(){
       return JSON.parse(localStorage.getItem('userData')).role === 'ADMIN'
     }
   }
}
</script>
<style scoped>
#navbar{
  padding: 1em 10em 1em 10em;
  display: flex;
  justify-content: space-between;
  background-color: rgba(211, 250, 218, 0.5);
  box-shadow: 0px -10px 30px black;
}

#logo-compo {
  display: flex;
}

#logo-compo:hover {
  cursor: pointer;
}

#logo {
  width: 50px;
  height: 51px;
  margin-right: .75em;
  align-self: center;
}

#logo-text {
  font-family: 'Oswald', sans-serif;
  font-size: 2em;
  color: rgba(0,0,0, .70);
  align-self: center;
}

#account-buttons {
  display: flex;
  justify-content: flex-end;
  width: 25%;
}

.margin-right-1em {
  margin-right: 1em;
}

.margin-right-0_5em {
  margin-right: .5em;
}


@media (max-width: 1100px){
  #navbar {
    padding: 1em 3em 1em 3em;
  }
}

@media (max-width: 850px){
  #logo-text {
    display: none;
  }
}
</style>