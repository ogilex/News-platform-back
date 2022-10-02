package rs.raf.demo.repositories.commets;

import rs.raf.demo.entities.Comment;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {
    @Override
    public List<Comment> getNewsComments(int newsId) {
        List<Comment> comments = new ArrayList<Comment>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM comment WHERE newsId = ? ORDER BY created DESC");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int commentId = resultSet.getInt("commentId");
                String authorName = resultSet.getString("authorName");
                String content = resultSet.getString("content");
                java.util.Date created = resultSet.getTimestamp("created");
                comments.add(new Comment(commentId, newsId, authorName, content, created));
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

        return comments;
    }

    @Override
    public Comment createComment(int newsId, String authorName, String content) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Comment comment = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns={"commentId"};
            preparedStatement = connection.prepareStatement("INSERT INTO comment (newsId, authorName, content) VALUES (?, ?, ?)", generatedColumns);
            preparedStatement.setInt(1, newsId);
            preparedStatement.setString(2, authorName);
            preparedStatement.setString(3, content);

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next())
                comment = new Comment(resultSet.getInt(1), newsId, authorName, content, new Date());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comment;
    }
}
