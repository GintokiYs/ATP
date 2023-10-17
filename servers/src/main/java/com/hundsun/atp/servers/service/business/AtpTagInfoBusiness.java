package com.hundsun.atp.servers.service.business;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import com.hundsun.atp.common.domain.vo.folder.AtpCommonFolderVo;
import com.hundsun.atp.common.domain.vo.taginfo.AtpTagInfoVo;
import com.hundsun.atp.common.enums.EnableEnum;
import com.hundsun.atp.common.util.Precondition;
import com.hundsun.atp.persister.mapper.AtpRefTagUseCaseMapper;
import com.hundsun.atp.persister.mapper.AtpTagInfoMapper;
import com.hundsun.atp.persister.model.AtpRefTagUseCase;
import com.hundsun.atp.persister.model.AtpTagInfo;
import com.hundsun.atp.servers.service.convert.TagInfoConvert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 标签明细 处理类
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Service
public class AtpTagInfoBusiness extends ServiceImpl<AtpTagInfoMapper, AtpTagInfo> {
    @Autowired
    private AtpTagInfoMapper atpTagInfoMapper;

    @Autowired
    private AtpRefTagUseCaseMapper atpRefTagUseCaseMapper;

    @Autowired
    private TagInfoConvert tagInfoConvert;

    /**
     * 新建TagInfo
     *
     * @param tagInfoDto
     * @return
     */
    public AtpTagInfoVo createTagInfo(AtpTagInfoDto tagInfoDto) {
        // 根据标签名称 校验是否存在同名
        Precondition.checkArgument(!validateTagName(tagInfoDto.getTagKey()), "200000001");
        return insert(tagInfoDto);
    }

    /**
     * 编辑TagInfo并更新
     *
     * @param tagInfoDto
     * @return
     */
    public Boolean editTagInfo(AtpTagInfoDto tagInfoDto) {

        // 根据修改的标签名称 校验是否存在同名
        Precondition.checkArgument(!validateTagName(tagInfoDto.getTagKey()), "200000001");
        return update(tagInfoDto);

    }

    /**
     * 删除数据行
     *
     * @param id
     * @return
     */
    public Boolean deleteTagInfo(String id) {
        // 调用AtpTagInfoMapper的删除方法来删除数据
        int rowsDeleted = atpTagInfoMapper.deleteById(id);
        // todo 同时删除关联表的数据

        // 根据删除的行数判断删除是否成功
        return rowsDeleted > 0;
    }

    /**
     * 查询atp_tag_info表所有数据
     *
     * @return
     */
    public List<AtpTagInfoVo> queryTagInfo(String caseId, String projectId) {
        QueryWrapper<AtpRefTagUseCase> taginfoQueryWrapper = new QueryWrapper<>();
        QueryWrapper<AtpRefTagUseCase> useCaseQueryWrapper = taginfoQueryWrapper.eq("case_id", caseId).eq("project_id", projectId);
        List<AtpRefTagUseCase> atpRefTagUseCases = atpRefTagUseCaseMapper.selectList(useCaseQueryWrapper);
        List<String> tagInfoCollect = atpRefTagUseCases.stream().map(AtpRefTagUseCase::getTagId).collect(Collectors.toList());

        List<AtpTagInfo> tagInfoList = atpTagInfoMapper.selectByTagIds(tagInfoCollect); // 通过Mapper获取所有tag_info数据

        // 使用Java 8 Stream将AtpTagInfo对象列表直接映射到AtpTagInfoVo对象列表
        List<AtpTagInfoVo> tagInfoVoList = tagInfoList.stream()
                .map(tagInfo -> {
                    AtpTagInfoVo tagInfoVo = new AtpTagInfoVo();
                    BeanUtils.copyProperties(tagInfo, tagInfoVo);
                    return tagInfoVo;
                })
                .collect(Collectors.toList());

        return tagInfoVoList;
    }

    /**
     * 校验atp_tag_info表中是否已存在目标tagKey
     *
     * @param tagKey
     * @return
     */
    public boolean validateTagName(String tagKey) {
        QueryWrapper<AtpTagInfo> queryWrapper = new QueryWrapper<>();
        HashMap<String, Object> queryMap = MapUtil.newHashMap();
        queryMap.put("tag_key", tagKey);
        QueryWrapper<AtpTagInfo> tagInfoQueryWrapper = queryWrapper.allEq(queryMap);
        Long tagInfoCount = atpTagInfoMapper.selectCount(tagInfoQueryWrapper);
        Precondition.checkIndexGreaterZero(Convert.toInt(tagInfoCount), "200000001", tagKey);
        return true;
    }

    /**
     * 插入数据行
     *
     * @param tagInfoDto
     * @return
     */
    public AtpTagInfoVo insert(AtpTagInfoDto tagInfoDto) {
        AtpTagInfo atpTagInfo = new AtpTagInfo();
        //todo 后续要写模型转化convert类
        BeanUtils.copyProperties(tagInfoDto, atpTagInfo);

        atpTagInfo.setId(IdUtil.simpleUUID());
        atpTagInfo.setTagId(IdUtil.simpleUUID());
        atpTagInfo.setEnabled(EnableEnum.VALID.getCode());
        int insertCount = atpTagInfoMapper.insert(atpTagInfo);
        Precondition.checkArgument(insertCount < 1, "000000", "新增标签失败");
        return tagInfoConvert.toVo(atpTagInfo);
    }

    //todo 可能需要再写个insert方法，形参为tagKey

    /**
     * 更新数据行
     *
     * @param tagInfoDto
     * @return
     */
    public boolean update(AtpTagInfoDto tagInfoDto) {

        // 将AtpTagInfoDto转换为AtpTagInfo实体类
        AtpTagInfo atpTagInfo = new AtpTagInfo();
        BeanUtils.copyProperties(tagInfoDto, atpTagInfo);

        Wrapper<AtpTagInfo> updateWrapper = new UpdateWrapper<AtpTagInfo>()
                .eq("id", atpTagInfo.getId());

        // 调用AtpTagInfoMapper的更新方法来更新数据
        int rowsUpdated = atpTagInfoMapper.update(atpTagInfo, updateWrapper);

        // 根据更新的行数判断更新是否成功
        return rowsUpdated > 0;
    }

    public AtpTagInfo queryByTagKey(String tagKey) {
        QueryWrapper<AtpTagInfo> queryWrapper = new QueryWrapper<>();
        HashMap<String, Object> queryMap = MapUtil.newHashMap();
        queryMap.put("tag_key", tagKey);
        QueryWrapper<AtpTagInfo> tagInfoQueryWrapper = queryWrapper.allEq(queryMap);
        AtpTagInfo atpTagInfo = atpTagInfoMapper.selectOne(tagInfoQueryWrapper);
        return atpTagInfo;
    }

    public void processAtpTagInfoList(List<AtpTagInfoDto> atpTagInfoDtoList) {
        for (AtpTagInfoDto dto : atpTagInfoDtoList) {
            // 根据tagKey属性查询数据库表
            if (!validateTagName(dto.getTagKey())) {
                // 不存在具有相同tagKey属性的记录，执行新增操作
                insert(dto);
            }
            // 如果记录已存在，不执行任何操作
        }
    }

    /**
     * 查找一个AtpTagInfoDto的List中是否存在相同tagKey属性的元素
     *
     * @param dtoList
     * @return
     */
    public static boolean hasDuplicateTagKey(List<AtpTagInfoDto> dtoList) {
        Set<String> seenKeys = new HashSet<>();

        for (AtpTagInfoDto dto : dtoList) {
            if (seenKeys.contains(dto.getTagKey())) {
                return false; // 如果找到相同的key，返回true
            }
            seenKeys.add(dto.getTagKey());
        }

        return true; // 如果没有找到相同的key，返回false
    }

    public List<AtpTagInfo> queryByTagIds(List<String> tagIds) {
        List<AtpTagInfo> atpTagInfos = atpTagInfoMapper.selectByTagIds(tagIds);
        return atpTagInfos;
    }

    public void deleteByTagId(String tagId) {
        atpTagInfoMapper.deleteByTagId(tagId);
    }


    @Transactional
    public boolean deleteTagAndRef(String tagId) {
        //先删tag_info里的数据
        int deleteTagInfoByIdCount = atpTagInfoMapper.deleteThroughId(tagId);
        if (deleteTagInfoByIdCount > 0) {
            //再删关联表数据
            int deleteRefByTagIdCount = atpRefTagUseCaseMapper.deleteByTagId(tagId);
            return deleteRefByTagIdCount >= 0;
        }
        return false;
    }

    public List<AtpTagInfoVo> queryTagInfoAll() {
        // 通过Mapper获取所有tag_info数据
        List<AtpTagInfo> tagInfoList = atpTagInfoMapper.selectAllTagInfo();

        // 使用Java 8 Stream将AtpTagInfo对象列表直接映射到AtpTagInfoVo对象列表
        List<AtpTagInfoVo> tagInfoVoList = tagInfoList.stream()
                .map(tagInfo -> {
                    AtpTagInfoVo tagInfoVo = new AtpTagInfoVo();
                    BeanUtils.copyProperties(tagInfo, tagInfoVo);
                    return tagInfoVo;
                })
                .collect(Collectors.toList());

        return tagInfoVoList;
    }

    public List<AtpTagInfo> queryByFolderId(String folderId) {
        List<AtpTagInfo> atpTagInfos = atpTagInfoMapper.selectByFolderId(folderId);
        return atpTagInfos;
    }
}
