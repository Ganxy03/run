<template>
    <div class="messagePage">
        
        <div class="info">
            <p>Halo 来刷存在感了</p>
            <p>这里当吐槽板吧</p>
            <p>有什么建议 或者 有什么问题都在下面留言</p>
            <p>不允许灌水 诽谤 或者人身攻击哦</p>
            <p>我们会顺着网线来揍你的 &lt;(｀^´) &gt;</p>
            <div class="end">
                <p @click="test" style="text-align: right;">互联网社</p>
                <p style="text-align: right;">2024-05-01</p>
            </div>
        </div>

        <div class="messageBoard">
            <el-input v-model="input" type="textarea" placeholder="请输入留言内容" maxlength="100" show-word-limit></el-input>
            <div style="display: flex;justify-content: space-between;">
                <span style="font-size: 11px;line-height: 24px;">觉得不错 留个言鼓励一下叭~</span>
                <el-button @click="postBtn" style="margin-top: 6px;" type="warning" plain>发布</el-button>
            </div>

            <div class="list">
                <h2>留言列表</h2>
                <!-- <div style="display: flex;justify-content: flex-end;margin: 3px 12px 0 0">
                    <span style="font-size: 13px;">按时间: </span>
                    <span class="sortBtn">正序</span>
                    <span class="sortBtn">倒序</span>
                </div> -->
                <ul>
                    <el-empty v-if="messageBroad == '' || messageBroad == null"></el-empty>
                    <li v-for="item in filderItem" :key="item.id">
                        <p class="info">
                            <img src="@/assets/img/avatar.png" alt="">
                            <span style="display: flex;justify-content: space-between;width: calc(100% - 30px);">
                                <span>#{{ item.id }} 匿名同学<small>({{ item.ip }})</small></span>
                                <span style="font-size: 13px;">{{ item.time }}</span>
                            </span>
                        </p>
                        <p class="content">
                            <span>{{ item.content }}</span>
                            <el-button @click="replayTo(item)" type="text">回复</el-button>
                        </p>
                        <div>
                            <ul style="margin: 0 0 0 30px;font-size: 13px;padding: 0;line-height: 20px;">
                                <div>
                                    <li v-for="(item, index) in filteredReplies[item.id]" :key="index" style="margin: 2px 0;">
                                        <div>
                                            <div style="display: flex;">
                                                <img style="width: 20px;" src="@/assets/img/avatar.png" alt="">
                                                <div style="display: flex;justify-content: space-between;width: calc(100% - 20px);">
                                                    <span>#{{ item.id }} 匿名同学<small>({{ item.ip }})</small></span>
                                                    <span style="font-size: 11px;">{{ item.time }}</span>
                                                </div>
                                            </div>
                                        </div>
                                        <p style="margin: 0 0 0 20px">{{ item.content }}</p>
                                    </li>
                                </div>
                                <!-- <li style="margin: 2px 0;">
                                    <div style="display: flex;">
                                        <img style="width: 20px;" src="@/assets/img/avatar.png" alt="">
                                        <div style="display: flex;justify-content: space-between;width: calc(100% - 20px);">
                                            <span>#2 匿名同学<small>(158.55.215.23)</small></span>
                                            <span style="font-size: 11px;">2022-05-10 12:00:00</span>
                                        </div>
                                    </div>
                                    <p style="margin: 0 0 0 20px">1245855</p>
                                </li> -->
                            </ul>
                        </div>
                        <div v-if="item.id == showReplyArea" class="replyArea">
                            <el-input v-model="input2" type="textarea" :placeholder="'回复 @#' + item.id + ' 匿名同学(' + item.ip + ')'" maxlength="100" show-word-limit></el-input>
                            <div class="replyBtn">
                                <el-button @click="replayBtn(item)" type="warning" plain>回复</el-button>
                            </div>
                        </div>
                    </li>
                </ul>

                <div style="margin: 12px 0;">
                    <el-pagination
                    style="width: 100%;
                    display: flex;
                    justify-content: space-between;
                    overflow-x: auto;
                    background-color: transparent !important;"
                    layout="jumper, pager" 
                    :page-size="pageSize" 
                    :total="totalNum"
                    @current-change="handlePageChange"></el-pagination>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    data() {
        return {
            input: '',
            input2: '',
            currentPage: 1,
            showReplyArea: null,
            messageBroad: [
                // {
                //     id: 1,
                //     ip: '124.221.138.245',
                //     content: '不错',
                //     time: '2024-05-06 09:09:52'
                // },
                // {
                //     id: 2,
                //     ip: '124.221.138.245',
                //     content: '可以',
                //     time: '2024-05-06 09:11:02'
                // },
            ],
            messageReply: [],
            pageSize: 10,
            totalNum: 0
        }
    },
    computed: {
        // 计算当前页的条目
        filderItem() {
            const start = (this.currentPage - 1) * this.pageSize;
            const end = this.currentPage * this.pageSize;
            //return this.messageBroad.slice(start, end);
            return this.messageBroad.filter(item => item.status === 1).slice(start, end);
        },
        filteredReplies() {
            // const repliesGroupedByGoal = this.messageReply.reduce((acc, reply) => {
            // if (!acc[reply.goal]) {
            //     acc[reply.goal] = [];
            // }
            // acc[reply.goal].push(reply);
            // return acc;
            // }, {});
            // return repliesGroupedByGoal;
            const repliesGroupedByGoal = this.messageReply.reduce((acc, reply) => {
                if (reply.status === 1) {
                    if (!acc[reply.goal]) {
                        acc[reply.goal] = [];
                    }
                    acc[reply.goal].push(reply);
                }
                return acc;
            }, {});
            return repliesGroupedByGoal;
        },
    },
    created() {
        this.getAllBroads()
        this.getAllReply()
    },
    methods: {
        test() {
            console.log(this.filteredReplies);
        },
        getAllReply() {
            const url = '/api/message/getAllReplies'
            axios.post(url, {}, {
                headers: {
                    'verifyCode': '2024'
                }
            }).then(res => {
                this.messageReply = res.data
            })
        },
        async getAllBroads() {
            const url = '/api/message/getAllBroads'
            axios.post(url, {}, {
                headers: {
                    'verifyCode': '2024'
                }
            }).then(res => {
                this.messageBroad = res.data

                this.totalNum = res.data.filter(item => item.status === 1).length
            })
        },
        replayTo(item) {
            // console.log(item)
            if(this.showReplyArea == null) {
                this.showReplyArea = item.id
            } else if(this.showReplyArea != item.id) {
                this.showReplyArea = item.id
            } else {
                this.showReplyArea = null
            }
        },
        replayBtn(item) {
            console.log(item)
            if(item.content == '') {
                this.$message({
                    message: '请输入回复内容',
                    type: 'warning'
                })
            } else {
                const url = '/api/message/reply'
                axios.post(`${url}?content=${this.input2}&goal=${item.id}`, {}, {
                    headers: {
                        'verifyCode': '2024'
                    }
                }).then(res => {
                    // console.log(res.data)
                    if(res.data == 'Reply success') {
                        this.input2 = ''
                        this.getAllReply()
                        this.$message({
                            message: '回复成功 内容审核后展示',
                            type: 'success'
                        })
                    }
                })
            }
        },
        postBtn() {
            if(this.input == '') {
                this.$message({
                    message: '请输入内容',
                    type: 'warning'
                })
            } else {
                const url = '/api/message/post'
                axios.post(`${url}?content=${this.input}`, {}, {
                    headers: {
                        'verifyCode': '2024'
                    }
                }).then(res => {
                    if(res.data == 'Post success') {
                        this.input = ''
                        this.getAllBroads()
                        this.$message({
                            message: '留言成功 内容审核后展示',
                            type: 'success'
                        })
                    }
                })
            }
        },
        handlePageChange(newPage) {
            this.currentPage = newPage;
        },
    }
}
</script>

<style>
.messagePage {
    position: relative;
}
.info {
    margin-top: 24px;
}

.info p {
    font-size: 16px;
    margin: 3px 0;
}
.end {
    width: 76%;
}

.messageBoard {
    width: 76%;
    margin: 0 auto;
}
.list {
    text-align: left;
}
.list h2 {
    padding: 24px 0 12px 0;
    border-bottom: 1px solid #fff;
}
.list .sortBtn {
    font-size: 13px;
    margin: 0 3px;
    cursor: pointer;
}
.list .sortBtn:hover {
    color: #418ce8;
}
.list ul {
    padding: 0 6px;
    list-style: none;
}
.list .info {
    margin-top: 6px;
    display: flex;
    justify-content: space-between;
    line-height: 30px;
}
.list img {
    width: 30px;
}
.list .info span {
    line-height: 30px;
}
.list .content {
    margin: 0 0 0 30px;
    height: 24px;
    display: flex;
    justify-content: space-between;
}
.list .content .el-button {
    /* color: #fff; */
    /* display: none; */
}
/* .list li:hover .content .el-button {
    color: #fff;
    display: block;
} */
.replyBtn {
    margin-top: 6px;
    display: flex;justify-content: flex-end;
}
.messageBoard .el-textarea__inner {
    box-shadow: 0 0 0 1px #acacac inset;
    border-radius: 5px;
}
.messageBoard .el-textarea__inner:hover {
    box-shadow: 0 0 0 1px #fff inset;
}

.messageBoard .el-input__wrapper  {
    background-color: transparent;
    box-shadow: 0 0 0 1px #b1b1b1bf inset;
}
.messageBoard .el-input__wrapper:hover {
    box-shadow: none;
    box-shadow: 0 0 0 1px #b1b1b1bf inset;
}
.el-pager .number {
    margin: 0 1px;
    background-color: transparent;
    box-shadow: 0 0 0 1px #b1b1b1bf inset;
}
@media screen and (max-width: 768px) {
    .end {
        width: 96%;
    }
    .messageBoard {
        width: 96%;
    }
}

.el-page-header__header {
    height: 40px;
    line-height: 0;
}
.messagePage .el-page-header.is-contentful .el-page-header__main {
    margin-top: 0;
    border-top: 1px solid #fff;
}
.messagePage .el-divider--vertical{
    border-left: 1px #fff var(--el-border-style)
}
</style>