package br.com.jadlog.signature.view

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

abstract class BaseActivity(resourceView: Int) : FragmentActivity(resourceView) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LIFECYCLE", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("LIFECYCLE", "onStart")
        initViews()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "onResume")
    }

    abstract fun initViews()
    abstract fun initViewModel()
}