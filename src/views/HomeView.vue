<template>
  <div class="home">
    <HeadNav />
    <div class="masking">
      <div v-if="!isBroad" class="mainPage">
        <Calendar />
        <HelloWorld :msg="currentTime"/>
        <a class="bar_down" @click="isBroad = true"></a>
        <div @click="isBroad = true" style="width: 120px;position: absolute;bottom: 0;left: 2%;">
          <!-- <BubbleList /> -->
        </div>

        <div class="foot">
          <p>
            <a style="font-size: 11px;">项目仓库地址</a><br>
            <a style="line-height: 14px;" href="http://xh.ganxy03.cn">@阳光办公室 | 2024</a>
            <span style="font-size: 11px;"><br>互联网信息协会提供技术支持</span>
          </p>
          <p>
            <!-- <a href="https://beian.miit.gov.cn/"> 浙ICP备2022019862号-1 </a> -->
          </p>
        </div>
      </div>
      
      <div v-else class="messagePage">
        <el-page-header title="返回" @back="goBack" content="留言板">
          <MessagePage />
        </el-page-header>
        
      </div>
    </div>
  </div>
</template>

<script>
import HelloWorld from '@/components/HelloWorld.vue'
import Calendar from './module/CalendarModule.vue'
import HeadNav from './module/HeadNav.vue'
import MessagePage from './module/MessageBroad.vue'
// import BubbleList from './module/BubbleList.vue'

export default {
  name: 'HomeView',
  components: {
    HelloWorld,
    Calendar,
    HeadNav,
    MessagePage,
    // BubbleList
  },
  data() {
    return {
      currentTime: '',
      isBroad: false
    };
  },
  created() {
    this.updateTime();
    this.timer = setInterval(() => {
      this.updateTime();
    }, 1000);
    console.log(process.env.VUE_APP_BASE_URI)
  },
  unmounted() {
    clearInterval(this.timer);
  },
  methods: {
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
    goBack() {
      this.isBroad = false
    }
  }
};
</script>

<style>
.home {
  background: url(@/assets/img/110503466_p0_master1200.jpg) 50% no-repeat;
  /* banner4.png */
  /* background: url(@/assets/img/bac.png) 50% no-repeat; */
  background-size: cover;
  color: #000;
  height: 100vh;
  position: relative;
  overflow-y: hidden;
}
.masking {
  background-color: rgba(255, 255, 255, 0.4);
  width: 60%;
  margin: 0 auto;
  height: calc(100vh - 60px);
  overflow-y: auto;
}
.masking .mainPage {
  width: 100%;margin: 0 auto;padding-top: 24px;height: calc(100vh - 60px - 20px);
}
.foot {
  width: 100%;
  line-height: 20px;
}
.foot p a {
  /* color: #000; */
}
.foot a {
  font-size: 14px;
  color: rgb(38, 141, 224);
  text-decoration: none;
}


nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}



.bar_down {
  z-index: 999;
  position: absolute;
  bottom: 20px;
  width: 82px;
  height: 82px;
  margin-left: -40px;
  background: url(@/assets/img/i_down.png) 50% no-repeat;
  animation: ani_down 2s linear infinite;
  cursor: pointer;
}
.bar_down::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: url(@/assets/img/i_circle.png) 50% no-repeat;
  animation: ani_circle 2s linear infinite;
}
@keyframes ani_circle {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
@keyframes ani_down {
  0%, 100% {
    transform: translateY(10px);
  }
  50% {
    transform: translateY(-10px);
  }
}
@media screen and (max-width: 768px) {
  .appNav {
    display: none;
  }
  .masking {
    width: 96%;
  }
}
</style>