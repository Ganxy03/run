<template>
    <div class="edit-account">
        <el-select v-model="input.depart" placeholder="部门">
            <el-option
            v-for="item in option" :key="item.label" :label="item.label" :value="item.label"></el-option>
        </el-select>
        <el-input disabled v-model="input.account" placeholder="账号"></el-input>
        <el-input v-model="input.name" placeholder="姓名"></el-input>
        <el-input disabled v-model="input.mail" placeholder="邮箱"></el-input>
        <!-- <el-select disabled placeholder="身份">
            
        </el-select> -->
        <el-input disabled v-model="input.role">
        </el-input>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    data() {
        return {
            input: {
                depart: '',
                account: '',
                name: '',
                mail: '',
                role: '管理员'
            },
            accountInfo: [],
            selectValue: '',
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
    created() {
        this.getInfo()
    },
    methods: {
        getInfo() {
            const url = '/api/account/getInfo'
            axios.post(`${url}?account=${localStorage.getItem('eidtAccount')}`, {}, {
                headers: {
                    'verifyCode': '2024'
                }
            }).then(res => {
                this.input = res.data

                if(res.data.role == 1) {
                    this.input.role = '超级管理员'
                } else if(res.data.role == 0) {
                    this.input.role = '普通管理员'
                }
            })
        }
    }
}
</script>

<style scoped>
.edit-account .el-input, .el-select {
    margin: 6px 0;
}

</style>
