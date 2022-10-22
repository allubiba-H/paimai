<template>
  <div class="dashboard-container">
    <div class="dashboard-text">欢迎您: {{ name }}</div>
    <el-row :gutter="20">
      <el-col :span="10">
        <el-card header="统计" style="font-weight: bold;">
          <div ref="myEchart" style="height: 350px;">
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import {getIndexInfo} from "@/api/common";

export default {
  data(){
    return {
      total:''
    }
  },
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  mounted() {
    this.initEcharts();
  },
  created() {
    this.featchData()
  },
  methods: {
   async featchData() {
     await getIndexInfo().then((res) => {
      this.total = res.map.count;
      })
    },
    initEcharts() {
      let mychart = this.$echarts.init(this.$refs.myEchart);
      let option = {
        backgroundColor: '#2c343c',
        title: {
          text: '系统信息统计',
          left: 'center',
          top: 20,
          textStyle: {
            color: '#ccc'
          }
        },
        tooltip: {
          trigger: 'item'
        },
        visualMap: {
          show: false,
          min: 80,
          max: 600,
          inRange: {
            colorLightness: [0, 1]
          }
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: '55%',
            center: ['50%', '50%'],
            data: [
              {value: this.total * 1000, name: '用户数量'},
              {value: 310, name: '所有竞拍次数'},
              {value: 274, name: '参与竞拍人数'},
              {value: 235, name: '拍品浏览次数'},
              {value: 400, name: '参与竞拍拍品'}
            ].sort(function (a, b) {
              return a.value - b.value;
            }),
            roseType: 'radius',
            label: {
              color: 'rgba(255, 255, 255, 0.3)'
            },
            labelLine: {
              lineStyle: {
                color: 'rgba(255, 255, 255, 0.3)'
              },
              smooth: 0.2,
              length: 10,
              length2: 20
            },
            itemStyle: {
              color: '#c23531',
              shadowBlur: 200,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            },
            animationType: 'scale',
            animationEasing: 'elasticOut',
            animationDelay: function (idx) {
              return Math.random() * 200;
            }
          }
        ]
      };
      mychart.setOption(option);
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
