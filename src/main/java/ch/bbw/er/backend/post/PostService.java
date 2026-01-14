package ch.bbw.er.backend.post;

import ch.bbw.er.backend.exception.NotFoundException;
import ch.bbw.er.backend.user.User;
import ch.bbw.er.backend.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository,
                       UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public PostResponseDTO create(PostRequestDTO request) {
        User author = userService.findById(request.getAuthorId());

        Post post = new Post(author, request.getText(), request.getImageId());
        Post saved = postRepository.save(post);

        return mapToResponse(saved);
    }

    public List<PostResponseDTO> findAll() {
        return postRepository.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Post findByIdEntity(Integer id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post mit ID " + id + " nicht gefunden"));
    }

    public PostResponseDTO findById(Integer id) {
        Post post = findByIdEntity(id);
        return mapToResponse(post);
    }

    public void deleteById(Integer id) {
        if (!postRepository.existsById(id)) {
            throw new NotFoundException("Post mit ID " + id + " nicht gefunden");
        }
        postRepository.deleteById(id);
    }

    private PostResponseDTO mapToResponse(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getText(),
                post.getImageId(),
                post.getCreatedAt(),
                post.getAuthor().getId(),
                post.getAuthor().getUsername()
        );
    }
}
