package com.cozybinarybase.accountstopthestore.model.member.persist.repository;

import com.cozybinarybase.accountstopthestore.model.member.persist.entity.PasswordReset;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetRepository extends CrudRepository<PasswordReset, Long> {

}
