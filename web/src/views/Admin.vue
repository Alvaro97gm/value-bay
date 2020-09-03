<template>
  <div id="root">
    <h2>Bienvenido, <span class="green-text">{{adminInfo.firstName}}</span></h2>
    <hr>
    <div class="row">
      <!-- Bills management -->
      <div class="lot-management">
        <h4> 
          <b-icon class="title-icon" variant="success" scale=1.25 icon="cash-stack"></b-icon>
          Gestión de <span class="green-text">Billetes</span>
        </h4>
        <div class="actions">
          <div v-b-modal.add-bill-modal class="action">
            <b-icon variant="success" scale=1.75 icon="plus-circle-fill"></b-icon>
            <h3>Añadir</h3>
          </div>
          <div v-b-modal.delete-bill-modal class="action">
            <b-icon variant="danger" scale=1.75 icon="dash-circle-fill"></b-icon>
            <h3>Eliminar</h3>
          </div>
          <div v-b-modal.modify-bill-modal class="action">
            <b-icon variant="warning" scale=1.75 icon="sliders"></b-icon>
            <h3>Modificar</h3>
          </div>
          <div class="action">
            <b-icon variant="dark" scale=1.75 icon="question-circle-fill"></b-icon>
            <h3>Consultar</h3>
          </div>
        </div>
      </div>
      <!-- Coins management -->
      <div class="lot-management">
        <h4> 
          <b-icon class="title-icon" scale=1 variant="success" icon="slash-circle-fill"></b-icon>
          Gestión de <span class="green-text">Monedas</span>
        </h4>
        <div class="actions">
          <div class="action">
            <b-icon variant="success" scale=1.75 icon="plus-circle-fill"></b-icon>
            <h3>Añadir</h3>
          </div>
          <div class="action">
            <b-icon variant="danger" scale=1.75 icon="dash-circle-fill"></b-icon>
            <h3>Eliminar</h3>
          </div>
          <div class="action">
            <b-icon variant="warning" scale=1.75 icon="sliders"></b-icon>
            <h3>Modificar</h3>
          </div>
          <div class="action">
            <b-icon variant="dark" scale=1.75 icon="question-circle-fill"></b-icon>
            <h3>Consultar</h3>
          </div>
        </div>
      </div>
      <!-- Auctions management -->
      <div class="lot-management">
        <h4> 
          <b-icon class="title-icon" variant="success" scale=1.25 icon="award"></b-icon>
          Gestión de <span class="green-text">Subastas</span>
        </h4>
        <div class="actions">
          <div class="action">
            <b-icon variant="success" scale=1.75 icon="plus-circle-fill"></b-icon>
            <h3>Añadir</h3>
          </div>
          <div class="action">
            <b-icon variant="danger" scale=1.75 icon="dash-circle-fill"></b-icon>
            <h3>Eliminar</h3>
          </div>
          <div class="action">
            <b-icon variant="warning" scale=1.75 icon="sliders"></b-icon>
            <h3>Modificar</h3>
          </div>
          <div class="action">
            <b-icon variant="dark" scale=1.75 icon="question-circle-fill"></b-icon>
            <h3>Consultar</h3>
          </div>
        </div>
      </div>
      <!-- Reports management -->
      <div class="lot-management">
        <h4>
          <b-icon class="title-icon" variant="success" scale=1.25 icon="receipt"></b-icon>
          Gestión de <span class="green-text">Reportes</span>
        </h4>
      </div>
    </div>


    <!-- MODALS -->

    <!-- add-bill -->
    <b-modal
      id="add-bill-modal"
      header-bg-variant="success"
      header-text-variant="light"
      title="Añadir billete">

      <template v-slot:default>
        <form ref="form">
          <b-form-group label="Valor del billete">
            <b-input-group>
              <b-form-input id="item-value" required></b-form-input>
              <b-input-group-append is-text>Pts</b-input-group-append>
            </b-input-group>
          </b-form-group>
          <b-form-group label="Fecha de emisión" label-for="emission-date">
            <b-form-input type="date" id="emission-date" required></b-form-input>
          </b-form-group>
          <b-form-group label="Estado de conservación" label-for="conservation-status">          
            <b-form-select
              id="conservation-status"
              v-model="selected"
              :options="options"
              required>
            </b-form-select>
          </b-form-group>
          <b-form-group
            description="El ID del artículo será generado automaticamente"
            label="Precio"
            label-for="item-price">
            <b-input-group>
              <b-form-input type="number" id="item-price"></b-form-input>
              <b-input-group-append is-text>€</b-input-group-append>
            </b-input-group>
          </b-form-group>      
        </form>
      </template>

      <template v-slot:modal-footer="{ cancel }">
        <b-button size="sm" variant="outline-dark" @click="cancel()">
          Cancelar
        </b-button>
        <!-- TODO: Gestionar envío de información -->
        <b-button size="sm" variant="success" @click="ok()">
          Guardar
        </b-button>        
      </template>
    </b-modal>

    <!-- delete-bill -->
    <b-modal
      id="delete-bill-modal"
      header-bg-variant="danger"
      header-text-variant="light"
      title="Eliminar billete">

      <template v-slot:default>
        <form ref="form">
          <b-form-group label="Selecciona el artículo a eliminar" label-for="delete-lot">
            <b-form-select
              id="delete-lot"
              v-model="deleteBillSelected"
              required>
              <b-form-select-option :value="null">Artículos...</b-form-select-option>
              <b-form-select-option
                v-for="bill in billsInfo" 
                :key="bill.lotId"
                :value="lotId">
                ID: {{bill.lotId}} - {{bill.itemValue}} PTS - {{bill.emissionDay}}/{{bill.emissionMonth}}/{{bill.emissionYear}} - {{bill.conservationStatus}}
              </b-form-select-option>
            </b-form-select>
          </b-form-group>
          <p>O bien, </p>
          <b-form-group
            label="Introduce su ID"
            label-for="item-price">
            <!-- TODO: Verificar existencia del artículo -->
            <b-input-group>
              <b-form-input type="number" id="item-price"></b-form-input>              
            </b-input-group>
          </b-form-group>      
        </form>
      </template>

      <template v-slot:modal-footer="{ cancel }">
        <b-button size="sm" variant="outline-dark" @click="cancel()">
          Cancelar
        </b-button>
        <!-- TODO: Gestionar envío de información -->
        <b-button size="sm" variant="danger" @click="ok()">
          Eliminar
        </b-button>        
      </template>
    </b-modal>

    <!-- modify-bill -->
    <b-modal
      id="modify-bill-modal"
      header-bg-variant="warning"
      header-text-variant="dark"
      title="Modificar billete">

      <template v-slot:default>
        <form ref="form">
          <b-form-group label="Valor del billete">
            <b-input-group>
              <b-form-input id="item-value" required></b-form-input>
              <b-input-group-append is-text>Pts</b-input-group-append>
            </b-input-group>
          </b-form-group>
          <b-form-group label="Fecha de emisión" label-for="emission-date">
            <b-form-input type="date" id="emission-date" required></b-form-input>
          </b-form-group>
          <b-form-group label="Estado de conservación" label-for="conservation-status">          
            <b-form-select
              id="conservation-status"
              v-model="selected"
              :options="options"
              required>
            </b-form-select>
          </b-form-group>
          <b-form-group
            description="El ID del artículo será generado automaticamente"
            label="Precio"
            label-for="item-price">
            <b-input-group>
              <b-form-input type="number" id="item-price"></b-form-input>
              <b-input-group-append is-text>€</b-input-group-append>
            </b-input-group>
          </b-form-group>      
        </form>
      </template>

      <template v-slot:modal-footer="{ cancel }">
        <b-button size="sm" variant="outline-dark" @click="cancel()">
          Cancelar
        </b-button>
        <!-- TODO: Gestionar envío de información -->
        <b-button size="sm" variant="warning" @click="ok()">
          Guardar
        </b-button>        
      </template>
    </b-modal>

  </div>
</template>
<script>
import axios from 'axios';
import config from '../util/config';

export default {
  name: "Admin",
  data: function(){
    return {
      adminInfo: '',
      billsInfo: [],
      deleteBillSelected: null,
      options: [
          { value: null,  text: 'Selecciona una opción...', disabled: true},
          { value: 'BC',  text: 'BC - Buena conservación' },
          { value: 'MBC', text: 'MBC - Muy buena conservación' },
          { value: 'EBC', text: 'EBC - Excelentemente buena conservación' },
          { value: 'SC',  text: 'SC - Sin circular' }
      ],
      selected: null,
      
    }
  },
  methods: {
    getName: function(){
      return JSON.parse(localStorage.getItem('userData'))
    },
    getBillsInfo: function(){
      
      var currentContext = this 
      var ls = localStorage
      axios({
        method: 'get',
        url: config.serverURL + config.APIEndpoints.Lot.Bill.getAll,
        headers: {
          'Authorization': ls.getItem('jwt'),
          'Content-Type': 'application/json'
        }
      })      
      .then(res => {
        currentContext.$data.billsInfo = res.data 
      })
    }
  },
  mounted: function(){
    this.$data.adminInfo = this.getName();
    this.$data.billsInfo = this.getBillsInfo();
  }  
}
</script>
<style scoped>
p {
  align-self: center;
}
#root {
  margin: 2em;
}
h2 {
  font-family: 'Oswald', sans-serif;
  font-size: 2.5em;
}
h3 {
  margin: 1em 0 0 0;
  font-size: 1.25em;
}
h4 {
  font-size: 1.75em;
}

.lot-management {
  margin: .5em 0 1em 0;
  width: 47.5%;
  padding: 2em;
  border-radius: 10px;
  box-shadow: 0px 0px 10px -5px rgba(0,0,0,.5);;
}

.title-icon {
  margin-right: .5em;
}

.actions {
  margin-top: 2em;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.action {
  width: 7.5em;
  height: 7.5em;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  box-shadow: 0px 0px 10px -5px rgba(0,0,0,.5);
  font-family: 'Oswald', sans-serif;
  transition: .25s;
}

.action:hover {
  cursor: pointer;
  background-color: rgba(211, 250, 218, 0.5);
  transform: scale(1.15);
}

.row {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

#item-value {
  width: 50%;
}

hr{
  margin-bottom: 1.5em;
}
.green-text {
  color: rgb(122, 179, 132);
  margin-right: .5em;
}
</style>