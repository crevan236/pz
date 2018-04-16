<template>
    <v-container>
        <v-layout row>
            <v-flex xs12>
                <div id="scene"></div>
            </v-flex>
        </v-layout>
    </v-container>
</template>
<script>
import Konva from 'konva';

export default {
  name: 'Map',
  props: ['points', 'paths'],
  data () {
    return {
        stage: null,
        pointsLayer: null,
        pathsLayer: null,
        pointsData: {
            pointFillColor: '#e77728',
            pointStrokeColor: '#00a7e1',
            pointRadius: 5,
            strokeWidth: 1
        },
        stageWidth: 500,
        stageHeight: 1,
        xScale: 1,
        yScale: 1
    }
  },
  watch: {
      points: function (newVal) {
          if (newVal) {
              this.fillPointsLayer(newVal);
          }
      },
      paths: function (newVal) {
          if (newVal) {
              this.fillPathsLayer(newVal);
          }
      }
  },
  mounted: function () {
    this.createStage();
  },
  methods: {
      createStage () {
        this.stage = new Konva.Stage({
          container: 'scene',
          width: this.stageWidth,
          height: this.stageHeight
        });
      },
      fillPathsLayer (paths) {
          if (paths) {
            paths = this.scalePaths(paths);
            this.pathsLayer = new Konva.Layer();
            const points = [];
            for (let el of paths) {
                points.push(el.x);
                points.push(el.y);
            }
            const greenLine = new Konva.Line({
              points: points,
              stroke: '#223843',
              strokeWidth: 2,
              lineJoin: 'round',
              dash: [2, 1]
            });
            this.pathsLayer.add(greenLine);
            this.stage.add(this.pathsLayer);
          }
      },
      fillPointsLayer (points) {
          try {
            this.scaleStage();
            if  (points) {
                points = this.scalePoints(points);
                this.pointsLayer = new Konva.Layer();
                const circle = new Konva.Circle({
                    x: points[0].x,
                    y: points[0].y,
                    radius: this.pointsData.pointRadius,
                    fill: '#750d37',
                    stroke: '#210124',
                    strokeWidth: this.pointsData.strokeWidth
                });
                this.pointsLayer.add(circle);
                for (let i = 1; i < points.length - 1; i++) {
                    const circle = new Konva.Circle({
                        x: points[i].x,
                        y: points[i].y,
                        radius: this.pointsData.pointRadius,
                        fill: this.pointsData.pointFillColor,
                        stroke: this.pointsData.pointStrokeColor,
                        strokeWidth: this.pointsData.strokeWidth
                    });
                    this.pointsLayer.add(circle);
                }
            this.createStage();
            this.stage.add(this.pointsLayer);
            }
          } catch(e) {}
      },
      scaleStage () {
          const container = document.querySelector('#scene');
          this.stageWidth = container.offsetWidth;
          this.stageHeight = 400;

          this.stage.width(this.stageWidth);
          this.stage.height(this.stageHeight);
          // this.stage.scale({x: scale, y: scale});
          console.warn();
          this.stage.draw();
      },
      scalePoints(points) {
          let tmp = points.map(el => el.x);
          const maxX = Math.max.apply(Math, tmp);
          tmp = points.map(el => el.y);
          const maxY = Math.max.apply(Math, tmp);
          this.xScale = this.stageWidth / maxX; 
          this.yScale = this.stageHeight / maxY;
          points.forEach(el => {
              el.x = el.x * this.xScale;
              el.y = el.y * this.yScale;
          });
          return points;
      },
      scalePaths (paths) {
          paths.forEach(el => {
              el.x = el.x * this.xScale;
              el.y = el.y * this.yScale;
          });
          return paths;
      }
  }
}
</script>
<style scoped>

</style>
