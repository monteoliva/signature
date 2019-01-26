package br.com.jadlog.signature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import br.com.jadlog.signature.ui.SignatureView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SignatureView signatureView;
    private Button btnClear;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signatureView = findViewById(R.id.signature);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnSave  = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnClear) {
            signatureView.clear();
        }
        else if (v == btnSave) {
            save();
        }
    }

    private void save() {
        String hash = signatureView.getHash();

        Log.d("Signature","HASH: " + hash);

        Intent intent = new Intent(this, FinallyActivity.class);
        intent.putExtra("HASH", hash);

        startActivity(intent);
    }
}