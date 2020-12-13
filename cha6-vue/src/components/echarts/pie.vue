<template>
  <!-- <div class="table_title">{{title}}</div> -->
  <div :id="eId" :style="{height: height, width: '100%'}" />
</template>

<script>
var echarts = require('echarts/lib/echarts')
// 引入柱状图
require('echarts/lib/chart/pie')
// 引入提示框和标题组件
require('echarts/lib/component/tooltip')
require('echarts/lib/component/toolbox')
require('echarts/lib/component/title')

export default {
  name: 'Pie',
  props: ['title', 'eId', 'colorArr', 'chartData', 'height'],
  data() {
    return {
      formModel: {},
      option: {
        // color: ['rgba(228,128,128,1)'], /* color表示折线图的颜色  */
        title: {
          text: this.title,
          x: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        toolbox: {
          show: true,
          feature: {
            // dataZoom: {
            //   yAxisIndex: 'none'
            // },
            // dataView: { readOnly: false },
            // restore: {},
            // saveAsImage: {}
          }
        },
        legend: {
          orient: 'vertical',
          x: 'left',
          data: []
        },
        // roseType: 'radius',
        // xAxis: { type: 'category' },
        // yAxis: { gridIndex: 0 },
        // grid: { top: '55%' },
        series: [
          {
            name: this.title,
            type: 'pie',
            radius: '35%',
            center: ['50%', '50%'],
            data: [],
            label: {
              normal: {
                // formatter: '{b} : {c} ({d}%)'
                // backgroundColor: '#eee',
                // borderColor: '#aaa',
                // borderWidth: 1,
                // borderRadius: 4
                // shadowBlur:3,
                // shadowOffsetX: 2,
                // shadowOffsetY: 2,
                // shadowColor: '#999',
                // padding: [0, 7],
              }
            }
          }
        ]
      }
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        // debugger
        // 接收val之后，解析；取name放入legend
        const legend = []
        val.forEach(element => {
          legend.push(element.name)
        })
        this.option.legend.data = legend
        this.option.series[0].data = val
        this.setOptions()
      }
    }
  },
  created() {
  },
  mounted() {
    this.initCharts()
    window.addEventListener('resize', () => {
      this.chart.resize()
    })
  },
  methods: {
    initCharts() {
      this.chart = echarts.init(document.getElementById(this.eId))
      this.setOptions(...this.option)
    },
    setOptions() {
      // debugger
      this.chart.setOption(this.option)
    }

  }
}

</script>

<style scoped>

</style>
