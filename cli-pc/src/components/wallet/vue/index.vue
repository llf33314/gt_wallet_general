<style lang="stylus">


</style>
<template>
  <section>
    <el-breadcrumb separator="/" class="public-crumbs">
      <el-breadcrumb-item :to="{ path: '/' }">推广吸粉</el-breadcrumb-item>
      <el-breadcrumb-item>3D全景</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-gray-region">
      <el-button type="primary" @click="routerTo('/panorama3D/update')">新增</el-button>
      <el-button type="warning" class="gt-video-btn" @click="linkToVideo">
        <i class="iconfont gt-play"></i>视频教程
      </el-button>
    </div>
    <div class="public-content">
      <gt-null-data class="public-top20">您还没有授权人信息，
        <span @click="add()">点击这里</span>添加授权人吧
      </gt-null-data>
      <div class="public-table-title">
        666
      </div>
      <el-table ref="multipleTable" :data="tableData3" border tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column label="日期" width="120">
          <template slot-scope="scope">{{ scope.row.date }}</template>
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="120">
        </el-table-column>
        <el-table-column prop="address" label="操作" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button @click="routerTo('/panorama3D/update')" size="small">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="public-top20">
        <div class="public-fl">
          <el-button @click="toggleSelection([tableData3[1], tableData3[2]])" size="small">全选</el-button>
          <el-button @click="toggleSelection()" size="small">取消全选</el-button>
          <el-button @click="toggleSelection()" size="small">批量删除</el-button>
        </div>
        <div class="public-fr">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="currentPage3" :page-size="100"
            layout="prev, pager, next, jumper" :total="1000">
          </el-pagination>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
  export default {
    data() {
      return {
        tableData3: [{
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }],
        multipleSelection: []
      }
    },

    methods: {
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      routerTo(path) {
        this.$router.push({
          path: path
        })
      }
    }
  }

</script>
