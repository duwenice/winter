package com.dw.winter.biz.es.controller;

import com.dw.winter.biz.enums.EsIndexEnum;
import com.dw.winter.commom.base.IdDTO;
import com.dw.winter.common.base.CommonResponse;
import com.dw.winter.es.EsTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author duwen
 */
@RestController
@RequestMapping("/es")
public class EsController {

    @Resource
    private EsTemplate esTemplate;

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class EsDTO extends IdDTO {

        private String name;

        private Integer age;

        private String city;
    }


    @GetMapping("/index")
    public CommonResponse<?> index() {
        EsDTO esDTO = new EsDTO()
                .setName("duwen")
                .setAge(23)
                .setCity("changsha");
        esDTO.setId("2");
        esTemplate.insert(esDTO, EsIndexEnum.USER.getIndexName());
        return CommonResponse.success();
    }

    @GetMapping("/update")
    public CommonResponse<?> update() {
        EsDTO esDTO = new EsDTO()
                .setAge(24);
        esDTO.setId("2");
        esTemplate.updateByDocId(esDTO, EsIndexEnum.USER.getIndexName());
        return CommonResponse.success();
    }

    @GetMapping("/query")
    public CommonResponse<?> query() {
        Map<String, Object> queryMap = new HashMap<>(4);
        queryMap.put("age", 24);
        return CommonResponse.success(esTemplate.query(queryMap, EsIndexEnum.USER.getIndexName(), EsDTO.class));
    }
}
