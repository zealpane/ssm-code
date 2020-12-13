<template>
  <div class="row">
    <!-- <el-card class="col-3">
      <draggable
        class="dragArea list-group"
        :list="list1"
        :group="{ name: 'people', pull: 'clone', put: false }"
        @change="log"
      >
        <el-button
          class="list-group-item"
          v-for="element in list1"
          :key="element.name"
        >
          {{ element.name }}
        </el-button>
      </draggable>
    </el-card> -->

    <el-row :gutter="15">
      
        <el-col
          v-for="(element, index) in list2"
          :key="element.name"
          :span="element.span"
          class="list-group-item"
        ><el-card>
          <component
            :is="element.type"
            :e-id="'temperature' + index"
            :title="element.name"
            :height="element.height"
            color-arr="64,158,255"
            :chart-data="element.data"
            :x-param="element.xParam"
            :cfg="element.cfg"
          />
        </el-card>
        </el-col>

    </el-row>

  </div>
</template>

<script>
import LineBar from '@/components/echarts/line-bar.vue'
import Pie from '@/components/echarts/pie.vue'
import request from '@/utils/request'

export default {
  name: 'Clone',
  display: 'Clone',
  order: 2,
  components: {
    LineBar, Pie
  },
  data() {
    return {
      list1: [
        // { name: '张三', id: 1, span: 12,
        //   type: 'line-bar' },
        // { name: '李四', id: 2 },
        // { name: '王五', id: 3 },
        // { name: 'Gerard', id: 4 }
      ],
      list2: [
        { name: '设备编号1空气质量数据曲线', id: 3,
          span: 12, height: '768px',
          type: 'line-bar',
          dataSource: '/item/itemData?size=10',
          xParam: {
            label: '时间',
            paramName: 'createTime',
            xSign: ''
          },
          data: [],
          cfg: [{
            valueName: 'temperature',
            ySign: ' ℃',
            yLabel: '温度'
          }, {
            valueName: 'humidity',
            ySign: ' %',
            yLabel: '湿度'
          }, {
            valueName: 'PM2d5',
            ySign: ' %',
            yLabel: 'PM2.5'
          }]
        },
        { name: '设备编号1空气质量数据曲线（温度）', id: 3,
          span: 12, height: '228px',
          type: 'line-bar',
          dataSource: '/item/itemData?size=10',
          xParam: {
            label: '时间',
            paramName: 'createTime',
            xSign: ''
          },
          data: [],
          cfg: [{
            valueName: 'temperature',
            ySign: ' ℃',
            yLabel: '温度'
          }]
        },
        { name: '设备编号1空气质量数据曲线（湿度）', id: 3,
          span: 12, height: '228px',
          type: 'line-bar',
          dataSource: '/item/itemData?size=10',
          xParam: {
            label: '时间',
            paramName: 'createTime',
            xSign: ''
          },
          data: [],
          cfg: [{
            valueName: 'humidity',
            ySign: ' %',
            yLabel: '湿度'
          }]
        },
        { name: '设备编号1空气质量数据曲线（PM2.5）', id: 3,
          span: 12, height: '228px',
          type: 'line-bar',
          dataSource: '/item/itemData?size=10',
          xParam: {
            label: '时间',
            paramName: 'createTime',
            xSign: ''
          },
          data: [],
          cfg: [{
            valueName: 'PM2d5',
            ySign: ' %',
            yLabel: 'PM2.5'
          }]
        }
        // ,
        // { name: 'Juan2', id: 2,
        //   span: 12, height: '300px',
        //   type: 'pie',
        //   dataSource: '/logs/logsOperation/dayCountUnlock?yearVal=2019',
        //   data: []
        // }
      ],
      lineData: {
        createTime: [1, 2, 3, 4],
        temperature: [1, 3, 2, 4],
        pieData: []
      }
    }
  },
  mounted() {
    this.list2.forEach((element, index) => {
      request.get(element.dataSource).then(res => {
        if (res.code === 0) {
          this.list2[index].data = res.data.records
        }
      })
    })
  },
  methods: {
    log: function(evt) {
      window.console.log(evt)
    }
  }
}
</script>
<style scoped></style>
