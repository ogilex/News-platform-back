package rs.raf.demo.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Comment {

    private int commentId;

    @NotNull(message = "News is required")
    private Integer newsId;

    @NotNull(message = "Author field is required")
    @NotEmpty(message = "Author field is required")
    private String authorName;

    @NotNull(message = "Content field is required")
    @NotEmpty(message = "Content field is required")
    private String content;

    private Date created;

    public Comment() {}

    public Comment(Integer newsId, String authorName, String content) {
        this.newsId = newsId;
        this.authorName = authorName;
        this.content = content;
    }

    public Comment(int commentId, Integer newsId, String authorName, String content, Date created) {
        this.commentId = commentId;
        this.newsId = newsId;
        this.authorName = authorName;
        this.content = content;
        this.created = created;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
