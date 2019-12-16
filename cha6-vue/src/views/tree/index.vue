<template>
  <div>
    <el-card>
      <div slot="header">
        数据列表
        <!-- <el-button type="primary" size="small" @click="batchUnlock">开锁</el-button> -->
        <el-switch
          v-model="scheduleSearch.status"
          style="float: right;"
          active-text="开启定时刷新"
          @change="openScheduleSearch"
        />
      </div>
      <div class="form">
        <el-form :inline="true">
          <el-form-item label="设备名称">
            <el-input v-model="formModel.name" placeholder="请输入设备名称" />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="getList">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button>重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!-- 提示部分 -->
      <div class="point">
        <div class="point-text">
          <span>
            本次刷新时间：{{ new Date().toLocaleString() }}
            <!-- <el-button type="text">清空</el-button> -->
          </span>
        </div>
      </div>
      <!-- 主体部分的表格 -->
      <el-table stripe :data="tableData.row" style="width: 100%" :current-row-key="tableData.row.id" @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55"
        />
        <el-table-column
          type="index"
          label="编号"
          width="50"
        />
        <el-table-column label="设备信息" width="450">
          <template slot-scope="slotProps">
            <p>名称：{{ slotProps.row.name }}（型号：{{ slotProps.row.modelName }}）</p>
            <p>地址：{{ slotProps.row.address }}</p>
          </template>
        </el-table-column>

        <el-table-column label="温度">
          <template slot-scope="slotProps">
            {{ slotProps.row.temperature }} ℃
          </template>
        </el-table-column>
        <el-table-column label="湿度">
          <template slot-scope="slotProps">
            {{ slotProps.row.humidity }} %
          </template>
        </el-table-column>
        <el-table-column label="pm2.5">
          <template slot-scope="slotProps">
            {{ slotProps.row.pm2d5 }}
          </template>
        </el-table-column>
        <el-table-column label="数据上报时间">
          <template slot-scope="slotProps" width="240">
            {{ slotProps.row.createTime }}
          </template>
        </el-table-column>
      </el-table>
      <br>
      <!-- 底部的翻页器 -->
      <div class="pagination">
        <el-pagination
          background
          layout="total, prev, pager, next, jumper"
          :pager-count="5"
          :total="page.total"
          :page-size="page.pageSize"
          :current-page="page.currentPage"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
    <!-- 点击删除按钮出现的弹框 -->
    <el-dialog
      :title="delDialog.title"
      :visible.sync="delDialog.show"
      width="30%"
      center
    >
      <span>{{ delDialog.content }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delDialog.show = false">取 消</el-button>
        <el-button type="primary" @click="delSure">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 点击添加、编辑出现的弹框，弹框主体是表单 -->
    <el-dialog
      :title="adedDialog.title"
      :visible.sync="adedDialog.show"
      width="900px"
      center
      @close="handleDialogClose(`formData`)"
    >
      <el-form ref="formData" :model="formData" :rules="ruleForm" label-width="100px">
        <el-form-item label="名称" prop="modelName">
          <el-input v-model="formData.modelName" />
        </el-form-item>
        <el-form-item label="型号" prop="name">
          <el-input v-model="formData.name" />
        </el-form-item>
        <el-form-item label="位置" prop="address">
          <el-input v-model="formData.address" />
        </el-form-item>
        <el-form-item prop="address" type="flex" justify="start">
          <el-col :span="11">
            <el-form-item label="经度" prop="latitude">
              <el-input v-model="formData.latitude" />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="纬度" prop="longitude">
              <el-input v-model="formData.longitude" />
            </el-form-item>
          </el-col>
        </el-form-item>

        <el-form-item label="mac地址" prop="deviceId">
          <el-input v-model="formData.deviceId" />
        </el-form-item>

        <el-form-item label="电信识别码" prop="imei">
          <el-input v-model="formData.imei" />
        </el-form-item>

        <el-form-item label="启用" prop="status">
          <el-select v-model="formData.status" placeholder="请选择--">
            <el-option
              v-for="(item, index) in options"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="restForm(`formData`)">重 置</el-button>
        <el-button v-show="adedDialog.showButton" type="primary" @click="editSure(`formData`)">编 辑</el-button>
        <el-button v-show="!adedDialog.showButton" type="primary" @click="addSure(`formData`)">立即添加</el-button>
        <el-button @click="adedDialog.show = false">取 消</el-button>
      </span>
    </el-dialog>
    <!-- 点击关联设备的时候，弹出的弹框 ，弹框主体内容为设备树 -->
    <el-dialog
      :title="relDialog.title"
      :visible.sync="relDialog.show"
      width="500px"
      center
    >
      <!-- 设备树 -->
      <el-tree ref="tree" icon-class="el-icon-document" :data="experimentTreeData" :props="defaultProps" show-checkbox node-key="id" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="relDialog.show = false">取 消</el-button>
        <el-button type="primary" @click="relSure">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import request from '@/utils/request'
import { isvalidInt } from '@/utils/validate'
export default {
  components: {
  },
  data() {
    const validInt = (rule, value, callback) => { // 整数验证
      if (!value && this.radioNumber === 1) {
        callback(new Error('不能为空'))
      } else if (!isvalidInt(value) && this.radioNumber === 1) {
        callback(new Error('请输入整数'))
      } else {
        callback()
      }
    }
    return {
      // 定时查询
      scheduleSearch: {
        task: 0,
        status: false
      },
      formModel: {
        name: '',
        labStatus: ''
      },
      page: { // 页面相关数据
        total: 10, // 数据总数
        pageSize: 10, // 一页中最多的数据数
        currentPage: 1, // 当前页面
        experimentTotal: 10, // 特殊需求下的数据
        PageExperimentSize: 1000,
        currentExperimentPage: 1
      },
      tableData: {
        row: []
      },
      inputSearch: '',
      delDialog: {
        show: false,
        content: '',
        title: '删除',
        id: ''
      },
      adedDialog: {
        show: false,
        content: '',
        title: '',
        showButton: ''
      },
      relDialog: {
        title: '选择关联的设备类型',
        show: false
      },
      formData: {},
      currentIndex: {
        editIndex: '',
        delIndex: ''
      }, // 当前所操作的数据的下标
      currentLabId: '',
      experimentTreeData: [], // 设备树的值
      defaultProps: {
        label: 'name',
        id: 'id',
        children: 'children'
      },
      editorData: '',
      editorAll: {},
      options: [{
        value: 0,
        label: '正常'
      }, {
        value: 1,
        label: '禁用'
      }],
      ruleForm: { // 表单验证
        name: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        maxNumber: [
          { required: true, validator: validInt, trigger: 'blur' }
        ],
        purpose: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        isOpen: [
          { required: true, message: '请选择', trigger: 'change' }
        ]
      },
      radioNumber: 1, // 使用单选框来控制获取光交箱可容纳人数的方式
      dialogTable: {
        deviceData: [], // 设备表格中的数据
        currentRow: '',
        formTableData: [],
        deviceName: ''
      },
      multipleSelection: []
    }
  },
  mounted() {
    this.getList()
  },
  beforeDestroy() {
    clearInterval(this.scheduleSearch.task)
  },
  methods: {
    openScheduleSearch(status) {
      if (status) {
        this.scheduleSearch.task = setInterval(() => {
          this.getList()
        }, 5000)
      } else {
        clearInterval(this.scheduleSearch.task)
      }
    },
    // 获取表格选中
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    batchUnlock() {
      const t = this.multipleSelection
      console.log(t)

      var ids = ''
      t.forEach(element => {
        ids = ids === '' ? ids + element.id : ids + ',' + element.id
      })
      request.post('/nb/iot/batchUnlock', {}, {
        params: {
          'lockIds': ids
        }
      }).then(res => {
        alert(res.content)
      })
      this.getList()
    },
    // 根据id查询光交箱详细信息
    getListOfId(id) {
      request.get(`/device/lock/admin/${id}`).then(res => {
        this.formData = res.content
      })
    },
    // 查询数据列表
    getList() {
      return request.get('/device/scheduleUpload', { params: {
        size: this.page.pageSize,
        current: this.page.currentPage,
        ...this.formModel
      }}).then(res => {
        this.tableData.row = res.content.records
        this.page.total = res.content.total
      })
    },

    // 删除操作
    del(index) {
      this.currentIndex.delIndex = index
      this.delDialog.content = '您确定删除 ' + this.tableData.row[this.currentIndex.delIndex].name + ' 吗？'
      this.delDialog.id = this.tableData.row[this.currentIndex.delIndex].id
      this.delDialog.show = true
    },
    delSure() {
      this.delDialog.show = false
      request.delete(`/items/lab/${this.delDialog.id}`).then(res => {
        if (res.code === 0) {
          this.getList() // 删除后重新加载页面
        }
      })
    },
    // 编辑操作
    edit(index) {
      this.currentIndex.editIndex = index
      var currentId = this.tableData.row[this.currentIndex.editIndex].id
      this.getListOfId(currentId)
      this.adedDialog.title = '编辑'
      this.adedDialog.show = true
      this.adedDialog.showButton = true
    },
    editSure(formName) { // 提交根据id查询得到的详细数据
      this.$refs[formName].validate(valid => {
        if (valid) {
          request.put('/device/lock', {
            ...this.formData
          }).then(res => { // 以对象形式传参
            if (res.code === 0) {
              this.getList()
            } else {
              this.$message.warning(res.content)
            }
            this.adedDialog.show = false
          })
        }
      })
    },
    // 关联设备操作
    relate(index) {
      this.currentLabId = this.tableData.row[index].id
      this.relDialog.show = true
      this.getLabAndExperimentIds()
    },
    relSure() {
      request.post(`/items/labAndExperiment/bylab/${this.currentLabId}`,
        this.$refs.tree.getCheckedKeys()
      ).then(res => {
        this.$message.success(res.content)
        this.$refs.tree.setCheckedKeys([])
        this.relDialog.show = false
      })
    },
    // 添加操作
    add() {
      this.formData = {} // 富文本编辑器的重置有问题
      if (this.editorAll.txt !== undefined) {
        this.editorAll.txt.clear()
      }
      this.adedDialog.title = '添加'
      this.adedDialog.show = true
      this.adedDialog.showButton = false
    },
    addSure(formName) { //  经过通信，formData与表单中的数据绑定
      this.$refs[formName].validate(valid => {
        if (valid) {
          request.post('/device/lock', {
            ...this.formData
          }).then(res => {
            if (res.code === 0) {
              this.getList()
            } else {
              console.log('失败')
              this.$message.warning(res.content)
            }
            this.adedDialog.show = false
          })
        }
      })
    },
    // 单选表格中的数据
    handleTableCurrentChange(obj) {
      // this.dialogTable.formTableData[0] = { ...obj } // 由于js本身的限制，vue不能检测该方式变动的数组
      this.dialogTable.formTableData.splice(0, 1, { ...obj })
    },
    // 翻页时触发该函数
    handleCurrentChange(val) {
      this.page.currentPage = val
      this.getList()
    },
    // 关闭弹窗时会触发该钩子，重置表单
    handleDialogClose(formName) {
      this.adedDialog.show = false
      this.dialogTable.formTableData = []
      this.dialogTable.deviceName = ''
      this.radioNumber = 1
      this.restForm(formName)
    },
    // 重置表单函数
    restForm(formName) {
      this.$refs[formName].resetFields() // 重置
      // this.formData = {} // 将表单置空
    },
    // 搜索设备
    searchDevice() {
      // this.getDeviceList()
    }
  }
}
</script>

<style scoped>
.point {
  width: 100%;
  height: 40px;
  margin-top: 8px;
  background-color: #e6f7f7;
  border: 1px solid rgba(186, 231, 255, 1);
  border-radius: 4px;
  box-shadow: none;
  font-size: 14px;
  text-align: left;
}
.point-text {
  margin-left: 14px;
  line-height: 40px;
}
.pagination {
  float: right;
  margin-bottom: 15px;
}
</style>
