<template>
    <div class="personal-page">
        <h2>个人中心</h2>
        <div class="content">
            <div class="info">
                <div class="base">
                    <img :src="avatarURL" alt="">
                    <div class="intro">
                        <div style="display: flex;justify-content: center;">
                            <h3>{{ accountInfo.account }}</h3>
                            <el-icon style="cursor: pointer;margin-top: 24px;">
                                <Edit />
                            </el-icon>
                        </div>
                        <h3><small>管理员</small></h3>
                        <p>创建时间：NaN</p>
                    </div>
                </div>
                <div class="secure">
                    <el-table
                        height="250"
                        :data="tableData"
                        style="width: 100%"
                        :default-sort="{prop: 'date', order: 'descending'}"
                    >
                        <el-table-column prop="date" label="日期" sortable>
                        </el-table-column>
                        <el-table-column>
                            <template v-slot:header>
                                <h2 style="text-align: center">登录历史</h2>
                            </template>
                            <el-table-column prop="name" label="IP">
                            </el-table-column>
                            <el-table-column prop="address" label="地址" :formatter="formatter">
                            </el-table-column>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
            <div class="opera">
                <el-tabs v-model="showNav"  @tab-click="handleClick">
                    <el-tab-pane class="showForm" label="基本信息" name="intro">
                        <el-form :data="accountInfo" ref="form" label-width="60px">
                            <el-form-item label="账号">
                                <el-input disabled v-model="accountInfo.account"></el-input>
                            </el-form-item>
                            <el-form-item label="姓名">
                                <el-input disabled v-model="accountInfo.name"></el-input>
                            </el-form-item>
                            <el-form-item label="部门">
                                <el-select v-model="accountInfo.depart" disabled placeholder="请选择部门">
                                    <el-option label="阳光办" value="beijing"></el-option>
                                    <el-option label="互联网社" value="shanghai"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="邮箱">
                                <el-input v-model="accountInfo.mail" disabled></el-input>
                            </el-form-item>
                        </el-form>
                    </el-tab-pane>
                    <el-tab-pane class="showForm" label="修改密码">
                        <el-input v-model="pwd.old" placeholder="输入旧密码"></el-input>
                        <el-input v-model="pwd.new" placeholder="输入新密码"></el-input>
                        <div style="display: flex;justify-content: flex-end;">
                            <el-button @click="changePwd">修改</el-button>
                        </div>
                    </el-tab-pane>
                </el-tabs>
                <!-- <ul class="smallNav">
                    <li @click="navTo('intro')">基本信息</li>
                    <li @click="navTo('pwd')">修改密码</li>
                </ul>
                <div class="showForm" v-if="showNav == 'intro'">
                    <el-form ref="form" label-width="60px">
                        <el-form-item label="姓名">
                            <el-input ></el-input>
                        </el-form-item>
                        <el-form-item label="部门">
                            <el-select placeholder="请选择部门">
                            <el-option label="阳光办" value="beijing"></el-option>
                            <el-option label="互联网社" value="shanghai"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>
                </div>
                <div class="showForm" v-if="showNav == 'pwd'">
                    <el-input placeholder="输入旧密码"></el-input>
                    <el-input placeholder="输入新密码"></el-input>
                    <div style="display: flex;justify-content: flex-end;">
                        <el-button>修改</el-button>
                    </div>
                </div> -->
            </div>
        </div>
    </div>
</template>
<script>
import { Edit } from '@element-plus/icons-vue'
import axios from 'axios';
export default {
    components: {
        Edit,
    },
    data() {
        return {
            avatarURL: 'http://ganxy03.cn/img/dog.jpg',
            tableData: [
                {
                    date: '2016-05-02',
                    name: '127.0.0.1',
                    address: '上海市普陀区金沙江路 1518 弄',
                },
                {
                    date: '2016-05-04',
                    name: '127.0.0.1',
                    address: '上海市普陀区金沙江路 1517 弄',
                },
                {
                    date: '2016-05-01',
                    name: '127.0.0.1',
                    address: '上海市普陀区金沙江路 1519 弄',
                },
                {
                    date: '2016-05-03',
                    name: '127.0.0.1',
                    address: '上海市普陀区金沙江路 1516 弄',
                },
                {
                    date: '2016-05-03',
                    name: '127.0.0.1',
                    address: '上海市普陀区金沙江路 1516 弄',
                },
                {
                    date: '2016-05-03',
                    name: '127.0.0.1',
                    address: '上海市普陀区金沙江路 1516 弄',
                },
                {
                    date: '2016-05-03',
                    name: '127.0.0.1',
                    address: '上海市普陀区金沙江路 1516 弄',
                },
                {
                    date: '2016-05-03',
                    name: '127.0.0.1',
                    address: '上海市普陀区金沙江路 1516 弄',
                },
            ],
            showNav: 'intro',
            accountInfo: [],
            pwd: {
                old: '',
                new: ' '
            }
        }
    },
    created() {
        this.getInfo()
    },
    methods: {
        // eslint-disable-next-line
        formatter(row, column) {
            return row.address
        },
        navTo(params) {
            this.showNav = params
        },
        getInfo() {
            const url = '/api/account/getInfo'
            axios.post(`${url}?account=${localStorage.getItem('account')}`, {}, {
                headers: {
                    'verifyCode': '2024'
                }
            }).then(res => {
                this.accountInfo = res.data
            })
        },
        changePwd() {
            if(this.pwd.old == '') {
                this.$message({
                    message: '请输入旧密码',
                    type: 'warning'
                })
                return
            } else if(this.pwd.new == '') {
                this.$message({
                    message: '请输入新密码',
                    type: 'warning'
                })
            } else {
                const url = '/api/account/rePwd'
                axios.post(`${url}?account=${localStorage.getItem('account')}&old=${this.pwd.old}&pwd=${this.pwd.new}`, {}, {
                    headers: {
                        'verifyCode': '2024'
                    }
                }).then(res => {
                    if(res.data == 'Change success') {
                        this.pwd = ''
                        this.$message({
                            message: '修改成功',
                            type: 'success'
                        })
                    } else {
                        this.$message({
                            message: '密码错误',
                            type: 'warning'
                        })
                    }
                })
            }
        }
    }
}
</script>
<style>
.personal-page .content {
    display: flex;
    justify-content: space-between;
}
.personal-page .info {
    width: 540px;
    height: 50vh;
    /* background-color: rgba(255, 255, 255, 0.6); */
    margin: 0;
    padding: 12px;
}
.personal-page .info .base {
    display: flex;
    justify-content: space-between;
}
.personal-page .info img {
    width: 200px;
    border-radius: 50%;
}
.personal-page .info .intro {
    width: calc(100% - 200px);
    padding-top: 32px;
    text-align: center;
}
.personal-page .info h3 {
    font-size: 32px;
    margin: 8px 0;
    text-align: center;
}


.personal-page .opera {
    padding: 12px;
    width: calc(100% - 540px);
    height: calc(100vh - 60px - 12px - 31.33px - 48px - 24px);
    /* background-color: aqua; */
    padding-top: 48px;
}
.personal-page .opera .smallNav {
    display: flex;
    justify-content: flex-start;
    padding: 0;
}
.personal-page .opera .smallNav li {
    list-style: none;
    margin: 0 12px;
    cursor: pointer;
}
.personal-page .opera .showForm {
    width: calc(100% - 24px);
    height: 40vh;
    background-color: rgba(255, 255, 255, 0.6);
    padding: 12px;
}
.personal-page .opera .showForm .el-input {
    margin: 6px 0;
}
@media screen and (max-width: 1080px) {
    .personal-page .content {
        display: block;
    }
    .personal-page .info, .personal-page .opera {
        width: calc(100% - 24px);
    }
    .personal-page .info img {
        width: 120px;
    }
    .personal-page .info {
        height: 50vh;
        overflow-y: auto;
    }
    .personal-page .base {
        overflow-x: auto;
    }
    .personal-page .info .intro {
        width: 200px;
        padding-top: 0;
    }
    .personal-page .info .secure {
        margin-top: 24px;
    }
}
</style>