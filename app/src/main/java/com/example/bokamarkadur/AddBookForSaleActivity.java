package com.example.bokamarkadur;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bokamarkadur.POJO.Book;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import static com.squareup.okhttp.MediaType.parse;

public class AddBookForSaleActivity extends AppCompatActivity {

    private Button uploadImage;
    private Button submit;
    private ImageView viewUploadedImage;
    private ProgressDialog progressDialog;
    private static final int GALLERY_REQUEST_CODE = 1888;

    private Uri selectedImage;
    private String imgDecodableString;

    APIInterface apiInterface;

    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_for_sale);
        uploadImage = (Button) findViewById(R.id.btnUploadImage);
        submit = (Button) findViewById(R.id.submit);
        viewUploadedImage = (ImageView) findViewById(R.id.uploadImage);

        // Tengjumst API Interface sem talar við bakendann okkar.
        apiInterface = APIClient.getClient().create(APIInterface.class);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }

    private void pickFromGallery() {
        try {
            if (ActivityCompat.checkSelfPermission(AddBookForSaleActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddBookForSaleActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
            } else {
                //Create an Intent with action as ACTION_PICK
                Intent intent=new Intent(Intent.ACTION_PICK);
                // Sets the type as image/*. This ensures only components of type image are selected
                intent.setType("image/*");
                //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                // Launching the Intent
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    // Get user permission to access gallery.
                    if (ContextCompat.checkSelfPermission(AddBookForSaleActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Permission is not granted
                    } else {
                        //data.getData returns the content URI for the selected Image
                        selectedImage = data.getData();

                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                        // Get the cursor
                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();
                        //Get the column index of MediaStore.Images.Media.DATA
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        //Gets the String value in the column
                        imgDecodableString = cursor.getString(columnIndex);
                        cursor.close();

                        viewUploadedImage.setImageURI(selectedImage);
                    }
                    break;
            }
    }

    private void submitData() {

        EditText title = (EditText) findViewById(R.id.edtTitle);
        EditText author = (EditText) findViewById(R.id.edtAuthor);
        EditText edition = (EditText) findViewById(R.id.edtEdition);
        EditText subject = (EditText) findViewById(R.id.edtSubject);
        EditText price = (EditText) findViewById(R.id.edtPrice);
        EditText condition = (EditText) findViewById(R.id.edtCondition);
        progressDialog = new ProgressDialog(AddBookForSaleActivity.this);
        //progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        //File file = new  File("/sdcard/Images/test.png");
        //RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName());
        //Call<FileInfo> call1 = fileService.upload(body);

//        final JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("title", title.getText().toString());
//        jsonObject.addProperty("author", author.getText().toString());
//        jsonObject.addProperty("edition", edition.getText().toString());
//        jsonObject.addProperty("price", price.getText().toString());
//        jsonObject.addProperty("subject", subject.getText().toString());
//        jsonObject.addProperty("condition", condition.getText().toString());
//        jsonObject.addProperty("file", "");

        File file = new File(imgDecodableString);
        Log.d("filepath: ", imgDecodableString);
        // Create a request body with file and image media type
        okhttp3.RequestBody fileReqBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
        //Create request body with text description and text media type
        String titlePart = title.getText().toString();
        String authorPart = author.getText().toString();
        int editionPart = Integer.parseInt(edition.getText().toString());
        String conditionPart = condition.getText().toString();
        int pricePart = Integer.parseInt(price.getText().toString());
        String subjectPart = subject.getText().toString();

        Call<Book> newBookForSale = apiInterface.addBookForSale("application/json", "Bearer " + LoginActivity.token,
                imagePart, titlePart, authorPart, editionPart, conditionPart, pricePart, subjectPart);
        newBookForSale.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                Log.d("onResponse: ", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Title: " + response.body().getTitle() + " Price: " + response.body().getPrice(), Toast.LENGTH_LONG).show();
                    openMainActivity();
                    Log.d("Success: ", String.valueOf(response.body()));
                } else {
                    try {
                        Log.d("error", response.raw().body().string());
                        Log.d("error", response.errorBody().string());
                    } catch (Exception e) {
                        Log.d("error: ", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("myTag", "HELPHLEP");
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}