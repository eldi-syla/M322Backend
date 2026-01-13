package ch.bbw.er.backend.user;

public class TokenResponseDTO {
    private UserResponseDTO user;
    private String token;

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
