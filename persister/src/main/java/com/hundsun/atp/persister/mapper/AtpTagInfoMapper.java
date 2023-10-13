package com.hundsun.atp.persister.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hundsun.atp.persister.model.AtpTagInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 标签明细 Mapper 接口
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Mapper
public interface AtpTagInfoMapper extends BaseMapper<AtpTagInfo> {

    List<AtpTagInfo> selectAllTagInfo();

    List<AtpTagInfo> selectByTagIds(@Param("tagIds") List<String> tagIds);

    int deleteByTagId(String tagId);

    String selectByTagId(String tagId);
}
