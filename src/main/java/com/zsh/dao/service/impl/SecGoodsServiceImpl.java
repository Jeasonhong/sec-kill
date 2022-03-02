package com.zsh.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsh.dao.entity.SecGoods;
import com.zsh.dao.mapper.SecGoodsMapper;
import com.zsh.dao.service.SecGoodsService;
import org.springframework.stereotype.Service;

/**
 * (SecGoods)表服务实现类
 *
 * @author makejava
 * @since 2022-03-01 16:01:28
 */
@Service("secGoodsService")
public class SecGoodsServiceImpl extends ServiceImpl<SecGoodsMapper, SecGoods> implements SecGoodsService {

}

