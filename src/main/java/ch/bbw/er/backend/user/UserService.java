package ch.bbw.er.backend.user;

import ch.bbw.er.backend.configuration.JWTConfiguration;
import ch.bbw.er.backend.exception.ConflictException;
import ch.bbw.er.backend.exception.NotFoundException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JWTConfiguration jwtConfiguration;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JWTConfiguration jwtConfiguration, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtConfiguration = jwtConfiguration;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public String generateToken(User user) {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId() + "")
                .claim("username", user.getUsername())
                .expirationTime(new Date(new Date().getTime() + jwtConfiguration.getExpiration()))
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.parse(jwtConfiguration.getAlgorithm())), claimsSet);
        try {
            JWSSigner signer = new MACSigner(jwtConfiguration.getSecret());
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsByEmailOrUsername(String email, String username) {
        return userRepository.existsByUsernameOrEmail(email, username);
    }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> createAll(List<User> users) {
        users.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
        return userRepository.saveAll(users);
    }

    public User update(User changingUser, Integer id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User mit ID " + id + " nicht gefunden");
        }
        changingUser.setId(id);
        try {
            return userRepository.save(changingUser);
        } catch (Exception e) {
            // z. B. Unique-Constraints (email/username) -> Konflikt
            throw new ConflictException("Konflikt beim Aktualisieren des Users");
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User mit ID " + id + " nicht gefunden"));
    }

    public void deleteById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User mit ID " + id + " nicht gefunden");
        }
        userRepository.deleteById(id);
    }
}
