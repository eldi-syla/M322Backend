package ch.bbw.er.backend.comment;

import ch.bbw.er.backend.post.Post;
import ch.bbw.er.backend.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Post post;

    @ManyToOne(optional = false)
    private User author;

    @Column(nullable = false, length = 1000)
    private String text;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public Comment() {
    }

    public Comment(Post post, User author, String text) {
        this.post = post;
        this.author = author;
        this.text = text;
        this.createdAt = Instant.now();
    }

    public Integer getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public User getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
