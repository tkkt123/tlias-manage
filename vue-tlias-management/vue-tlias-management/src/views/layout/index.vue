<script setup>
import { onMounted, ref } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { useRouter } from 'vue-router';
import { changePasswordApi } from '@/api/user.js';

  const router = useRouter();
  const loginName=ref();

  const getLoginName=()=>{
    const loginUser=JSON.parse(localStorage.getItem('loginUser'));
    if(loginUser&&loginUser.name){
      loginName.value=loginUser.name;
    }
  }

  const logout=()=>{
    ElMessageBox.confirm('确认退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async() => {
      ElMessage.success('退出登录成功');
      localStorage.removeItem('loginUser');
      router.push('/login');
    }).catch(() => {
      ElMessage.error('取消退出登录');
    });
  }

  const changePasswd=()=>{
    ElMessageBox.prompt('请输入新密码', '修改密码', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/,
    inputErrorMessage: '密码必须包含字母、数字和特殊字符，并且至少8位'
    }).then(async({ value }) => {
      console.log(value);
      
      const loginUser = JSON.parse(localStorage.getItem('loginUser'));
      if (loginUser && loginUser.id) {
        const res = await changePasswordApi({ id: loginUser.id, password: value });
        console.log(res);
        
        if (res.code) {
          ElMessage.success('密码修改成功');
        } else {
          ElMessage.error(res.msg);
        }
      }
    }).catch(() => {
      ElMessage.error('取消修改密码');
    });
  }


  onMounted(()=>{
    getLoginName();
  })

</script>

<template>
  <div class="common-layout">
    <el-container>
      <!-- Header 区域 -->
      <el-header class="header">
        <span class="title">Tlias智能学习辅助系统</span>
        <span class="right_tool">
          <a href="javascript:void(0)" @click="changePasswd">
            <el-icon><EditPen /></el-icon> 修改密码;
          </a>
          <a href="javascript:void(0)" @click="logout">
            <el-icon><SwitchButton /></el-icon> 退出登录[{{ loginName }}]
          </a>
        </span>
      </el-header>
      
      <el-container>
        <!-- 左侧菜单 -->
        <el-aside width="200px" class="aside">
          <!-- 左侧菜单栏 -->
          <el-menu router>
        <!-- 首页菜单 -->
          <el-menu-item index="/index">
            <el-icon><Promotion /></el-icon> 首页
          </el-menu-item>

        <!-- 班级管理菜单 -->
          <el-sub-menu index="/manage">
            <template #title>
              <el-icon><Menu /></el-icon> 班级学员管理
            </template>
            <el-menu-item index="/clazz">
              <el-icon><HomeFilled /></el-icon>班级管理
            </el-menu-item>
            <el-menu-item index="/stu">
              <el-icon><UserFilled /></el-icon>学员管理
            </el-menu-item>
          </el-sub-menu>
          <!-- 系统信息管理 -->
          <el-sub-menu index="/system">
            <template #title>
              <el-icon><Tools /></el-icon>系统信息管理
            </template>
            <el-menu-item index="/dept">
              <el-icon><HelpFilled /></el-icon>部门管理
            </el-menu-item>
            <el-menu-item index="/emp">
              <el-icon><Avatar /></el-icon>员工管理
            </el-menu-item>
          </el-sub-menu>

          <!-- 数据统计管理 -->
          <el-sub-menu index="/report">
            <template #title>
              <el-icon><Histogram /></el-icon>数据统计管理
            </template>
            <el-menu-item index="/report/emp">
              <el-icon><InfoFilled /></el-icon>员工信息统计
            </el-menu-item>
            <el-menu-item index="/report/stu">
              <el-icon><Share /></el-icon>学员信息统计
            </el-menu-item>
            <el-menu-item index="/log">
              <el-icon><Document /></el-icon>日志信息统计
            </el-menu-item>
          </el-sub-menu>
          </el-menu>
        </el-aside>

        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
      
    </el-container>
  </div>
</template>

<style scoped>
.header {
  background-image: linear-gradient(to right, #00547d, #007fa4, #00aaa0, #00d072, #a8eb12);
}

.title {
  color: white;
  font-size: 40px;
  font-family: 楷体;
  line-height: 60px;
  font-weight: bolder;
}

.right_tool{
  float: right;
  line-height: 60px;
}

a {
  color: white;
  text-decoration: none;
}

.aside {
  width: 220px;
  border-right: 1px solid #ccc;
  height: 730px;
}
</style>
