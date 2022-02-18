package me.nyaken.gitsearch

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseActivity<DataBinding : ViewDataBinding>(
    @LayoutRes val layoutId: Int
): AppCompatActivity() {

    protected lateinit var binding: DataBinding
    private val compositeDisposable by lazy { CompositeDisposable() }

    protected fun Disposable.addToDisposable(): Disposable = apply { compositeDisposable.add(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<DataBinding>(this, layoutId).also { binding = it }

        onNewIntent(intent)
        viewBinding()
        setupObserve()
        initLayout()

    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    protected abstract fun viewBinding()

    protected abstract fun setupObserve()

    protected abstract fun initLayout()

}