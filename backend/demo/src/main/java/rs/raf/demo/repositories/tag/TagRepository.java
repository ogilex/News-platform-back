package rs.raf.demo.repositories.tag;

import rs.raf.demo.entities.News;
import rs.raf.demo.entities.Tag;

import java.util.List;

public interface TagRepository {

    List<Tag> getTagsByNews(int newsId);

    List<News> getNewsByTag(int tagId);

    Tag createTag(int newsId, String keyword);

    Tag findById(int tagId);

    Tag findByKeyword(String keyword);
}
