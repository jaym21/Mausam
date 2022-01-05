package dev.jaym21.mausam.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposableManager {

    companion object {

        private var compositeDisposable: CompositeDisposable? = null

        //to provide singleton composite disposable
        private fun getCompositeDisposable(): CompositeDisposable {
            if (compositeDisposable == null || compositeDisposable!!.isDisposed) {
                compositeDisposable = CompositeDisposable()
            }
            return compositeDisposable!!
        }

        //adding a disposable to composite disposable
        fun add(disposable: Disposable) {
            getCompositeDisposable().add(disposable)
        }

        //disposing to composite disposable
        fun dispose() {
            getCompositeDisposable().dispose()
        }
    }
}