package rs.raf.demo.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Tag {

    private int tagId;

    @NotNull(message = "Keyword field is required")
    @NotEmpty(message = "Keyword field is required")
    private String keyword;

    public Tag() {}

    public Tag(int tagId, String keyword) {
        this.tagId = tagId;
        this.keyword = keyword;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
