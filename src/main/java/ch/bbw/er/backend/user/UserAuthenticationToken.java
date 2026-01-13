package ch.bbw.er.backend.user;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;


public class UserAuthenticationToken extends AbstractAuthenticationToken {
    private final User user;
    private final Jwt jwt;

    public UserAuthenticationToken(Jwt jwt, User user) {
        this(jwt, user, List.of());
    }

    public UserAuthenticationToken(Jwt jwt, User user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        this.jwt = jwt;
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}