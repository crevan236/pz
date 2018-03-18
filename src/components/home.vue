<template>
    <v-container grid-list-md>
        <v-layout row>
            <v-flex xs6>
                <v-card>
                    <v-card-title><h3 class="title" >Podaj ilość punktów</h3></v-card-title>
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
                                <!-- <v-flex xs12>
                                    <v-radio
                                      :key="2"
                                      :label="'Podaj współrzędne'"
                                      :value="2"
                                    ></v-radio>
                                </v-flex> -->
                                <v-flex xs12>
                                    <v-radio
                                      :key="2"
                                      :label="'Wczytaj z pliku'"
                                      :value="2"
                                    ></v-radio>
                                </v-flex>
                            </v-radio-group>
                        </v-layout>
                        <v-layout row>
                            <template v-if="pointsType == 1">
                                <v-flex>
                                    <v-text-field
                                        v-model="amountOfPoints"
                                        label="Podaj ilość punktów"
                                        required
                                    ></v-text-field>
                                </v-flex>
                                <v-flex>
                                    <v-btn color="orange" class="white--text" @click="generatePoints" ><v-icon>play_arrow</v-icon></v-btn>
                                </v-flex>
                            </template>
                            <v-flex v-if="pointsType == 3" >
                                <v-layout row>
                                    <v-flex xs5><v-text-field v-model="currentPoint.x" label="X:" required></v-text-field></v-flex>
                                    <v-flex xs5><v-text-field v-model="currentPoint.y" label="Y:" required></v-text-field></v-flex>
                                    <v-flex align-content-start xs2>
                                        <v-btn color="orange" class="white--text" @click="onAddPoint" ><v-icon>playlist_add</v-icon></v-btn>
                                    </v-flex>
                                </v-layout>
                            </v-flex>
                            <v-flex v-if="pointsType == 2" >
                                <vue-dropzone v-on:vdropzone-upload-progress="onFileProgress" v-on:vdropzone-complete="onFileAdded" ref="fileDrop" id="dropzone" :options="dropzoneOptions"></vue-dropzone>
                            </v-flex>
                        </v-layout>
                    </v-card-text>
                </v-card>
                <template v-if="currentPoints.length > 0" >
                    <v-layout row>
                        <v-flex xs12>
                            <v-toolbar>
                                <v-toolbar-title>Punkty:</v-toolbar-title>
                            </v-toolbar>
                        </v-flex>
                    </v-layout>
                    <v-layout row>
                        <v-flex class="scrolling-box" xs12>
                            <v-card >
                                <v-card-text>
                                    <v-list two-line>
                                      <template v-for="(item, index) in currentPoints">
                                        <v-list-tile ripple :key="index" >
                                          <v-list-tile-content>
                                            <v-list-tile-title>Punkt: {{ index + 1 }}</v-list-tile-title>
                                            <v-list-tile-sub-title class="text--primary">
                                                X: {{ item.x }} Y: {{ item.y }}
                                            </v-list-tile-sub-title>
                                          </v-list-tile-content>
                                          <v-list-tile-action>
                                            <v-btn flat color="orange" @click="currentPoints.splice(index, 1)" ><v-icon color="grey">delete</v-icon></v-btn>
                                          </v-list-tile-action>
                                        </v-list-tile>
                                        <v-divider v-if="index + 1 < currentPoints.length" :key="`divider-${index}`"></v-divider>
                                      </template>
                                    </v-list>
                                </v-card-text>
                            </v-card>
                        </v-flex>
                    </v-layout>
                </template>
            </v-flex>
            <v-flex xs6>
                <v-layout row>
                    <v-flex fill-height xs12>
                        <v-card>
                            <v-card-title>
                                <h3 class="title" >Wybór algorytmu</h3>
                            </v-card-title>
                            <v-card-text>
                                    <v-radio-group v-model="algType">
                                      <v-radio
                                        :key="1"
                                        :label="'Permutacje'"
                                        :value="'PERM'"
                                      ></v-radio>
                                      <v-radio
                                        :key="2"
                                        :label="'Zachłanny'"
                                        :value="'GREEDY'"
                                      ></v-radio>
                                      <v-radio
                                        :key="3"
                                        :label="'Zachłanny rozszerzony'"
                                        :value="'GREEDYS'"
                                      ></v-radio>
                                    </v-radio-group>
                            </v-card-text>
                            <v-card-actions>
                                <v-btn color="orange" class="white--text" v-on:click="onRoadCount" >Wyznacz trasę</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-flex>
                </v-layout>
                <template v-if="result.length > 0" >
                    <v-layout row>
                        <v-flex xs12>
                            <v-toolbar>
                                <v-toolbar-title>Trasa:</v-toolbar-title>
                            </v-toolbar>
                        </v-flex>
                    </v-layout>
                    <v-layout row>
                        <v-flex align-content-center xs12>
                            <v-card height="500px" >
                                <v-progress-circular class="map-loading" v-if="isMapLoading" indeterminate :size="70" :width="7" color="green"></v-progress-circular>
                                <template v-if="!isMapLoading" >
                                    <canvas id="canvas" width="500" height="500" ></canvas>
                                </template>
                            </v-card>
                        </v-flex>
                    </v-layout>
                </template>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
import vue2Dropzone from 'vue2-dropzone'
import 'vue2-dropzone/dist/vue2Dropzone.css'

export default {
  name: 'home',
  components: {
      vueDropzone: vue2Dropzone
  },
  data () {
    return {
      pointsType: 1,
      algType: 'GREEDY',
      isMapLoading: false,
      pointsAssigned: [],
      amountOfPoints: null,
      result: [],
      dropzone: '',
      dropzoneOptions: {
          url: 'https://localhost:8080',
          thumbnailWidth: 50,
          maxFilesize: 0.5,
          maxFiles: 1,
          acceptedFiles: 'application/json'
      },
      currentPoint: {x: null, y: null},
      currentPoints: []
    }
  },
  methods: {
      onRoadCount () {
        this.isMapLoading = true;

        var model = {
            algorithm: this.algType,
            points: this.currentPoints
        }
        this.$http.post('http://localhost:9090/tsp', model).then(
            val => {
                this.result = val.body;
                this.isMapLoading = false;
                setTimeout(() => {
                    this.drawPoints();
                }, 1000);
            }
        );
      },
      onPointAssginedPush () {
          this.pointsAssigned.push({x: 0, y: 0, roads: []});
      },
      onFileAdded (file) {
          this.readLoadedFile();
      },
      onFileProgress (file, progress, bytes) {
          if (progress === 100) {
              this.readLoadedFile();
          }
      },
      onAddPoint () {
          const tmp = {
              name: Date.now(),
              x: this.currentPoint.x,
              y: this.currentPoint.y
          }
          this.currentPoints.push(tmp);
          this.currentPoint.x = null;
          this.currentPoint.y = null;
      },
      generatePoints () {
          if (this.amountOfPoints <= 100) {
            var tmp = [];
            for (var i = 0; i < this.amountOfPoints; i++) {
                var routes = [];
                for (var j = 0; j < Math.round((Math.random() * 100)%this.amountOfPoints); j++) {
                    routes.push(Math.round((Math.random() * 100)%this.amountOfPoints));
                }
                tmp.push({
                    name: '' + Date.now() + Math.random(),
                    x: Math.random() * 100,
                    y: Math.random() * 100,
                    routes: routes
                });
            }
            this.currentPoints = tmp;
          }
      },
      readLoadedFile () {
          var files = this.$refs.fileDrop.getAcceptedFiles();
          var reader = new FileReader();
          reader.onloadend = (f) => {
              var tmp = JSON.parse(f.target.result);

              this.currentPoints = tmp;
          }
          reader.readAsText(files[0]);
      },
      drawPoints () {
          this.result.forEach(el => { el.x = el.x*5, el.y = el.y*5 });
          var canvas = document.querySelector('#canvas');
          if(canvas.getContext) {
              var ctx = canvas.getContext('2d');

              for(let i = 0; i < this.result.length; i++) {
                  /// ctx.fillStyle = 'rgb(' + Math.floor(255 - 42.5 * i+1) + ', ' + Math.floor(255 - 42.5 * i+1) + ',' + '0)';
                  ctx.fillStyle = 'red';
                  ctx.fillText('' + i, this.result[i].x, this.result[i].y);
                  try {
                      ctx.beginPath();
                      ctx.moveTo(this.result[i].x, this.result[i].y);
                      ctx.lineTo(this.result[i+1].x, this.result[i+1].y);
                      ctx.closePath();
                      ctx.stroke();
                  } catch (e) {}
              }
          }
      }
  }
}
</script>

<style scoped>
    .map-loading {
        margin-top: 180px;
    }
    .scrolling-box {
        max-height: 350px;
        overflow-y: scroll;
    }
    .points-title {
        text-align: left;
        padding: 20px 0px;
    }
    #canvas {
        border: 1px solid #ddd;
    }
</style>
