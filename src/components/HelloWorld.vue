<template>
  <div class="hello">
    <p>
      公告教育学院打卡时间为: 6:50之前<br>
      信息工程学院打卡时间为: 7:10之前
      <!-- <a href="https://cli.vuejs.org" target="_blank" rel="noopener">更多公告</a>. -->
    </p>
    <div style="display: flex;justify-content: center">
      <h1>今日晨跑情况: <span v-if="todayStatus.status == 0">正常打卡</span> <span style="color: brown;" v-if="todayStatus.status == 1">不打卡</span></h1>
      <el-icon @click="getReason" style="margin-top: 36px;cursor: pointer;">
        <InfoFilled />
      </el-icon>
    </div>
    
    <small v-if="operateDara == '' || operateDara == null">(更新于: {{ todayStatus.date }})</small>
    <small v-else>(由 {{ operateDara.operator }} 更新于: {{ operateDara.time }} )</small>
    
    <h3>关注公众号</h3><small>后台回复"晨跑"等关键字 即可获取今日跑操情况</small>
    <ul>
      <img src="@/assets/img/qrcode_for_gh_de4aed74344e_258.jpg">
    </ul>
    
    <h1>{{ msg }}</h1>
    <!-- <h3>Essential Links</h3>
    <ul>
      <li><a href="https://vuejs.org" target="_blank" rel="noopener">Core Docs</a></li>
      <li><a href="https://forum.vuejs.org" target="_blank" rel="noopener">Forum</a></li>
      <li><a href="https://chat.vuejs.org" target="_blank" rel="noopener">Community Chat</a></li>
      <li><a href="https://twitter.com/vuejs" target="_blank" rel="noopener">Twitter</a></li>
      <li><a href="https://news.vuejs.org" target="_blank" rel="noopener">News</a></li>
    </ul>
    <h3>Ecosystem</h3>
    <ul>
      <li><a href="https://router.vuejs.org" target="_blank" rel="noopener">vue-router</a></li>
      <li><a href="https://vuex.vuejs.org" target="_blank" rel="noopener">vuex</a></li>
      <li><a href="https://github.com/vuejs/vue-devtools#vue-devtools" target="_blank" rel="noopener">vue-devtools</a></li>
      <li><a href="https://vue-loader.vuejs.org" target="_blank" rel="noopener">vue-loader</a></li>
      <li><a href="https://github.com/vuejs/awesome-vue" target="_blank" rel="noopener">awesome-vue</a></li>
    </ul> -->
  </div>
</template>

<script>
import axios from 'axios'
import { InfoFilled } from '@element-plus/icons-vue'

export default {
  components: {
    InfoFilled,
  },
  name: 'HelloWorld',
  props: {
    msg: String
  },
  data() {
    return {
      todayStatus: {},
      operateDara: {}
    }
  },
  created() {
    this.getStatus()
    this.getAllOperates()
  },
  methods: {
    getReason() {
      // this.$alert(`${this.todayStatus.remark}`, '备注', {
      //   confirmButtonText: '确定',
      //   callback: (action) => {
      //     if(action == 'confirm') {
      //       //
      //     } else if(action == 'cancel') {
      //       //
      //     }
      //   },
      // })
      this.$alert(`<p>${this.todayStatus.remark}</p><p>${this.operateDara.time}</p>`, '备注', {
        dangerouslyUseHTMLString: true,
          callback: (action) => {
            if(action == 'confirm') {
              //
            } else if(action == 'cancel') {
              //
            }
          },
      })
    },
    getAllOperates() {
      const url = '/api/run/getAllOperates'
      axios.post(url, {}, {
          headers: {
              'verifyCode': '2024'
          }
      }).then(res => {
          this.operateDara = res.data[0]
      })
    },
    getStatus() {
      const url = '/api/run/getStatus'
      axios.post(url, {}, {
          headers: {
              'verifyCode': '2024'
          }
      }).then(res => {
          // console.log(res.data)
          this.todayStatus = res.data
      })
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.hello {
  padding-bottom: 40px;
}
.hello h1, .hello h2, .hello h3 {
  margin: 12px 0 0;
}
.hello ul {
  list-style-type: none;
  padding: 0;
}
.hello li {
  display: inline-block;
  margin: 0 10px;
}
.hello a {
  color: #42b983;
}
</style>
