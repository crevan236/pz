<template>
    <v-container>
        <v-layout row>
            <v-flex xs12>
                <p>aloha</p>
                <div id="scene"></div>
            </v-flex>
        </v-layout>
    </v-container>
</template>
<script>
import Konva from 'konva';

export default {
  name: 'Map',
  data () {
    return {
        stage: null,
        pointsLayer: null,
        pathsLayer: null,
        pointsData: {
            pointFillColor: '#e77728',
            pointStrokeColor: '#00a7e1',
            pointRadius: 10,
            strokeWidth: 2
        }
    }
  },
  mounted () {
    this.stage = new Konva.Stage({
      container: 'scene',
      width: 500,
      height: 500
    });
    this.pointsLayer = new Konva.Layer();

    const circle = new Konva.Circle({
      x: 250,
      y: 250,
      radius: this.pointsData.pointRadius,
      fill: this.pointsData.pointFillColor,
      stroke: this.pointsData.pointStrokeColor,
      strokeWidth: this.pointsData.strokeWidth
    });

    this.pointsLayer.add(circle);
    this.stage.add(this.pointsLayer);
  },
  methods: {
      fillPointsLayer () {

      }
  }
}
</script>
<style scoped>
</style>
