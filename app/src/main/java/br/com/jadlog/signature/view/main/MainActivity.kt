package br.com.jadlog.signature.view.main

import android.content.Intent
import android.graphics.Bitmap
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog

import kotlinx.android.synthetic.main.main_activity.*

import org.koin.android.viewmodel.ext.android.viewModel

import br.com.jadlog.signature.R
import br.com.jadlog.signature.ui.OnSignatureListener
import br.com.jadlog.signature.view.BaseActivity
import br.com.jadlog.signature.view.result.ResultActivity

class MainActivity : BaseActivity(R.layout.main_activity) {
    private val viewModel: MainViewModel by viewModel()

    override fun initViews() {
        signatureComponent.setOnAssinaturaListener(
                object : OnSignatureListener {
                    override fun onBitmap(bitmap: Bitmap?) {
                    }

                    override fun onByteArray(byteArray: ByteArray?) {
                        signatureComponent.hide()
                        confirm(byteArray)
                    }

                    override fun onHash(hash: String?) {
                    }
                }
        )

        btnSignature.setOnClickListener { open() }
    }

    override fun initViewModel() {}

    private fun confirm(hash: ByteArray?) {
        if (hash == null) {
            msg()
            return
        }
        val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("HASH", hash)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.righttoleft, R.anim.stable)
    }

    private fun open() {
        signatureComponent.show()
    }

    //**********************************************************************************************
    // Menu Principal
    //**********************************************************************************************
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_close -> finish()
            R.id.action_open  -> open()
        }
        return true
    }

    //**********************************************************************************************
    private fun msg() {
        // abre a janela
        val dialog = AlertDialog.Builder(this, R.style.AlertDialogThemeMsg)
            dialog.apply {
                setTitle(R.string.signature_error_title)
                setMessage(R.string.signature_error_msg)
                setCancelable(false)
                setNeutralButton("OK") { dialog, whichButton -> }
                create().show()
            }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            false
        }
        else {
            super.onKeyDown(keyCode, event)
        }
    }
}