package br.com.jadlog.signature.view

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.KeyEvent
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.finally_activity.*

import br.com.jadlog.signature.R

class FinallyActivity : BaseActivity(R.layout.finally_activity) {
    override fun initViews() {
        val hash = intent.getByteArrayExtra("HASH")
        val bitmap = BitmapFactory.decodeByteArray(hash, 0, hash.size)

        imageView.setImageBitmap(bitmap)

        actionBar()
    }

    override fun initViewModel() {
    }

    private fun actionBar() {
        setSupportActionBar(findViewById(R.id.toolbar))

        val actionBar: ActionBar = supportActionBar!!
            actionBar.title = ""
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
    }

    private fun back() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        overridePendingTransition(R.anim.lefttoright, R.anim.stable)
    }

    override fun onSupportNavigateUp(): Boolean {
        back()
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            back()
            false
        }
        else {
            super.onKeyDown(keyCode, event)
        }
    }
}