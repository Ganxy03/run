<template>
<div class="calendar">
    <div class="header">
    <!-- <button @click="previousMonth">&lt;</button> -->
    <el-button type="text" @click="previousMonth">&lt;</el-button>
    <h3>{{ year }} {{ month }}</h3>
    <!-- <button @click="nextMonth">&gt;</button> -->
    <el-button type="text" @click="nextMonth">&gt;</el-button>
    </div>
    <div style="display: flex;">
    <!-- <button style="margin-left: auto;" @click="goToToday">回到今天</button> -->
    <el-button type="text" style="margin-left: auto;" @click="goToToday" size="mini">回到今天</el-button>
    </div>
    <table>
    <thead>
        <tr>
        <th v-for="day in weekDays" :key="day">{{ day }}</th>
        </tr>
    </thead>
    <tbody>
        <tr v-for="(week, weekIndex) in weeks" :key="weekIndex">
            <td @click="printDate(day)" v-for="(day, dayIndex) in week" :key="`${day.date.getTime()}_${dayIndex}`" :class="{ today: isToday(day.date) }">
                <template v-if="day.date.getMonth() === currentDate.getMonth()">
                    {{ day.day }}
                </template>
            </td>
        </tr>
    </tbody>
    </table>
</div>
</template>

<script>
export default {
data() {
    return {
    currentDate: new Date(),
    weekDays: ['天', '一', '二', '三', '四', '五', '六'],
    };
},
computed: {
    year() {
    return this.currentDate.getFullYear();
    },
    month() {
    return this.currentDate.toLocaleString('default', { month: 'long' });
    },
    weeks() {
    const year = this.currentDate.getFullYear();
    const month = this.currentDate.getMonth();
    const firstDayOfMonth = new Date(year, month, 1);
    const lastDayOfMonth = new Date(year, month + 1, 0);
    const firstDayOfWeek = firstDayOfMonth.getDay();
    // eslint-disable-next-line
    const lastDayOfWeek = lastDayOfMonth.getDay();

    const weeks = [];
    let week = [];

    // 填充上个月的日期
    const previousMonth = new Date(year, month, 0);
    const daysInPreviousMonth = previousMonth.getDate();
    for (let i = firstDayOfWeek - 1; i >= 0; i--) {
        const date = new Date(year, month - 1, daysInPreviousMonth - i);
        week.push({ day: date.getDate(), date });
    }

    // 填充本月的日期
    for (let i = 1; i <= lastDayOfMonth.getDate(); i++) {
        const date = new Date(year, month, i);
        week.push({ day: i, date });
        if (week.length === 7) {
        weeks.push(week);
        week = [];
        }
    }

    // 填充下个月的日期
    const remainingDays = 7 - week.length;
    for (let i = 1; i <= remainingDays; i++) {
        const date = new Date(year, month + 1, i);
        week.push({ day: i, date });
    }

    weeks.push(week);

    return weeks;
    },
},
methods: {
    previousMonth() {
    this.currentDate = new Date(this.year, this.currentDate.getMonth() - 1, 1);
    },
    nextMonth() {
    this.currentDate = new Date(this.year, this.currentDate.getMonth() + 1, 1);
    },
    isToday(date) {
    const today = new Date();
    return (
        date.getDate() === today.getDate() &&
        date.getMonth() === today.getMonth() &&
        date.getFullYear() === today.getFullYear()
    );
    },
    goToToday() {
    this.currentDate = new Date(); // 设置当前日期为今天的日期
    },
    printDate(day) {
    console.log(day.date); // 打印当天的日期
    },
},
};
</script>

<style scoped>
.calendar {
width: 360px;
margin: 0 auto;
font-family: Arial, sans-serif;
margin-bottom: 20px;
}

.calendar .header {
display: flex;
align-items: center;
justify-content: space-between;
margin-bottom: 1rem;
}

.calendar table {
border-collapse: collapse;
margin: 0 auto;
}

.calendar td {
padding: 0.5rem;
text-align: center;
}

.calendar .today {
font-weight: bold;
color: red;
}
</style>