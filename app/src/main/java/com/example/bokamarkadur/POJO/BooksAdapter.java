package com.example.bokamarkadur.POJO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bokamarkadur.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private List<Book> books;
    private int rowLayout;
    private Context context;


    public static class BookViewHolder extends RecyclerView.ViewHolder {
        LinearLayout booksLayout;
        TextView bookTitle;
        TextView bookAuthor;
        TextView price;
        ImageView image;


        public BookViewHolder(View v) {
            super(v);
            booksLayout = (LinearLayout) v.findViewById(R.id.books_layout);
            bookTitle = (TextView) v.findViewById(R.id.title);
            bookAuthor = (TextView) v.findViewById(R.id.author);
            price = (TextView) v.findViewById(R.id.price);
            image = (ImageView) v.findViewById(R.id.image);
        }
    }

    public BooksAdapter(List<Book> books, int rowLayout, Context context) {
        this.books = books;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public BooksAdapter.BookViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new BookViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BookViewHolder holder, final int position) {
        holder.bookTitle.setText(books.get(position).getTitle());
        holder.bookAuthor.setText(books.get(position).getAuthor());
        holder.price.setText("Verð: " + books.get(position).getPrice().toString() + " kr");
        Picasso.get().load("https://radiant-bayou-59420.herokuapp.com/" + books.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
