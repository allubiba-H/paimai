import request from "@/utils/request";

export function getPtypes() {
  return request({
    url: 'ptypes/selectList',
    method: 'post'
  })
}
export function insert(data) {
  return request({
    url:'ptypes/insert',
    method:'post',
    params:data
  })
}
export function getPtypesOfPages(no,size) {
  return request({
    url:'ptypes/getAll',
    method:'get',
    params:{no,size}
  })
}
