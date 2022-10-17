import request from "@/utils/request";

export function getPtypes() {
  return request({
    url: 'ptypes/selectList',
    method: 'post'
  })
}
