<template>
    <div class="add-account">
        <el-select @change="recordDepart(input.depart)" v-model="input.depart" placeholder="部门">
            <el-option
            v-for="item in option" :key="item.label" :label="item.label" :value="item.label"></el-option>
        </el-select>
        <el-input disabled v-model="input.account" placeholder="账号"></el-input>
        <el-input @input="recordName(input.name)" v-model="input.name" placeholder="姓名"></el-input>
        <el-input v-model="input.mail" @input="updateAccount(input.mail)" placeholder="邮箱"></el-input>
    </div>
</template>

<script>
export default {
    data() {
        return {
            input: {
                depart: '',
                account: '',
                name: '',
                mail: ''
            },
            selectValue: '',
            option: [
                {
                    label: '互联网社'
                },
                {
                    label: '阳光办'
                }
            ]
        }
    },
    methods: {
        updateAccount(value) {
            localStorage.setItem('addMail', value)
            if (value) {
                this.input.account = value.split('@')[0];
                localStorage.setItem('addAccount', value.split('@')[0])
            } else {
                this.input.account = '';
            }
        },
        recordDepart(value) {
            localStorage.setItem('addDepart', value)
        },
        recordName(value) {
            localStorage.setItem('addName', value)
        }
    }
}
</script>

<style scoped>
.add-account .el-input, .el-select {
    margin: 6px 0;
}
</style>
