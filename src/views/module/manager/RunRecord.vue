<template>
    <div class="record-page">
        <h2>晨跑记录</h2>
        <el-table :data="filterData" height="600">
            <el-table-column prop="date" label="日期"> </el-table-column>
            <el-table-column>
                <template v-slot:header>
                    <div style="display: flex;justify-content: space-between;">
                        <el-select v-model="this.selectValue" placeholder="选择是否出操">
                          <el-option v-for="item in option" :key="item.label" :label="item.label" :value="item.label"></el-option>
                        </el-select>
                        <el-button>导出</el-button>
                    </div>
                </template>
                <el-table-column prop="status" label="出操">
                  <template v-slot="scope">
                    <el-tag type="warning" v-if="scope.row.status == 1">不出</el-tag>
                    <el-tag type="success" v-else>出操</el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="remark" label="备注">
                  <template v-slot="scope">
                    <span v-if="scope.row.remark == '' || scope.row.remark == null">无</span>
                    <span v-else>{{ scope.row.remark }}</span>
                  </template>
                </el-table-column>
            </el-table-column>
        </el-table>
    </div>
</template>
<script>
  import axios from 'axios';
  export default {
    data() {
      return {
        tableData: [
          // {
          //   date: '2016-05-02',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1518 弄',
          // },
          // {
          //   date: '2016-05-04',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1517 弄',
          // },
          // {
          //   date: '2016-05-01',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1519 弄',
          // },
          // {
          //   date: '2016-05-03',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1516 弄',
          // },
          // {
          //   date: '2016-05-04',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1517 弄',
          // },
          // {
          //   date: '2016-05-01',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1519 弄',
          // },
          // {
          //   date: '2016-05-03',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1516 弄',
          // },
          // {
          //   date: '2016-05-04',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1517 弄',
          // },
          // {
          //   date: '2016-05-01',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1519 弄',
          // },
          // {
          //   date: '2016-05-03',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1516 弄',
          // },
          // {
          //   date: '2016-05-04',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1517 弄',
          // },
          // {
          //   date: '2016-05-01',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1519 弄',
          // },
          // {
          //   date: '2016-05-03',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1516 弄',
          // },
          // {
          //   date: '2016-05-04',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1517 弄',
          // },
          // {
          //   date: '2016-05-01',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1519 弄',
          // },
          // {
          //   date: '2016-05-03',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1516 弄',
          // },
        ],
        selectValue: '出操',
        option: [
          {
            label: '出操'
          },
          {
            label: '不出操'
          }
        ]
      }
    },
    computed: {
      filterData() {
        if(this.selectValue == '出操') {
          return this.tableData.filter(item => item.status == 0)
        } else if(this.selectValue == '不出操') {
          return this.tableData.filter(item => item.status == 1)
        } else {
          return this.tableData
        }
      }
    },
    created() {
      this.getAllRecords()
    },
    methods: {
      getAllRecords() {
        const url = '/api/run/getAllRecords'
        axios.post(url, {}, {
          headers: {
            'verifyCode': '2024'
          }
        }).then(res => {
          this.tableData = res.data
        })
      }
    }
  }
</script>