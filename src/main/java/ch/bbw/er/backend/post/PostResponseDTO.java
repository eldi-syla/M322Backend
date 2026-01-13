package ch.bbw.er.backend.post;

import java.time.Instant;

public class PostResponseDTO {

    private Integer id;
    private String text;
    private Integer imageId;
    private Instant createdAt;

    private Integer authorId;
    private String authorUsername;

    public PostResponseDTO() {
    }

    public PostResponseDTO(Integer id,
                           String text,
                           Integer imageId,
                           Instant createdAt,
                           Integer authorId,
                           String authorUsername) {
        this.id = id;
        this.text = text;
        this.imageId = imageId;
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

    public Integer getImageId() {
        return imageId;
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
