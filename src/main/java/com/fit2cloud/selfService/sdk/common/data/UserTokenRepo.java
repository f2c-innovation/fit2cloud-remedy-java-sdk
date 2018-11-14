package com.fit2cloud.selfService.sdk.common.data;

import com.fit2cloud.selfService.sdk.common.domain.UserToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserTokenRepo extends CrudRepository<UserToken, String> {
}
