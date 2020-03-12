package app.firestore.ahmad.zeeshan.com.retrofit_001;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BookClient {

    @GET("book")
    Call<List<Book>> getAllBooks(@Header("x-access-token") String authToken);

    @GET("book/{book_id}")
    Call<Book> getSingleBook(@Header("x-access-token") String authToken, @Path(value = "book_id") int book_id);

    @POST("book")
    Call<Message> addBook(@Header("x-access-token") String authToken, @Body Book book);

    @PUT("book/{book_id}")
    Call<Message> updateBook(@Header("x-access-token") String authToken, @Path(value = "book_id") int book_id, @Body Book book);

    @DELETE("book/{book_id}")
    Call<Message> deleteBook(@Header("x-access-token") String authToken, @Path(value = "book_id") int book_id);

    @GET("login")
      Call<Token> signIn(@Header("Authorization") String authHeader);

    @POST("signup")
    Call<Message> signUp(@Body User user);

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadImage(@Part("description") RequestBody description, @Part MultipartBody.Part photo);


}
