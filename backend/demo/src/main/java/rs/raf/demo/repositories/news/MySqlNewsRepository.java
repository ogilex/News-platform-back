package rs.raf.demo.repositories.news;

import rs.raf.demo.entities.News;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlNewsRepository extends MySqlAbstractRepository implements NewsRepository{
    @Override
    public News findById(int newsId) {
        News news = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE newsId = ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int categoryId = resultSet.getInt("categoryId");
                int authorId = resultSet.getInt("authorId");
                int viewCount = resultSet.getInt("viewCount");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                java.util.Date created = resultSet.getTimestamp("created");

                news = new News(newsId, categoryId, authorId, viewCount, title, content, created);
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

        return news;
    }

    @Override
    public List<News> getRecentNews() {
        List<News> news = new ArrayList<News>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news ORDER BY created DESC LIMIT 10");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int newsId = resultSet.getInt("newsId");
                int categoryId = resultSet.getInt("categoryId");
                int authorId = resultSet.getInt("authorId");
                int viewCount = resultSet.getInt("viewCount");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                java.util.Date created = resultSet.getTimestamp("created");
                news.add(new News(newsId, categoryId, authorId, viewCount, title, content, created));
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

        return news;
    }

    @Override
    public List<News> searchNews(int page, String query) {
        List<News> news = new ArrayList<News>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE title LIKE ? OR content LIKE ? ORDER BY created DESC LIMIT 10 OFFSET ?");
            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");
            preparedStatement.setInt(3, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int newsId = resultSet.getInt("newsId");
                int categoryId = resultSet.getInt("categoryId");
                int authorId = resultSet.getInt("authorId");
                int viewCount = resultSet.getInt("viewCount");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                java.util.Date created = resultSet.getTimestamp("created");
                news.add(new News(newsId, categoryId, authorId, viewCount, title, content, created));
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

        return news;
    }

    @Override
    public List<News> getNewsForCategory(int page, int categoryId) {
        List<News> news = new ArrayList<News>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE categoryId = ? ORDER BY created DESC LIMIT 10 OFFSET ? ");
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int newsId = resultSet.getInt("newsId");
                int authorId = resultSet.getInt("authorId");
                int viewCount = resultSet.getInt("viewCount");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                java.util.Date created = resultSet.getTimestamp("created");
                news.add(new News(newsId, categoryId, authorId, viewCount, title, content, created));
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

        return news;
    }

    @Override
    public Integer getNewsForCategoryCount(int categoryId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer count = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS total FROM news WHERE categoryId = ?");
            preparedStatement.setInt(1, categoryId);
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
    public List<News> getPopularNews() {
        List<News> news = new ArrayList<News>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE created BETWEEN NOW() - INTERVAL 30 DAY AND NOW() ORDER BY viewCount DESC LIMIT 10");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int newsId = resultSet.getInt("newsId");
                int categoryId = resultSet.getInt("categoryId");
                int authorId = resultSet.getInt("authorId");
                int viewCount = resultSet.getInt("viewCount");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                java.util.Date created = resultSet.getTimestamp("created");
                news.add(new News(newsId, categoryId, authorId, viewCount, title, content, created));
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

        return news;
    }

    @Override
    public News createNews(int categoryId, int authorId, String title, String content) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns={"newsId"};
            preparedStatement = connection.prepareStatement("INSERT INTO news (categoryId, authorId, title, content) VALUES (?, ?, ?, ?)", generatedColumns);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, authorId);
            preparedStatement.setString(3, title);
            preparedStatement.setString(4, content);

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next())
                news = new News(resultSet.getInt(1), categoryId, authorId, 0, title, content, new Date());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public News updateNews(int newsId, int categoryId, String title, String content) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;
        try {
            connection = this.newConnection();

            // Check if news exists:
            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE newsId = ? ");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet == null || !resultSet.next())
                throw new Exception();

            // Update news:
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            preparedStatement = connection.prepareStatement("UPDATE news AS n SET n.categoryId = ?, n.title = ?, n.content = ? WHERE n.newsId = ?");
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, content);
            preparedStatement.setInt(4, newsId);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getResultSet();
            if (resultSet.next())
                news = new News(newsId, categoryId, resultSet.getInt("authorId"), resultSet.getInt("viewCount"), title, content, resultSet.getDate("created"));

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

        return news;
    }

    @Override
    public void deleteNews(int newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM news WHERE newsId = ?");
            preparedStatement.setInt(1, newsId);
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

    @Override
    public List<News> getAllNews(int page) {
        List<News> news = new ArrayList<News>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news ORDER BY created DESC LIMIT 10 OFFSET ?");
            preparedStatement.setInt(1, (page - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int newsId = resultSet.getInt("newsId");
                int categoryId = resultSet.getInt("categoryId");
                int authorId = resultSet.getInt("authorId");
                int viewCount = resultSet.getInt("viewCount");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                java.util.Date created = resultSet.getTimestamp("created");
                news.add(new News(newsId, categoryId, authorId, viewCount, title, content, created));
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

        return news;
    }

    @Override
    public void recordView(int newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            // Check if news exists:
            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE newsId = ? ");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet == null || !resultSet.next())
                throw new Exception();

            // Update news:
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            preparedStatement = connection.prepareStatement("UPDATE news AS n SET n.viewCount = n.viewCount + 1 WHERE n.newsId = ?");
            preparedStatement.setInt(1, newsId);
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

}
