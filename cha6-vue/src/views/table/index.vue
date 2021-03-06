<template>
  <div class="app-container">
    <div class="form">
      <el-form :inline="true">
        <el-form-item label="设备类型" prop="type">
          <el-select v-model="searchForm.deviceType" placeholder="请选择--">
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
    <div slot="header">
      <el-button type="primary" size="small" @click="showCreateForm">新增</el-button>
      <el-button type="danger" size="small" @click="batchDelete">删除</el-button>
    </div>
    <br>
    <el-table
      v-loading="devicePage.listLoading"
      :data="devicePage.records"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55"
      />
      <el-table-column align="center" label="序号" width="95">
        <template slot-scope="scope">
          {{ scope.$index + 1 + (devicePage.current - 1) * devicePage.size }}
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
      <el-table-column align="center" prop="created_at" label="报装时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="slotProps">
          <el-button type="text" @click="showEditForm(slotProps.row.id)">编辑</el-button>
          <!-- <el-button type="text" @click="relate(slotProps.$index)">查看详情</el-button> -->
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

    <el-drawer
      :visible.sync="deviceFormDialog.visible"
      direction="rtl"
      :with-header="false"
      :before-close="handleClose">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>{{ deviceFormDialog.isCreate ? "增加" : '编辑' }}设备 {{deviceFormDialog.deviceForm.name}}</span>
          <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
        </div>
        <el-form ref="form" :model="deviceFormDialog.deviceForm" label-width="80px">
          <el-form-item label="设备名称">
            <el-input v-model="deviceFormDialog.deviceForm.name"></el-input>
          </el-form-item>
          <el-form-item label="设备类型" prop="type">
            <el-select v-model="deviceFormDialog.deviceForm.deviceType" placeholder="请选择--">
              <el-option
                v-for="(item, index) in dependencyList.deviceTypeList"
                :key="index"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="描述">
            <el-input v-model="deviceFormDialog.deviceForm.des"></el-input>
          </el-form-item>
          <el-form-item label="位置">
            <el-input v-model="deviceFormDialog.deviceForm.address"></el-input>
            经度：<el-input v-model="deviceFormDialog.deviceForm.longitude"></el-input>
            纬度：<el-input v-model="deviceFormDialog.deviceForm.latitude"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="onSubmit">立即创建</el-button>
            <el-button @click="deviceFormDialog.visible = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-drawer>
  </div>
</template>

<script>
import request from '@/utils/request'
import qs from 'qs'

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
      multipleSelection: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询设备列表
    getList() {
      this.devicePage.listLoading = true
      request.get(`/item/itemDevice`, 
        { 
          params: {
            size: this.devicePage.size,
            current: this.devicePage.current,
            ...this.searchForm
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
      this.deviceFormDialog.isCreate = true
      // 将表单置空
      this.deviceFormDialog.deviceForm = {}
    },
    showEditForm(id) {
      this.deviceFormDialog.visible = true
      this.deviceFormDialog.isCreate = false
      // 获取设备信息，填充至表单
      request.get(`/item/itemDevice/${id}`).then(res => {
        debugger
        this.deviceFormDialog.deviceForm = res.data
      })
    },
    // 点击保存按钮
    onSubmit() {
      // 这里可以根据变量判断是要新增还是要修改
      if (this.deviceFormDialog.isCreate) {
        // 新增
        request.post('/item/itemDevice', this.deviceFormDialog.deviceForm).then(res => {
          this.deviceFormDialog.visible = false
          this.$message.success('新增成功')
        })
      } else {
        // 编辑
        request.put('/item/itemDevice', this.deviceFormDialog.deviceForm).then(res => {
          this.deviceFormDialog.visible = false
          this.$message.success('新增成功')
        })
      }
      this.getList()
    },
    handleSelectionChange(val) {
      let ids = []
      // 这里将值构造成id数组
      val.forEach(element => {
        ids.push(element.id)
      });
      this.multipleSelection = ids;
    },
    batchDelete() {
      // 一次限制只能删除一条数据
      if (this.multipleSelection.size > 1) {
        alert("一次限制只能删除一条数据")
        return
      }

      // 这里传入要删除的id
      request.delete("/item/itemDevice?id=" + this.multipleSelection[0]).then(res => {
        this.$message("删除成功")
        this.getList()
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
