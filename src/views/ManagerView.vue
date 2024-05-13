<template>
    <div class="managerPage">
        <HeadNav />
        <div style="display: flex;justify-content: space-between;">
            <SideMenu class="sideMenu" @link-to="handleLinkTo"/>
            <div class="mainArea">
                <!-- <h2>你好2122345</h2> -->
                <PersonalPage v-if="currentIndex == '1'"/>
                <AccountPage v-if="currentIndex == '2'"/>
                <RunNotice v-if="currentIndex == '3-1'"/>
                <RunRecord v-if="currentIndex == '3-2'"/>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios'

import HeadNav from './module/HeadNav'
import SideMenu from './module/manager/SideMenu'
import PersonalPage from './module/manager/PersonalPage'
import AccountPage from './module/manager/AccountPage'
import RunNotice from './module/manager/RunNotice'
import RunRecord from './module/manager/RunRecord'
export default {
    components: {
        SideMenu,
        HeadNav,
        PersonalPage,
        AccountPage,
        RunNotice,
        RunRecord
    },
    data() {
        return {
            currentIndex: ''
        }
    },
    created() {
        if(localStorage.getItem("currentPage") == '3-1') {
            this.getIndex()
        } else {
            this.currentIndex = localStorage.getItem("currentPage")
        }
        this.verifyLogin()
    },
    methods: {
        verifyLogin() {
            const url = '/api/account/verify'
            const params = {
                account: localStorage.getItem('account'),
                token: localStorage.getItem('token')
            }
            axios.post(`${url}?account=${params.account}&token=${params.token}`, {} ,
                {
                    headers: {
                        'verifyCode': '2024'
                    }
                }
            ).then(res => {
                if(res.data == 'Valid identity') {
                    console.log('身份有效')
                } else {
                    localStorage.clear()
                    this.$router.push({
                        path: '/login'
                    })
                    this.$message({
                        message: '身份失效，请重新登录',
                        type: 'warning'
                    });
                }
            }).catch(err => {
                this.$message({
                    message: err,
                    type: 'warning'
                });
            })
        },
        getIndex() {
            const index = localStorage.getItem("currentPage")
            this.currentIndex = index;
            console.log(this.currentIndex)
        },
        // eslint-disable-next-line
        handleLinkTo(index) {
            // this.currentIndex = index;
            console.log(this.currentIndex)
            this.getIndex()
        }
    },
}
</script>
<style>
.managerPage {
    height: 100vh;
    /* background: url(@/assets/img/110503466_p0_master1200.jpg) 50% no-repeat; */
    background: url(@/assets/img/bac.jpg) 50% no-repeat;
    background-size: cover;
}

.managerPage .mainArea {
    margin: 6px;
    width: calc(100% - 12px);
    height: calc(100vh - 60px - 12px);
    background-color: rgba(255, 255, 255, 0.6);
    overflow-y: auto;
}
</style>