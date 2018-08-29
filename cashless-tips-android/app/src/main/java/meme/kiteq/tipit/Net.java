package meme.kiteq.tipit;

import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import meme.kiteq.tipit.model.ExposedRegisteredFn;
import meme.kiteq.tipit.model.ExposedRegisteredInn;
import meme.kiteq.tipit.model.ExposedWaiter;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Net {
    @GET("getInnInfo")
    Observable<ExposedRegisteredInn> getInnInfo(@Query("inn") String inn);

    @GET("getFnInfo")
    Observable<ExposedRegisteredFn> getFnInfo(@Query("fn") String fn);

    @GET("listFnByInn")
    Observable<List<ExposedRegisteredFn>> listFnByInn(@Query("inn") String inn);

    @GET("listWaitersByInn")
    Observable<List<ExposedWaiter>> listWaitersByInn(@Query("inn") String inn);

    // registerReceipt(receiptTime : Int, sum : Long, fn : String, fd : String, fp : String, n : String)
    @GET("registerReceipt")
    Completable registerReceipt(@Query("receiptTime") int receiptTime,
                                       @Query("sum") long sum,
                                       @Query("fn") String fn,
                                       @Query("fd") String fd,
                                       @Query("fp") String fp,
                                       @Query("n") String n);

    @GET("payTips")
    Completable payTips(@Query("inn") String inn,
                        @Query("amount") long amount,
                        @Query("rate") @Nullable Integer rate,
                        @Query("comment") @Nullable String comment,
                        @Query("waiter_id") @Nullable String waiterId);

}
