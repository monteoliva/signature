package br.com.jadlog.signature.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(resourceView: Int) : AppCompatActivity(resourceView) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initViewModel()
    }

    abstract fun initViews()
    abstract fun initViewModel()
}