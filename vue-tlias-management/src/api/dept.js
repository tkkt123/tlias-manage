import request from "@/utils/request";

//查询全部部门
export const queryAllApi = () => {
    return request.get('/depts');
}

//新增部门
export const addApi = (data) => {
    return request.post('/depts', data);
}

//根据id查询部门
export const queryByIdApi = (id) => {
    return request.get(`/depts/${id}`);
}

//修改部门
export const updateApi = (dept) => {
    return request.put('/depts', dept);
}

//删除部门
export const deleteApi = (ids) => {
    return request.delete(`/depts?ids=${ids}`);
}