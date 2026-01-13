package ch.bbw.er.backend.user;

import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO extends UserSignInDTO {
    @NotBlank(message = "Username can't be blank")
    private String username;

    public @NotBlank(message = "Username can't be blank") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username can't be blank") String username) {
        this.username = username;
    }
}
