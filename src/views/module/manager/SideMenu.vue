<template>
    <div class="sideMenu">
        <el-menu
        :default-active="currentIndex"
        class="el-menu-vertical-demo"
        :collapse="isCollapse"
        >
            
            <el-radio-group style="display: flex;justify-content: flex-start;margin-left: 8px" v-model="isCollapse">
                <el-radio-button :label="false">展开</el-radio-button>
                <el-radio-button :label="true">收起</el-radio-button>
            </el-radio-group>

            <el-menu-item @click="linkTo('1')" index="1">
                <el-icon>
                    <User />
                </el-icon>
                <template #title>个人中心</template>
            </el-menu-item>
            <el-menu-item @click="linkTo('2')" index="2">
                <el-icon>
                    <Finished />
                </el-icon>
                <template #title>账号管理</template>
            </el-menu-item>
            <el-sub-menu index="3">
                <template #title>
                    <el-icon>
                        <DataLine />
                    </el-icon>
                    <span>晨跑</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item @click="linkTo('3-1')" index="3-1">晨跑公告</el-menu-item>
                    <el-menu-item @click="linkTo('3-2')" index="3-2">晨跑记录</el-menu-item>
                </el-menu-item-group>
                <!-- <el-sub-menu index="1-3">
                    <template #title>更多功能..</template>
                    <el-menu-item index="1-4-1">待开发</el-menu-item>
                </el-sub-menu> -->
            </el-sub-menu>
            <el-menu-item @click="linkTo('4')" index="4" disabled>
                <el-icon>
                    <PieChart />
                </el-icon>
                <template #title>数据统计</template>
            </el-menu-item>
        </el-menu>
        <!-- <el-radio-group style="float: left;" v-model="isCollapse">
            <el-radio-button :label="false">展开</el-radio-button>
            <el-radio-button :label="true">收起</el-radio-button>
        </el-radio-group> -->
    </div>
</template>

<script>
import { User, Finished, DataLine, PieChart } from '@element-plus/icons'
export default {
    components: {
        User,
        Finished,
        DataLine,
        PieChart
    },
    mounted() {
        if(localStorage.getItem("currentPage") == null) {
            localStorage.setItem("currentPage", "3-1")
            this.currentIndex = '3-1'
        } else {
            this.currentIndex = localStorage.getItem("currentPage")
        }
    },
    data() {
        return {
            isCollapse: true,
            currentIndex: ''
        }
    },
    methods: {
        linkTo(index) {
            console.log(index)
            localStorage.setItem("currentPage", index)
            this.$emit('link-to', index);
        }
    },
}
</script>
<style>
.sideMenu .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    height: calc(100vh - 60px);
}

.sideMenu .el-menu--vertical {
    height: calc(100vh - 60px);
}
</style>