package com.zsh.dao.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * (SecGoods)表实体类
 *
 * @author makejava
 * @since 2022-03-01 16:01:28
 */
@Data
@TableName(value = "sec_goods")
public class SecGoods implements Serializable {

    private static final long serialVersionUID = 6969312498735042367L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    //商品名称
//    @TableField(value = "goods_name")
//    private String goodsName;

    @TableField(value = "price")
    private BigDecimal price;
    @TableField(value = "stock")
    private Integer stock;
    @TableField(value = "start_date")
    private LocalDateTime startDate;
    @TableField(value = "end_date")
    private LocalDateTime endDate;
}

