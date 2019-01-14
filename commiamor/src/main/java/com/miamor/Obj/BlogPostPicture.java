package com.miamor.Obj;

/**
 * Created by Marcot on 8/20/2015.
 */
public class BlogPostPicture {

    private int id;

    private int blogPostId;

    private int pictureId ;

    private int displayOrder ;

    private BlogPicture picture ;

    public BlogPostPicture() {
    }

    public BlogPostPicture(int id, int blogPostId, int pictureId, int displayOrder, BlogPicture picture) {
        this.id = id;
        this.blogPostId = blogPostId;
        this.pictureId = pictureId;
        this.displayOrder = displayOrder;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(int blogPostId) {
        this.blogPostId = blogPostId;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public BlogPicture getPicture() {
        return picture;
    }

    public void setPicture(BlogPicture picture) {
        this.picture = picture;
    }
}
