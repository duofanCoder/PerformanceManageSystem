package com.xiaobai.pms.model.business;

import com.xiaobai.pms.model.common.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "biz_record",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"owner_id", "audience_id", "scheme_id"})}
)
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createTime;
    private Date updateTime;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @ManyToOne
    @JoinColumn(name = "audience_id")
    private User audience;
    @ManyToOne
    @JoinColumn(name = "scheme_id")
    private Scheme scheme;
    private String scoreJson;
}
