package ch.bbw.er.backend.user;


public class UserMapper {
    public static User fromDTO(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        return user;
    }

    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static TokenResponseDTO toDTO(User user, String token) {
        TokenResponseDTO dto = new TokenResponseDTO();
        dto.setUser(toDTO(user));
        dto.setToken(token);
        return dto;
    }
}
