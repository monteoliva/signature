package br.com.jadlog.signature;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import br.com.jadlog.signature.ui.SignatureView;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends CordovaActivity {
    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;

    protected ActionBar actionBar;

    @ViewById(R.id.signature)
    protected SignatureView signatureView;

    @AfterViews
    protected void afterViews() {
        // toolbar
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("");
        }
    }

    @Click(R.id.btnClear)
    protected void clear() {
        signatureView.clear();
    }

    @Click(R.id.btnSave)
    protected void save() {
        String hash = signatureView.getHash();

        Log.d("Signature","HASH: " + hash);

        Intent intent = new Intent(this, FinallyActivity_.class);
               intent.putExtra("HASH", hash);

        startActivity(intent);
        finish();
        overridePendingTransition( R.anim.righttoleft, R.anim.stable );
    }

    @OptionsItem(R.id.action_close)
    protected void close() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { finish(); return false; }
        else                                  { return super.onKeyDown(keyCode, event); }
    }
}