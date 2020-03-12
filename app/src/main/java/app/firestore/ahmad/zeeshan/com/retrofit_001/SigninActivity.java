package app.firestore.ahmad.zeeshan.com.retrofit_001;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.internal.http.RealResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SigninActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText passwordEditText;
    Button signinButton;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        nameEditText = findViewById(R.id.name_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        signinButton = findViewById(R.id.signin_btn);



        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:5000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        final BookClient bookClient = retrofit.create(BookClient.class);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                String username_password = nameEditText.getText().toString() + ":" + passwordEditText.getText().toString();

                String authHeader = "Basic " + Base64.encodeToString(username_password.getBytes(), Base64.NO_WRAP);

                final Call<Token> getTokenCall = bookClient.signIn(authHeader);

                getTokenCall.clone().enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getBaseContext(), response.body().getToken(), Toast.LENGTH_SHORT).show();
                            token = response.body().getToken();
                        }
                        else{
                            Toast.makeText(getBaseContext(), "login info incorrect!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Failed to connect to server!!", Toast.LENGTH_SHORT).show();

                    }
                });








            }
        });



    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("token", token);
        setResult(RESULT_OK, intent);
        finish();
    }






}
