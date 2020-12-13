<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>设备分布</span>
        <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
      </div>
      
      <div class="amap-wrapper">
        <div id="amap-vue2" class="amap-wrapper" />
        <div class="toolbar">position: [{{ amap.lng }}, {{ amap.lat }}]（{{ amap.address }}）</div>
      </div>
    </el-card>
    <div class="form">
      <el-form :inline="true">
        <el-form-item label="设备类型" prop="type">
          <el-select v-model="searchForm.type" placeholder="请选择--">
            <el-option
              v-for="(item, index) in dependencyList.deviceTypeList"
              :key="index"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称">
          <el-input v-model="searchForm.name" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList">查询</el-button>
        </el-form-item>
        <!-- <el-form-item>
      <el-button>重置</el-button>
    </el-form-item> -->
      </el-form>
    </div>
    
    <br>
    <el-table
      v-loading="devicePage.listLoading"
      :data="devicePage.records"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column
        type="selection"
        width="55"
      />
      <el-table-column align="center" label="序号" width="95">
        <template slot-scope="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column label="设备">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.des }}</span>
        </template>
      </el-table-column>
      <el-table-column label="地址" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.address }}
            <p>经度：{{ scope.row.longitude }}
            纬度：{{ scope.row.latitude }}
            </p>
          </span>
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="状态" width="110" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status = 1 ? '正常' : '告警' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="最近动态时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.lastDataTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="text" @click="moveTo(scope.row)">查看定位</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          :pager-count="5"
          :total="devicePage.total"
          :page-size="devicePage.size"
          :current-page="devicePage.current"
          :page-sizes="[10, 50, 100, 200, 500, 1000]"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
        />
    </div>


  </div>
</template>

<script>
import request from '@/utils/request'
import MapLod from '@/utils/MapLod.js'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      devicePage: { // 主题数据，设备分页列表，这个地方与list同级的还有分页的其他参数信息
        records: [],
        listLoading: true
      },
      deviceFormDialog: { // 设备表单弹框
        visible: false, // 控制是否显示
        isCreate: true,
        deviceForm: {
          metadata: {},
          specification: '',
          siteToken: ''
        },
        deviceFormRules: { // 增加或编辑表单校验规则
          comments: [{ required: true, message: "请输入设备名称", trigger: "blur" }],
          specificationToken: [
            { required: true, message: "请选择分类", trigger: "change" }
          ]
        }
      },
      dependencyList: { // 依赖列表，主要是下拉框相关
        deviceTypeList: [{
          id: 1,
          name: '空气质量'
        }]
      },
      searchForm: {
        // 不需要可见属性，其他规则与增加编辑表单一致
      },
      Zmap: null,
      zmap: null,
      amap: {
        lng: 0,
        lat: 0,
        address: ''
      },
    }
  },
  created() {
    this.getList()
    const $t = this
    MapLod().then(
      AMap => {
        $t.Zmap = AMap
        
        console.log('初始化地图')
        $t.zmap = new AMap.Map('amap-vue2', {})

// 地图点击事件
        $t.zmap.on('click', function(e) {
          const { lng, lat } = e.lnglat
          $t.amap.lng = lng
          $t.amap.lat = lat
          AMap.plugin('AMap.Geocoder', function() {
            // 这里通过高德 SDK 完成。
            var geocoder = new AMap.Geocoder({
              radius: 1000,
              extensions: 'all'
            })
            geocoder.getAddress([lng, lat], function(status, result) {
              if (status === 'complete' && result.info === 'OK') {
                if (result && result.regeocode) {
                  $t.amap.address = result.regeocode.formattedAddress
                  $t.$nextTick()
                }
              }
            })
          })
        })
        // 获取要标注的坐标点
        request.get('/item/itemDevice?size=1000').then(res => {
          const list = res.data.records
          list.forEach(element => {
            if (typeof element.longitude == 'undefined') {
              return
            }
            const marker = new AMap.Marker({
              position: new AMap.LngLat(element.longitude, element.latitude), // 经纬度对象，如 new AMap.LngLat(116.39, 39.9); 也可以是经纬度构成的一维数组[116.39, 39.9]
              title: `(${element.name})`,
              icon: (function() {
                // 状态：1、删除；0、正常；2、告警；3、失联
                if (element.status === 2) {
                  return '/static/img/设备告警.png'
                } else if (element.status === 3) {
                  return '/static/img/设备离线.png'
                } else {
                  return '/static/img/光交箱.png'
                }
              })(),
              size: new AMap.Size(40, 50)
            })
            $t.zmap.add(marker)

            $t.zmap.setFitView()
            // this.amap.center = [list[0].latitude, list[0].longitude]
            AMap.event.addListener(marker, 'click', function() {
              var info = []
              info.push(
                "<div class='input-card content-window-card'><div><img src=\"/static/img/guangjiaoxiang.png \"/></div><hr/> "
              )
              info.push(
                '<div style="padding:7px 0px 0px 0px;"><h4>' +
                  element.name 
              )
              info.push(
                "<p class='input-item'>地址：" +
                  element.address +
                  '</p></div></div>'
              )
              const infoWindow = new AMap.InfoWindow({
                content: info.join('') // 使用默认信息窗体框样式，显示信息内容
              })
              infoWindow.open($t.zmap, marker.getPosition())
            })
          })
        })
      },
      e => {
        console.log('地图加载失败')
      }
    )
  },
  methods: {
    moveTo(row) {
      // 传入经纬度，设置地图中心点
      var position = new this.Zmap.LngLat(row.longitude, row.latitude) // 标准写法
      // 简写 var position = [116, 39];
      this.zmap.setCenter(position)
      this.zmap.setZoom(12)
    },
    // 查询设备列表
    getList() {
      this.devicePage.listLoading = true
      request.get(`/item/itemDevice`, 
        { 
          params: {
            size: this.devicePage.size,
            current: this.devicePage.current,
            ...this.formModel
          }
        }
      )
      .then(res => {
        // res.content.records.forEach((item, index) => {
        //   item.status = item.status === 0 ? '正常' : item.status === 2 ? '告警' : '失联'
        // })
          this.devicePage = res.data
          this.devicePage.listLoading = false
     })
    },
    // 显示增加表单
    showCreateForm() {
      this.deviceFormDialog.visible = true
    },
    // 每页条数变更
    handleSizeChange(size) {
      this.devicePage.size = size
      this.getList()
    },
    // 翻页
    handleCurrentChange(current) {
      this.devicePage.current = current
      this.getList()
    }
  }
}
</script>
</script>
<style lang="scss" scoped>
.amap-wrapper {
  height: 300px;
}
</style>
