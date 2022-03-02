package com.zsh.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsh.dao.entity.SecOrder;
import com.zsh.dao.mapper.SecOrderMapper;
import com.zsh.dao.service.SecOrderService;
import org.springframework.stereotype.Service;

/**
 * (SecOrder)表服务实现类
 *
 * @author makejava
 * @since 2022-03-01 16:01:28
 */
@Service("secOrderService")
public class SecOrderServiceImpl extends ServiceImpl<SecOrderMapper, SecOrder> implements SecOrderService {

}

