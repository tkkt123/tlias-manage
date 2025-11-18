<script setup>
import { onMounted, ref } from 'vue';
import { queryAllApi, addApi , queryByIdApi,updateApi,deleteApi }  from '@/api/dept.js';
import { ElMessage,ElMessageBox } from 'element-plus'

onMounted(() => {
  search();
});

const search = async () => {
  const res = await queryAllApi();
  if(res.code){
    DeptList.value=res.data;
  }
};

const DeptList = ref([]);
const dialogFormVisible = ref(false);
const dept = ref({name: ''});
const formTitle = ref('');

const addDept = () => {
  formTitle.value = '新增部门';
  dept.value = {name: ''};
  dialogFormVisible.value = true;
  if(deptFormRef.value){
    deptFormRef.value.resetFields();
  }
};
const save = async () => {
  if (!deptFormRef.value) return
  await deptFormRef.value.validate(async (valid,fields) => {
    if (valid) {
        let res;
        if(dept.value.id){
          //修改部门
          res = await updateApi(dept.value);
        }
        else {
          //新增部门
          res = await addApi(dept.value);
        }
        if(res.code){
          dialogFormVisible.value=false;
          ElMessage.success("操作成功！");
          search();
        }
        else {
          ElMessage.error('校验不通过', fields)
        }
    }
    else{
      ElMessage.error('部门新增失败', res.data);
    }
  })



}
const rules = ref({
  name: [
    { required: true, message: '部门名称是必填项', trigger: 'blur' },
    { min: 2, max: 10, message: '部门名称的长度应该在2-10位', trigger: 'blur' }
  ]
})
const deptFormRef = ref();

const editDept = async (id)=>{
  //很奇怪，这个显示对话框如果放在if里面就不会生效
  dialogFormVisible.value=true;
  const res = await queryByIdApi(id);
  deptFormRef.value.resetFields();
  if(res.code){
    formTitle.value='修改部门';
    dept.value = res.data;
  }
}

const deleteDept = async (id) => {
  //弹出确认框
  ElMessageBox.confirm('确定要删除该部门吗','警告',
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

</script>

<template>
  <h1>部门管理</h1>
  <div class="container">
    <el-button type="primary" @click="addDept()"> + 新增部门</el-button>
  </div>

  <div class="container">
    <el-table :data="DeptList" border style="width: 100%">
      <el-table-column type="index" width="100" label="序号" />
      <el-table-column prop="name" label="部门名称" width="180" />
      <el-table-column prop="updateTime" label="最后操作时间"  />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button type="primary" size="default" @click="editDept(scope.row.id)"><el-icon><EditPen /></el-icon>编辑</el-button>
          <el-button type="danger" size="default" @click="deleteDept(scope.row.id)"><el-icon><Delete /></el-icon>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 对话框组件 -->
  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="500">
    <el-form :model="dept" :rules="rules" ref="deptFormRef">
      <el-form-item label="部门名称" :label-width="'80px'" prop="name">
        <el-input v-model="dept.name" autocomplete="off" />
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save() ">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<!--删除提示框-->


<style scoped>
.container {
  margin: 10px 0 ;
}
</style>
