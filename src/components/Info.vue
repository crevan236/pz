<template>
    <v-container>
        <v-layout row>
            <v-flex>
                <h2 class="display-1" >Model danych</h2>
            </v-flex>
        </v-layout>
        <v-layout row>
            <v-flex>
                <h3 class="headline" >Ładowany plik powinien być postaci:</h3>
                <p class="code">
                    {<br>
                    algorithm: 'greedy',<br>
                    points: [<br>
                        { name: 'a', x: 1, y: 1, routes: [b, e, g] },<br>
                        { name: 'b', x: 1, y: 1, routes: [c, a, e] },<br>
                        { name: 'c', x: 1, y: 1, routes: [e, f, d] },<br>
                        { name: 'd', x: 1, y: 1, routes: [d, e, g] },<br>
                        { name: 'e', x: 1, y: 1, routes: [f, a, b, d] },<br>
                        { name: 'f', x: 1, y: 1, routes: [a, b, c, d, e, f, g] },<br>
                        { name: 'g', x: 1, y: 1, routes: [a] },<br>
                    ]<br>
                    }
                </p>
            </v-flex>
            <v-flex >
                <h3 class="headline" >Odbierany:</h3>
                    <p class="code">
                        [<br>
                        { name: 'a', x: 1, y: 1, routes: [b, e, g] },<br>
                        { name: 'b', x: 1, y: 1, routes: [c, a, e] },<br>
                        { name: 'c', x: 1, y: 1, routes: [e, f, d] },<br>
                        { name: 'd', x: 1, y: 1, routes: [d, e, g] },<br>
                        { name: 'e', x: 1, y: 1, routes: [f, a, b, d] },<br>
                        { name: 'f', x: 1, y: 1, routes: [a, b, c, d, e, f, g] },<br>
                        { name: 'g', x: 1, y: 1, routes: [a] },<br>
                    ]
                    </p>
            </v-flex>
        </v-layout>
        <v-layout row>
            
        </v-layout>
    </v-container>
</template>

<script>
export default {
  name: 'Info',
  data () {
    return {
    }
  }
}
</script>

<style scoped>
.code {
    text-align: left;
    display: block;
    width: 100%;
    padding-left: 100px;
    padding-top: 30px;
    font-weight: bold;
    font-style: oblique;
    color: rebeccapurple;
}
</style>
