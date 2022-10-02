package rs.raf.demo.repositories.news;

import rs.raf.demo.entities.News;

import java.util.List;

public interface NewsRepository {

    News findById(int newsId);

    List<News> searchNews(int page, String query);

    void recordView(int newsId);

    List<News> getNewsForCategory(int page, int categoryId);

    List<News> getAllNews(int page);

    List<News> getRecentNews();

    List<News> getPopularNews();

    News createNews(int categoryId, int authorId, String title, String content);

    News updateNews(int newsId, int categoryId, String title, String content);

    Integer getNewsForCategoryCount(int categoryId);

    void deleteNews(int newsId);
}
