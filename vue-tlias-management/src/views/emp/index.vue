<script setup>
import { onMounted, ref ,watch,nextTick} from 'vue'
import {queryPageApi , addApi , queryByIdApi ,updateApi,deleteApi} from '@/api/emp.js'
import {queryAllApi as queryAllDeptApi} from '@/api/dept.js'
import { ElMessage,ElMessageBox } from 'element-plus'

const searchEmp = ref({
  name:'',
  gender:'',
  date:[],
  begin:'',
  end:''
})

const search= async ()=>{
  const res=await queryPageApi(
    searchEmp.value.name,
    searchEmp.value.gender,
    searchEmp.value.begin,
    searchEmp.value.end,
    currentPage.value,
    pageSize.value
  );
  if(res.code){
    empList.value=res.data.rows;
    total.value=res.data.total;
  }
}

const clear=()=>{
  searchEmp.value = {
    name:'',
    gender:'',
    date:[]
  }
  search();
}
watch(() => searchEmp.value.date, (newVal) => {
  if(newVal.length===2){
    searchEmp.value.begin=newVal[0];
    searchEmp.value.end=newVal[1];
  }
  else {
    searchEmp.value.begin='';
    searchEmp.value.end='';
  }
})

const empList = ref([])

const currentPage = ref(1)
const pageSize = ref(5)
const background=ref(true)
const total = ref(0)

const handleSizeChange = () => {
  search();
}
const handleCurrentChange = () => {
  search();
}

const employeeFormRef = ref(null)
const employee = ref({
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: []
})

const genders = ref([
  { name: '男', value: 1 },
  { name: '女', value: 2 }
])

const jobs=ref([
  { name: '班主任', value: 1 },
  { name: '讲师', value: 2 },
  { name: '学工主管', value: 3 },
  { name: '科研主管', value: 4 },
  { name: '咨询师', value: 5 }
])

const depts=ref([])

const dialogVisible = ref(false)
const dialogTitle = ref()

const addEmp=()=>{
  dialogVisible.value=true;
  dialogTitle.value='新增员工';
  if(employeeFormRef.value){
    employeeFormRef.value.resetFields();
  }
  employee.value = {
    username: '',
    name: '',
    gender: '',
    phone: '',
    job: '',
    salary: '',
    deptId: '',
    entryDate: '',
    image: '',
    exprList: []
  }
}

const searchDepts= async ()=>{
  const res=await queryAllDeptApi();
  if(res.code){
    depts.value=res.data;
  }
}

const handleAvatarSuccess = (response) => {
  console.log(response);
  employee.value.image = response.data;
}

const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上传图片')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('只能上传10M以内图片')
    return false
  }
  return true
}
// 添加工作经历条目
const addExprItem = () => {
  employee.value.exprList.push({
    company: "",
    job: "",
    begin: "",
    end: "",
    exprDate: []
  })
}
watch(() => employee.value.exprList, (newVal, oldVal) => {
  if(employee.value.exprList&&employee.value.exprList.length>0){
    employee.value.exprList.forEach((item)=>{
        if(item.exprDate&&item.exprDate.length>0){
            item.begin=item.exprDate[0];
            item.end=item.exprDate[1];
        }
    })
  }
},{deep:true})

const removeExprItem = (index) => {
  employee.value.exprList.splice(index, 1);
}

const save= async ()=>{
  if (!employeeFormRef.value) return
  await employeeFormRef.value.validate(async (valid,fields) => {
    if(valid){
      let res;
      if(employee.value.id){
        res = await updateApi(employee.value);
      }
      else{
        res = await addApi(employee.value);
      }

      if(res.code){
        ElMessage.success("操作成功");
        dialogVisible.value = false;
        search();
      }
      else{
        ElMessage.error(res.msg);
      }
    }
    else{
      ElMessage.error('表单校验不通过', fields)
    }
  });

}

//表单校验规则
const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度应在2到20个字符之间', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ]
});

const editEmp = async (id)=>{
  //根据id查询员工信息  
  const res = await queryByIdApi(id);
  if (res.code) {
    dialogVisible.value = true;
    dialogTitle.value = '编辑员工';
    employee.value = res.data;
    //处理工作经历的时间范围
    let exprList = employee.value.exprList;
    if(exprList&&exprList.length>0){
      exprList.forEach((item)=>{
          item.exprDate=[item.begin,item.end];
      })
    }

  }
}

const deleteEmp= async (id)=>{
    //弹出确认框
  ElMessageBox.confirm('确定要删除这些员工吗','警告',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
  .then(async () => {
    //单个删除
    if(id&&id!=null){
      const res = await deleteApi(id);
      if(res.code){
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
        search();
      }
      else {
        ElMessage.error('删除失败');
      }
    }
    //批量删除
    else{
      if(selectIds.value&&selectIds.value.length===0){
        ElMessage({
          type: 'info',
          message: '请选择要删除的员工',
        })
        return;
      }
      const res = await deleteApi(selectIds.value);
      if(res.code){
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
        selectIds.value=[];
        search();
      }
      else {
        ElMessage.error('删除失败');
      }
    }

  })
  .catch(() => {
    ElMessage({
      type: 'info',
      message: '删除已取消',
    })
  })
}

const selectIds=ref([]);
const handleSelectionChange = (val) => {
  selectIds.value = val.map(item => item.id);
}

const token = ref();

const getToken=()=>{
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  if(loginUser&&loginUser.token){
    token.value=loginUser.token;
  }
}

onMounted(() => {
  search();
  searchDepts();
  getToken();
})
</script>

<template>
  <h1>员工管理</h1>
  <!--查询表单-->
  <div class="container">
    <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchEmp.name" placeholder="请输入姓名" clearable />
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="searchEmp.gender" placeholder="请选择性别" clearable>
          <el-option label="男" value="1" />
          <el-option label="女" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="入职时间">
        <el-date-picker
          v-model="searchEmp.date"
          type="daterange"
          range-separator="到"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD"
        />
        </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>
  <!--功能操作按钮-->
  <div class="container">
    <el-button type="primary" round @click="addEmp"> + 新增员工</el-button>
    <el-button type="danger" round @click="deleteEmp(null)"> - 批量删除员工</el-button>
  </div>
  <!--员工列表-->
  <div class="container">
    <el-table :data="empList" border style="width: 100%" @selection-change="handleSelectionChange" row-key="id" >
      <el-table-column type="selection" align="center" reserve-selection />

      <el-table-column prop="name" label="姓名" width="120" align="center"  />
      <el-table-column label="性别" width="120" align="center">
        <template #default="scope">
          {{ scope.row.gender === 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column prop="image" label="头像" width="120" align="center">
        <template #default="scope">
          <img :src="scope.row.image" alt="头像" height="40px" align="center"/>
        </template>
      </el-table-column>
      <el-table-column prop="deptName" label="所属部门" width="120" align="center">
        <template #default="scope">
          <span v-if="scope.row.deptName">{{ scope.row.deptName }}</span>
          <span v-else style="color: red;font-weight: bold;">暂无部门</span>
        </template>
      </el-table-column>
      <el-table-column prop="job" label="职位" width="120" align="center">
        <template #default="scope">
          <span v-if="scope.row.job === 1">班主任</span>
          <span v-else-if="scope.row.job === 2">讲师</span>
          <span v-else-if="scope.row.job === 3">学工主管</span>
          <span v-else-if="scope.row.job === 4">科研主管</span>
          <span v-else-if="scope.row.job === 5">咨询师</span>
          <span v-else style="color: red;font-weight: bold;">暂无职业</span>
        </template>
      </el-table-column>
      <el-table-column prop="entryDate" label="入职时间" width="180" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" width="200" align="center" />
      <el-table-column label="操作" >
        <template #default="scope">
          <el-button type="primary" size="default" @click="editEmp(scope.row.id)"><el-icon><EditPen /></el-icon>编辑</el-button>
          <el-button type="danger" size="default" @click="deleteEmp(scope.row.id)"><el-icon><Delete /></el-icon>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <!-- 分页 -->
  <div class="container">
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[5, 10, 20, 50]"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>

  <!-- 新增/修改员工弹窗 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form ref="employeeFormRef" :model="employee" label-width="80px" :rules="rules">
        <!-- 基本信息 -->
        <!-- 第一行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item prop="username" label="用户名">
              <el-input  v-model="employee.username" placeholder="请输入员工用户名，2-20个字"></el-input>
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item prop="name" label="姓名">
              <el-input v-model="employee.name" placeholder="请输入员工姓名，2-10个字"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item prop="gender" label="性别">
              <el-select v-model="employee.gender" placeholder="请选择性别" style="width: 100%;">
                <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item prop="phone" label="手机号">
              <el-input v-model="employee.phone" placeholder="请输入员工手机号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第三行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="职位">
              <el-select v-model="employee.job" placeholder="请选择职位" style="width: 100%;">
                <el-option v-for="j in jobs" :key="j.value" :label="j.name" :value="j.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="薪资">
              <el-input v-model="employee.salary" placeholder="请输入员工薪资"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第四行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属部门">
              <el-select v-model="employee.deptId" placeholder="请选择部门" style="width: 100%;">
                <el-option v-for="d in depts" :key="d.value" :label="d.name" :value="d.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入职日期">
              <el-date-picker v-model="employee.entryDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第五行 -->
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="头像">
              <el-upload
                class="avatar-uploader"
                action="/api/upload"
                :headers="{'token': token}"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                >
                <img v-if="employee.image" :src="employee.image" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        
        <!-- 工作经历 -->
        <!-- 第六行 -->
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="工作经历">
              <el-button type="success" size="small" @click="addExprItem">+ 添加工作经历</el-button>
            </el-form-item>
          </el-col>
        </el-row>

        
        <!-- 第七行 ...  工作经历 -->
        <el-row :gutter="3" v-for="(ex,index) in employee.exprList" :key="ex.index">
          <el-col :span="10">
            <el-form-item size="small" label="时间" label-width="80px">
              <el-date-picker type="daterange" v-model="ex.exprDate" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" ></el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small"  label="公司" label-width="60px">
              <el-input v-model="ex.company" placeholder="请输入公司名称"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small"  label="职位" label-width="60px">
              <el-input v-model="ex.job" placeholder="请输入职位"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="2">
            <el-form-item size="small" label-width="0px">
              <el-button type="danger" @click="removeExprItem(index)">- 删除</el-button>
            </el-form-item>
          </el-col>
        </el-row>

    </el-form>
    
    <!-- 底部按钮 -->
    <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </span>
    </template>
  </el-dialog>

</template>



<style>
.container {
  margin: 10px 0 ;
}

.avatar {
  height: 40px;
}
.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}
</style>
