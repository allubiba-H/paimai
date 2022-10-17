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
