package com.miamor.Obj;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Marcot on 8/20/2015.
 */
public class Blog {

    private int Id ;
    private String Title ;
    private String ShortDescription ;
    private int CommentCount ;
    private String Body ;
    private String Tags ;
    private Date StartDateUtc ;
    private int TotalLikes ;
    private Collection<BlogPicture> BlogPostPictures ;
    private BlogPicture defaultPic;

    public Blog() {
    }

    public Blog(int id, String title, String shortDescription, int commentCount, String body, String tags, Date startDateUtc, int totalLikes, Collection<BlogPicture> blogPostPictures) {
        Id = id;
        Title = title;
        ShortDescription = shortDescription;
        CommentCount = commentCount;
        Body = body;
        Tags = tags;
        StartDateUtc = startDateUtc;
        TotalLikes = totalLikes;
        BlogPostPictures = blogPostPictures;
    }


    public BlogPicture getDefaultPic() {
        return defaultPic;
    }

    public void setDefaultPic(BlogPicture defaultPic) {
        this.defaultPic = defaultPic;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public Date getStartDateUtc() {
        return StartDateUtc;
    }

    public void setStartDateUtc(Date startDateUtc) {
        StartDateUtc = startDateUtc;
    }

    public int getTotalLikes() {
        return TotalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        TotalLikes = totalLikes;
    }

    public Collection<BlogPicture> getBlogPostPictures() {
        return BlogPostPictures;
    }

    public void setBlogPostPictures(Collection<BlogPicture> blogPostPictures) {
        BlogPostPictures = blogPostPictures;
    }
}
