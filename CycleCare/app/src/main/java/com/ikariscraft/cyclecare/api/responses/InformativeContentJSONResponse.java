package com.ikariscraft.cyclecare.api.responses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class InformativeContentJSONResponse implements Parcelable {

    private int contentId;
    private String title;
    private String description;
    private String creationDate;
    private String media;
    private String username;

    protected InformativeContentJSONResponse(Parcel in) {
        contentId = in.readInt();
        title = in.readString();
        description = in.readString();
        creationDate = in.readString();
        media = in.readString();
        username = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(contentId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(creationDate);
        dest.writeString(media);
        dest.writeString(username);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InformativeContentJSONResponse> CREATOR = new Creator<InformativeContentJSONResponse>() {
        @Override
        public InformativeContentJSONResponse createFromParcel(Parcel in) {
            return new InformativeContentJSONResponse(in);
        }

        @Override
        public InformativeContentJSONResponse[] newArray(int size) {
            return new InformativeContentJSONResponse[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformativeContentJSONResponse that = (InformativeContentJSONResponse) o;
        return contentId == that.contentId && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(creationDate, that.creationDate) && Objects.equals(media, that.media) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentId, title, description, creationDate, media, username);
    }

    public InformativeContentJSONResponse() {}

    public InformativeContentJSONResponse(int contentId, String title, String description, String creationDate, String image, String username) {
        this.contentId = contentId;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.media = image;
        this.username = username;
    }

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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getImage() {
        return media;
    }

    public void setImage(String image) {
        this.media = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
