package app.firestore.ahmad.zeeshan.com.retrofit_001;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final static int ACCESS_TOKEN_REQUEST_CODE = 0;

    Button loadDataButton;
    Button manipulateDataButton;
    Button signinButton;
    Button signupButton;
    Button uploadImageButton;

    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadDataButton = findViewById(R.id.load_btn);
        manipulateDataButton = findViewById(R.id.manipulate_data_btn);
        signinButton = findViewById(R.id.signin_btn);
        signupButton = findViewById(R.id.signup_btn);
        uploadImageButton = findViewById(R.id.upload_image_btn);


        loadDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });


        manipulateDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getBaseContext(), token, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ManipulateActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });



        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SigninActivity.class);
                startActivityForResult(intent, ACCESS_TOKEN_REQUEST_CODE);
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });




        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACCESS_TOKEN_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                token = data.getStringExtra("token");
            }
        }

    }
}
