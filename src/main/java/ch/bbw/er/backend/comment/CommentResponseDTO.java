package ch.bbw.er.backend.comment;

import java.time.Instant;

public class CommentResponseDTO {

    private Integer id;
    private String text;
    private Instant createdAt;
    private Integer authorId;
    private String authorUsername;

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Integer id,
                              String text,
                              Instant createdAt,
                              Integer authorId,
                              String authorUsername) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }
}
