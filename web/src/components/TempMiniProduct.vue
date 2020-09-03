<template>
  <div id="root">
    <p>{{itemType}} de {{itemPrice}} Pts</p>
    <p>Año de emisión: {{itemDate}}</p>
    <p>Estado de conservación: {{itemConservationStatus}} - {{itemConservationStatusDetailed}}</p>
    <p>Precio: {{itemPrice}}</p>
  </div>
</template>
<script>
export default {
  name: "TempMiniProduct",
  props: {
    itemId: Number,
    itemType: String,
    itemValue: Number,
    itemDate: Number,
    itemConservationStatus: String,
    itemConservationStatusDetailed: String,
    itemPrice: Number,    
  }
}
</script>
<style scoped>
#root {
  width: 20%;
  display: flex;
  flex-direction: column;
  margin: 1em;
  padding: 2em;
  border-radius: 15px;
  box-shadow: 0 0 18px black;
  background-color: rgba(211, 250, 218, 0.5);
}
</style>