package com.zsh.service.impl;

import com.zsh.dao.entity.SecGoods;
import com.zsh.dao.service.SecGoodsService;
import com.zsh.service.SecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <h1>秒杀服务实现类</h1>
 *
 * https://www.sohu.com/a/435308838_100004247 秒杀所要考虑的问题
 * 明天要记录秒杀问题 1
 * 保证库存不会超卖 1
 * 保证不会被重复抢购 1
 * 保证服务器不会被抢购到宕机
 * 库存扣减成功后还要保存到数据库里面
 * 当单机服务无法保证抢购的稳定性时,需要使用分布式锁了
 * 服务降级熔断
 * 抢购完后直接返回抢购结束/抢购完成
 *
 * @author <a href="jeasonhong@gmail.com">jeasonhong</a>
 * @version 1.0.0
 * @since 2022-03-01
 */
@Service
@Slf4j
public class SecServiceImpl implements SecService {

    @Resource
    private SecGoodsService secGoodsService;
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 库存redis键值
     */
    private final static String SEC_GOODS_STOCK="sec:goods:stock";

    /**
     * 抢购启用键(启用:true,禁用:false)
     */
    private final static String OPEN_KILL="sec:open_kill";

    /**
     * 抢购人员集合
     */
    private final static String KILLER_HASH="sec:killer:hash";

    /**
     * md5 加盐
     */
    private final String SALT = "thisIsASaltValue";

    @Override
    public String getUrl(int goodsId){
        SecGoods secGoods = secGoodsService.getById(goodsId);
        if (secGoods==null){
            return "请求出错";
        }
        LocalDateTime now=LocalDateTime.now();
        LocalDateTime startTime = secGoods.getStartDate();
        LocalDateTime endTime = secGoods.getEndDate();
        if (now.isAfter(startTime) && now.isBefore(endTime)) {
            //秒杀开启，返回秒杀商品的id,用给接口加密的md5
            return getMd5(goodsId);
        }
        return  "md5找不到";
    }

    /**
     * 获取MD5字符串
     *
     * @param goodsId 商品ID
     * @return
     */
    private String getMd5(int goodsId) {
        String base = goodsId + "/" + SALT;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }


    @Override
    public String killGoods(Integer goodsId, String phone) {
        if (redisTemplate.hasKey(OPEN_KILL).equals(Boolean.TRUE) && redisTemplate.opsForValue().get(OPEN_KILL).equals(Boolean.FALSE.toString())) {
            return "对不起,货品已经被抢完了哦!";
        }
        if (saveKiller(goodsId, phone)) {
            return "已经抢购过了";
        }
        long stock = redisTemplate.opsForHash().increment(SEC_GOODS_STOCK, goodsId.toString(), -1);
        if (stock < 0) {
            redisTemplate.opsForValue().set(OPEN_KILL, Boolean.FALSE.toString());
            return "对不起,货品已经被抢完了哦!";
        }
        updateStockV2(goodsId, stock);
        return "抢购成功!";
    }

    /**
     * 同步库存进数据库,这个时候就需要看你是否信任redis的稳定性了
     *
     * @param goodsId
     * @param stock
     */
    private void updateStockV2(Integer goodsId,Long stock){
        secGoodsService.lambdaUpdate().eq(SecGoods::getId, goodsId).set(SecGoods::getStock, stock).update();
    }

    /**
     * 保存抢购人,过滤重复点击,重复抢购的
     *
     * @param goodsId
     * @param phone
     * @return
     */
    private Boolean saveKiller(Integer goodsId,String phone){
        if (redisTemplate.opsForHash().hasKey(KILLER_HASH,phone).equals(Boolean.TRUE)){
            log.info("已经抢购过了");
            return false;
        }
        redisTemplate.opsForHash().put(KILLER_HASH,phone,goodsId);
        return true;
    }


    @Override
    public String resetCache(Integer goodsId, Integer stock) {
        redisTemplate.opsForHash().put(SEC_GOODS_STOCK, goodsId.toString(), stock);
        SecGoods secGoods = secGoodsService.getById(goodsId);
        secGoods.setStock(stock);
        secGoodsService.updateById(secGoods);
        return "库存设置成功";
    }

    private void zhanting(){
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            log.info("bb");
        }
    }


    /**
     * 假设更新数据库要1秒
     *
     * @param secGoods
     * @throws InterruptedException
     */
    private void updateStock(SecGoods secGoods) throws InterruptedException {
        Thread.sleep(1000);
        secGoodsService.updateById(secGoods);
    }

}