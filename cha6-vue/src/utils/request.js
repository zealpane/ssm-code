import axios from 'axios'
import { Message } from 'element-ui'
import store from '@/store'
// import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'// progress bar style
import router from '@/router'

// NProgress.configure({ showSpinner: true })// NProgress Configuration

// create an axios instance
const service = axios.create({
  baseURL: '/api',
  timeout: 15000
})

// request interceptor
service.interceptors.request.use(config => {
  // NProgress.start()
  return config
}, error => {
  console.log(error) // for debug
  Promise.reject(error)
})

// respone interceptor
service.interceptors.response.use(
  // response => response,
  response => { // 响应成功
    // NProgress.done()
    const res = response.data
    switch (res.code) {
      case 0:
        return res
      case 1:
        router.replace('/login')
        store.dispatch('FedLogOut').then(() => {
          location.reload() // 为了重新实例化vue-router对象 避免bug
        })
        break
      default:
        Message({
          message: res.content,
          type: 'error',
          duration: 5 * 1000
        })
        break
    }
    // if (res.code === 0) {
    //   return res
    // } else {
    //   Message({
    //     message: res.message,
    //     type: 'error',
    //     duration: 3 * 1000
    //   })
    // }
    return Promise.reject('error')
  },
  error => { // 响应失败
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  })

export default service
