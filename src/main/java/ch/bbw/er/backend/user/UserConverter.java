package ch.bbw.er.backend.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;

public class UserConverter implements Converter<Jwt, UserAuthenticationToken> {
    @Override
    public UserAuthenticationToken convert(Jwt jwt) {
        int id = Integer.parseInt(jwt.getSubject());
        String username = jwt.getClaimAsString("username");
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return new UserAuthenticationToken(jwt, user);
    }
}
