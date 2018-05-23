package com.example.flaviuoprea.shopapp.presenter;

import android.support.annotation.MainThread;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by flaviuoprea on 12/30/17.
 */

public abstract class BasePresenter implements Presenter {
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {
        configureSubscription();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        unSubscribeAll();
    }

    protected void unSubscribeAll(){
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription.clear();
        }
    }

    private CompositeSubscription configureSubscription(){
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()){
            mCompositeSubscription = new CompositeSubscription();
        }
        return mCompositeSubscription;
    }

    protected <T> void subscribe(Observable<T> observable, Observer<T> observer){
        Subscription subscription = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.computation())
                .subscribe(observer);
        configureSubscription().add(subscription);
    }


}
