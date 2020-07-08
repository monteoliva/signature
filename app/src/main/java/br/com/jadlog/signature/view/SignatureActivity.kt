package br.com.jadlog.signature.view

import android.content.Intent
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar

import org.koin.android.viewmodel.ext.android.viewModel

import br.com.jadlog.signature.R
import br.com.jadlog.signature.ui.AssinaturaComponent
import br.com.jadlog.signature.ui.AssinaturaViewModel

class SignatureActivity : BaseActivity(R.layout.signature_activity) {
    private lateinit var toolbar: Toolbar
    private lateinit var actionBar: ActionBar
    private lateinit var btnOpen: Button
    private lateinit var assinatura: AssinaturaComponent

    private val viewModel: AssinaturaViewModel by viewModel()

    override fun initViews() {
        assinatura = findViewById(R.id.assinaturaComponent)
        actionBar()
    }

    override fun initViewModel() {
    }

    private fun actionBar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        actionBar = supportActionBar!!
        actionBar.title = ""
    }

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
            R.id.action_open -> assinatura.show()
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