package com.example.android.booksfinder;

/**
 * Created by Hritik on 06-05-2017.
 */

public class Book {

    private String mTitle;

    private String mAuthor;

    private String mCategory;


    Book(String title, String author, String category) {
        mTitle = title;
        mAuthor = author;
        mCategory = category;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getCategory() {
        return mCategory;
    }

}
