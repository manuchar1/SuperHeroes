package com.school_project.superHeroesApp.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import com.school_project.superHeroesApp.R
import com.school_project.superHeroesApp.data.storage.DataStore
import com.school_project.superHeroesApp.utils.updateLocale

abstract class LanguageAwareActivity : AppCompatActivity() {
    private lateinit var loadingView: View

    private lateinit var contentView: ContentFrameLayout

    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MyApplication)
        contentView = findViewById<ContentFrameLayout>(android.R.id.content)
        loadingView = layoutInflater.inflate(R.layout.dialog_loading, contentView, false)
    }

    override fun attachBaseContext(newBase: Context?) {
        val newLangContext = newBase?.let { updateLocale(it, DataStore.language) }
        super.attachBaseContext(newLangContext)
    }

    protected fun showDialog(@StringRes title: Int, @StringRes message: Int) {
        showDialog(title, getString(message))
    }

    protected fun showDialog(@StringRes title: Int, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(
                R.string.common_Ok
            ) { dialog, _ -> dialog.dismiss() }
            .setCancelable(true)
            .show()
    }

    fun showLoading() {
        if (loading) return
        contentView.addView(loadingView)
        loading = true
    }

    fun hideLoading() {
        if (!loading) return
        contentView.removeView(loadingView)
        loading = false
    }

}