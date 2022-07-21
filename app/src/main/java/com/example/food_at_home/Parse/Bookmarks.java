package com.example.food_at_home.Parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Bookmarks")
public class Bookmarks extends ParseObject {
    public static final String BOOKMARK_TITLE = "BookmarkTitle";
    public static final String BOOKMARK_ID = "BookmarkID";
    public static final String BOOKMARK_USER = "BookmarkUser";
    public static final String BOOKMARK_PHOTO = "BookmarkImage";

    public String getBookmarkTitle(){
        return getString(BOOKMARK_TITLE);
    }

    public void setBookmarkTitle(String bookmarkTitle){
        put(BOOKMARK_TITLE, bookmarkTitle);
    }

    public String getBookmarkId(){
        return getString(BOOKMARK_ID);
    }

    public void setBookmarkId(String bookmarkId){
        put(BOOKMARK_ID, bookmarkId);
    }

    public ParseUser getUser(){
        return getParseUser(BOOKMARK_USER);
    }

    public void setUser(ParseUser user){
        put(BOOKMARK_USER, user);
    }
    public String getBookmarkImage(){
        return getString(BOOKMARK_PHOTO);
    }
    public void setBookmarkPhoto(String bookmarkPhoto){
        put(BOOKMARK_PHOTO, bookmarkPhoto);
    }

}
