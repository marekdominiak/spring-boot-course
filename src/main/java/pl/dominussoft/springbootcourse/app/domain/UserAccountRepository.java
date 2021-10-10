package pl.dominussoft.springbootcourse.app.domain;

import lombok.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository {
    UserAccount findByEmailIgnoreCase(@NonNull String username);

    Optional<UserAccount> findById(UUID uuid);

    <S extends UserAccount> S save(S entity);
}
