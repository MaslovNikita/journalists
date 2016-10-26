package model;

/**
 * Bean of items of feed channel
 */
public class ItemsFeedChannel {
    private String title;
    private String link;
    private String datetime;

    public ItemsFeedChannel() {
    }

    public ItemsFeedChannel(final String title, final String link, final String datetime) {
        this.title = title;
        this.link = link;
        this.datetime = datetime;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(final String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "ItemsFeedChannel{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
