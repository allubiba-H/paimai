import request from "@/utils/request";

export function getAllMoneyRocred(page, size) {
  return request({
    url: 'moneyrecord/getMoneyRecord',
    method: 'get',
    params: {page, size}
  })
}

export function del(id) {
  return request({
    url: 'moneyrecord/del',
    method: 'delete',
    params: {id}
  })
}

export function insert(record) {
  return request({
    url: 'moneyrecord/saveOrUpdate',
    method: 'put',
    params: record
  })
}

export function getByName(name) {
  return request({
    url: 'moneyrecord/getByName',
    method: 'get',
    params: {name}
  })
}
