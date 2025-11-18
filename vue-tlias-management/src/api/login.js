import request from "@/utils/request";

//登录
export const loginApi = (data) => {
    return request.post('/login', data);
}

export const changePasswordApi = (data) => {
    return request.put('/change', data);
}