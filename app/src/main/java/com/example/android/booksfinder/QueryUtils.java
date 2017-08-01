package com.example.android.booksfinder;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public final class QueryUtils {

    private QueryUtils() {
    }

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<Book> fetchData(String requestURL) {
        URL url = createUrl(requestURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }
        List<Book> books = booksList(jsonResponse);
        return books;
    }

    private static URL createUrl(String url) {
        URL finalUrl = null;
        try {
            finalUrl = new URL(url);
        } catch (MalformedURLException e) {

        }
        return finalUrl;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = getStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code - " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem in retrieving data ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String getStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String reads = reader.readLine();
            while (reads != null) {
                builder.append(reads);
                reads = reader.readLine();
            }
        }
        return builder.toString();
    }

    private static List<Book> booksList(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        List<Book> bookList = new ArrayList<>();
        try {
            JSONObject Repo = new JSONObject(str);
            JSONArray array = Repo.getJSONArray("items");
            for (int i = 0; i < array.length(); i++) {
                JSONObject feature = array.optJSONObject(i);
                JSONObject volumeInfo = feature.optJSONObject("volumeInfo");
                String bookName = volumeInfo.optString("title");
                String author = volumeInfo.optString("authors");
                String category = volumeInfo.optString("categories");
                bookList.add(new Book(bookName, author, category));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "problem fetching data ", e);
        }
        return bookList;
    }
}