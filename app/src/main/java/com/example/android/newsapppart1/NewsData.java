package com.example.android.newsapppart1;
/*
    A class that stores a NewsData object containing information about a news story from a web source such as the Guardian
 */

public class NewsData {
    //Defines the values associated with a news story: story title, author, category, date, and website URL
    private String title;
    private String section;
    private String author;
    private String date;
    private String url;

    /**
     * Create a new Location Data object to display the text for the various value related to a physical location
     *
     * @param story_title The Article title for the news story
     * @param story_author The author/contributors for the article
     * @param publication_date The date the article was published on the Guardian website
     * @param story_section The section of the Guardian website that the story comes from
     * @param story_url The URL link to the article on the Guardian website
     */
    public NewsData(String story_title, String story_author, String story_section, String publication_date, String story_url) {
        setTitle(story_title);
        setAuthor(story_author);
        setSection(story_section);
        setDate(publication_date);
        setUrl(story_url);
    }

    /*Getter and Setter functions for a NewsData object's private attributes.*/

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
