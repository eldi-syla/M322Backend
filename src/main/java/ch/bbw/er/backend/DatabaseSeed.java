package ch.bbw.er.backend;

import ch.bbw.er.backend.comment.Comment;
import ch.bbw.er.backend.comment.CommentRepository;
import ch.bbw.er.backend.post.Post;
import ch.bbw.er.backend.post.PostRepository;
import ch.bbw.er.backend.user.User;
import ch.bbw.er.backend.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeed implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeed(
            UserRepository userRepository,
            PostRepository postRepository,
            CommentRepository commentRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (userRepository.count() > 0) {
            return;
        }

        // ---------- USERS (11) ----------
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            User u = new User();
            u.setUsername("user" + i);
            u.setEmail("user" + i + "@example.com");
            u.setPassword(passwordEncoder.encode("Password123!"));
            users.add(u);
        }
        userRepository.saveAll(users);

        // ---------- POSTS (11) ----------
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Post p = new Post(
                    users.get(i),
                    "Das ist Test-Post Nummer " + (i + 1),
                    i % 2 == 0 ? i : null
            );
            posts.add(p);
        }
        postRepository.saveAll(posts);

        // ---------- COMMENTS (11) ----------
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Comment c = new Comment(
                    posts.get(i),
                    users.get((i + 1) % users.size()),
                    "Kommentar " + (i + 1) + " zu Post " + (i + 1)
            );
            comments.add(c);
        }
        commentRepository.saveAll(comments);
    }
}
