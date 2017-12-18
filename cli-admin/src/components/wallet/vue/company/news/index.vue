<template>
        <div>
          <el-table :data="tableData3" style="width: 100%" ref="multipleTable" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="date" label="日期">
            </el-table-column>
            <el-table-column prop="name" label="内容">
            </el-table-column>
            <el-table-column prop="address" label="状态" :formatter="formatter">
            </el-table-column>
            <el-table-column prop="tag" label="消息类型" width="200" :filters="[{ text: '家', value: '家' }, { text: '公司', value: '公司' }]" :filter-method="filterTag"
              filter-placement="bottom-end">
              <template slot-scope="scope">
                <el-tag :type="scope.row.tag === '家' ? 'primary' : 'success'" close-transition>{{scope.row.tag}}</el-tag>
              </template>
            </el-table-column>
          </el-table>
          <div style="margin-top: 20px">
            <el-button size="small" @click="toggleSelection([tableData3[1], tableData3[2]])">标记已读</el-button>
            <el-button size="small" @click="toggleSelection()">批量删除</el-button>
          </div>
        </div>
      </template>
      <script>
        export default {
          data() {
            return {
              tableData3: [{
                date: '2016-05-02',
                name: '王小虎',
                address: '已读',
                tag: '家'
              }, {
                date: '2016-05-04',
                name: '王小虎',
                address: '未读',
                tag: '公司'
              },],
              multipleSelection: []
            }
          },
          methods: {
            formatter(row, column) {
              return row.address;
            },
            filterTag(value, row) {
              return row.tag === value;
            },
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
            }
          }
        }
      
      </script>
      