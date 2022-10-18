package com.paipai.paipai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paipai.paipai.entity.Auction;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
public interface IAuctionService extends IService<Auction> {

    public void auctionning(Auction auction) throws Exception;


}
