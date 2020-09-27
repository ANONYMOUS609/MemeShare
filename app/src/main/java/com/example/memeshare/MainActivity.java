package com.example.memeshare;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.memeshare.json.ApiHandler;
import com.example.memeshare.json.Meme;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView meme_image;
    private TextView caption;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        caption = findViewById(R.id.caption);
        meme_image = findViewById(R.id.meme);
        progressBar = findViewById(R.id.progressBar);

        fetchMeme();
        progressBar.setVisibility(View.VISIBLE);

    }

    public void refresh_btn(View view) {
        fetchMeme();
        progressBar.setVisibility(View.VISIBLE);
    }

    public void fetchMeme(){
        ApiHandler apiHandler = GetMethod.getRetrofit().create(ApiHandler.class);
        Call<Meme> call = apiHandler.getMeme();
        call.enqueue(new Callback<Meme>() {
            @Override
            public void onResponse(Call<Meme> call, Response<Meme> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Ruko zara thoda sabr kro", Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
                    Meme meme = response.body();
                    progressBar.setVisibility(View.VISIBLE);
                assert meme != null;
                caption.setText(meme.getTitle());
                    Glide.with(getApplicationContext()).load(meme.getUrl()).into(meme_image);

            }

            @Override
            public void onFailure(Call<Meme> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Sorry Babu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}