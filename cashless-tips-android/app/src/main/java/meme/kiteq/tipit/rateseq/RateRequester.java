package meme.kiteq.tipit.rateseq;

import org.androidannotations.annotations.EBean;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import meme.kiteq.tipit.Net;
import meme.kiteq.tipit.NetRef;
import meme.kiteq.tipit.model.CheckInfo;
import meme.kiteq.tipit.model.ExposedRegisteredFn;
import meme.kiteq.tipit.model.ExposedRegisteredInn;
import meme.kiteq.tipit.model.ExposedWaiter;

@EBean(scope = EBean.Scope.Singleton)
public class RateRequester {
    private final Net net;
    private final CompositeDisposable d;

    public RateRequester() {
        net = NetRef.getNet();
        d = new CompositeDisposable();
    }

    public void reg(Disposable what) {
        d.add(what);
    }

    public void detach() {
        d.clear();
    }

    public Completable regRec(CheckInfo checkInfo) {
        return net.registerReceipt(checkInfo.timeDescrewed(),
                (long) Double.parseDouble(checkInfo.sum),
                checkInfo.fn,
                checkInfo.i,
                checkInfo.fp,
                checkInfo.n)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ExposedRegisteredFn> getFn(String fnId) {
        return net.getFnInfo(fnId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ExposedRegisteredInn> getInn(String innId) {
        return net.getInnInfo(innId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<ExposedWaiter>> getWaiters(String innId) {
        return net.listWaitersByInn(innId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable submitResult(String inn, long amount, Integer rate, String comment, String waiterId) {
        return net.payTips(inn, amount, rate, comment, waiterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
