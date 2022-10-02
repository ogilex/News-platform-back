package rs.raf.demo.resources;

import rs.raf.demo.entities.News;
import rs.raf.demo.services.NewsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/news")
public class NewsResource {

    @Inject
    private NewsService service;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News findById(@PathParam("id") int newsId) {
        return service.findById(newsId);
    }

    @POST
    @Path("/record-view/{id}")
    public void recordView(@PathParam("id") int newsId) {
        service.recordView(newsId);
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> searchNews(@QueryParam("page") int page, @QueryParam("query") String query) {
        return service.searchNews(page, query);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getAllNews(@QueryParam("page") int page) {
        return service.getAllNews(page);
    }

    @GET
    @Path("/news-for-category")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getNewsForCategory(@QueryParam("page") int page, @QueryParam("categoryId") int categoryId) {
        return service.getNewsForCategory(page, categoryId);
    }

    @GET
    @Path("/recent-news")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getRecentNews() {
        return service.getRecentNews();
    }

    @GET
    @Path("/popular-news")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getPopularNews() {
        return service.getPopularNews();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public News createNews(@Valid News news) {
        return service.createNews(news.getCategoryId(), news.getAuthorId(), news.getTitle(), news.getContent());
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News updateNews(@PathParam("id") int newsId, @Valid News news) {
        return service.updateNews(newsId, news.getCategoryId(), news.getTitle(), news.getContent());
    }

    @GET
    @Path("/news-for-category-count")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer getNewsForCategoryCount(@QueryParam("categoryId") int categoryId) {
        return service.getNewsForCategoryCount(categoryId);
    }

    @DELETE
    public void deleteNews(@QueryParam("newsId") int newsId) {
        service.deleteNews(newsId);
    }

}
