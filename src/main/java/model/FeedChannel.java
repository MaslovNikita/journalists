package model;

/**
 * Created by homie on 25.10.16.
 */
public class FeedChannel {

    private String feedsUrl;
    private String title;
    private String link;
    private String description;
    private String imageUrl;

    public FeedChannel() {
    }

    public FeedChannel(final String feedsUrl, final String title, final String link, final String description, final String imageUrl) {
        this.feedsUrl = feedsUrl;
        this.title = title;
        this.link = link;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFeedsUrl() {
        return feedsUrl;
    }

    public void setFeedsUrl(final String feedsUrl) {
        this.feedsUrl = feedsUrl;
    }

    public void set(FeedChannel feedChannel) {
        this.feedsUrl = feedChannel.feedsUrl;
        this.title = feedChannel.title;
        this.link = feedChannel.link;
        this.description = feedChannel.description;
        this.imageUrl = feedChannel.imageUrl;
    }

    @Override
    public String toString() {
        return "FeedChannel{" +
                "feedsUrl='" + feedsUrl + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
