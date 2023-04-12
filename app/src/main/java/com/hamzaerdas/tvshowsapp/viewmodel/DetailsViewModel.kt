package com.hamzaerdas.tvshowsapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.hamzaerdas.tvshowsapp.model.TvShowDetail
import com.hamzaerdas.tvshowsapp.service.FavoriteDao
import com.hamzaerdas.tvshowsapp.service.TvShowAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val tvShowAPI: TvShowAPI,
    private val favoriteDao: FavoriteDao,
    application: Application
) : BaseViewModel(favoriteDao, application) {

    val tvShowsDetail = MutableLiveData<TvShowDetail>()
    val tvShowDetailLoading = MutableLiveData<Boolean>()

    fun getDataDetails(id: Int) {
        getDataDetailsToAPI(id)
    }

    private fun getDataDetailsToAPI(id: Int) {
        tvShowDetailLoading.value = true

        disposable.add(
            tvShowAPI.getTvShowDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvShowDetail>() {
                    override fun onSuccess(t: TvShowDetail) {
                        t.vote = ((t.vote).toString()).substring(0, 3).toFloat()
                        tvShowsDetail.value = t
                        tvShowDetailLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        tvShowDetailLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }
}