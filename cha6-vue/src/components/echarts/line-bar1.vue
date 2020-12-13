<template>
  <!-- <div class="table_title">{{title}}</div> -->
  <div :id="eId" :style="{height: height}" />
</template>

<script>
var echarts = require('echarts/lib/echarts')
// 引入柱状图
require('echarts/lib/chart/line')
require('echarts/lib/chart/bar')
// 引入提示框和标题组件
require('echarts/lib/component/tooltip')
require('echarts/lib/component/toolbox')
require('echarts/lib/component/title')
require('echarts/lib/component/markPoint')
require('echarts/lib/component/markLine')
require('echarts/lib/component/legend')

export default {
  name: 'LineBar',
  props: {
    'title': {
      type: String
    },
    eId: {
      type: String
    },
    'colorArr': {
      type: String,
      default: '204,54,69'
    },
    xParam: {
      type: Object,
      // eslint-disable-next-line vue/require-valid-default-prop
      default: {}
    },
    cfg: {
      type: Array,
      // eslint-disable-next-line vue/require-valid-default-prop
      default: [{
        valueName: 'value',
        ySign: ''
      }]
    },
    'chartData': {
      type: Array
    },
    'height': {
      type: String,
      default: '328px'
    }
  },
  data() {
    return {
      xAxisData: [],
      seriesData: [],
      legendData: []
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        if (val.length === 0) {
          return
        }

        // 先循环组，有多组数据
        val.forEach(element => {
          this.xAxisData.push(element[this.xParam.paramName])
        })

        this.cfg.forEach(e2 => {
          const sData = []
          // 再循环数据，把其中的值根据键取出来
          val.forEach(element => {
            sData.push(element.m[e2.valueName] ? element.m[e2.valueName] : '')
          })

          this.legendData.push(e2.yLabel)
          // var colorRgb = 'rgba(' + this.colorArr
          // 定义series的数据
          const s = {
            name: e2.yLabel,
            type: 'line',
            data: sData,
            // areaStyle: {
            //   normal: {
            // areaStyle: {
            //   type: 'default'
            // },
            // color: {
            //   type: 'linear',
            //   x: 0,
            //   y: 0,
            //   x2: 0,
            //   y2: 1,
            //   colorStops: [{
            //     offset: 0, color: colorRgb + ',0.9)'
            //   }, {
            //     offset: 0.65, color: colorRgb + ',0.6)' // 50%处的颜色
            //   }, {
            //     offset: 1, color: colorRgb + ',0)'
            //   }
            //   ],
            //   global: false // 缺省为 false
            // }
            // }
            // },
            markPoint: {
              data: [
                { type: 'max', name: '最大值' },
                { type: 'min', name: '最小值' }
              ]
            },
            markLine: {
              data: [
                { type: 'average', name: '平均值' }
              ]
            }
          }
          this.seriesData.push(s)
        })
        this.setOptions()
      }
    }
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
      this.setOptions(this.chartData)
    },
    setOptions() {
      // const $this = this
      var colorRgb = 'rgba(' + this.colorArr
      console.log('colorRgb' + colorRgb)
      this.chart.setOption({
        // color: ['rgba(228,128,128,1)'], /* color表示折线图的颜色  */
        // color: [colorRgb + ')'],
        title: {
          text: this.title
        },
        legend: {
          data: this.legendData
        },
        tooltip: {
          trigger: 'axis' /* 增加这个可以出现一条竖线显示信息  */
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          show: true,
          feature: {
            // dataZoom: {
            //   yAxisIndex: 'none'
            // },
            // dataView: { readOnly: false },
            magicType: { type: ['line', 'bar'] },
            // restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          name: this.xParam, /* x轴名称 */
          data: this.xAxisData, /* x轴显示数据*/
          type: 'category',
          boundaryGap: false, /* 两边留白策略*/
          axisPointer: {
            label: {
              formatter: function(params) {
                return params.value
              }
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value}'
          }
        },
        series: this.seriesData
      })
    }

  }
}

</script>

<style scoped>

</style>
