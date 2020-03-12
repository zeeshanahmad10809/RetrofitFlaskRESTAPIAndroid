package app.firestore.ahmad.zeeshan.com.retrofit_001;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadActivity extends AppCompatActivity {

    ImageView imageView;
    Button loadButton;
    Uri imageUri;
    String imagePath;
    TextView pathTextView;
    TextView uriTextView;
    Button uploadButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        imageView = findViewById(R.id.gallery_image);
        loadButton = findViewById(R.id.load_image_btn);
        uriTextView = findViewById(R.id.uri_textview);
        pathTextView = findViewById(R.id.path_textview);
        uploadButton = findViewById(R.id.upload_image_btn);




        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);//If this uri is not provided here it wil show option to select all kinds of files likes videos, contact, albums and all others apps that has implementatio for this type of intent....
                startActivityForResult(intent, 1000);







            }
        });


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:5000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        final BookClient bookClient = retrofit.create(BookClient.class);






        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, "Implicit description here!!");

                File imageFile = new File(imagePath);

                RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), imageFile);

                MultipartBody.Part completeFile = MultipartBody.Part.createFormData("photo", imageFile.getName(), filePart);

                Call<ResponseBody> uploadFileCall = bookClient.uploadImage(descriptionPart, completeFile);

                uploadFileCall.clone().enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getBaseContext(), "File Uploaded!!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Error while processing request!!", Toast.LENGTH_LONG).show();

                    }
                });







            }
        });







    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            if(requestCode == 1000){

                imageUri = data.getData();
                imageView.setImageURI(imageUri);
                uriTextView.setText("Image Uri: " + imageUri.toString());

                Cursor cursor;// = managedQuery(imageUri, proj, null, null, null);
                cursor = getContentResolver().query(imageUri, null, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//index = 1 (Data = exact file path in storage...)
                cursor.moveToFirst();//Cursor will have only data because the uri has direct access to that image because of content://media/external/images/media/file_id (file_id in content provider tables and not in raw data tables)
                imagePath = cursor.getString(column_index);
                pathTextView.setText("Image Path: " + cursor.getString(column_index));


            }

        }


    }





}
