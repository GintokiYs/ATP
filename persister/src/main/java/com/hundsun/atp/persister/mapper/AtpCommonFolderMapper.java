package com.hundsun.atp.persister.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.hundsun.atp.persister.model.AtpCommonFolder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 通用文件夹 Mapper 接口
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Mapper
public interface AtpCommonFolderMapper extends BaseMapper<AtpCommonFolder> {

}
