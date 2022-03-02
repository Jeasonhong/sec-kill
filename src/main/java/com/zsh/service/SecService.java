package com.zsh.service;

/**
 * <h1>秒杀服务接口</h1>
 *
 * @author <a href="jeasonhong@gmail.com">jeasonhong</a>
 * @version 1.0.0
 * @since 2022-03-01
 */
public interface SecService {


    /**
     * 获取秒杀货品地址
     *
     * @param goodsId 秒杀货品ID
     * @return 请求地址
     */
    String getUrl(int goodsId);

    /**
     * 抢购货品
     *
     * @param goodsId 货品ID
     * @return
     */
    String killGoods(Integer goodsId,String phone) throws InterruptedException;

    /**
     * 更新库存
     *
     * @param goodsId 货品ID
     * @param stock 库存
     * @return
     */
    String resetCache(Integer goodsId,Integer stock);

}