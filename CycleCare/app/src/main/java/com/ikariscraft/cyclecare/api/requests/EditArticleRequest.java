package com.ikariscraft.cyclecare.api.requests;

public class EditArticleRequest {

    private int contentId;

    private String title;

    private String description;

    private String imageName;

    private String newImage;

    public EditArticleRequest(int contentId, String title, String description, String imageName, String newImage) {
        this.contentId = contentId;
        this.title = title;
        this.description = description;
        this.imageName = imageName;
        this.newImage = newImage;
    }

    public EditArticleRequest(){}

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getNewImage() {
        return newImage;
    }

    public void setNewImage(String newImage) {
        this.newImage = newImage;
    }
}
