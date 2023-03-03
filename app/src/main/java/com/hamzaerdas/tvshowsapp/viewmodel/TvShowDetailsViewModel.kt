package com.hamzaerdas.tvshowsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamzaerdas.tvshowsapp.model.TvShowDetail
import com.hamzaerdas.tvshowsapp.service.TvShowAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.text.DecimalFormat

class TvShowDetailsViewModel : ViewModel() {

    val tvShowsDetail = MutableLiveData<TvShowDetail>()
    val tvShowDetailLoading = MutableLiveData<Boolean>()

    private val tvShowAPIService = TvShowAPIService()
    private val disposable = CompositeDisposable()

    fun getDataDetails(id: Int){
        /*
        if(id == 1){
            val viking = TvShow(1, "Vikings", "Viking Dizisi", 8.9, "hamza.com")
            tvShows.value = viking
        }
         */
        getDataDetailsToAPI(id)
    }

    fun getDataDetailsToAPI(id: Int){
        tvShowDetailLoading.value = true

        disposable.add(
            tvShowAPIService.getDataDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvShowDetail>(){
                    override fun onSuccess(t: TvShowDetail) {

                        t.vote = ((t.vote).toString()).substring(0,3).toFloat()
                        println(t.vote)

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