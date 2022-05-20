package com.xiaobai.pms.controller.v1.condition.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/4/4
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCondition extends BaseCondition {
    private String name;

    private Long positionId;

    private boolean gender;

    private Long departmentId;

    public String getName() {
        if (this.name == null) {
            return "";
        }
        return this.name;
    }
}
