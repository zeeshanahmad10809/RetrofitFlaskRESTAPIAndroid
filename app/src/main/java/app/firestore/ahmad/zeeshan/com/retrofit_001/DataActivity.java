package app.firestore.ahmad.zeeshan.com.retrofit_001;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataActivity extends AppCompatActivity {

    Button loadAllButton;
    EditText searchDataEditText;
    Button searchDataButton;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);


        loadAllButton = findViewById(R.id.load_all_btn);
        searchDataEditText = findViewById(R.id.single_load_edittext);
        searchDataButton = findViewById(R.id.load_single_btn);
        textView = findViewById(R.id.data_textview);


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:5000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        final BookClient bookClient = retrofit.create(BookClient.class);


        final String token = getIntent().getStringExtra("token");

        loadAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Call<List<Book>> bookListCall = bookClient.getAllBooks(token);


                bookListCall.clone().enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                        List<Book> bookList = response.body();

                        StringBuilder stringBuilder = new StringBuilder("");
                        for(Book book : bookList){
                            stringBuilder.append("{\nid : " + book.getId() + " ,\n name : " + book.getName() + " ,\n author : " + book.getAuthor() + " ,\n publisher : " + book.getPublisher() + "\n}\n");
                        }

                        textView.setText(stringBuilder.toString());

                    }

                    @Override
                    public void onFailure(Call<List<Book>> call, Throwable t) {
                        textView.setText("Data not loaded!");
                    }
                });
            }
        });



        searchDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Call<Book> getSingleBookCall = bookClient.getSingleBook(token, Integer.parseInt(searchDataEditText.getText().toString()));
                getSingleBookCall.clone().enqueue(new Callback<Book>() {
                    @Override
                    public void onResponse(Call<Book> call, Response<Book> response) {
                        Book book = response.body();

                        StringBuilder stringBuilder = new StringBuilder("");
                        stringBuilder.append("{\nid : " + book.getId() + " ,\n name : " + book.getName() + " ,\n author : " + book.getAuthor() + " ,\n publisher : " + book.getPublisher() + "\n}\n");

                        textView.setText(stringBuilder.toString());

                    }

                    @Override
                    public void onFailure(Call<Book> call, Throwable t) {
                        textView.setText("Data not loaded!");
                    }
                });
            }
        });









    }
}
