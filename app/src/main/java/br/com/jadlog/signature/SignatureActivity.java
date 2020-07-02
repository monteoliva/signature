package br.com.jadlog.signature;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

import br.com.jadlog.signature.ui.Assinatura;
import br.com.jadlog.signature.ui.AssinaturaView;
import br.com.jadlog.signature.ui.OnAssinaturaListener;

public class SignatureActivity extends CordovaActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ActionBar actionBar;
    private AssinaturaView signatureView;
    private Button btnClear;
    private Button btnSave;

    private Assinatura assinatura;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signature_activity);

        signatureView = findViewById(R.id.signature);
        btnClear      = findViewById(R.id.btnClear);
        btnSave       = findViewById(R.id.btnSave);

        actionBar();

        // set Listener
        btnClear.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        assinatura = new Assinatura.Builder()
                .context(this)
                .listener(new OnAssinaturaListener() {
                    @Override
                    public void onSuccess(@NotNull byte[] hash) { confirm(hash); }
                })
                .build();
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("");
        }
    }

    private void save() {
        assinatura.show();
        //confirm(signatureView.getHash());
    }

    private void confirm(byte[] hash) {
        if (hash == null) {
            msg();
            return;
        }

        Intent intent = new Intent(this, FinallyActivity.class);
               intent.putExtra("HASH", hash);

        startActivity(intent);
        finish();
        overridePendingTransition( R.anim.righttoleft, R.anim.stable );
    }

    //**********************************************************************************************
    // Menu Principal
    //**********************************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close:
                finish();
                break;
        }
        return true;
    }
    //**********************************************************************************************

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

    @Override
    public void onClick(View v) {
        if      (v == btnClear) { signatureView.clear(); }
        else if (v == btnSave)  { save(); }
    }
}