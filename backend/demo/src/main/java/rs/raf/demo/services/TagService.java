package rs.raf.demo.services;

import rs.raf.demo.entities.News;
import rs.raf.demo.entities.Tag;
import rs.raf.demo.repositories.tag.TagRepository;

import javax.inject.Inject;
import java.util.List;

public class TagService {

    @Inject
    private TagRepository repository;


    public List<Tag> getTagsByNews(int newsId) {
        return repository.getTagsByNews(newsId);
    }


    public List<News> getNewsByTag(int tagId) {
        return repository.getNewsByTag(tagId);
    }


    public Tag createTag(int newsId, String keyword) {
        return repository.createTag(newsId, keyword);
    }


    public Tag findById(int tagId) {
        return repository.findById(tagId);
    }


    public Tag findByKeyword(String keyword) {
        return repository.findByKeyword(keyword);
    }
}
