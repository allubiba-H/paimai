import request from "@/utils/request";

export function batchDel(url, arr) {
  return request({
    url,
    method: 'post',
    params: arr
  })
}

export function getIndexInfo() {
  return request({
    url: 'main/index',
    method: 'get',
  })
}

export function search(target, item) {
  return request({
    url: `${target}/search`,
    method: 'get',
    params: {item}
  })
}
