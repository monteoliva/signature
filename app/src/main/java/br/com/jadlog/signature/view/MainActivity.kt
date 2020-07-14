package br.com.jadlog.signature.view

import android.content.Intent
import android.graphics.Bitmap
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.main_activity.*

import org.koin.android.viewmodel.ext.android.viewModel

import br.com.jadlog.signature.R
import br.com.jadlog.signature.ui.OnAssinaturaListener

class MainActivity : BaseActivity(R.layout.main_activity) {
    private val viewModel: MainViewModel by viewModel()

    override fun initViews() {
        assinaturaComponent.setOnAssinaturaListener(
                object : OnAssinaturaListener {
                    override fun onBitmap(bitmap: Bitmap?) {
                    }

                    override fun onByteArray(byteArray: ByteArray?) {
                        assinaturaComponent.hide()
                        confirm(byteArray)
                    }

                    override fun onHash(hash: String?) {
                    }
                }
        )

        btnAssinatura.setOnClickListener { open() }
    }

    override fun initViewModel() {}

    private fun confirm(hash: ByteArray?) {
        if (hash == null) {
            msg()
            return
        }
        val intent = Intent(this, FinallyActivity::class.java)
            intent.putExtra("HASH", hash)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.righttoleft, R.anim.stable)
    }

    private fun open() {
        assinaturaComponent.show()
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
            dialog.setTitle(R.string.signature_error_title)
            dialog.setMessage(R.string.signature_error_msg)
            dialog.setCancelable(false)
            dialog.setNeutralButton("OK") { dialog, whichButton -> }

        // show the Dialog
        dialog.create().show()
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