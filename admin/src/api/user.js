import request from '@/utils/request'

export function _login(u, p) {
  return request({
    url: `admin/login?name=${u}&password=${p}`,
    method: 'get'
  })
}

export function _getInfo(token) {
  return request({
    url: 'admin/getInfo',
    method: 'get',
    params: {token}
  })
}

// export function _logout() {
//   return request({
//     url: '/vue-admin-template/user/logout',
//     method: 'post'
//   })
// }
