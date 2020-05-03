package com.dw.winter.commom.base;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.dw.winter.biz.enums.YesOrNoEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author duwen
 * @date 2020/4/15
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否已删除
     */
    @TableLogic
    private YesOrNoEnum isDeleted;
}
