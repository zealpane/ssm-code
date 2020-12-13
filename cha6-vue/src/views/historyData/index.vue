<template>
  <div class='app-container'>
    <div class='form'>
      <el-form :inline='true'>
        <el-form-item label='设备类型' prop='type'>
          <el-select v-model='searchForm.type' placeholder='请选择--'>
            <el-option
              v-for='(item, index) in dependencyList.deviceTypeList'
              :key='index'
              :label='item.name'
              :value='item.id'
            />
          </el-select>
        </el-form-item>
        <el-form-item label='设备名称'>
          <el-input v-model='searchForm.name' placeholder='请输入设备名称' />
        </el-form-item>
        <el-form-item>
          <el-button type='primary' @click='getList'>查询</el-button>
        </el-form-item>
        <!-- <el-form-item>
      <el-button>重置</el-button>
    </el-form-item> -->
      </el-form>
    </div>
    <div slot='header'>
      <el-button type='primary' size='small' @click='showCreateForm'>新增</el-button>
      <el-button type='danger' size='small' @click='batchDelete'>删除</el-button>
    </div>
    <br>
    <el-table
      v-loading='devicePage.listLoading'
      :data='devicePage.records'
      element-loading-text='Loading'
      border
      fit
      highlight-current-row
    >
      <el-table-column
        type='selection'
        width='55'
      />
      <el-table-column align='center' label='序号' width='95'>
        <template slot-scope='scope'>
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column label='设备信息'>
        <template slot-scope='scope'>
          设备编号：{{ scope.row.deviceId }}
        </template>
      </el-table-column>
      <el-table-column label='采集数据' align='center'>
        <template slot-scope='scope'>
          <p>温度：{{ scope.row.temperature }}℃</p>
          <p>湿度：{{ scope.row.humidity }}%</p>
          <p>PM2.5：{{ scope.row.pm2d5 }}</p>
        </template>
      </el-table-column>
      
      <el-table-column align='center' prop='created_at' label='上传时间' width='200'>
        <template slot-scope='scope'>
          <i class='el-icon-time' />
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label='操作' width='200'>
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
    <el-drawer
      title="我是标题"
      :visible.sync="deviceFormDialog.visible"
      direction="rtl"
    >
      <span>我来啦!</span>
    </el-drawer>
  </div>
</template>

<script>
import request from '@/utils/request'

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
          comments: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
          specificationToken: [
            { required: true, message: '请选择分类', trigger: 'change' }
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
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询设备列表
    getList() {
      this.devicePage.listLoading = true
      request.get(`/item/itemData`, { params: { 
          params: {
            size: this.devicePage.size,
            current: this.devicePage.current,
            ...this.formModel
          }
        }}).then(res => {
        // res.content.records.forEach((item, index) => {
        //   item.status = item.status === 0 ? '正常' : item.status === 2 ? '告警' : '失联'
        // })
        this.devicePage = res.data
        this.devicePage.listLoading = false
      })
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
