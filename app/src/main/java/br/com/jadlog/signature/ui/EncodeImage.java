package br.com.jadlog.signature.ui;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class EncodeImage {
    public byte[] encodeImage(Bitmap bitmap){
        byte[] retorno = null;

        if (bitmap == null) { return null; }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);

            retorno = stream.toByteArray();
        }
        catch (Exception e) {
            Log.d("Signature", "Error encodeImage - " + e.getMessage());
        }

        // retorna em branco
        return retorno;
    }
}