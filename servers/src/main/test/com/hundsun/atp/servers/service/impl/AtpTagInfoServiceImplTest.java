package com.hundsun.atp.servers.service.impl;


import com.hundsun.atp.api.usecase.AtpUsecaseService;
import com.hundsun.atp.api.taginfo.AtpTagInfoService;
import com.hundsun.atp.common.domain.dto.folder.AtpCommonFolderDto;
import com.hundsun.atp.common.domain.dto.tag.AtpTagInfoDto;
import com.hundsun.atp.common.domain.dto.usecase.InterfaceUsecaseDto;
import com.hundsun.atp.common.domain.entity.RpcResultDTO;
import com.hundsun.atp.common.domain.vo.taginfo.AtpTagInfoVo;
import com.hundsun.atp.persister.mapper.AtpTagInfoMapper;
import com.hundsun.atp.persister.model.AtpTagInfo;
import com.hundsun.atp.servers.AtpApplication;
import com.hundsun.atp.servers.service.business.AtpTagInfoBusiness;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtpApplication.class)
public class AtpTagInfoServiceImplTest {

    @Autowired
    private AtpTagInfoService atpTagInfoService;

    @Autowired
    private AtpTagInfoMapper atpTagInfoMapper;

    @Autowired
    private AtpUsecaseService atpUsecaseService;

    @Autowired
    private AtpTagInfoBusiness atpTagInfoBusiness;

    @Test
    public void testCreateTagInfo() {
        AtpTagInfoDto atpTagInfoDto = new AtpTagInfoDto();
        atpTagInfoDto.setTagId("testTagId");
        atpTagInfoDto.setTagKey("标签Test");
        atpTagInfoDto.setProjectId("空间Test");
//        RpcResultDTO<Boolean> resultCreate = atpTagInfoService.createTagInfo(atpTagInfoDto);
        RpcResultDTO<List<AtpTagInfoVo>> listRpcResultDTO = atpTagInfoService.queryTagInfoAll();
        System.out.println(listRpcResultDTO);
        String id = atpTagInfoMapper.selectByTagId(atpTagInfoDto.getTagId());
        List<String> tagIds = new ArrayList<>();
        tagIds.add("testTagId");
        atpTagInfoMapper.selectByTagIds(tagIds);
        atpTagInfoDto.setId(id);
        atpTagInfoDto.setTagKey("标签Test-update");
        RpcResultDTO<Boolean> resultUpdate = atpTagInfoService.editTagInfo(atpTagInfoDto);
        atpTagInfoService.deleteTagInfo(atpTagInfoDto);
        System.out.println("----------");
    }


    @Test
    public void testTagProcess() {
        // 创建好用例集之后，应该还没有标签
        // 现在要开始新建一个用例
        // 此时新增用例界面会列出已有的标签：传参FodlerDto
        AtpCommonFolderDto atpCommonFolderDto = new AtpCommonFolderDto();//随便怎么传，只要里面包含folderId就行
        // 模拟一个folderId
        atpCommonFolderDto.setId("1000");
        // 返回该用例集的所有标签
        RpcResultDTO<List<AtpTagInfoVo>> folderTagInfos = atpTagInfoService.queryFolderTags(atpCommonFolderDto);

        // 这里先不创建标签，也不选择标签，直接把用例造出来
        // 然后在用例右边的标签按钮这里点进去，选择新增标签：传参tagInfoDto
        AtpTagInfoDto atpTagInfoDto1 = new AtpTagInfoDto();//要求有tagId,tagKey,enable,projectId
        atpTagInfoDto1.setTagId("createTagId1");
        atpTagInfoDto1.setTagKey("标签Test-create1");
        atpTagInfoDto1.setProjectId("空间Test");
        atpTagInfoDto1.setFolderId("1000");

        AtpTagInfoDto atpTagInfoDto2 = new AtpTagInfoDto();//要求有tagId,tagKey,enable,projectId
        atpTagInfoDto2.setTagId("createTagId2");
        atpTagInfoDto2.setTagKey("标签Test-create2");
        atpTagInfoDto2.setProjectId("空间Test");
        atpTagInfoDto2.setFolderId("1000");

        AtpTagInfoDto atpTagInfoDto3 = new AtpTagInfoDto();//要求有tagId,tagKey,enable,projectId
        atpTagInfoDto3.setTagId("createTagId3");
        atpTagInfoDto3.setTagKey("标签Test-create3");
        atpTagInfoDto3.setProjectId("空间Test");
        atpTagInfoDto3.setFolderId("1000");

        AtpTagInfoDto atpTagInfoDto4 = new AtpTagInfoDto();//要求有tagId,tagKey,enable,projectId
        atpTagInfoDto4.setTagId("createTagId4");
        atpTagInfoDto4.setTagKey("标签Test-create4");
        atpTagInfoDto4.setProjectId("空间Test");
        atpTagInfoDto4.setFolderId("1000");

        atpTagInfoService.createTagInfo(atpTagInfoDto1);
        atpTagInfoService.createTagInfo(atpTagInfoDto2);
        // 增完后再查一遍
        RpcResultDTO<List<AtpTagInfoVo>> listRpcResultDTO = atpTagInfoService.queryFolderTags(atpCommonFolderDto);


        atpTagInfoService.createTagInfo(atpTagInfoDto3);
        atpTagInfoService.createTagInfo(atpTagInfoDto4);
        RpcResultDTO<List<AtpTagInfoVo>> listRpcResultDTO1 = atpTagInfoService.queryFolderTags(atpCommonFolderDto);

        // 由于没有编辑标签的选项，这里不做测试

        AtpTagInfo atpTagInfo = atpTagInfoMapper.selectByTagKey(atpTagInfoDto1.getTagKey());
        AtpTagInfoDto atpTagInfoDto = new AtpTagInfoDto();
        BeanUtils.copyProperties(atpTagInfo, atpTagInfoDto);
        // 这里再测标签删除：传参FodlerDto，这里要求dto中的属性id一定要有，是根据这个来删
        atpTagInfoService.deleteTagInfo(atpTagInfoDto);
        // 删完后再查一遍
        RpcResultDTO<List<AtpTagInfoVo>> listRpcResultDTO2 = atpTagInfoService.queryFolderTags(atpCommonFolderDto);

        List<AtpTagInfo> atpTagInfos = atpTagInfoBusiness.queryByFolderId(atpCommonFolderDto.getId());

        ArrayList<AtpTagInfoDto> atpTagInfoDtosAfterQuery = new ArrayList<>();
        for (AtpTagInfo atpTagInfoSing : atpTagInfos) {
            AtpTagInfoDto atpTagInfoDtoTmp = new AtpTagInfoDto();
            BeanUtils.copyProperties(atpTagInfoSing, atpTagInfoDtoTmp);
            atpTagInfoDtosAfterQuery.add(atpTagInfoDtoTmp);
        }

        // 再测选中标签，并加入对应的用例：传参是一个interfaceUsecaseDto
        InterfaceUsecaseDto interfaceUsecaseDto = new InterfaceUsecaseDto();
        interfaceUsecaseDto.setCaseId("2000");
        interfaceUsecaseDto.setFolderId(atpCommonFolderDto.getId());
        ArrayList<AtpTagInfoDto> atpTagInfoDtos = new ArrayList<>();
        for (AtpTagInfoDto tagInfoDto : atpTagInfoDtosAfterQuery) {
            if ("标签Test-create2".equals(tagInfoDto.getTagKey()) || "标签Test-create3".equals(tagInfoDto.getTagKey())) {
                atpTagInfoDtos.add(tagInfoDto);
            }
        }
        interfaceUsecaseDto.setTags(atpTagInfoDtos);
        atpUsecaseService.editUsecaseTags(interfaceUsecaseDto.getTags(),interfaceUsecaseDto.getCaseId());

        // 再去选择一次标签，这次不选择2、3，选择3、4
        ArrayList<AtpTagInfoDto> atpTagInfoDtos1 = new ArrayList<>();
        for (AtpTagInfoDto tagInfoDto : atpTagInfoDtosAfterQuery) {
            if ("标签Test-create3".equals(tagInfoDto.getTagKey()) || "标签Test-create4".equals(tagInfoDto.getTagKey())) {
                atpTagInfoDtos1.add(tagInfoDto);
            }
        }
        interfaceUsecaseDto.setTags(atpTagInfoDtos1);
        atpUsecaseService.editUsecaseTags(interfaceUsecaseDto.getTags(),interfaceUsecaseDto.getCaseId());

        AtpTagInfo atpTagInfoSec = atpTagInfoMapper.selectByTagKey(atpTagInfoDto3.getTagKey());
        AtpTagInfoDto atpTagInfoDtoSec = new AtpTagInfoDto();
        BeanUtils.copyProperties(atpTagInfoSec, atpTagInfoDtoSec);
        atpTagInfoService.deleteTagInfo(atpTagInfoDtoSec);
    }

}