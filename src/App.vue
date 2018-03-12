<template>
  <div id="app">
    <v-container grid-list-md>
      <v-toolbar>
        <v-toolbar-title>Komiwojażer solver</v-toolbar-title>
        <v-spacer></v-spacer>
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
</style>
