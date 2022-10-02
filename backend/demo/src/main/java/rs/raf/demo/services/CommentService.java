package rs.raf.demo.services;

import rs.raf.demo.entities.Comment;
import rs.raf.demo.repositories.commets.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepository repository;

    public List<Comment> getNewsComments(int newsId) {
        return repository.getNewsComments(newsId);
    }

    public Comment createComment(int newsId, String authorName, String content) {
        return repository.createComment(newsId, authorName, content);
    }
}
