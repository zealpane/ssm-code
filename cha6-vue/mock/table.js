import Mock from 'mockjs'

const data = Mock.mock({
  'items|30': [{
    id: '@id+1',
    title: '设备@integer(3, 150)',
    'status|1': ['正常', '告警'],
    author: '轻量级ssm',
    display_time: '2019年11月5日',
    pageviews: '@integer(300, 5000)'
  }]
})

export default [
  {
    url: '/table/list',
    type: 'get',
    response: config => {
      const items = data.items
      return {
        code: 20000,
        data: {
          total: items.length,
          items: items
        }
      }
    }
  }
]
