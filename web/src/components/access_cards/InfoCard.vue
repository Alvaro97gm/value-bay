<template>
  <div id="info-card">
    <h3>¿Cómo funciona?</h3>
    <div class="info-item">
      <div class="icon-background">
        <b-icon icon="search" scale="2" variant="light"></b-icon>
      </div>
      <p class="h5 info-msg">Encuentra el artículo que buscas</p>
    </div>
    <hr>
    <div class="info-item">
      <div class="icon-background">
        <b-icon icon="award-fill" scale="2" variant="light"></b-icon>
      </div>
      <p class="h5 info-msg">¡Puja por él y gánalo en subasta!</p>
    </div>
    <hr>
    <div class="info-item">
      <div class="icon-background">
        <b-icon icon="truck" scale="2" variant="light"></b-icon>
      </div>
      <p class="h5 info-msg">Recibe tu artículo en menos de 48 horas</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "InfoCard"
}
</script>

<style scoped>
#info-card {
  width: 70%;
  height: 90vh;
  padding: 3em 1em;
  display: flex;
  flex-direction: column;
  align-self: center;
  align-items: center;
  justify-content: space-around;
  border-radius: 25px;
  background-color: rgba(255, 255, 255, 0.75);
  box-shadow: -2px 5px 35px black;
}

.icon-background {
  align-self: center;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 60px;
  height: 60px;
  background-color: rgba(0, 0, 0, 0.75);
  border-radius: 50%;
}

.info-item {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
}

.info-msg {
  margin-top: .5em;
}

hr {
  margin: 0;
  width: 90%;
}
</style>