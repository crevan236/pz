<template>
  <div id="app">
    <v-container grid-list-md>
      <v-toolbar dark color="blue" >
        <v-toolbar-title class="white--text" >Komiwojażer solver</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-toolbar-items class="hidden-sm-and-down">
          <v-btn :to="{ path: '/' }" flat>Strona główna</v-btn>
          <v-btn :to="{ path: '/info' }" flat>Instrukcja</v-btn>
        </v-toolbar-items>
      </v-toolbar>
    </v-container>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
}
#app > h1 {
  padding: 30px 0px;
}
.hidden-sm-and-down a {
  text-decoration: none;
  color: #2c3e50;
}
</style>
