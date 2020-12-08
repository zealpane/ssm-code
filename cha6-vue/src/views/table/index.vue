<template>
  <div class="app-container">
    <div class="form">
      <el-form :inline="true">
        <el-form-item label="设备类型" prop="type">
          <el-select v-model="searchForm.type" placeholder="请选择--">
            <el-option
              v-for="(item, index) in deviceTypeList"
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
      <el-button type="primary" size="small" @click="add">新增</el-button>
      <el-button type="danger" size="small" @click="batchDelete">删除</el-button>
    </div>
    <br>
    <el-table
      v-loading="listLoading"
      :data="list"
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
      <el-table-column label="所属" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.author }}</span>
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="状态" width="110" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="最近动态时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.display_time }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="slotProps">
          <el-button type="text" @click="edit(slotProps.$index)">编辑</el-button>
          <el-button type="text" @click="relate(slotProps.$index)">查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>
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
      list: null,
      listLoading: true,
      deviceTypeList: [{
        id: 1,
        name: '空气质量'
      }],
      searchForm: {
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询设备列表
    getList() {
      this.listLoading = true
      request.get(`/item/itemDevice`, 
        { 
          params: {
            size: 10,
            ...this.formModel
          }
        }
      )
      .then(res => {
        res.content.records.forEach((item, index) => {
          item.status = item.status === 0 ? '正常' : item.status === 2 ? '告警' : '失联'
        })
        this.list = res.data.records
        this.listLoading = false
      })
    }
  }
}
</script>
