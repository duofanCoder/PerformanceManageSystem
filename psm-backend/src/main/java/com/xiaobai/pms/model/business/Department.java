package com.xiaobai.pms.model.business;

import com.xiaobai.pms.model.common.User;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/28
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "biz_department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createTime;
    private Date updateTime;
    private String name;

    private String description;

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<User> userList;

}
