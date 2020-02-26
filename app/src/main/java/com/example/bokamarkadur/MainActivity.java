package com.example.bokamarkadur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private Button loginbutton;
    private Button registerbutton;
    private Button AddBook;
    private Button RequestBook;
    private Button AllBooksBtn;
//    private static final String TAG = MainActivity.class.getSimpleName();

    APIInterface apiInterface;
    private TextView textViewResult;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


//            /**
//             * Þórdís start hér
//             */
//            apiInterface = APIClient.getClient().create(APIInterface.class);
//
//            final RecyclerView recyclerView = findViewById(R.id.books_recycler_view);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setAdapter(new BooksAdapter(new ArrayList<Book>(), R.layout.list_item, getApplicationContext()));
//
//
//            /**
//             GET kall sem skilar lista af öllum bókum.
//             **/
//            Call<BookList> getAllBooks = apiInterface.getBooks();
//            getAllBooks.enqueue(new Callback<BookList>() {
//                @Override
//                public void onResponse(Call<BookList> call, Response<BookList> response) {
//                    int statusCode = response.code();
//                    List<Book> books = response.body().getBooks();
//                    recyclerView.setAdapter(new BooksAdapter(books, R.layout.list_item, getApplicationContext()));
//
//
//                    Log.d(TAG, "Number of books received: " + books.size());
//
//
//                    // Má eyða - Birtir toast ef svar hefur borist.
//                    Toast.makeText(getApplicationContext(), "Response received", Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onFailure(Call<BookList> call, Throwable t) {
//                    // Log error here since request failed
//                    Log.e(TAG, t.toString());
//                    call.cancel();
//                }
//            });


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

