package rs.raf.demo.repositories.commets;

import rs.raf.demo.entities.Comment;

import java.util.List;

public interface CommentRepository {

    List<Comment> getNewsComments(int newsId);

    Comment createComment(int newsId, String authorName, String content);
}
