import request from "@/utils/request";

export function getAllActions(page, s) {
  return request({
    url: 'auction/getAll',
    method: 'get',
    params: {no: page, size: s}
  })
}

export function check(id, flag) {
  return request({
    url: 'auction/check',
    method: 'post',
    params: {id, flag}
  })
}

export function del(id) {
  return request({
    url: 'auction/del',
    method: 'post',
    params: {id}
  })
}

export function save(data) {
  return request({
    url:'auction/adminSaveAuction',
    method:'post',
    params:data
  })
}
export function getAllAdmin(no,size) {
  return request({
    url:'auction/getAllAdmin',
    method:'get',
    params:{no,size}
  })
}
