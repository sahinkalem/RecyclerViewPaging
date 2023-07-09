package sk.recyclerviewpaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Book> bookList;

    public BookAdapter(Context context, ArrayList<Book> bookList) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Book book = bookList.get(position);

        viewHolder.textViewBookName.setText(book.getBookName());
        viewHolder.textViewBookAuthor.setText(book.getBookAuthor());
        viewHolder.textViewBookPublisher.setText(book.getBookPublisher());
        viewHolder.txtBookISBN.setText(book.getBookISBN());
        viewHolder.checkBoxBookIsRead.setChecked(book.getBookIsRead());
        viewHolder.checkBoxBookInLibrary.setChecked(book.getBookInLibrary());
        Glide.with(viewHolder.itemView.getContext()).load(book.getBookImageURL()).into(viewHolder.imageView);

        //viewHolder.itemCardView.startAnimation(AnimationUtils.loadAnimation(layoutInflater.getContext(), R.anim.anim_four));

        viewHolder.itemView.setOnClickListener(view ->
                Toast.makeText(viewHolder.itemView.getContext(), "Clicked to item " + position, Toast.LENGTH_SHORT).show());
    }


    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewBookName;
        private final TextView textViewBookAuthor;
        private final TextView textViewBookPublisher;
        private final TextView txtBookISBN;
        private final CheckBox checkBoxBookIsRead;
        private final CheckBox checkBoxBookInLibrary;
        private final ImageView imageView;
        private final CardView itemCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemCardView = itemView.findViewById(R.id.itemCardView);

            textViewBookName = itemView.findViewById(R.id.textBookName);
            textViewBookAuthor = itemView.findViewById(R.id.textBookAuthor);
            textViewBookPublisher = itemView.findViewById(R.id.textBookPublisher);
            imageView = itemView.findViewById(R.id.imageView);
            txtBookISBN = itemView.findViewById(R.id.txtBookISBN);
            checkBoxBookIsRead = itemView.findViewById(R.id.checkBoxBookIsRead);
            checkBoxBookInLibrary = itemView.findViewById(R.id.checkBoxBookInLibrary);
        }
    }
}

