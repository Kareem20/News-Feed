package com.example.news.modals;

public class newsList {

    private String titleOfArticle;
    private String dateOfArticle;
    private String url;
    private String section;
    private String image;
    private String author;
    private String trailText;
    private String articlesText;
    private String lastModified;
    private String imageOfAuthor;

    public newsList(String titleOfArticle, String dateOfArticle, String url, String section, String image
            , String author, String trailText, String articlesText, String lastModified, String imageOfAuthor) {
        this.titleOfArticle = titleOfArticle;
        this.dateOfArticle = dateOfArticle;
        this.url = url;
        this.section = section;
        this.image = image;
        this.author = author;
        this.trailText = trailText;
        this.articlesText = articlesText;
        this.lastModified = lastModified;
        this.imageOfAuthor = imageOfAuthor;
    }

    public String getTitleOfArticle() {
        return titleOfArticle;
    }

    public void setTitleOfArticle(String titleOfArticle) {
        this.titleOfArticle = titleOfArticle;
    }

    public String getDateOfArticle() {
        return dateOfArticle;
    }

    public void setDateOfArticle(String dateOfArticle) {
        this.dateOfArticle = dateOfArticle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTrailText() {
        return trailText;
    }

    public void setTrailText(String trailText) {
        this.trailText = trailText;
    }

    public String getArticlesText() {
        return articlesText;
    }

    public void setArticlesText(String articlesText) {
        this.articlesText = articlesText;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getImageOfAuthor() {
        return imageOfAuthor;
    }

    public void setImageOfAuthor(String imageOfAuthor) {
        this.imageOfAuthor = imageOfAuthor;
    }
}
