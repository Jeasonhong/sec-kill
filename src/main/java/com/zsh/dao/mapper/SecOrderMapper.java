package com.zsh.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsh.dao.entity.SecOrder;
import org.apache.ibatis.annotations.Param;

/**
 * (SecOrder)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-01 16:01:28
 */
public interface SecOrderMapper extends BaseMapper<SecOrder> {

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SecOrder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SecOrder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SecOrder> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SecOrder> entities);

}

