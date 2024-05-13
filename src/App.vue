<template>
  <router-view/>
</template>

<script>
export default {
  mounted() {
    // localStorage.clear()
  }
}
</script>

<style>
* {
  margin: 0;
}
::-webkit-scrollbar {
  width: 5px;
  height: 5px;
}
::-webkit-scrollbar-track {
  background: #fff;
}
::-webkit-scrollbar-thumb {
  background: rgba(57, 60, 59, 0.6);
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

body {
  background-color: #fff;
  color: #000;
}

.dark .masking,.dark .managerPage .mainArea {
  /* background: #35495D; */
  background-color: rgba(53, 73, 93, 0.6);
  color: #fff;
}
.dark .personal-page .opera .showForm {
  background-color: rgba(53, 73, 93, 0.8);
}
.dark .el-table {
  --el-table-tr-bg-color: rgba(53, 73, 93, 1);
  --el-table-border-color: #5b5b5b;
  --el-table-row-hover-bg-color: #6e6e6e;
  --el-table-border: 1px solid var(--el-table-border-color);
  color: #b0b3b9;
}
.dark .el-table thead.is-group th.el-table__cell {
  background: rgba(53, 73, 93, 1);;
}
.dark .head-nav,.dark .el-menu {
  background-color: rgba(53, 73, 93, 1);
  color: #fff;
}
</style>
