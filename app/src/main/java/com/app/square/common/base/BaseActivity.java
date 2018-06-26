package com.app.square.common.base;

import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import com.app.square.SquareApp;
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseActivity extends AppCompatActivity {

  private Disposable networkDisposable;

  @Override protected void onResume() {
    super.onResume();

    networkDisposable = ReactiveNetwork.observeNetworkConnectivity(SquareApp.getContext())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Connectivity>() {
          @Override public void accept(final Connectivity connectivity) {
            if(connectivity.getState().equals(NetworkInfo.State.CONNECTING) ||
                connectivity.getState().equals(NetworkInfo.State.CONNECTED)){
              hasConnection();

            } else {
              loseConnection();
            }
          }
        });
  }

  @Override protected void onPause() {
    super.onPause();
    safelyDispose(networkDisposable);
  }

  private void safelyDispose(Disposable... disposables) {
    for (Disposable subscription : disposables) {
      if (subscription != null && !subscription.isDisposed()) {
        subscription.dispose();
      }
    }
  }

  public abstract void hasConnection();
  public abstract void loseConnection();

}
