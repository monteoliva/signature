package br.com.jadlog.signature;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

public class FinallyActivity extends CordovaActivity {
    private Toolbar toolbar;
    private ActionBar actionBar;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.finally_activity);

        final byte[] hash   = getIntent().getByteArrayExtra("HASH");
        final Bitmap bitmap = BitmapFactory.decodeByteArray(hash, 0, hash.length);

        toolbar   = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);

        actionBar();
    }

    private void actionBar() {
        // toolbar
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private void back() {
        startActivity(new Intent(this, SignatureActivity.class));
        finish();
        overridePendingTransition( R.anim.lefttoright, R.anim.stable );
    }

    @Override
    public boolean onSupportNavigateUp() {
        back();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { back(); return false; }
        else                                  { return super.onKeyDown(keyCode, event); }
    }
}
