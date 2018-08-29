package meme.kiteq.tipit;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import meme.kiteq.tipit.model.CheckInfo;
import meme.kiteq.tipit.model.ExposedRegisteredFn;
import meme.kiteq.tipit.model.ExposedRegisteredInn;
import meme.kiteq.tipit.model.ExposedWaiter;
import meme.kiteq.tipit.rateseq.RateActivity_;
import meme.kiteq.tipit.rateseq.RateRequester;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @Bean
    RateRequester presenter;
    CompositeDisposable cd = new CompositeDisposable();

    @ViewById
    View start, progress;

    private static final String TAG = "MainActivity";
    private IntentIntegrator currentIntegrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void init() {
        start.setOnClickListener(v -> {
            scan();
        });
    }

    private void scan() {
        start.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);

        IntentIntegrator intentIntegrator = new IntentIntegrator(this)
                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                .setBeepEnabled(false)
                .setOrientationLocked(false)
                .setCaptureActivity(CaptureActivityPortrait.class)
                .setPrompt("Отсканируйте QR-код с чека");

        currentIntegrator = intentIntegrator;
        intentIntegrator
                .initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult r = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (r != null && r.getContents() != null) {
            String contents = r.getContents();
            Log.d(TAG, "onActivityResult: " + contents);
            CheckInfo c = new CheckInfo(contents);
            if (c.valid()) {
                cd.add(
                        presenter.regRec(c)
                                .subscribe(() -> onRegisterCheck(c), this::onError)
                );
            } else {
                onError(new RuntimeException("inalid checkinfo"));
            }
        }
    }

    private void onError(Throwable e) {
        Snackbar.make(findViewById(android.R.id.content), "Что-то пошло не так!", Snackbar.LENGTH_LONG).show();
        Log.e(TAG, "onError: ", e);
    }

    private void onRegisterCheck(CheckInfo c) {
        cd.add(
                presenter.getFn(c.fn)
                    .subscribe(f -> onFn(f, c), this::onError)
        );
    }

    private void onFn(ExposedRegisteredFn f, CheckInfo c) {
        cd.add(
                Observable.zip(presenter.getInn(f.getInn()), presenter.getWaiters(f.getInn()), Pair::new)
                .subscribe(t -> onAllData(t, f, c), this::onError)
        );
    }

    private void onAllData(Pair<ExposedRegisteredInn, List<ExposedWaiter>> t, ExposedRegisteredFn f, CheckInfo c) {
        List<ExposedWaiter> second = t.second;
        ArrayList<ExposedWaiter> list = new ArrayList<>(second);
        Log.d(TAG, "onAllData: tinn" + t.first + " , tw: " + list + " , fn: " + f);
        RateActivity_.intent(this)
                .info(c)
                .targetFn(f)
                .targetInn(t.first)
                .targetWaiters(list)
                .start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cd.clear();
    }
}
