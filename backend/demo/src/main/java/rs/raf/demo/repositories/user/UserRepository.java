package rs.raf.demo.repositories.user;

import rs.raf.demo.entities.User;

import java.util.List;

public interface UserRepository {

    User getUser(int userId);

    User getUserByEmail(String email);

    List<User> getUsers(int page);

    User createUser(String email, String name, String surname, String password, boolean isAdmin);

    User updateUser(int userId, String email, String name, String surname, boolean isAdmin);

    void changeUserStatus(int userId);
}
