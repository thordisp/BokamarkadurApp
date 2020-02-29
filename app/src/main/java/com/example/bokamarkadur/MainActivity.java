package com.example.bokamarkadur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bokamarkadur.POJO.Book;
import com.example.bokamarkadur.POJO.BookList;
import com.example.bokamarkadur.POJO.BooksAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    // Takkar neðst á síðu - Verður fært í menu.
    private Button loginbutton;
    private Button registerbutton;
    private Button AddBook;
    private Button RequestBook;
    private Button AllBooksBtn;

    // Notað fyrir debugging
    private static final String TAG = MainActivity.class.getSimpleName();

    APIInterface apiInterface;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Tengjumst API Interface sem talar við bakendann okkar.
            apiInterface = APIClient.getClient().create(APIInterface.class);

            // RecyclerView - Birtir lista af bókum eins og skilgreint er í list_item.
            final RecyclerView BookrecyclerView = findViewById(R.id.newest_books_recycler_view);
            BookrecyclerView.setLayoutManager(new LinearLayoutManager(this));
            BookrecyclerView.setAdapter(new BooksAdapter(new ArrayList<Book>(), R.layout.list_item, getApplicationContext()));

            final RecyclerView SubjectsrecyclerView = findViewById(R.id.newest_books_recycler_view);
            SubjectsrecyclerView.setLayoutManager(new LinearLayoutManager(this));
            SubjectsrecyclerView.setAdapter(new BooksAdapter(new ArrayList<Book>(), R.layout.list_item, getApplicationContext()));


            /**
             GET kall sem skilar lista af 10 nýjustu bókunum.
             **/
            Call<BookList> getNewestBooks = apiInterface.getNewestBooks();
            getNewestBooks.enqueue(new Callback<BookList>() {
                @Override
                public void onResponse(Call<BookList> call, Response<BookList> response) {
                    List<Book> books = response.body().getNewestBooks();
                    BookrecyclerView.setAdapter(new BooksAdapter(books, R.layout.list_item, getApplicationContext()));
                    SubjectsrecyclerView.setAdapter(new BooksAdapter(books, R.layout.list_subjects,  getApplicationContext()));

                    // TODO: Debug virkni, má eyða síðar meir.
                    Log.d(TAG, "Number of books received: " + books.size());
                }

                @Override
                public void onFailure(Call<BookList> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    call.cancel();
                }
            });


            //add book 4 sale
            AddBook = (Button) findViewById(R.id.AddBook);
            AddBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddbookforsaleActivity();
                }
            });

            //Request book
            RequestBook = (Button) findViewById(R.id.RequestBook);
            RequestBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openRequestBookActivity();
                }
            });

            // login button
            loginbutton = (Button) findViewById(R.id.login);
            loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     openLoginActivity();
                }
            });
            //register
            registerbutton = (Button) findViewById(R.id.register);
            registerbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openRegisterActivity();
                }
            });
            // Förum yfir í AllBooksActivity þar sem allar bækur eru birtar.
            AllBooksBtn = (Button) findViewById(R.id.all_books);
            AllBooksBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAllBooksActivity();
                }
            });

        }

    public void openLoginActivity() {
            Intent intent= new Intent(this, LoginActivity.class);
            startActivity(intent);
    }
    public void openRegisterActivity() {
        Intent intent= new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void AddbookforsaleActivity() {
        Intent intent= new Intent(this, AddBookForSaleActivity.class);
        startActivity(intent);
    }
    public void openRequestBookActivity() {
        Intent intent = new Intent(this, RequestBookActivity.class);
        startActivity(intent);
    }
    public void openAllBooksActivity() {
        Intent intent = new Intent(this, AllBooksActivity.class);
        startActivity(intent);
    }
}

