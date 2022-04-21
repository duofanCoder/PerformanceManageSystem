package com.xiaobai.pms.repository.common;

import com.xiaobai.pms.model.business.Record;
import com.xiaobai.pms.model.common.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Arpit Khandelwal.
 */
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);
}
