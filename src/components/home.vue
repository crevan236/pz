<template>
    <v-container grid-list-md>
        <v-layout row>
            <v-flex xs12>
                <Map :points="currentPoints" :paths="result" :loading="isMapLoading" ></Map>
            </v-flex>
        </v-layout>
        <v-layout row>
            <v-flex xs6>
                <v-card>
                    <v-card-title><h3 class="title" >Podaj liczbę punktów</h3></v-card-title>
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
                                      :value="3"
                                    ></v-radio>
                                </v-flex>
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
                                        label="Podaj liczbę punktów"
                                        :error-messages="nofpErrors"
                                        @input="$v.amountOfPoints.$touch()"
                                        @blur="$v.amountOfPoints.$touch()"
                                    ></v-text-field>
                                </v-flex>
                                <v-flex>
                                    <v-btn color="orange" class="white--text" @click="generatePointsAlltoAll" :disabled="$v.amountOfPoints.$invalid" ><v-icon>play_arrow</v-icon></v-btn>
                                </v-flex>
                            </template>
                            <v-flex v-if="pointsType == 3" >
                                <v-layout row>
                                    <v-flex xs6><v-text-field v-model="currentPoint.x" label="X:" @input="$v.currentPoint.x.$touch()" @blur="$v.currentPoint.x.$touch()" required></v-text-field></v-flex>
                                    <v-flex xs6><v-text-field v-model="currentPoint.y" label="Y:" @input="$v.currentPoint.y.$touch()" @blur="$v.currentPoint.y.$touch()" required></v-text-field></v-flex>
                                </v-layout>
                                <v-layout row>
                                    <v-flex xs10>
                                        <multiselect
                                          v-model="currentPoint.routes"
                                          :options="currentPoints"
                                          :multiple="true"
                                          placeholder="Drogi:"
                                          :customLabel="generateCustomLabel"
                                          :track-by="name"
                                          >
                                        </multiselect>
                                    </v-flex>
                                    <v-flex align-content-start xs2>
                                        <v-btn color="orange" class="white--text" @click="onAddPoint" :disabled="$v.currentPoint.x.$invalid || $v.currentPoint.y.$invalid" ><v-icon>playlist_add</v-icon></v-btn>
                                    </v-flex>
                                </v-layout>
                            </v-flex>
                            <v-flex v-if="pointsType == 2" >
                                <vue-dropzone v-on:vdropzone-upload-progress="onFileProgress" v-on:vdropzone-complete="onFileAdded" ref="fileDrop" id="dropzone" :options="dropzoneOptions"></vue-dropzone>
                            </v-flex>
                        </v-layout>
                    </v-card-text>
                </v-card>
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
            </v-flex>
            <v-flex xs6>
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
                    <v-layout row>
                        <v-flex xs12>
                            <v-btn color="orange" class="white--text" @click="saveFile" >Zapisz do pliku</v-btn>
                            <a target="_blank" download="generated.json" id="fileDownloader" style="visibility: hidden" >Kliknij</a>
                        </v-flex>
                    </v-layout>
                </template>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
import vue2Dropzone from "vue2-dropzone";
import "vue2-dropzone/dist/vue2Dropzone.css";
import { validationMixin } from "vuelidate";
import { required, between } from "vuelidate/lib/validators";
import Multiselect from "vue-multiselect";
import Map from "./Map";

export default {
  name: "home",
  components: {
    vueDropzone: vue2Dropzone,
    multiselect: Multiselect,
    Map: Map
  },
  mixins: [validationMixin],
  validations: {
    amountOfPoints: { between: between(0, 100) },
    currentPoint: {
      x: { between: between(0, 100) },
      y: { between: between(0, 100) }
    }
  },
  data() {
    return {
      pointsType: 1,
      algType: "GREEDY",
      isMapLoading: false,
      pointsAssigned: [],
      amountOfPoints: null,
      result: [],
      dropzone: "",
      dropzoneOptions: {
        url: "https://localhost:3000",
        thumbnailWidth: 50,
        maxFilesize: 0.5,
        maxFiles: 1,
        acceptedFiles: "application/json"
      },
      currentPoint: { name: Date.now(), x: null, y: null, routes: [] },
      currentPoints: []
    };
  },
  computed: {
    nofpErrors() {
      const errors = [];
      if (!this.$v.amountOfPoints.$dirty) return errors;
      !this.$v.amountOfPoints.between &&
        errors.push("Wartość powinna być pomiędzy 0 a 100");
      return errors;
    },
    cordsxErrors() {
      const errors = [];
      if (!this.$v.currentPoint.x.$dirty) return errors;
      !this.$v.currentPoint.x.between &&
        errors.push("Wartość powinna być pomiędzy 0 a 100");
      return errors;
    },
    cordsyErrors() {
      const errors = [];
      if (!this.$v.currentPoint.y.$dirty) return errors;
      !this.$v.currentPoint.y.between &&
        errors.push("Wartość powinna być pomiędzy 0 a 100");
      return errors;
    }
  },
  methods: {
    onRoadCount() {
      this.isMapLoading = true;

      var model = {
        algorithm: this.algType,
        points: this.currentPoints
      };
      this.$http.get("http://localhost:3000/points").then(val => {
        this.result = val.body;
        this.isMapLoading = false;
      });
    },
    onPointAssginedPush() {
      this.pointsAssigned.push({ x: 0, y: 0, roads: [] });
    },
    onFileAdded(file) {
      this.readLoadedFile();
    },
    onFileProgress(file, progress, bytes) {
      if (progress === 100) {
        this.readLoadedFile();
      }
    },
    onAddPoint() {
      const tmp = {
        name: Date.now(),
        x: this.currentPoint.x,
        y: this.currentPoint.y,
        routes: this.currentPoint.routes
      };
      this.currentPoints.push(tmp);
      console.warn(tmp.routes);
      this.currentPoint.x = null;
      this.currentPoint.y = null;
      this.currentPoint.routes = [];
    },
    generatePoints() {
      if (this.amountOfPoints <= 100) {
        var tmp = [];
        for (var i = 0; i <= this.amountOfPoints; i++) {
          tmp.push({
            name: "" + Date.now() + Math.random(),
            x: Math.random() * 100,
            y: Math.random() * 100,
            routes: []
          });
        }
        for (let el of tmp) {
          const routes = [];
          for (
            var j = 0;
            j < Math.round((Math.random() * 100) % this.amountOfPoints) + 3;
            j++
          ) {
            let n = Math.round((Math.random() * 100) % this.amountOfPoints - 1);
            n = n < 0 ? 0 : n;
            routes.push(tmp[n].name);
          }
          el.routes = routes;
        }
        this.currentPoints = tmp.splice(0, tmp.length - 1);
      }
    },
    generatePointsAlltoAll() {
      if (this.amountOfPoints <= 100) {
        var tmp = [];
        for (var i = 0; i <= this.amountOfPoints; i++) {
          tmp.push({
            name: "" + Date.now() + Math.random(),
            x: Math.random() * 100,
            y: Math.random() * 100,
            routes: []
          });
        }
        for (let el of tmp) {
            const routes = [];
            for (let element of tmp) {
                if (element != el) {
                    routes.push(element.name);
                }
            }
            el.routes = tmp;
        }
        this.currentPoints = tmp.splice(0, tmp.length - 1);
        console.warn(this.currentPoints);
      }
    },
    readLoadedFile() {
      var files = this.$refs.fileDrop.getAcceptedFiles();
      var reader = new FileReader();
      reader.onloadend = f => {
        var tmp = JSON.parse(f.target.result);

        this.currentPoints = tmp;
      };
      try {
        reader.readAsText(files[0]);
      } catch (e) {
        console.warn("Wrong file format!!!");
      }
    },
    saveFile() {
      let newFile = null;
      try {
        newFile = new File(
          JSON.stringify(this.currentPoints),
          "generatedPoints.json",
          { type: "application/json" }
        );
      } catch (e) {
        console.warn("blob");
        newFile = new Blob([JSON.stringify(this.currentPoints)], {
          type: "text/plain"
        });
      }
      const url = URL.createObjectURL(newFile);
      let btn = document.querySelector("#fileDownloader");
      btn.href = url;
      btn.click();
    },
    generateCustomLabel(obj) {
      return `Punkt: ${this.currentPoints.findIndex(
        el => el.name === obj.name
      )}`;
    }
  }
};
</script>
<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
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
