package com.example.android.newsapppart1;

public class NewsData {
    //Defines the values associated with a news story: story title, author, category, and date
    private String title;
    //Resource ID is initialized to -1 (a value that would not be an ID) to indicate that no image is associated with a Location
    private int storyPictureId = -1;
    private String section;
    private String author;
    private String date;
    private String url;

    /**
     * Create a new Location Data object to display the text for the various value related to a physical location
     *
     * @param story_title
     * @param story_author
     * @param story_picture_ID
     * @param publication_date
     * @param story_section
     */
    public NewsData(String story_title , String story_author, String story_section, int story_picture_ID, String publication_date, String story_url){
        setTitle(story_title);
        setAuthor(story_author);
        setSection(story_section);
        setStoryPictureId(story_picture_ID);
        setDate(publication_date);
        setUrl(story_url);
    }

    /**
     * Create a new Location Data object to display the text for the various value related to a physical location
     *
     * @param story_title
     * @param story_author
     * @param publication_date
     * @param story_url
     */
    public NewsData(String story_title , String story_section, String story_author, String publication_date, String story_url){

        setTitle(story_title);
        setAuthor(story_author);
        setDate(publication_date);
        setUrl(story_url);
        setSection(story_section);
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSection() {
        return section;
    }

    public int getStoryPictureId() {
        return storyPictureId;
    }

    public String getDate() { return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setStoryPictureId(int storyPictureId) {
        this.storyPictureId = storyPictureId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }




}
