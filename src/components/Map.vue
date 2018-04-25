<template>
    <v-container>
        <v-layout row>
            <v-flex class="loading-wrapper" xs12>
                <div v-if="isLoading" class="loading-overlay">
                    <div><img src="../assets/three-dots.svg" class="loader" /></div>
                </div>
                <div id="scene"></div>
            </v-flex>
        </v-layout>
    </v-container>
</template>
<script>
import Konva from 'konva';

export default {
  name: 'Map',
  props: ['points', 'paths', 'loading'],
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
        yScale: 1,
        isLoading: false
    }
  },
  watch: {
      points: function (newVal) {
          if (newVal) {
              this.fillPointsLayer(newVal);
          }
      },
      paths: function (newVal) {
          if (newVal.length > 1) {
              this.fillPathsLayer(newVal);
          }
      },
      loading: function (val) {
          this.isLoading = val;
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
            this.createStage();
            this.stage.add(this.pointsLayer);
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
            if (points) {
                this.createStage();
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
                for (let i = 1; i < points.length; i++) {
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
              el.x = el.x * this.xScale - 5;
              el.y = el.y * this.yScale - 5;
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
.loading-wrapper {
    position: relative;
}
.loading-overlay {
    display: flex;
    width: 100%;
    height: 100%;
    background-color: rgba(0,0,0,0.2);
    position: absolute;
    top: 0;
    left: 0;
    align-items: center;
}
.loading-overlay div {
    display: block;
    width: 100%;
    flex-direction: row;
}
.loading {
    margin: 0 auto;
}
</style>
