<template>
  <div id="app">
    <router-view />
    <audio id="myaudio" :src="aSource" controls="controls" loop="false" hidden="true" />
  </div>
</template>

<script>
// 可以在这里导入request，然后服务器配置DeferedResult，使其延迟返回；等服务器返回时再向服务器请求
import io from 'socket.io-client'
import axios from 'axios'
import { alarmType } from '@/utils/constant'

export default {
  name: 'App',
  data() {
    return {
      alarmType: alarmType,
      cmdStatus: {
        'DELIVERED': '已送达',
        'PENDING': '等待中',
        'SENT': '已发送'
      },
      myAuto: null,
      aSource: '/static/audio/10344.mp3'
    }
  },
  mounted() {
    // this.$notify({
    //   title: '有新的告警信息',
    //   dangerouslyUseHTMLString: true,
    //   message: this.alarmType[0],
    //   type: 'warning',
    //   duration: 0,
    //   onClose: () => {
    //     this.myAuto.pause()
    //   }
    // })
    // const key = `open${Date.now()}`
    // this.$notification.open({
    //   message: 'Notification Title',
    //   description: 'A function will be be called after the notification is closed (automatically after the "duration" time of manually).',
    //   btn: (h) => {
    //     return h('a-button', {
    //       props: {
    //         type: 'primary',
    //         size: 'small'
    //       },
    //       on: {
    //         click: () => this.$notification.close(key)
    //       }
    //     }, 'Confirm')
    //   },
    //   key,
    //   onClose: close
    // })
    this.myAuto = document.getElementById('myaudio')
    // this.myAuto.play()

    axios.get('/api/ac/acUser/selfInfo').then(res => {
      if (res.data.code !== 0) {
        return
      }
      // 这里要提示还有几条告警信息待处理
      const socket = io('http://localhost:8868?no=' + res.data.content, {
        transports: ['websocket']
      })
      socket.on('connect', () => {
        console.log('connect')
      })
      // 告警推送
      socket.on('alarmEvent', (data) => {
        console.log('接收到告警信息', data)
        this.aSource = 'http://tsn.baidu.com/text2audio?lan=zh&ctp=1&cuid=abcdxxx&tok=24.7f2bf541d211c6a0d5f8d51c7c642d34.2592000.1558256775.282335-16041202&tex=有新的告警信息&vol=9&per=0&spd=5&pit=5&aue=3'
        this.$notify({
          title: '有新的告警信息',
          dangerouslyUseHTMLString: true,
          message: this.alarmType[data.type],
          type: 'warning',
          // duration: 0,
          onClose: () => {
            this.myAuto.pause()
          }
        })
        this.myAuto.play()
        console.log('alarmEvent', data)
      })
      socket.on('disconnect', () => {
        console.log('disconnect')
      })
    })
    if (!window.EventSource) {
      console.error('不支持SSE')
      // var source = new EventSource('')
    }
  }
}
</script>

<style>
body{
  background-color: #f0f2f5;
}
.a-card{
  margin: 15px;
}
</style>
