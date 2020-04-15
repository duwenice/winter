package com.dw.winter.biz.suborder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dw.winter.biz.suborder.entity.SubOrderEntity;
import com.dw.winter.biz.suborder.mapper.SubOrderMapper;
import com.dw.winter.biz.suborder.service.ISubOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 子订单表 服务实现类
 * </p>
 *
 * @author duwen
 * @since 2020-04-15
 */
@Service
public class SubOrderServiceImpl extends ServiceImpl<SubOrderMapper, SubOrderEntity> implements ISubOrderService {

}
