<script setup>
import { onMounted, ref } from 'vue';
import { queryPageApi ,addApi,queryByIdApi,updateApi,deleteApi} from '@/api/clazz';
import { ElMessage,ElMessageBox } from 'element-plus';
import { queryAllApi as queryMastersApi } from '@/api/emp.js';

const subjects=ref([
  {name:'java',value:1},
  {name:'前端',value:2},
  {name:'大数据',value:3},
  {name:'python',value:4},
  {name:'Go',value:5},
  {name:'嵌入式',value:6}
]);

const masters=ref([]);
const getMasters=async ()=>{
  const res = await queryMastersApi();
  if(res.code){
    masters.value=res.data;
  }
  else{
    ElMessage({
      message: '获取班主任列表失败',
      type: 'error',
    });
  }
}

const searchClazz = ref({
  name:'',
  date:[],
  begin:'',
  end:''
})
const currentPage=ref(1);
const pageSize=ref(5);
const background=ref(true);
const total=ref(0);
const clazzList = ref([])

const search = async () => {
  const res=await queryPageApi(
    searchClazz.value.name,
    searchClazz.value.begin,
    searchClazz.value.end,
    currentPage.value,
    pageSize.value
  );
  if(res.code){
    clazzList.value=res.data.rows;
    total.value=res.data.total;
  }
}
const clear = () => {
  searchClazz.value={
    name:'',
    date:[],
    begin:'',
    end:''
  }
  search();
}
const handleSizeChange = () => {
  search();
}
const handleCurrentChange = () => {
  search();
}

const dialogVisible=ref(false);
const dialogTitle=ref('新增班级');

const clazzFormRef=ref(null);
const clazz=ref({
  name:'',
  room:'',
  beginDate:'',
  endDate:'',
  masterId:'',
  subject:''
})
const addClazz= async ()=>{
  dialogTitle.value='新增班级';
  dialogVisible.value=true;
  if(clazzFormRef.value)clazzFormRef.value.resetFields();
  clazz.value={
    name:'',
    room:'',
    beginDate:'',
    endDate:'',
    masterId:'',
    subject:''
  }
}
const save=async ()=>{
  if (!clazzFormRef.value) return
  await clazzFormRef.value.validate(async (valid,fields) => {
    if(valid){
      let res;
      if(clazz.value.id){
        res = await updateApi(clazz.value);
      }
      else{
        res = await addApi(clazz.value);
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
const editClazz=async (id)=>{
  const res = await queryByIdApi(id);
  if(clazzFormRef.value)clazzFormRef.value.resetFields();
  if(res.code){
    dialogTitle.value='编辑班级';
    dialogVisible.value=true;
    clazz.value=res.data;
  }
  else{
    ElMessage({
      message: '获取班级信息失败',
      type: 'error',
    });
  }
}
//表单校验规则
const rules = ref({
  name: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 4, max: 30, message: '用户名长度应在4到30个字符之间', trigger: 'blur' }
  ],
  room:[
    {required:false, message: '请输入教室', trigger: 'blur' },
    { min: 1, max: 20, message: '教室长度应在1到20个字符之间', trigger: 'blur' }
  ],
  beginDate: [
    { required: true, message: '请选择开课时间', trigger: 'change' }
  ],
  endDate: [
    { required: true, message: '请选择结课时间', trigger: 'change' }
  ],
  subject: [
    { required: true, message: '请选择科目', trigger: 'change' }
  ]
});

const deleteClazz=async (id)=>{
    ElMessageBox.confirm('确定要删除该班级吗','警告',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
  .then(async () => {
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
  })
  .catch(() => {
    ElMessage({
      type: 'info',
      message: '删除已取消',
    })
  })
}

onMounted(() => {
  search();
  getMasters();
});
</script>

<template>
  <h1>班级管理</h1>
  <!--搜索框-->
  <div class ="container">
    <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
      <el-form-item label="班级名称">
        <el-input v-model="searchClazz.name" placeholder="请输入班级名称" clearable />
      </el-form-item>

      <el-form-item label="结课时间">
        <el-date-picker
          v-model="searchClazz.date"
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
  <!--操作按钮-->
  <div class ="container">
    <el-button type="primary" round @click="addClazz"> + 新增班级</el-button>
  </div>

  <!-- 班级列表 -->
  <div class="container">
    <el-table :data="clazzList" border style="width: 100%" @selection-change="handleSelectionChange" row-key="id" >

      <el-table-column prop="name" label="班级名称" width="120" align="center"  />
      <el-table-column prop="room" label="教室" width="120" align="center" />
      <el-table-column prop="masterName" label="班主任" width="120" align="center" />
      <el-table-column prop="beginDate" label="开课时间" width="180" align="center" />
      <el-table-column prop="endDate" label="结课时间" width="200" align="center" />
      <el-table-column prop="status" label="状态" width="200" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" width="200" align="center" />

      <el-table-column label="操作" >
        <template #default="scope">
          <el-button type="primary" size="default" @click="editClazz(scope.row.id)"><el-icon><EditPen /></el-icon>编辑</el-button>
          <el-button type="danger" size="default" @click="deleteClazz(scope.row.id)"><el-icon><Delete /></el-icon>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!--分页-->
  <div class ="container">
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

  <!--对话框-->
  <el-dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form ref="clazzFormRef" :model="clazz" label-width="80px" :rules="rules">

        <!-- 第一行 -->
        <el-form-item prop="name" label="班级名称">
          <el-input  v-model="clazz.name" placeholder="请输入班级名称"></el-input>
        </el-form-item>

        <!-- 第二行 -->
        <el-form-item prop="room" label="班级教室">
          <el-input v-model="clazz.room" placeholder="请输入班级教室"></el-input>
        </el-form-item>
        <!-- 第三行 -->   
        <el-form-item prop="beginDate" label="开课时间">
          <el-date-picker v-model="clazz.beginDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
        </el-form-item>
        <!-- 第四行 -->
        <el-form-item prop="endDate" label="结课时间">
          <el-date-picker v-model="clazz.endDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
        </el-form-item>
        <!-- 第五行 -->
        <el-form-item prop="masterId" label="班主任">
          <el-select v-model="clazz.masterId" placeholder="请选择" style="width: 100%;">
            <el-option v-for="m in masters" :key="m.id" :label="m.name" :value="m.id"></el-option>
          </el-select>
        </el-form-item>

        <!-- 第六行 -->
        <el-form-item prop="subject" label="学科">
          <el-select v-model="clazz.subject" placeholder="请选择" style="width: 100%;">
            <el-option v-for="s in subjects" :key="s.value" :label="s.name" :value="s.value"></el-option>
          </el-select>
        </el-form-item>
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

<style scoped>
  .container {
  margin: 10px 0 ;
}
</style>