package app.firestore.ahmad.zeeshan.com.retrofit_001;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText passwordEditText;
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        nameEditText = findViewById(R.id.newuser_name_edittext);
        passwordEditText = findViewById(R.id.newuser_password_edittext);
        signupButton = findViewById(R.id.signup_btn);




        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:5000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        final BookClient bookClient = retrofit.create(BookClient.class);





        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User(nameEditText.getText().toString(),
                        passwordEditText.getText().toString());

                Call<Message> signUpCall = bookClient.signUp(user);

                signUpCall.clone().enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                      /*  if(response.isSuccessful()){
                            Toast.makeText(getBaseContext(), "Account Created!!", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(getBaseContext(), "Failed to create account!!", Toast.LENGTH_SHORT).show();
                        }*/

                        if(response != null){
                            Toast.makeText(getBaseContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Server might be down!!", Toast.LENGTH_SHORT).show();

                    }
                });




            }
        });




























    }

















}
