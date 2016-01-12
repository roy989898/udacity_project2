package pom2.poly.com.tryretrofit2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MyTask().execute();
    }


    class MyTask extends AsyncTask<Void, Void, StackOverflowQuestions> {

        @Override
        protected StackOverflowQuestions doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.stackexchange.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            StackOverflowAPI stackOverflowAPI = retrofit.create(StackOverflowAPI.class);

            Call<StackOverflowQuestions> call = stackOverflowAPI.loadQuestions("android");
            //asynchronous call
            Response<StackOverflowQuestions> qo=null;
            try {
               qo = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return qo.body();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(StackOverflowQuestions stackOverflowQuestions) {
            StackOverflowQuestions answer = stackOverflowQuestions;
        }
    }
}
