package rs.raf.demo.repositories.user;

import rs.raf.demo.entities.User;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository{
    @Override
    public User getUser(int userId) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE userId = ?");
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("isAdmin");
                boolean isActive = resultSet.getBoolean("isActive");

                user = new User(userId, email, name, surname, password, isAdmin, isActive);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public void changeUserStatus(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            // Check if user exists:
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE userId = ? ");
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
                throw new Exception();

            boolean isActive = resultSet.getBoolean("isActive");


            // Update user:
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            preparedStatement = connection.prepareStatement("UPDATE user AS u SET u.isActive = ? WHERE u.userId = ?");
            preparedStatement.setBoolean(1, !isActive);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {

                Integer userId = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("isAdmin");
                boolean isActive = resultSet.getBoolean("isActive");

                user = new User(userId, email, name, surname, password, isAdmin, isActive);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public List<User> getUsers(int page) {
        List<User> users = new ArrayList<User>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM user LIMIT 10 OFFSET ?");
            preparedStatement.setInt(1, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer userId = resultSet.getInt("userId");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("isAdmin");
                boolean isActive = resultSet.getBoolean("isActive");

                users.add(new User(userId, email, name, surname, password, isAdmin, isActive));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return users;
    }

    @Override
    public User createUser(String email, String name, String surname, String password, boolean isAdmin) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns={"userId"};
            preparedStatement = connection.prepareStatement("INSERT INTO user (email, name, surname, password, isAdmin, isActive) VALUES (?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, password);
            preparedStatement.setBoolean(5, isAdmin);
            preparedStatement.setBoolean(6, true);

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next())
                user = new User(resultSet.getInt("userId"), email, name, surname, password, isAdmin, true);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public User updateUser(int userId, String email, String name, String surname, boolean isAdmin) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = this.newConnection();

            // Check if news exists:
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE userId = ? ");
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet == null || !resultSet.next())
                throw new Exception();

            // Check if updated name already exists:
            closeStatement(preparedStatement);
            closeResultSet(resultSet);

            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ? ");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet == null || !resultSet.next())
                throw new Exception();

            // Update news:
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            preparedStatement = connection.prepareStatement("UPDATE user AS u SET u.email = ?, u.name = ?, u.surname = ?, u.isAdmin = ? WHERE u.userId = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setBoolean(4, isAdmin);
            preparedStatement.setInt(5, userId);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getResultSet();
            if (resultSet.next())
                user = new User(userId, email, name, surname, resultSet.getString("password"), isAdmin, resultSet.getBoolean("isActive"));

            preparedStatement.close();
            connection.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return user;
    }
}
