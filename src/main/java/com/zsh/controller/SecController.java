package com.zsh.controller;

import com.zsh.service.SecService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <h1>秒杀控制器</h1>
 *
 * @author <a href="mailto::jeasonhong@dingtalk.com">张顺鸿</a>
 * @version 1.0.0
 * @since 2022-03-01
 */
@RestController
@RequestMapping("sec")
public class SecController {

    @Resource
    private SecService secService;

    /**
     * 获取秒杀url
     *
     * @return 秒杀url
     */
    @GetMapping("url/{goodsId}")
    public String url(@PathVariable Integer goodsId){
        return secService.getUrl(goodsId);
    }

    private Integer memberNumber = 0;

    /**
     * 抢购货品
     * @param goodsId
     *
     * @return
     */
    @GetMapping("kill/{goodsId}")
    public String kill(@PathVariable Integer goodsId) throws InterruptedException {
        String member = "member" + memberNumber++;
        return secService.killGoods(goodsId,member);
    }

    /**
     * 重设库存
     *
     * @param goodsId
     * @param stock
     * @return
     */
    @GetMapping("reset")
    public String resetCache(@RequestParam("goodsId") Integer goodsId,@RequestParam("stock") Integer stock){
        return secService.resetCache(goodsId,stock);
    }

}