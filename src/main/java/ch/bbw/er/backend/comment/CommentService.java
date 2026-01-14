package ch.bbw.er.backend.comment;

import ch.bbw.er.backend.exception.BadRequestException;
import ch.bbw.er.backend.post.Post;
import ch.bbw.er.backend.post.PostService;
import ch.bbw.er.backend.user.User;
import ch.bbw.er.backend.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository,
                          PostService postService,
                          UserService userService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public CommentResponseDTO create(CommentRequestDTO request) {
        if (request.getText() == null || request.getText().isBlank()) {
            throw new BadRequestException("Kommentartext darf nicht leer sein");
        }

        Post post = postService.findByIdEntity(request.getPostId());
        User author = userService.findById(request.getAuthorId());

        Comment comment = new Comment(post, author, request.getText());
        Comment saved = commentRepository.save(comment);

        return new CommentResponseDTO(
                saved.getId(),
                saved.getText(),
                saved.getCreatedAt(),
                author.getId(),
                author.getUsername()
        );
    }

    public List<CommentResponseDTO> findByPost(Integer postId) {
        Post post = postService.findByIdEntity(postId);
        return commentRepository.findByPost(post).stream()
                .map(c -> new CommentResponseDTO(
                        c.getId(),
                        c.getText(),
                        c.getCreatedAt(),
                        c.getAuthor().getId(),
                        c.getAuthor().getUsername()
                ))
                .collect(Collectors.toList());
    }
}
