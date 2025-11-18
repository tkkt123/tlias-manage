import request from "@/utils/request";

//分页查询学员
export const queryPageApi = (name,degree,clazzId,page,pageSize) => {
    return request.get(`/students?name=${name}&&degree=${degree}&clazzId=${clazzId}&page=${page}&pageSize=${pageSize}`);
};

//新增学员
export const addApi = (data) => {
    return request.post('/students', data);
}

//根据id查询学员
export const queryByIdApi = (id) => {
    return request.get(`/students/${id}`);
}

//修改学员
export const updateApi = (emp) => {
    return request.put('/students', emp);
}

//删除学员
export const deleteApi = (ids) => {
    return request.delete(`/students/${ids}`);
}

//违纪处理
export const dealViolationApi = (id, score) => {
    return request.put(`/students/violation/${id}/${score}`);
}