import request from "@/utils/request";

//分页查询班级
export const queryPageApi = (name,begin,end,page,pageSize) => {
    return request.get(`/clazzs?name=${name}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`);
};

//新增班级
export const addApi = (data) => {
    return request.post('/clazzs', data);
}

//根据id查询班级
export const queryByIdApi = (id) => {
    return request.get(`/clazzs/${id}`);
}

//更新班级
export const updateApi = (data) => {
    return request.put(`/clazzs`, data);
}

//删除班级
export const deleteApi = (id) => {
    return request.delete(`/clazzs/${id}`);
}

export const queryAllApi = () => {
    return request.get('/clazzs/list');
}