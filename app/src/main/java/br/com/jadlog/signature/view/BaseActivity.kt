package br.com.jadlog.signature.view

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(resourceView: Int) : AppCompatActivity(resourceView) {
    override fun onStart() {
        super.onStart()
        initViews()
        initViewModel()
    }

    abstract fun initViews()
    abstract fun initViewModel()
}