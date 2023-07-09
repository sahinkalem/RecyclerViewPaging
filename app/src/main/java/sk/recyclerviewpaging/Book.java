package sk.recyclerviewpaging;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Book {
    private int bookID;
    private String bookISBN;
    private String bookName;
    private String bookAuthor;
    private String bookPublisher;
    private boolean bookInLibrary;
    private boolean bookIsRead;
    private String bookImageURL;

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int _bookID) {
        bookID = _bookID;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String _bookISBN) {
        if (!(_bookISBN == null) && !(_bookISBN.trim().equals(""))) bookISBN = _bookISBN;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String _bookName) {
        if (!(_bookName == null) && !(_bookName.trim().equals(""))) bookName = _bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String _bookAuthor) {
        if (!(_bookAuthor == null) && !(_bookAuthor.trim().equals(""))) bookAuthor = _bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String _bookPublisher) {
        if (!(_bookPublisher == null) && !(_bookPublisher.trim().equals("")))
            bookPublisher = _bookPublisher;
    }

    public boolean getBookInLibrary() {
        return bookInLibrary;
    }

    public void setBookInLibrary(boolean _bookInLibrary) {
        bookInLibrary = _bookInLibrary;
    }

    public boolean getBookIsRead() {
        return bookIsRead;
    }

    public void setBookIsRead(boolean _bookRead) {
        bookIsRead = _bookRead;
    }

    public String getBookImageURL() {
        return bookImageURL;
    }

    public void setBookImageURL(String bookImageURL) {
        this.bookImageURL = bookImageURL;
    }

    Context context;
    ArrayList<Book> bookList;

    private static final SqlServer SQL_SERVER_DB = new SqlServer();

    public Book(Context context) {
        this.context = context;
    }

    public ArrayList<Book> GetRecordsFromJSON(int firstRecord, int lastRecord) {

        try {
            String jsonDataString = ReadJSONDataFromFile();
            JSONArray jsonArray = new JSONArray(jsonDataString);
            bookList = new ArrayList<>();
            for (int i = firstRecord; i <= lastRecord; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Book book = new Book(context.getApplicationContext());
                book.setBookImageURL(jsonObject.getString("BookImageURL"));
                book.setBookISBN(jsonObject.getString("BookISBN"));
                book.setBookName(jsonObject.getString("BookName"));
                book.setBookAuthor(jsonObject.getString("BookAuthor"));
                book.setBookPublisher(jsonObject.getString("BookPublisher"));
                book.setBookInLibrary(jsonObject.getString("BookInLibrary").equals("1"));
                book.setBookIsRead(jsonObject.getString("BookRead").equals("1"));
                bookList.add(book);
            }
        } catch (JSONException | IOException e) {
            Log.d("BookModel", "addItemsFromJSON: ", e);
        }
        return bookList;
    }

    public ArrayList<Book> GetRecordsFromSqlServer(int firstRecord, int lastRecord) {
        try {
            Connection connection = SQL_SERVER_DB.Connect();
            if (connection != null) {
                String query = "SELECT BookID, BookISBN, BookName, BookAuthor, BookPublisher, BookInLibrary, BookRead, BookImageURL FROM tbl_Books WHERE BookID>=" + firstRecord + "AND BookID<=" + lastRecord +" ORDER BY BookID ASC";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                bookList = new ArrayList<>();
                while (resultSet.next()) {
                    Book book = new Book(context);
                    book.setBookID(resultSet.getInt(1));
                    book.setBookISBN(resultSet.getString(2));
                    book.setBookName(resultSet.getString(3));
                    book.setBookAuthor(resultSet.getString(4));
                    book.setBookPublisher(resultSet.getString(5));
                    book.setBookInLibrary(resultSet.getBoolean(6));
                    book.setBookIsRead(resultSet.getBoolean(7));
                    book.setBookImageURL(resultSet.getString(8));
                    bookList.add(book);
                    Log.i("List State","Book is added");
                                    }
                resultSet.close();
                connection.close();
                statement.close();
            }
        } catch (SQLException sqlException) {
            Log.e("Books Error Message", sqlException.getMessage());
        }
        return bookList;
    }

    private String ReadJSONDataFromFile() throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonString;
            inputStream = context.getResources().openRawResource(R.raw.books);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            while ((jsonString = bufferedReader.readLine()) != null) {
                builder.append(jsonString);
            }
        } finally {
            if (inputStream == null)
                inputStream.close();
        }
        return new String(builder);
    }

}