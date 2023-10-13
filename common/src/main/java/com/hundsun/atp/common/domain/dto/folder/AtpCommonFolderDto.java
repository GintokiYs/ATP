package com.hundsun.atp.common.domain.dto.folder;


import com.hundsun.atp.common.domain.dto.AtpBaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 通用文件夹
 * </p>
 *
 * @author yeyh
 * @since 2023-09-27
 */
@SuperBuilder
@Data
@Accessors(chain = true)
@ApiModel(value = "AtpCommonFolder对象", description = "通用文件夹")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AtpCommonFolderDto extends AtpBaseDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("父文件夹id")
    private String parentId;

    @ApiModelProperty("文件夹名称")
    private String folderName;

    @ApiModelProperty("文件夹类型")
    private Integer folderType;

    private String parentUtreeid;

    @ApiModelProperty("前端utree展示用")
    private String utreeid;

    @ApiModelProperty("是否生效(-1: 已删除, 0: 未生效, 1: 已生效)")
    private Integer enabled;

    @ApiModelProperty("执行配置,Map<String,String>")
    private Map<String, String> executeConfig;

    @ApiModelProperty("创建者")
    private String createUser;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改者")
    private String updateUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("备注")
    private String remark;
}
