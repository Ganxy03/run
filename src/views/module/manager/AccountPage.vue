<template>
    <div class="account-page">
        <h2>账号管理</h2>
        <el-table :data="filterData" height="600">
            <el-table-column prop="depart" label="部门"> </el-table-column>
            <el-table-column>
                <template v-slot:header>
                    <div style="display: flex;justify-content: space-between;">
                        <el-select v-model="selectValue" placeholder="选择部门">
                          <el-option
                          v-for="item in option" :key="item.label" :label="item.label" :value="item.label"></el-option>
                        </el-select>
                        <el-button @click="openAddDialog">添加</el-button>
                    </div>
                </template>
                <el-table-column prop="account" label="账号"> </el-table-column>
                <el-table-column prop="name" label="姓名"> </el-table-column>
                <el-table-column prop="mail" label="邮箱"> </el-table-column>
                <el-table-column label="操作">
                    <template v-slot:default="{ row }">
                      <div style="display: flex;justify-content: flex-start;">
                        <el-button @click="openEditDialog(row)">编辑</el-button>
                        <el-button @click="delBtn">删除</el-button>
                      </div>
                    </template>
                </el-table-column>
            </el-table-column>
        </el-table>
    </div>
</template>
<script>
import axios from 'axios';
import { h } from 'vue'

import EditAccount from './module/EditAccount'
import AddAccount from './module/AddAccount'
  export default {
    components: {
      // eslint-disable-next-line
      EditAccount
    },
    data() {
      return {
        tableData: [
          // {
          //   date: '2016-05-02',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1518 弄',
          // },
        ],
        selectValue: '阳光办',
        option: [
          {
            label: '阳光办'
          },
          {
            label: '互联网社'
          }
        ]
      }
    },
    computed: {
      filterData() {
        return this.tableData.filter(item => item.depart == this.selectValue)
      }
    },
    created() {
      this.getAllAccount()
    },
    methods: {
      addOneAccount() {
        const url = '/api/account/create'
        axios.post(`${url}?account=${localStorage.getItem('addAccount')}`, {}, {
          headers: {
            'verifyCode': '2024'
          }
        }).then(res => {
          if(res.data == 'Create success') {
            this.updateInfo()
            localStorage.removeItem('addName')
            localStorage.removeItem('addDepart')
            localStorage.removeItem('addAccount')
            localStorage.removeItem('addMail')
            this.$message({
              type: 'success',
              message: '添加成功'
            })
          } else {
            localStorage.removeItem('addName')
            localStorage.removeItem('addDepart')
            localStorage.removeItem('addAccount')
            localStorage.removeItem('addMail')
            this.$message({
              type: 'warning',
              message: '账号已存在'
            })
          }
        })
      },
      updateInfo() {
        const url = '/api/account/editInfo'
        axios.post(`${url}?account=${localStorage.getItem('addAccount')}&depart=${localStorage.getItem('addDepart')}&name=${localStorage.getItem('addName')}&mail=${localStorage.getItem('addMail')}`, {}, {
          headers: {
            'verifyCode': '2024'
          }
        }).then(res => {
          // if(res.data = 'Edit success') {
          //   //
          // }
          this.getAllAccount()
          console.log(res.data)
        })
      },
      openAddDialog() {
        this.$msgbox({
          title: '创建账户',
          message: h('div', null, [
            h(AddAccount)
          ]),
          showCancelButton: false,
          closeOnClickModal: false,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          callback: (action) => {
            if(action == 'confirm') {

              this.addOneAccount()

              // this.$message({
              //   type: 'success',
              //   message: '确认'
              // })
            } else if(action == 'cancel') {
              localStorage.removeItem('eidtAccount')
              localStorage.removeItem('addName')
              localStorage.removeItem('addDepart')
              localStorage.removeItem('addAccount')
            localStorage.removeItem('addMail')
              this.$message({
                type: 'warning',
                message: '取消'
              })
            }
          }
         }).then((action) => {
          this.$message({
            type: 'warning',
            message: 'action: ' + action,
          })
        })
      },
      openEditDialog(item) {
        localStorage.setItem('eidtAccount', item.account)
        this.$msgbox({
          title: '修改信息',
          message: h('div', null, [
            h(EditAccount)
          ]),
          showCancelButton: false,
          closeOnClickModal: false,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          callback: (action) => {
            if(action == 'confirm') {
              localStorage.removeItem('eidtAccount')
              this.$message({
                type: 'success',
                message: '确认'
              })
            } else if(action == 'cancel') {
              localStorage.removeItem('eidtAccount')
              this.$message({
                type: 'warning',
                message: '取消'
              })
            }
          }
         }).then((action) => {
          this.$message({
            type: 'warning',
            message: 'action: ' + action,
          })
        })
      },
      delBtn() {
        this.$message({
          message: '没有权限',
          type: 'warning'
        })
      },
      getAllAccount() {
        const url = '/api/account/getAll'
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