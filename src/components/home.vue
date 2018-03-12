<template>
    <v-container grid-list-md>
        <v-layout row>
            <v-flex xs6>
                <v-card>
                    <v-card-title><h3>Podaj ilość punktów</h3></v-card-title>
                    <v-card-text>
                        <v-layout row>
                            <v-radio-group v-model="pointsType">
                                <v-flex xs12>
                                      <v-radio
                                        :key="1"
                                        :label="'Generowanie losowo'"
                                        :value="1"
                                      ></v-radio>
                                </v-flex>
                                <v-flex xs12>
                                    <v-radio
                                      :key="2"
                                      :label="'Podaj współrzędne'"
                                      :value="2"
                                    ></v-radio>
                                </v-flex>
                                <v-flex xs12>
                                    <v-radio
                                      :key="3"
                                      :label="'Wczytaj z pliku'"
                                      :value="3"
                                    ></v-radio>
                                </v-flex>
                            </v-radio-group>
                        </v-layout>
                        <v-layout row>
                            <v-flex v-if="pointsType == 1" >
                                    <v-text-field
                                        label="Podaj ilość punktów"
                                        required
                                    ></v-text-field>
                            </v-flex>
                            <v-flex v-if="pointsType == 2" >
                                    Podaj współrzędne
                            </v-flex>
                            <v-flex v-if="pointsType == 3" >
                                <v-text-field
                                    label="Wybierz plik"
                                    required
                                ></v-text-field>
                            </v-flex>
                        </v-layout>
                    </v-card-text>
                </v-card>
                <v-card>
                    <v-card-title>
                        <h3>Wybór algorytmu</h3>
                    </v-card-title>
                    <v-card-text>
                            <v-radio-group v-model="algType">
                              <v-radio
                                :key="1"
                                :label="'Permutacje'"
                                :value="1"
                              ></v-radio>
                              <v-radio
                                :key="2"
                                :label="'Zachłanny'"
                                :value="2"
                              ></v-radio>
                              <v-radio
                                :key="3"
                                :label="'Zachłanny rozszerzony'"
                                :value="3"
                              ></v-radio>
                            </v-radio-group>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn v-on:click="onRoadCount" >Wyznacz trasę</v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
            <v-flex align-content-center xs6>
                <v-card height="500px" >
                    <v-card-media v-if="!isMapLoading" >
                        <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2517.432291198976!2d20.637764316046425!3d50.87870897953661!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x471827be35849801%3A0x99149978917cac90!2sPolitechnika+%C5%9Awi%C4%99tokrzyska!5e0!3m2!1spl!2spl!4v1520859135913" width="100%" height="auto" frameborder="0" style="border:0; min-height: 500px" allowfullscreen></iframe>
                    </v-card-media>
                    <v-progress-circular v-if="isMapLoading" indeterminate :size="70" :width="7" color="green"></v-progress-circular>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
/* eslist-disable */
export default {
  name: 'home',
  data () {
    return {
      pointsType: 1,
      algType: 1,
      isMapLoading: false
    }
  },
  methods: {
      onRoadCount () {
          this.isMapLoading = true;
          setTimeout(() => {
              this.isMapLoading = false;
          }, 2000);
      }
  }
}
</script>

<style scoped>

</style>
