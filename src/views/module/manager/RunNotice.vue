<template>
    <div class="notice-page">
        <h2>晨跑公告</h2>
        <div class="box">
            <div class="leftBox">
                <div class="notice">
                    <div style="display: flex;justify-content: flex-end;">
                        <el-button>编辑公告</el-button>
                    </div>
                    <h3>{{ currentTime }}</h3>
                    <!-- Remark operate -->
                    <div @click="operate" class="circle">
                        <h3 v-if="todayStatus.status == 0">出操</h3>
                        <h3 v-if="todayStatus.status == 1">不出</h3>
                    </div>

                    <div>
                        <h4>操作日志</h4>
                        <ul style="overflow-y: auto;overflow-x: auto;height: 60px;padding-left: 18px;text-align: left">
                            <li v-for="item in allOperates" :key="item.id">
                                <i>{{item.operator}}</i> - <b v-if="item.result == 0">出操</b><b v-else>不出</b> <small>{{ item.time }}</small>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="comment">
                    <h3 style="text-align: left">评论审核</h3>
                    <el-table :data="filteredTableData" height="360">
                        <el-table-column prop="ip" label="IP"> </el-table-column>
                        <el-table-column>
                            <template v-slot:header>
                                <div style="display: flex;justify-content: space-between;">
                                    <el-select v-model="selectValue" placeholder="选择状态">
                                        <el-option v-for="item in option" :key="item.label" :label="item.label" :value="item.label"></el-option>
                                    </el-select>
                                    <el-button>导出</el-button>
                                </div>
                            </template>
                            <el-table-column prop="goal" label="回复"></el-table-column>
                            <el-table-column prop="content" label="内容"></el-table-column>
                            <el-table-column prop="status" label="状态">
                                <template v-slot="{ row }">
                                    <el-tag type="info" v-if="row.status == 0">待审核</el-tag>
                                    <el-tag type="success" v-if="row.status == 1">通过</el-tag>
                                    <el-tag type="danger" v-if="row.status == 2">不通过</el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column label="操作">
                                <div style="display: flex;justify-content: flex-start;">
                                    <el-button>查看</el-button>
                                    <el-button>操作</el-button>
                                </div>
                            </el-table-column>
                        </el-table-column>
                        
                    </el-table>
                </div>
            </div>
            <div class="rightBox">
                <el-empty description="暂无内容.."></el-empty>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios'
export default {
    props: {
        row: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            tableData: [
                // {
                //     date: '2016-05-02',
                //     name: '王小虎',
                //     address: '上海市普陀区金沙江路 1518 弄',
                // },
            ],
            currentTime: '',
            todayStatus: {},
            result: 0,
            allOperates: [],
            reason: '',
            selectValue: '通过',
            option: [
                {
                    label: '待审核'
                },
                {
                    label: '通过'
                },
                {
                    label: '不通过'
                }
            ],
        }
    },
    
    computed: {
        filteredTableData() {
            if(this.selectValue == '待审核') {
                return this.tableData.filter(item => item.status === 0);
            } else if(this.selectValue == '通过') {
                return this.tableData.filter(item => item.status === 1);
            } else if(this.selectValue == '不通过') {
                return this.tableData.filter(item => item.status === 2);
            } else {
                return this.tableData;
            }
            // if (this.isLoading) {
            //     return [];
            // }
            // return this.tableData.filter(item => item.status === this.selectValue);
        }
    },
    created() {
        this.updateTime();
        this.timer = setInterval(() => {
            this.updateTime();
        }, 1000);
        this.getStatus()
        this.getAllOperates()
        this.getAllMessage()
        this.getAllReplies()
    },
    unmounted() {
        clearInterval(this.timer);
    },
    methods: {
        getAllReplies() {
            const url = '/api/message/getAllReplies'
            axios.post(url, {}, {
                headers: {
                    'verifyCode': '2024'
                }
            }).then(res => {
                this.tableData = this.tableData.concat(res.data)

                console.log(this.tableData)
            })
        },
        getAllMessage() {
            const url = '/api/message/getAllBroads'
            axios.post(url, {}, {
                headers: {
                    'verifyCode': '2024'
                }
            }).then(res => {
                this.tableData = res.data
            })
        },
        Remark() {
            this.$prompt('请备注不跑操原因', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(({ action, value }) => {
                if(action == 'confirm') {
                    // console.log(value)
                    if(value == '' || value == null) {
                        this.$message({
                            message: '备注不能为空',
                            type: 'warning'
                        })
                        return
                    }
                    this.reason = value

                    const url = '/api/run/remark'
                    axios.post(`${url}?reason=${this.reason}`, {}, {
                        headers: {
                            'verifyCode': '2024'
                        }
                    }).then(res => {
                        if(res.data == "Remark success") {
                            this.changeStatus()
                            this.$message({
                                message: '备注成功',
                                type: 'success'
                            })
                        }
                    })
                }
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '取消输入'
                });       
            });
            
        },
        changeStatus() {
            this.getStatus()
            if(this.result == 0) {
                const reason = '今天晨跑: 信工6:50前 人文..'
                const url2 = '/api/run/remark'
                axios.post(`${url2}?reason=${reason}`, {}, {
                    headers: {
                        'verifyCode': '2024'
                    }
                }).then(res => {
                    console.log(res.data)
                })
            } 
            // else if(this.todayStatus.result == 0) {
            //     const reason = 'null'
            //     const url2 = '/api/run/remark'
            //     axios.post(`${url2}?reason=${reason}`, {}, {
            //         headers: {
            //             'verifyCode': '2024'
            //         }
            //     }).then(res => {
            //         console.log(res.data)
            //     })
            // }
            

            const url = '/api/run/operate'
            axios.post(`${url}?id=${localStorage.getItem('todayId')}&operator=${localStorage.getItem('account')}&result=${this.result}`, {}, {
                headers: {
                    'verifyCode': '2024'
                }
            }).then(res => {
                if(res.data == 'Operate success') {
                    this.getStatus()
                    this.getAllOperates()
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    })
                }
            })
        },
        getAllOperates() {
            const url = '/api/run/getAllOperates'
            axios.post(url, {}, {
                headers: {
                    'verifyCode': '2024'
                }
            }).then(res => {
                this.allOperates = res.data
            })
        },
        operate() {
            this.$confirm('此操作将更改晨跑状态, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                callback: (action) => {
                    if(action === 'confirm') {
                        if(this.todayStatus.status == 0) {
                            this.result = 1
                            this.Remark()
                            
                        } else {
                            this.result = 0
                            this.changeStatus()
                        }
                       
                    } else {
                        this.$message({
                            message: '操作取消',
                            type: 'info'
                        })
                    }
                },
            })
        },
        getStatus() {
            const url = '/api/run/getStatus'
            axios.post(url, {}, {
                headers: {
                    'verifyCode': '2024'
                }
            }).then(res => {
                console.log(res.data)
                localStorage.setItem('todayId', res.data.id)
                this.todayStatus = res.data
            })
        },
        updateTime() {
            const now = new Date();
            const year = now.getFullYear();
            const month = (now.getMonth() + 1).toString().padStart(2, '0');
            const day = now.getDate().toString().padStart(2, '0');
            const hours = now.getHours().toString().padStart(2, '0');
            const minutes = now.getMinutes().toString().padStart(2, '0');
            const seconds = now.getSeconds().toString().padStart(2, '0');
            this.currentTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        },
    }
}
</script>

<style>
.box {
    display: flex;
}
.notice-page .leftBox,.notice-page .rightBox {
    height: calc(100vh - 12px - 60px - 31.33px);
    /* background-color: aqua; */
}
.notice-page .leftBox {
    width: 40%;
}
.notice-page .rightBox {
    width: 60%;
}

.notice {
    height: 40vh;
    /* background-color: blueviolet; */
    padding: 12px;
}
.notice .circle {
    height: 200px;
    width: 200px;
    border-radius: 50%;
    margin: 3px auto;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 32px;
    background: linear-gradient(to right, rgba(58, 141, 219, 0.6), rgba(211, 172, 211, 1));
    cursor: pointer;


}

.comment {
    height: 40vh;
    padding: 6px 0;
    /* background-color: rgb(43, 171, 226); */
}
@media screen and (max-width: 1080px) {
    .notice-page .box {
        display: block;
    }
    .notice-page .leftBox,.notice-page .rightBox {
        width: 100%;
    }
}
</style>