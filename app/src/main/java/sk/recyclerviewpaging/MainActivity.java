package sk.recyclerviewpaging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Book book;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;
    RecyclerView recyclerView;
    BookAdapter bookAdapter;
    ArrayList<Book> bookList;
    int firstRecord = 0;
    int lastRecord = 11;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingPB = findViewById(R.id.idPBLoading);
        nestedSV = findViewById(R.id.idNestedSV);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        book = new Book(this);
        bookList = book.GetRecordsFromSqlServer(firstRecord, lastRecord);
        bookAdapter = new BookAdapter(this, bookList);
        recyclerView.setAdapter(bookAdapter);

        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });

        nestedSV.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                firstRecord = lastRecord + 1;
                lastRecord += 10;
                loadingPB.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        book = new Book(getApplicationContext());
                        bookList.addAll(book.GetRecordsFromSqlServer(firstRecord, lastRecord));
                        bookAdapter.notifyItemRangeInserted(firstRecord + 1, 10);
                    }
                },2000);



            } else {
                loadingPB.setVisibility(View.GONE);
            }
        });

    }


}