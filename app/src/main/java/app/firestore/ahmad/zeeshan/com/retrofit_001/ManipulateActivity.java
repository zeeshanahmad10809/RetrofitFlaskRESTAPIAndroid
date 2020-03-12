package app.firestore.ahmad.zeeshan.com.retrofit_001;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManipulateActivity extends AppCompatActivity {

    EditText idEditText;
    EditText nameEditText;
    EditText authorEditText;
    EditText publisherEditText;
    Button addData;
    Button updateData;
    Button deleteData;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulate);


        idEditText = findViewById(R.id.id_edittext);
        nameEditText = findViewById(R.id.name_edittext);
        authorEditText = findViewById(R.id.author_edittext);
        publisherEditText = findViewById(R.id.publisher_edittext);
        addData = findViewById(R.id.add_data_btn);
        updateData = findViewById(R.id.update_data_btn);
        deleteData = findViewById(R.id.delete_data_btn);


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:5000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        final BookClient bookClient = retrofit.create(BookClient.class);

        final String token = getIntent().getStringExtra("token");

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Book book = new Book(Integer.parseInt(idEditText.getText().toString()),
                        nameEditText.getText().toString(),
                        authorEditText.getText().toString(),
                        publisherEditText.getText().toString());


                Call<Message> addBookCall = bookClient.addBook(token, book);

                addBookCall.clone().enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if(response != null){
                            Toast.makeText(getBaseContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Error while processing request!!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Book book = new Book(Integer.parseInt(idEditText.getText().toString()),
                        nameEditText.getText().toString(),
                        authorEditText.getText().toString(),
                        publisherEditText.getText().toString());


                Call<Message> updateBookCall = bookClient.updateBook(token, Integer.parseInt(idEditText.getText().toString()), book);

                updateBookCall.clone().enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if(response != null){
                            Toast.makeText(getBaseContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Error while processing request!!", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<Message> deleteBookCall = bookClient.deleteBook(token, Integer.parseInt(idEditText.getText().toString()));

                deleteBookCall.clone().enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if(response != null){
                            Toast.makeText(getBaseContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Error while processing request!!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


    }




}
