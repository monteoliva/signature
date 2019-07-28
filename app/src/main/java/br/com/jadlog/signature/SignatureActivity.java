package br.com.jadlog.signature;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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

@EActivity(R.layout.signature_activity)
@OptionsMenu(R.menu.menu_main)
public class SignatureActivity extends CordovaActivity {
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
        byte[] hash = signatureView.getHash();

        if (hash == null) {
            msg();
            return;
        }

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

    private void msg() {
        // abre a janela
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.AlertDialogThemeMsg);
        dialog.setTitle(R.string.signature_error_title);
        dialog.setMessage(R.string.signature_error_msg);
        dialog.setCancelable(false);
        dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        // show the Dialog
        dialog.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { finish(); return false; }
        else                                  { return super.onKeyDown(keyCode, event); }
    }
}