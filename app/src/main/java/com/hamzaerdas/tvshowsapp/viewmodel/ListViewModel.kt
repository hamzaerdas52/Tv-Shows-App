package com.hamzaerdas.tvshowsapp.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.hamzaerdas.tvshowsapp.model.TvShow
import com.hamzaerdas.tvshowsapp.model.TvShowResponse
import com.hamzaerdas.tvshowsapp.service.TvShowAPI
import com.hamzaerdas.tvshowsapp.service.TvShowDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val tvShowAPI: TvShowAPI, application: Application) : BaseViewModel(application){
    val popularTvShows = MutableLiveData<TvShowResponse>()
    val otherTvShows = MutableLiveData<TvShowResponse>()
    val tvShowErrorMessage = MutableLiveData<Boolean>()
    val tvShowLoading = MutableLiveData<Boolean>()
    val tvShowInfiniteLoading = MutableLiveData<Boolean>()
    val popularTvShowUpdated = MutableLiveData<Boolean>()
    var runnable: Runnable = Runnable{}
    var handler: Handler = Handler(Looper.getMainLooper())
    private var tvShowList = arrayListOf<TvShow>()
    var number = 0

    fun refreshPopularData() {
        deleteTvShow()
        getPopularDataToAPI()
        getPopularDataToList()
        timerPopularData()
    }

    private fun timerPopularData(){

        number = 0
        popularTvShowUpdated.value = false
        runnable = object : Runnable {
            override fun run() {
                if(number == 60) {
                    getPopularDataToList()
                    launch {
                        val tvShowListSQL = TvShowDatabase(getApplication()).getTvShowDao().getPopularTvShow()
                        var i = 0
                        while (i < tvShowList.size){
                            if(tvShowListSQL[i].id == tvShowList[i].id ){
                                popularTvShowUpdated.value = false
                            } else {
                                println("${tvShowListSQL[i].name} ${tvShowList[i].name}")
                                popularTvShowUpdated.value = true
                                println("Veri güncellendi")
                            }
                            i++
                        }
                        tvShowList.clear()
                    }
                    number = 0
                }
                number++
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }

    private fun timerStop(){
        handler.removeCallbacks(runnable)
        number = 0
    }

    fun refreshOtherData(page: Int) {
        getOtherDataToAPI(page)
    }

    fun refreshFromAPI(){
        timerStop()
        deleteTvShow()
        getPopularDataToAPI()
        timerPopularData()
    }

    private fun getPopularDataToAPI() {
        tvShowLoading.value = true

        disposable.add(
            tvShowAPI.getPopularTvShow()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvShowResponse>() {
                    override fun onSuccess(t: TvShowResponse) {
                        addTvShowSqlite(t)
                    }

                    override fun onError(e: Throwable) {
                        tvShowErrorMessage.value = true
                        tvShowLoading.value = false
                        tvShowInfiniteLoading.value = false
                        e.printStackTrace()
                    }
                }
                )
        )
    }

    private fun getOtherDataToAPI(page:Int) {
        tvShowInfiniteLoading.value = true

        disposable.add(
            tvShowAPI.getOtherTvShow(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvShowResponse>() {
                    override fun onSuccess(t: TvShowResponse) {
                        otherTvShows.value = t
                        tvShowInfiniteLoading.value = false
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

    private fun getTvShow(tvShow: TvShowResponse){
        popularTvShows.value = tvShow
        tvShowLoading.value = false
        tvShowInfiniteLoading.value = false
        tvShowErrorMessage.value = false
    }

    fun addTvShowSqlite(tvShow: TvShowResponse){
        launch {
            val dao = TvShowDatabase(getApplication()).getTvShowDao()
            dao.addTvShow(*tvShow.tvShows.toTypedArray())
            getTvShow(tvShow)
        }
    }

    private fun deleteTvShow(){
        launch {
            val dao = TvShowDatabase(getApplication()).getTvShowDao()
            dao.deleteAllTvShow()
        }
    }

    fun getPopularDataToList(){
        disposable.add(
            tvShowAPI.getPopularTvShow()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvShowResponse>() {
                    override fun onSuccess(t: TvShowResponse) {
                        tvShowList.addAll(t.tvShows)
                    }
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                }
                )
        )
    }
}