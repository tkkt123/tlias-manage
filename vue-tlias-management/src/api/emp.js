import request from "@/utils/request";

//分页查询员工
export const queryPageApi = (name,gender,begin,end,page,pageSize) => {
    return request.get(`/emps?name=${name}&gender=${gender}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`);
};

//新增员工
export const addApi = (data) => {
    return request.post('/emps', data);
}

//根据id查询员工
export const queryByIdApi = (id) => {
    return request.get(`/emps/${id}`);
}

//修改员工
export const updateApi = (emp) => {
    return request.put('/emps', emp);
}

//删除员工
export const deleteApi = (ids) => {
    return request.delete(`/emps?ids=${ids}`);
}

//查询所有员工
export const queryAllApi = () => {
    return request.get('/emps/list');
}