package br.com.jadlog.signature;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import br.com.jadlog.signature.ui.EncodeImage;

public class FinallyActivity extends AppCompatActivity {

    private EncodeImage encodeImage = new EncodeImage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally);

        String hash = getIntent().getStringExtra("HASH");

        Bitmap bitmap = encodeImage.decodeImageBase64(hash);

        ImageView imageView = findViewById(R.id.imageView);
                  imageView.setImageBitmap(bitmap);
    }
}
