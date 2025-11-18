<script setup>
import { onMounted, ref } from 'vue';
import { queryPageApi , updateApi , addApi,queryByIdApi ,deleteApi,dealViolationApi} from '@/api/stu.js';
import { ElMessage,ElMessageBox } from 'element-plus';
import { queryAllApi as queryAllClazzApi } from '@/api/clazz.js';

const degrees = ref([
  { name: '初中', value: 1 },
  { name: '高中', value: 2 },
  { name: '大专', value: 3 },
  { name: '本科', value: 4 },
  { name: '硕士', value: 5 },
  { name: '博士', value: 6 },
]);
const genders = ref([
  { name: '男', value: 1 },
  { name: '女', value: 2 }
]);
const clazzs=ref([]);
const getClazzs=async ()=>{
  const res = await queryAllClazzApi();
  if (res.code) {
    clazzs.value = res.data;
  }
}

const currentPage = ref(1);
const pageSize = ref(5);
const background = ref(true);
const total = ref(0);

const stuList = ref([]);

const search = async () => {
  const res = await queryPageApi(
    searchStu.value.name,
    searchStu.value.degree,
    searchStu.value.clazzId,
    currentPage.value,
    pageSize.value
  );
  if (res.code) {
    stuList.value = res.data.rows;
    total.value = res.data.total;
  }
};

const clear = () => {
  searchStu.value = {
    name: '',
    degree: '',
    clazzId: ''
  };
  search();
};

const handleSizeChange = () => {
  search();
};
const handleCurrentChange = () => {
  search();
};

const searchStu = ref({
  name: '',
  degree: '',
  clazzId: ''
});

const dialogVisible = ref(false);
const dialogTitle = ref();
const student = ref({
  name:'',
  no:'',
  gender:'',
  phone:'',
  degree:'',
  clazzId:'',
  idCard:'',
  isCollege:'',//1 yes;0 no
  address:'',
  graduationDate:'',
});
const studentFormRef = ref(null);

const addStu=()=>{
  dialogTitle.value = '添加学员';
  dialogVisible.value = true;
  if(studentFormRef.value){
    studentFormRef.value.resetFields();
  }
  student.value = {
    name:'',
    no:'',
    gender:'',
    phone:'',
    degree:'',
    clazzId:'',
    idCard:'',
    isCollege:'',//1 yes;0 no
    address:'',
    graduationDate:'',
  };
}
const save=async()=>{
  if (!studentFormRef.value) return
  await studentFormRef.value.validate(async (valid,fields) => {
    if(valid){
      let res;
      if(student.value.id){
        res = await updateApi(student.value);
      }
      else{
        res = await addApi(student.value);
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

const rules = ref({
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '姓名长度应在2到20个字符之间', trigger: 'blur' }
  ],
  no: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    { len: 10, message: '学号长度应为10个字符', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ],
  idCard: [
    { required: true, message: '请输入身份证号', trigger: 'blur' },
    { len: 18, message: '请输入有效的身份证号', trigger: 'blur' }
  ],
  isCollege: [
    { required: true, message: '请选择是否是院校学生', trigger: 'blur' }
  ],
  address: [
    { max: 100, message: '联系地址不能超过100个字符', trigger: 'blur' }
  ],
  clazzId: [
    { required: true, message: '请选择班级', trigger: 'blur' }
  ]
});

const editStu = async (id) => {
  const res = await queryByIdApi(id);
  if(res.code){
    dialogTitle.value = '编辑学员';
    dialogVisible.value = true;
    student.value=res.data;
  }
}

const selectIds = ref([]);
const handleSelectionChange = (val) => {
  selectIds.value = val.map(item => item.id);
}

const deleteStu = async (id) => {
    //弹出确认框
  ElMessageBox.confirm('确定要删除这些学员吗','警告',
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
};

const dealViolation=async(id)=>{
    ElMessageBox.prompt('违纪扣分', '学员违纪处理', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    }).then(async({ value }) => {
      const res = await dealViolationApi(id,value);
      if(res.code){
        ElMessage.success('操作成功');
        search();
      }
      else{
        ElMessage.error(res.msg);
      }
    }).catch(() => {
      ElMessage.error('取消操作');
    });
}

onMounted(() => {
  search();
  getClazzs();
});
</script>

<template>
  <h1>学员管理</h1>
  <!--搜索框区域-->
  <div class="container">
    <el-form :inline="true" :model="searchStu" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchStu.name" placeholder="请输入姓名" clearable />
      </el-form-item>

      <el-form-item label="最高学历">
        <el-select v-model="searchStu.degree" placeholder="请选择" clearable>
          <el-option v-for="de in degrees" :key="de.value" :label="de.name" :value="de.value" />
        </el-select>
      </el-form-item>

      <el-form-item label="所属班级">
        <el-select v-model="searchStu.clazzId" placeholder="请选择" clearable>
          <el-option v-for="cla in clazzs" :key="cla.id" :label="cla.name" :value="cla.id" />
        </el-select>
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
    <el-button type="primary" round @click="addStu"> + 添加学员</el-button>
    <el-button type="danger" round @click="deleteStu()"> - 批量删除</el-button>
  </div>

  <!--学员列表-->
  <div class="container">
    <el-table :data="stuList" border style="width: 100%" @selection-change="handleSelectionChange" row-key="id" >
      <el-table-column type="selection" align="center" reserve-selection />

      <el-table-column prop="name" label="姓名" width="120" align="center"  />
      <el-table-column prop="no" label="学号" width="120" align="center"  />
      <el-table-column prop="clazzName" label="班级" width="120" align="center"  />
      <el-table-column label="性别" width="120" align="center">
        <template #default="scope">
          {{ scope.row.gender === 1 ? '男' : '女' }}
        </template>
      </el-table-column>

      <el-table-column prop="phone" label="手机号" width="120" align="center" />
      <el-table-column prop="degree" label="最高学历" width="120" align="center">
        <template #default="scope">
          {{ degrees[scope.row.degree - 1]?.name }}
        </template>
      </el-table-column>
      <el-table-column prop="violationCount" label="违纪次数" width="120" align="center"  />
      <el-table-column prop="violationScore" label="违纪扣分" width="120" align="center"  />

      <el-table-column prop="updateTime" label="最后操作时间" width="200" align="center" />
      <el-table-column label="操作" >
        <template #default="scope">
          <el-button type="primary" size="default" @click="editStu(scope.row.id)"><el-icon><EditPen /></el-icon>编辑</el-button>
          <el-button type="primary" size="default" @click="dealViolation(scope.row.id)"><el-icon><EditPen /></el-icon>违纪</el-button>
          <el-button type="danger" size="default" @click="deleteStu(scope.row.id)"><el-icon><Delete /></el-icon>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!--分页区域-->
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

  <!--对话框-->
  <div class="container">
    <el-dialog v-model="dialogVisible" :title="dialogTitle">

      <el-form ref="studentFormRef" :model="student" label-width="80px" :rules="rules" >
        <!-- 第一行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item prop="name" label="姓名" p>
              <el-input  v-model="student.name" placeholder="请输入姓名"></el-input>
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item prop="no" label="学号">
              <el-input v-model="student.no" placeholder="请输入学号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item prop="gender" label="性别">
              <el-select v-model="student.gender" placeholder="请选择性别" style="width: 100%;">
                <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item prop="phone" label="手机号">
              <el-input v-model="student.phone" placeholder="请输入学员手机号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第三行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="student.idCard" placeholder="请输入身份证号"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否是院校学生" prop="isCollege">
              <el-select v-model="student.isCollege" placeholder="请选择" style="width: 100%;">
                <el-option label="是" :value="1" />
                <el-option label="否" :value="0" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第四行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="联系地址" prop="address">
              <el-input v-model="student.address" placeholder="请输入联系地址"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最高学历">
              <el-select v-model="student.degree" placeholder="请选择最高学历" style="width: 100%;">
                <el-option v-for="item in degrees" :key="item.value" :label="item.name" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第五行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="毕业时间">
              <el-date-picker v-model="student.graduationDate" type="date" placeholder="请选择毕业时间" style="width: 100%;" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属班级" prop="clazzId">
              <el-select v-model="student.clazzId" placeholder="请选择" style="width: 100%;">
                <el-option
                  v-for="item in clazzs" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
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
  </div>

</template>

<style scoped>
  .container {
    margin: 10px 0 ;
  }
</style>