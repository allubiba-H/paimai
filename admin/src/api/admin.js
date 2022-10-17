import request from "@/utils/request";

export function getAdminList() {
  return request({
    url: 'admin/getList',
    method: 'get'
  })
}

export function getAdminByName(param) {
  return request({
    url: 'admin/getByName',
    method: 'get',
    params: {username: param}
  })
}

export function del(param) {
  return request({
    url: 'admin/delById',
    method: 'get',
    params: {id: param}
  })
}

export function insertAdmin(admin) {
  return request({
    url: 'admin/insert',
    method: 'post',
    params: admin
  })
}
