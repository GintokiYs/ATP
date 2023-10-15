package com.hundsun.atp.servers.service.business;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.vo.folder.AtpCommonFolderVo;
import com.hundsun.atp.common.enums.EnableEnum;
import com.hundsun.atp.common.enums.FolderTypeEnum;
import com.hundsun.atp.common.util.Precondition;
import com.hundsun.atp.persister.mapper.AtpCommonFolderMapper;
import com.hundsun.atp.persister.mapper.AtpUseCaseMapper;
import com.hundsun.atp.persister.model.AtpCommonFolder;
import com.hundsun.atp.persister.model.AtpUseCase;
import com.hundsun.atp.servers.service.convert.CommonFolderConvert;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * <p>
 * 通用文件夹处理类
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@Service
public class AtpCommonFolderBusiness extends ServiceImpl<AtpCommonFolderMapper, AtpCommonFolder> {

    @Autowired
    private AtpCommonFolderMapper atpCommonFolderMapper;

    @Autowired
    private AtpUseCaseMapper atpUseCaseMapper;

    @Autowired
    private CommonFolderConvert commonFolderConvert;

    public AtpCommonFolderVo create(AtpCommonFolderDto folderDto) {
        // 根据空间Id、文件夹类型及文件夹名称 校验是否存在同名
        FolderTypeEnum folderTypeEnum = FolderTypeEnum.getByCode(folderDto.getFolderType());
        Precondition.checkArgument(!validateFolderName(folderDto.getFolderName(), folderDto.getProjectId(), folderTypeEnum), "10000013");

        String parentId = folderDto.getParentId();
        QueryWrapper<AtpCommonFolder> parentFolderQueryWrapper = new QueryWrapper<>();
        HashMap<String, Object> parentFolderQueryMap = MapUtil.newHashMap();

        parentFolderQueryMap.put("id", parentId);
        parentFolderQueryMap.put("enabled", EnableEnum.VALID.getCode());
        AtpCommonFolder parentFolder = atpCommonFolderMapper.selectOne(parentFolderQueryWrapper.allEq(parentFolderQueryMap));
        Precondition.checkNotNull(parentFolder, "10000002", "父节点");

        // 只有父节点为PROJECT的才能创建项目文件夹(PROJECT)
        if (FolderTypeEnum.PROJECT == folderTypeEnum &&
                !FolderTypeEnum.PROJECT.getCode().equals(parentFolder.getFolderType())) {
            throw Precondition.exceptionHandle("10000114");
        }

        // 只有父节点为PROJECT的才能创建用例类别文件夹(CATEGORY)
        if (FolderTypeEnum.CATEGORY == folderTypeEnum &&
                !FolderTypeEnum.PROJECT.getCode().equals(parentFolder.getFolderType())) {
            throw Precondition.exceptionHandle("10000114");
        }

        // 只有父节点为CATEGORY的才能创建用例集(USECASE)
        if (FolderTypeEnum.USECASE == folderTypeEnum &&
                !FolderTypeEnum.CATEGORY.getCode().equals(parentFolder.getFolderType())) {
            throw Precondition.exceptionHandle("10000115");
        }
        folderDto.setParentUtreeid(parentFolder.getUtreeid());


        return insert(folderDto);
    }

    public AtpCommonFolderVo insert(AtpCommonFolderDto folderDto) {
        AtpCommonFolder atpCommonFolder = commonFolderConvert.toModel(folderDto);

        String parentId = folderDto.getParentId();
        String folderName = folderDto.getFolderName();

        // 判断父文件夹是否存在
        AtpCommonFolder commonFolder = atpCommonFolderMapper.selectById(parentId);
        Precondition.checkNotNull(commonFolder.getId(), "10000002", "folder_id");
        validateFolderName(folderDto.getProjectId(), folderName, FolderTypeEnum.getByCode(folderDto.getFolderType()));
        atpCommonFolder.setParentUtreeid(commonFolder.getUtreeid());

        // 生成uid
        atpCommonFolder.setId(IdUtil.simpleUUID());
        atpCommonFolder.setUtreeid(IdUtil.simpleUUID());
        atpCommonFolder.setProjectId(folderDto.getProjectId());
        atpCommonFolder.setCreateUser(folderDto.getOperatorCode());
        atpCommonFolder.setCreateTime(new Date());
        atpCommonFolder.setUpdateUser(folderDto.getOperatorCode());
        atpCommonFolder.setUpdateTime(new Date());

        // 插入数据
        atpCommonFolderMapper.insert(atpCommonFolder);
        AtpCommonFolderVo atpCommonFolderVo = commonFolderConvert.toVo(atpCommonFolder);

        return atpCommonFolderVo;
    }

    public Boolean delete(String id, Integer folderType, String operatorCode) {
        try {
            // 检查该目录下是否还有子文件夹
            FolderTypeEnum folderTypeEnum = FolderTypeEnum.getByCode(folderType);
            if (folderTypeEnum.equals(FolderTypeEnum.USECASE)) {
                QueryWrapper<AtpUseCase> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("folder_id", id).eq("enabled", EnableEnum.VALID.getCode());
                Long count = atpUseCaseMapper.selectCount(queryWrapper);
                Precondition.checkIndexGreaterZero(Convert.toInt(count), "000000", "检测到用例集下还有测试用例存在，请先删除测试用例");
            } else {
                QueryWrapper<AtpCommonFolder> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("folder_id", id).eq("enabled", EnableEnum.VALID.getCode());
                Long count = atpCommonFolderMapper.selectCount(queryWrapper);
                Precondition.checkIndexGreaterZero(Convert.toInt(count), "000000", "检测到还有子文件夹存在，请先删除子文件夹");
            }
            // 进行删除操作（假删）
            AtpCommonFolder deleteAtpCommonFolder = AtpCommonFolder.builder().id(id).enabled(EnableEnum.DELETE.getCode()).updateUser(operatorCode).updateTime(DateUtil.date()).build();
            atpCommonFolderMapper.updateById(deleteAtpCommonFolder);
            return true;
        } catch (Exception e) {
            log.error("delete folder fail,error message is:");
            return false;
        }


//        //获取删除文件夹的子文件夹
//        AtpCommonFolder parentFolder = atpCommonFolderMapper.selectById(id);
//        atpCommonFolderMapper.selectById(id).setEnabled(EnableEnum.DELETE.getCode());
//        atpCommonFolderMapper.selectById(id).setUpdateUser(operatorCode);
//        atpCommonFolderMapper.selectById(id).setUpdateTime(new Date());
//        QueryWrapper<AtpCommonFolder> queryWrapper = new QueryWrapper<>();
//        HashMap<String, Object> queryMap = MapUtil.newHashMap();
//
//        queryMap.put("project_id", parentFolder.getProjectId());
//        queryMap.put("enabled", EnableEnum.VALID.getCode());
//        QueryWrapper<AtpCommonFolder> commonFolderQueryWrapper = queryWrapper.allEq(queryMap);
//        List<AtpCommonFolder> atpCommonFolders = atpCommonFolderMapper.selectList(commonFolderQueryWrapper);
//        List<AtpCommonFolderVo> folderVos = commonFolderConvert.toVoList(atpCommonFolders);
//        List<String> childrenIds = getAllChildrenIds(id,folderVos);
//        for(String i : childrenIds){
//            atpCommonFolderMapper.selectById(i).setEnabled(EnableEnum.DELETE.getCode());
//            atpCommonFolderMapper.selectById(i).setUpdateTime(new Date());
//            atpCommonFolderMapper.selectById(i).setUpdateUser(operatorCode);
//        }
//        return true;
    }

    /**
     * 判断是否存在同名文件夹
     *
     * @param projectId
     * @param folderName
     * @param typeEnums
     * @return
     */
    public boolean validateFolderName(String projectId, String folderName, FolderTypeEnum typeEnums) {
        QueryWrapper<AtpCommonFolder> queryWrapper = new QueryWrapper<>();
        HashMap<String, Object> queryMap = MapUtil.newHashMap();

        queryMap.put("project_id", projectId);
        queryMap.put("folder_type", typeEnums.getCode());
        queryMap.put("folder_name", folderName);
        queryMap.put("enabled", EnableEnum.VALID.getCode());
        QueryWrapper<AtpCommonFolder> commonFolderQueryWrapper = queryWrapper.allEq(queryMap);
        Long commonFolderCount = atpCommonFolderMapper.selectCount(commonFolderQueryWrapper);
        Precondition.checkIndexGreaterZero(Convert.toInt(commonFolderCount), "10000003", folderName);
        return true;
    }

    public List<AtpCommonFolderVo> selectFlatFolders(String projectId) {
        // 获取当前空间下所有的Folder
        QueryWrapper<AtpCommonFolder> queryWrapper = new QueryWrapper<>();
        HashMap<String, Object> queryMap = MapUtil.newHashMap();

        queryMap.put("project_id", projectId);
        queryMap.put("enabled", EnableEnum.VALID.getCode());
        QueryWrapper<AtpCommonFolder> commonFolderQueryWrapper = queryWrapper.allEq(queryMap).or().isNull("project_id");
        List<AtpCommonFolder> atpCommonFolders = atpCommonFolderMapper.selectList(commonFolderQueryWrapper);

        return commonFolderConvert.toVoList(atpCommonFolders);
    }

    public AtpCommonFolderVo select(String id) {
        AtpCommonFolder atpCommonFolder = atpCommonFolderMapper.selectById(id);
        if (EnableEnum.VALID.getCode().equals(atpCommonFolder.getEnabled())) {
            AtpCommonFolderVo atpCommonFolderVo = commonFolderConvert.toVo(atpCommonFolder);
            atpCommonFolderVo.setExecuteConfig(JSON.parseObject(atpCommonFolder.getExecuteConfig(), new HashMap<String, String>().getClass()));
            return atpCommonFolderVo;
        }
        return null;
    }

//    public static List<String> getAllChildrenIds(String id, List<AtpCommonFolderVo> folderVos) {
//        List<String> childrenIds = new ArrayList<>();
//        for (AtpCommonFolderVo folderVo : folderVos) {
//            if (folderVo.getParentId() != null && folderVo.getParentId() == id && folderVo.getProjectId() != null) {
//                childrenIds.add(folderVo.getId());
//                childrenIds.addAll(getAllChildrenIds(folderVo.getId(), folderVos));
//            }
//        }
//        return childrenIds;
//    }

    public Boolean update(AtpCommonFolderDto atpCommonFolderDto) {
        // 校验要更新目录的名称是否合规（重名---可以调用validateFolderName方法）
        AtpCommonFolder sourceCommonFolder = atpCommonFolderMapper.selectById(atpCommonFolderDto.getId());
        BeanMap sourceFolderMap = BeanMap.create(sourceCommonFolder);

        if (atpCommonFolderDto.getFolderType() == null || atpCommonFolderDto.getFolderType() == 0 && Integer.valueOf(sourceFolderMap.get("folderType").toString()) != 0) {
            atpCommonFolderDto.setFolderType(commonFolderConvert.toVo(sourceCommonFolder).getFolderType());
        }
        FolderTypeEnum folderTypeEnum = FolderTypeEnum.getByCode(atpCommonFolderDto.getFolderType());
        Precondition.checkArgument(!validateFolderName(atpCommonFolderDto.getFolderName(), atpCommonFolderDto.getProjectId(), folderTypeEnum), "10000013");

        // 更新的话推荐走 智能替换而不是全覆盖
        AtpCommonFolder atpCommonFolder = commonFolderConvert.toModel(atpCommonFolderDto);
        AtpCommonFolder targetCommonFolder = new AtpCommonFolder();
        BeanMap atpFolderMap = BeanMap.create(atpCommonFolder);
        HashMap<String, Object> targetFolderMap = new HashMap<String, Object>();
        targetFolderMap.putAll(atpFolderMap);

        //替换无需更新的字段
        for (String key : targetFolderMap.keySet()) {
            if (sourceFolderMap.get(key) != null && targetFolderMap.get(key) == null) {
                Object value = sourceFolderMap.get(key);
                targetFolderMap.put(key, value);
            }
        }
        try {
            targetCommonFolder = mapToBean(targetFolderMap, AtpCommonFolder.class);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        targetCommonFolder.setUpdateTime(new Date());
        if (targetCommonFolder.getUpdateUser() == null) {
            targetCommonFolder.setUpdateUser(atpCommonFolder.getCreateUser());
        }
        UpdateWrapper<AtpCommonFolder> folderUpdateWrapper = new UpdateWrapper<>();
        HashMap<String, Object> folderUpdateMap = MapUtil.newHashMap();

        folderUpdateMap.put("id", targetCommonFolder.getId());
        atpCommonFolderMapper.update(targetCommonFolder, folderUpdateWrapper.allEq(folderUpdateMap));
        return true;
    }

    public static <T> T mapToBean(HashMap<String, Object> map, Class<T> c) {
        try {
            // 拿到实体类对象
            T t = c.newInstance();
            //1、拆开map,给对象t的属性赋值
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                Field f = c.getDeclaredField(entry.getKey());
                // 设置修改权限，可以拿到private权限的属性
                f.setAccessible(true);
                // 传递参数：第一个为实体类对象，第二个为map集合中的value值
                f.set(t, entry.getValue());
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            //将异常放大，可不用写返回值
            throw new RuntimeException("出错啦！");
        }
    }
}
