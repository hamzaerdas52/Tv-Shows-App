package com.hamzaerdas.tvshowsapp.viewmodel

import android.app.Application
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.hamzaerdas.tvshowsapp.R
import com.hamzaerdas.tvshowsapp.model.Favorite
import com.hamzaerdas.tvshowsapp.service.TvShowAPIService
import com.hamzaerdas.tvshowsapp.service.TvShowDatabase
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    var isFavorite = MutableLiveData<Boolean>()

    val tvShowAPIService = TvShowAPIService()
    val disposable = CompositeDisposable()

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun addFavorite(favorite: Favorite){
        launch {
            TvShowDatabase(getApplication()).getTvShowDao().addFavorite(favorite)
            val toast = Toast.makeText(getApplication(), "Favorilere eklendi", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.show()
        }
    }

    fun deleteFavorite(favorite: Favorite){
        launch{
            favorite.favoriteId?.let { TvShowDatabase(getApplication()).getTvShowDao().deleteFavorite(it) }
            val toast = Toast.makeText(getApplication(), "Favorilerden silindi", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.show()
        }
    }

    fun isFavorite(favoriteId: Int) {
        launch {
            val favorite = TvShowDatabase(getApplication()).getTvShowDao().hasBeenAdded(favoriteId)
            isFavorite.value = favorite == 1
        }
    }

}