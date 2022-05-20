package com.xiaobai.pms.repository.business;


import com.xiaobai.pms.model.business.Scheme;
import com.xiaobai.pms.model.common.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
public interface SchemeRepository extends CrudRepository<Scheme, Long>, JpaSpecificationExecutor<Scheme> {
    Page<Scheme> findByNameContaining(String title, Pageable pageable);
}
