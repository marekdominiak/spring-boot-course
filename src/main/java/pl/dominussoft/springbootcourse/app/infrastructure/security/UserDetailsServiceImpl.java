package pl.dominussoft.springbootcourse.app.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dominussoft.springbootcourse.app.domain.UserAccount;
import pl.dominussoft.springbootcourse.app.domain.UserAccountRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userAccountRepository.findByEmailIgnoreCase(username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        Set<GrantedAuthority> authorities = user.getRole().getAuthorities().stream().map(
                authority -> new SimpleGrantedAuthority(authority.name())
        ).collect(Collectors.toSet());
        boolean enabled = user.isEnabled();

        return new CustomUser(
                user.getEmail(),
                user.getPassword(),
                enabled,
                true,
                true,
                true,
                authorities,
                user.getId());
    }
}
