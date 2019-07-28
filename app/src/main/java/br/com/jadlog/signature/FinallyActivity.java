package br.com.jadlog.signature;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.com.jadlog.signature.ui.EncodeImage;

@EActivity(R.layout.finally_activity)
public class FinallyActivity extends CordovaActivity {
    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;

    protected ActionBar actionBar;

    @ViewById(R.id.imageView)
    protected ImageView imageView;

    @AfterViews
    protected void afterViews() {
        final byte[] hash   = getIntent().getByteArrayExtra("HASH");
        final Bitmap bitmap = BitmapFactory.decodeByteArray(hash, 0, hash.length);

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
        startActivity(new Intent(this, SignatureActivity_.class));
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
