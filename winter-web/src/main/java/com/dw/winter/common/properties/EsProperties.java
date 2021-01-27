package com.dw.winter.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author duwen
 */
@Data
@ConfigurationProperties(prefix = "es")
public class EsProperties {

    private String host;

    private Integer port;
}
