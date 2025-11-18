import axios from 'axios'
import{ElMessage} from 'element-plus'
import router from '@/router/index.js'

//创建axios实例对象
const request = axios.create({
  baseURL: '/api',
  timeout: 600000
})

//请求拦截器
request.interceptors.request.use(
  (config) => {
    //加上token请求头
    const loginUser = JSON.parse(localStorage.getItem('loginUser'))
    if(loginUser&&loginUser.token){
      config.headers.token = loginUser.token;
    }
    
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

//axios的响应 response 拦截器
request.interceptors.response.use(
  (response) => { //成功回调
    
    return response.data
  },
  (error) => { //失败回调
    // 统一处理 401 未授权
    if (error.response && error.response.status === 401) {
      // 可以显示提示信息
      ElMessage.error('登录已过期，请重新登录')
      // 清除本地存储的登录信息
      localStorage.removeItem('loginUser')
      // 跳转到登录页
      router.push('/login')

    }
    return Promise.reject(error)
  }
)

export default request