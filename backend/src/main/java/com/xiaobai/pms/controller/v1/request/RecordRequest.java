package com.xiaobai.pms.controller.v1.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordRequest {
    private Long id;
    @NotNull(message = "{constraints.NotEmpty.message}")
    private Long schemeId;
    @NotNull(message = "{constraints.NotEmpty.message}")
    private Map<Integer, Integer> scoreMap;
    @NotNull(message = "{constraints.NotEmpty.message}")
    private Long audience;
    private Long owner;
}
