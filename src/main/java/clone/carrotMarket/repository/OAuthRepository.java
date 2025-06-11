package clone.carrotMarket.repository;

import clone.carrotMarket.domain.AuthProvideerEnum;
import clone.carrotMarket.domain.OAuthAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthRepository extends JpaRepository<OAuthAccount, Long> {

    Optional<OAuthAccount> findByEmailAndProvider(String email, AuthProvideerEnum provideerEnum);
}
