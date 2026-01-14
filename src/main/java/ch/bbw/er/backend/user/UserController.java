package ch.bbw.er.backend.user;

import ch.bbw.er.backend.exception.ConflictException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping(UserController.PATH)
public class UserController {
    public static final String PATH = "/users";

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    @SecurityRequirements
    public ResponseEntity<?> signUp(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        if (userService.existsByEmailOrUsername(userRequestDTO.getEmail(), userRequestDTO.getUsername())) {
            throw new ConflictException("E-Mail oder Username ist bereits vergeben");
        }
        User user = UserMapper.fromDTO(userRequestDTO);
        user = userService.create(user);
        UserResponseDTO userResponseDTO = UserMapper.toDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PostMapping("/signin")
    @SecurityRequirements
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSignInDTO userSignInDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userSignInDTO.getEmail(), userSignInDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String token = userService.generateToken(user);
        TokenResponseDTO dto = UserMapper.toDTO(user, token);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    public ResponseEntity<?> findById(
            @Parameter(description = "Id of user to get")
            @PathVariable("id") Integer id
    ) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User was deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User could not be deleted",
                    content = @Content)
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "Id of user to delete")
            @PathVariable("id") Integer id
    ) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Find users with a given name. Only not blank inputs are considered, otherwise all users are returned.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))
                    )
            )
    })
    public ResponseEntity<?> findUsers(
            @Parameter(description = "User name to search, leave empty for all")
            @RequestParam(required = false) String name
    ) {
        List<User> users = userService.findAll();

        return ResponseEntity.ok(users.stream()
                .map(UserMapper::toDTO)
                .toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was updated successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the user",
                    content = @Content)
    })
    public ResponseEntity<?> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The user to update")
            @Valid
            @RequestBody UserRequestDTO updateUserDTO,

            @Parameter(description = "Id of user to update")
            @PathVariable Integer id) {

        User updateUser = UserMapper.fromDTO(updateUserDTO);
        User savedUser = userService.update(updateUser, id);
        return ResponseEntity.ok(UserMapper.toDTO(savedUser));
    }
}
