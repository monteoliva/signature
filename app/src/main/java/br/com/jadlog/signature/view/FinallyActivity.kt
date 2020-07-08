package br.com.jadlog.signature.view

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.KeyEvent
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import br.com.jadlog.signature.R

class FinallyActivity : BaseActivity(R.layout.finally_activity) {
    private lateinit var toolbar: Toolbar
    private lateinit var actionBar: ActionBar
    private lateinit var imageView: ImageView

    override fun initViews() {
        val hash = intent.getByteArrayExtra("HASH")
        val bitmap = BitmapFactory.decodeByteArray(hash, 0, hash.size)

        imageView = findViewById(R.id.imageView)
        imageView.setImageBitmap(bitmap)

        actionBar()
    }

    override fun initViewModel() {
    }

    private fun actionBar() {
        // toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        actionBar = supportActionBar!!
        actionBar.title = ""
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)
    }

    private fun back() {
        startActivity(Intent(this, SignatureActivity::class.java))
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