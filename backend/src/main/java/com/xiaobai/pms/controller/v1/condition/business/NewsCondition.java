package com.xiaobai.pms.controller.v1.condition.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xiaobai.pms.controller.v1.condition.common.BaseCondition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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
public class NewsCondition extends BaseCondition {
    private String name;

    public String getName() {
        if (this.name == null) {
            return "";
        }
        return this.name;
    }
}
