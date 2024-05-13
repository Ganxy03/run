<template>
<div class="bubble-list">
    <div v-for="(item, index) in filderItem" :key="index" class="bubble-item"
        :style="{ animationDelay: `${getAnimationDelay(index)}s` }">
    {{ item.content }}
    </div>
</div>
</template>

<script>
import axios from 'axios';

export default {
data() {
    return {
    bubbleList: [],
    currentCycle: 0, // 用于跟踪循环次数
    isPaused: false,
    animationDuration: 3, // 动画持续时间
    animationGap: 0.5, // 动画间隔时间
    };
},
created() {
    this.getAllComments();
},
computed: {
    filderItem() {
        return this.bubbleList.filter(item => item.status === 1)
    }
},
methods: {
    getAllComments() {
    const url = '/api/message/getAllBroads';
    axios.post(url, {}, {
        headers: {
        'verifyCode': '2024',
        },
    }).then((res) => {
        this.bubbleList = res.data;
    });
    },
    cycleBubble() {
    if (!this.isPaused) {
        this.currentCycle++;
    }
    },
    getAnimationDelay(index) {
    // 计算每个气泡的动画延迟时间，确保它们不会重叠
    const baseDelay = this.animationDuration + this.animationGap;
    return (index + this.currentCycle) * baseDelay;
    },
},
};
</script>

<style scoped>
.bubble-list {
position: relative;
width: 100%;
height: 160px; /* 根据需要调整高度 */
overflow: hidden;
}

.bubble-item {
position: absolute;
bottom: 0;
left: 24px; /* 居中对齐 */
transform: translateX(-50%);
width: 80px; /* 根据需要调整宽度 */
height: 80px; /* 根据需要调整高度 */
animation: bubble 3s ease-in-out; /* 动画持续时间 */
animation-fill-mode: forwards;
opacity: 0; /* 开始时不可见 */
}

/* 动画关键帧 */
@keyframes bubble {
0% {
    transform: translateY(100%);
    opacity: 0;
}
10% {
    opacity: 1;
}
90% {
    opacity: 0;
    transform: translateY(-100%);
}
100% {
    transform: translateY(-100%);
    opacity: 0;
}
}
</style>