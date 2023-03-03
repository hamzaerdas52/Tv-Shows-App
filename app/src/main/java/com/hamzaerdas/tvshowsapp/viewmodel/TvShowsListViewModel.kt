package com.hamzaerdas.tvshowsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamzaerdas.tvshowsapp.model.TvShow
import com.hamzaerdas.tvshowsapp.model.TvShowResponse
import com.hamzaerdas.tvshowsapp.service.TvShowAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class TvShowsListViewModel : ViewModel() {
    val popularTvShows = MutableLiveData<TvShowResponse>()
    val otherTvShows = MutableLiveData<TvShowResponse>()
    val tvShowErrorMessage = MutableLiveData<Boolean>()
    val tvShowLoading = MutableLiveData<Boolean>()

    private val tvShowAPIService = TvShowAPIService()
    private val disposable = CompositeDisposable()

    fun refreshPopularData() {
        getPopularDataToAPI()
    }

    fun refreshOtherData(page: Int) {
        getOtherDataToAPI(page)
    }

    fun getPopularDataToAPI() {
        tvShowLoading.value = true

        disposable.add(
            tvShowAPIService.getPopularData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvShowResponse>() {
                    override fun onSuccess(t: TvShowResponse) {
                        popularTvShows.value = t
                        tvShowLoading.value = false
                        tvShowErrorMessage.value = false
                    }

                    override fun onError(e: Throwable) {
                        tvShowErrorMessage.value = true
                        tvShowLoading.value = false
                        e.printStackTrace()
                    }
                }
                )
        )
    }

    fun getOtherDataToAPI(page:Int) {
        tvShowLoading.value = true

        disposable.add(
            tvShowAPIService.getOtherData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvShowResponse>() {
                    override fun onSuccess(t: TvShowResponse) {
                        otherTvShows.value = t
                        tvShowLoading.value = false
                        tvShowErrorMessage.value = false
                    }

                    override fun onError(e: Throwable) {
                        tvShowErrorMessage.value = true
                        tvShowLoading.value = false
                        e.printStackTrace()
                    }
                }
                )
        )
    }

}