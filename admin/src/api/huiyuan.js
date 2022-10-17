import request from "@/utils/request";

export function getAll(page, size) {
  return request({
    url: 'huiyuan/getList',
    method: 'get',
    params: {page, size}
  })
}

export function getSalsmanInfo(page) {
  return request({
    url: 'huiyuan/getSals',
    method: 'get',
    params: {page}
  })
}

