import request from '@/utils/request'

//查询员工列表
export const queryPageApi = (name,gender,entryStartDate,entryEndDate,page,pageSize) => request.get(`/emps?name=${name}&gender=${gender}&entryStartDate=${entryStartDate}&entryEndDate=${entryEndDate}&page=${page}&pageSize=${pageSize}`)

//添加员工
export const addEmpApi = (data) => request.post('/emps', data)

//根据ID查询员工信息
export const queryInfoApi = (id) => request.get(`/emps/${id}`)

//修改员工信息
export const updateEmpApi = (data) => request.put('/emps', data)

//删除员工
export const deleteEmpApi = (ids) => request.delete(`/emps?ids=${ids}`)

//查询所有员工
export const queryAllApi = () => request.get(`/emps/list`)
