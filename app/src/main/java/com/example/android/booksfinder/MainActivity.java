package com.example.android.booksfinder;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    public String Book_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    public TextView emptyTextView;
    public BookAdapter adapter;
    private static final int bookLoader = 1;
    public String url;
    LoaderManager loader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView book_View = (ListView) findViewById(R.id.list);
        emptyTextView = (TextView) findViewById(R.id.empty_view);
        book_View.setEmptyView(emptyTextView);

        adapter = new BookAdapter(this, new ArrayList<Book>());
        book_View.setAdapter(adapter);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        View indicator = findViewById(R.id.indicator);
        indicator.setVisibility(View.GONE);
        emptyTextView.setText(R.string.noBooks);
        adapter.clear();
        if (books != null && !books.isEmpty()) {
            adapter.addAll(books);
        } else {
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        adapter.clear();
    }

    public void search(View v) {
        View load = findViewById(R.id.indicator);
        load.setVisibility(View.VISIBLE);
        EditText editText = (EditText) findViewById(R.id.searchQuery);
        String query = editText.getText().toString();
        url = Book_URL + query + "&maxResults=15";
        ConnectivityManager connect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connect.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            loader = getLoaderManager();
            loader.initLoader(bookLoader, null, this);
            loader.restartLoader(bookLoader,null,this);
        } else {
            load.setVisibility(View.GONE);
            emptyTextView.setText(R.string.noInternet);
        }
    }
}
