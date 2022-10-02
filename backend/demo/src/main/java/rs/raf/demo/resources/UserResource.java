package rs.raf.demo.resources;

import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.demo.entities.User;
import rs.raf.demo.requests.LoginRequest;
import rs.raf.demo.requests.UpdateUserRequest;
import rs.raf.demo.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService service;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest) {
        Map<String, String> response = new HashMap<>();

        String jwt = service.login(loginRequest.getEmail(), DigestUtils.sha256Hex(loginRequest.getPassword()));
        if (jwt == null) {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);

        return Response.ok(response).build();
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userId") int userId) {
        return service.getUser(userId);
    }

    @PUT
    @Path("/change-status/{userId}")
    public void changeUserStatus(@PathParam("userId") int userId) {
        service.changeUserStatus(userId);
    }

    @GET
    @Path("/user-by-email")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserByEmail(@QueryParam("email") String email) {
        return service.getUserByEmail(email);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(@QueryParam("page") int page) {
        return service.getUsers(page);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(@Valid User user) {
        return service.createUser(user.getEmail(), user.getName(), user.getSurname(), DigestUtils.sha256Hex(user.getPassword()), user.isAdmin());
    }

    @PUT
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(@PathParam("userId") int userId, @Valid UpdateUserRequest user) {
        return service.updateUser(userId, user.getEmail(), user.getName(), user.getSurname(), user.isAdmin());
    }
}