package com.zsh.dao.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (SecOrder)表实体类
 *
 * @author makejava
 * @since 2022-03-01 16:01:28
 */
@Data
@TableName(value = "sec_order")
public class SecOrder implements Serializable {

    private static final long serialVersionUID = 78981836280478862L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    //用户ID
    @TableField(value = "user_id")
    private Long userId;
    //订单ID
    @TableField(value = "order_id")
    private Long orderId;
    //商品ID
    @TableField(value = "goods_id")
    private Long goodsId;

}

