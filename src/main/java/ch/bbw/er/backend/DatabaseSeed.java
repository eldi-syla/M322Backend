package ch.bbw.er.backend;

import ch.bbw.er.backend.comment.Comment;
import ch.bbw.er.backend.comment.CommentRepository;
import ch.bbw.er.backend.post.Post;
import ch.bbw.er.backend.post.PostRepository;
import ch.bbw.er.backend.user.User;
import ch.bbw.er.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
public class DatabaseSeed implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    
    private PasswordEncoder passwordEncoder;

    public DatabaseSeed(UserRepository userRepository,
                        PostRepository postRepository,
                        CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
       
        if (userRepository.count() > 0 || postRepository.count() > 0 || commentRepository.count() > 0) {
            return;
        }

        
        List<User> users = new ArrayList<>();
        users.add(makeUser("eldi", "eldi@bbw.ch", "Test1234!"));
        users.add(makeUser("lina", "lina@bbw.ch", "Test1234!"));
        users.add(makeUser("marco", "marco@bbw.ch", "Test1234!"));
        users.add(makeUser("nermin", "nermin@bbw.ch", "Test1234!"));
        users.add(makeUser("oliver", "oliver@bbw.ch", "Test1234!"));
        users.add(makeUser("shion", "shion@bbw.ch", "Test1234!"));
        users.add(makeUser("eva", "eva@bbw.ch", "Test1234!"));
        users.add(makeUser("jonas", "jonas@bbw.ch", "Test1234!"));
        users.add(makeUser("ursula", "ursula@bbw.ch", "Test1234!"));
        users.add(makeUser("sam", "sam@bbw.ch", "Test1234!"));
        users.add(makeUser("lea", "lea@bbw.ch", "Test1234!"));
        users.add(makeUser("noah", "noah@bbw.ch", "Test1234!"));

        userRepository.saveAll(users);

     
        List<Post> posts = new ArrayList<>();
        posts.add(makePost(users.get(0), "Erster Post", "Heute habe ich das Backend initialisiert und die API getestet.", LocalDateTime.now().minusDays(12)));
        posts.add(makePost(users.get(1), "Spring Boot Setup", "Gradle Wrapper laeuft, als naechstes kommen Testdaten und Postman.", LocalDateTime.now().minusDays(11)));
        posts.add(makePost(users.get(2), "Repository Methoden", "Ich habe findById, findAll und Custom Queries umgesetzt.", LocalDateTime.now().minusDays(10)));
        posts.add(makePost(users.get(3), "DTOs", "RequestDTO und ResponseDTO machen die API sauberer und sicherer.", LocalDateTime.now().minusDays(9)));
        posts.add(makePost(users.get(4), "Security", "JWT Auth ist eingebaut. Login gibt ein Token zurueck.", LocalDateTime.now().minusDays(8)));
        posts.add(makePost(users.get(5), "Kommentare", "Comments sind mit Posts verknuepft und werden nach Datum sortiert.", LocalDateTime.now().minusDays(7)));
        posts.add(makePost(users.get(6), "OpenAPI", "Swagger / OpenAPI Doku hilft beim Testen der Endpoints.", LocalDateTime.now().minusDays(6)));
        posts.add(makePost(users.get(7), "Controller", "ResponseEntity mit passenden Statuscodes ist umgesetzt.", LocalDateTime.now().minusDays(5)));
        posts.add(makePost(users.get(8), "Fehlerbehandlung", "NotFound und BadRequest muessen einheitlich gemappt werden.", LocalDateTime.now().minusDays(4)));
        posts.add(makePost(users.get(9), "Seed Daten", "Testdaten werden beim Start automatisch erstellt, wenn DB leer ist.", LocalDateTime.now().minusDays(3)));
        posts.add(makePost(users.get(10), "Postman Tests", "Alle Endpoints werden in Postman als Collection exportiert.", LocalDateTime.now().minusDays(2)));
        posts.add(makePost(users.get(11), "JUnit Tests", "Ein paar automatische Tests sollen build und test gruen machen.", LocalDateTime.now().minusDays(1)));

        postRepository.saveAll(posts);

        
        List<Comment> comments = new ArrayList<>();
        comments.add(makeComment(posts.get(0), users.get(1), "Nice, sieht nach einem sauberen Start aus!", LocalDateTime.now().minusDays(11)));
        comments.add(makeComment(posts.get(1), users.get(0), "Wrapper ist der richtige Weg, nicht gradle global.", LocalDateTime.now().minusDays(11)));
        comments.add(makeComment(posts.get(2), users.get(3), "Achte auf Optional und saubere NotFound Antworten.", LocalDateTime.now().minusDays(10)));
        comments.add(makeComment(posts.get(3), users.get(2), "DTOs retten dir spaeter extrem viel Zeit.", LocalDateTime.now().minusDays(9)));
        comments.add(makeComment(posts.get(4), users.get(4), "JWT passt, aber vergiss Refresh / Ablauf nicht zu dokumentieren.", LocalDateTime.now().minusDays(8)));
        comments.add(makeComment(posts.get(5), users.get(5), "Sortierung nach createdAt ist genau richtig.", LocalDateTime.now().minusDays(7)));
        comments.add(makeComment(posts.get(6), users.get(6), "OpenAPI Screenshot in die Doku ist ein Pluspunkt.", LocalDateTime.now().minusDays(6)));
        comments.add(makeComment(posts.get(7), users.get(7), "HTTP Codes konsequent halten: 200, 201, 204, 400, 404.", LocalDateTime.now().minusDays(5)));
        comments.add(makeComment(posts.get(8), users.get(8), "Global Exception Handler waere jetzt der naechste Schritt.", LocalDateTime.now().minusDays(4)));
        comments.add(makeComment(posts.get(9), users.get(9), "Seed nur wenn DB leer ist: perfekt.", LocalDateTime.now().minusDays(3)));
        comments.add(makeComment(posts.get(10), users.get(10), "Postman Collection ins Repo nicht vergessen.", LocalDateTime.now().minusDays(2)));
        comments.add(makeComment(posts.get(11), users.get(11), "JUnit Tests sollen im CI auch laufen.", LocalDateTime.now().minusDays(1)));

        commentRepository.saveAll(comments);
    }

    private User makeUser(String username, String email, String rawPassword) {
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);

        String pw = rawPassword;
        if (passwordEncoder != null) {
            pw = passwordEncoder.encode(rawPassword);
        }
        
        u.setPassword(pw);


        return u;
    }

    private Post makePost(User owner, String title, String content, LocalDateTime createdAt) {
        Post p = new Post();
        p.setUser(owner);         
        p.setTitle(title);
        p.setContent(content);
        p.setCreatedAt(createdAt); 
        return p;
    }

    private Comment makeComment(Post post, User user, String text, LocalDateTime createdAt) {
        Comment c = new Comment();
        c.setPost(post);
        c.setUser(user);
        c.setContent(text);        
        c.setCreatedAt(createdAt); 
        return c;
    }
}
