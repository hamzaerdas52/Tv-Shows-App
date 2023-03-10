package com.hamzaerdas.tvshowsapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.hamzaerdas.tvshowsapp.model.TvShowDetail
import com.hamzaerdas.tvshowsapp.service.TvShowAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class TvShowDetailsViewModel(application: Application) : BaseViewModel(application) {

    val tvShowsDetail = MutableLiveData<TvShowDetail>()
    val tvShowDetailLoading = MutableLiveData<Boolean>()

    private val tvShowAPIService = TvShowAPIService()
    private val disposable = CompositeDisposable()

    fun getDataDetails(id: Int){
        getDataDetailsToAPI(id)
    }

    private fun getDataDetailsToAPI(id: Int){
        tvShowDetailLoading.value = true

        disposable.add(
            tvShowAPIService.getDataDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvShowDetail>(){
                    override fun onSuccess(t: TvShowDetail) {
                        t.vote = ((t.vote).toString()).substring(0,3).toFloat()
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