package rs.raf.demo.resources;

import rs.raf.demo.entities.Comment;
import rs.raf.demo.services.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService service;

    @GET
    @Path("/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getNewsComments(@PathParam("newsId") int newsId) {
        return service.getNewsComments(newsId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Comment createComment(@Valid Comment comment) {
        return service.createComment(comment.getNewsId(), comment.getAuthorName(), comment.getContent());
    }
}
