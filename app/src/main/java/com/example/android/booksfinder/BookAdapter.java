package com.example.android.booksfinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hritik on 06-05-2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {


    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_item, parent, false);
        }
        // Find the book at the given position in the list of earthquakes
        Book currentBook = getItem(position);
        Log.i("Value of title",currentBook.getTitle());

        // Find the TextView with view ID title and display the title of the book
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        String title = currentBook.getTitle();
        titleView.setText(title);

        // Find the TextView with view ID magnitude and Display the author
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        String authors = currentBook.getAuthor();
        authorView.setText(authors);

        // Find the TextView with view ID magnitude and Display the category
        TextView categoryView = (TextView) listItemView.findViewById(R.id.category);
        String category = currentBook.getCategory();
        categoryView.setText(category);

        return listItemView;
    }
}
