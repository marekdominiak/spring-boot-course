package pl.dominussoft.springbootcourse.app.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.dominussoft.springbootcourse.app.application.Context;
import pl.dominussoft.springbootcourse.app.domain.UserAccount;
import pl.dominussoft.springbootcourse.app.domain.UserAccountRepository;

@Component
@RequiredArgsConstructor
public class ContextImpl implements Context {

    private final UserAccountRepository userAccountRepository;

    /**
     * Another alternative would be with retruning optional, discuss what are pros and cons
     * of throwing exception here, or returning null or Optional
     */
    @Override
    public UserAccount loggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUser) {
            String email = ((CustomUser) principal).getUsername();
            return userAccountRepository.findByEmailIgnoreCase(email);
        }
        return null;
    }
}
