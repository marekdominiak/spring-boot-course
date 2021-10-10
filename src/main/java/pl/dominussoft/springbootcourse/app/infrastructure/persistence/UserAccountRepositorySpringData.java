package pl.dominussoft.springbootcourse.app.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;
import pl.dominussoft.springbootcourse.app.domain.UserAccount;
import pl.dominussoft.springbootcourse.app.domain.UserAccountRepository;

import java.util.UUID;

public interface UserAccountRepositorySpringData extends CrudRepository<UserAccount, UUID>, UserAccountRepository {
}
