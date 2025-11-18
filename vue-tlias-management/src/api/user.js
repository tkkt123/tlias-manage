import request from '@/utils/request'

//修改密码
export const changePasswordApi = (data) => {
    return request.put('/change', data);
}