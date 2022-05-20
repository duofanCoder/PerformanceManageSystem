package com.xiaobai.pms.controller.v1.request;


import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xiaobai.pms.model.business.Group;
import com.xiaobai.pms.model.enums.BizFrequency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchemeRequest {
    private Long id;
    @NotNull(message = "{constraints.NotEmpty.message}")
    private String name;
    @NotNull(message = "{constraints.NotEmpty.message}")
    private String description;
    private Long methodId;
    private Long groupId;
    private Long[] quotaIds;
    @JsonEnumDefaultValue
    private BizFrequency frequency;
}
