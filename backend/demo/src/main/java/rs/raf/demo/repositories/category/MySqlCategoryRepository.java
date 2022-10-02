package rs.raf.demo.repositories.category;

import rs.raf.demo.entities.Category;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCategoryRepository extends MySqlAbstractRepository implements CategoryRepository {
    @Override
    public Category findById(int categoryId) {
        Category category = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM category WHERE categoryId = ?");
            preparedStatement.setInt(1, categoryId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                category = new Category(categoryId, name, description);
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

        return category;
    }

    @Override
    public List<Category> getCategories(int page) {
        List<Category> categories = new ArrayList<Category>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM category LIMIT 10 OFFSET ?");
            preparedStatement.setInt(1, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int categoryId = resultSet.getInt("categoryId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                categories.add(new Category(categoryId, name, description));
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

        return categories;
    }

    @Override
    public Category createCategory(String name, String description) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Category category = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM category AS c where c.name = ? ");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                preparedStatement.close();
                resultSet.close();

                String[] generatedColumns={"categoryId"};
                preparedStatement = connection.prepareStatement("INSERT INTO category (name, description) VALUES (?,?)", generatedColumns);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, description);

                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next())
                    category = new Category(resultSet.getInt(1), name, description);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return category;
    }

    @Override
    public Category updateCategory(int categoryId, String name, String description) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Category category = null;
        try {
            connection = this.newConnection();

            // Check if category exists:
            preparedStatement = connection.prepareStatement("SELECT * FROM category WHERE categoryId = ? ");
            preparedStatement.setInt(1, categoryId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet == null || !resultSet.next())
                throw new Exception();

            // Check if updated name already exists:
            closeStatement(preparedStatement);
            closeResultSet(resultSet);

            preparedStatement = connection.prepareStatement("SELECT * FROM category WHERE name = ? ");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                throw new Exception();


            // Update category:
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            preparedStatement = connection.prepareStatement("UPDATE category AS c SET c.name = ?, c.description = ? WHERE c.categoryId = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, categoryId);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getResultSet();
            if (resultSet.next())
                category = new Category(categoryId, name, description);

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

        return category;
    }

    @Override
    public Integer getCategoryCount() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer count = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS total FROM category");
            resultSet = preparedStatement.executeQuery();
            count = resultSet.getInt("total");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return count;
    }

    @Override
    public void deleteCategory(int categoryId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS total FROM news as n WHERE n.categoryId = ?");
            preparedStatement.setInt(1, categoryId);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int count = resultSet.getInt("total");
            if (count > 0)
                throw new Exception();

            closeStatement(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM category WHERE categoryId = ?");
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
