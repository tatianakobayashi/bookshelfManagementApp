package atividade.mobile.tatiana.trabalhocontrolelivros.activities.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Exemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;

public class ShowExemplarCoverActivity extends AppCompatActivity {
    private ImageView imgCover;
    private TextView textView;

    private User user;
    private Exemplar exemplar;

    private static final String TAG = "coverImage";

    //TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exemplar_cover);

        user = (User) getIntent().getExtras().getSerializable("logged_user");

        if(user == null){
            finish();
        }
        exemplar = (Exemplar) getIntent().getExtras().getSerializable("Exemplar");
        if(exemplar == null){
            finish();
        }

        imgCover = findViewById(R.id.imgCover);
        textView = findViewById(R.id.textView);

        if (exemplar.getCoverImage() == null){
            imgCover.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
        else{
            imgCover.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);

            new Thread() {
                public void run(){
                    try
                    {
                        Log.i(TAG, "Iniciando o Download da imagem...");
                        URL url = new URL(exemplar.getCoverImage());
                        HttpURLConnection connection;
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();

                        InputStream stream = connection.getInputStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(stream);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgCover.setImageBitmap(bitmap);
                            }
                        });
                        connection.disconnect();
                    }catch (MalformedURLException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
