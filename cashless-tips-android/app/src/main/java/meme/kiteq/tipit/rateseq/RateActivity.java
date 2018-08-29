package meme.kiteq.tipit.rateseq;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import meme.kiteq.tipit.R;
import meme.kiteq.tipit.model.CheckInfo;
import meme.kiteq.tipit.model.ExposedRegisteredFn;
import meme.kiteq.tipit.model.ExposedRegisteredInn;
import meme.kiteq.tipit.model.ExposedWaiter;

@EActivity(R.layout.rate_activity)
public class RateActivity extends AppCompatActivity {
    private static DateFormat sdf = SimpleDateFormat.getDateInstance();
    @Extra
    CheckInfo info;

    @Extra
    ExposedRegisteredFn targetFn;
    @Extra
    ExposedRegisteredInn targetInn;
    @Extra
    ArrayList<ExposedWaiter> targetWaiters;

    @ViewById
    FrameLayout container;

    @AfterViews
    void init() {
        Date d = new Date();
        d.setTime(info.time());

        RateFragment f = RateFragment_.builder()
                .targetFn(targetFn)
                .targetInn(targetInn)
                .targetWaiters(targetWaiters)
                .container(container.getId())
                .orderDate(sdf.format(d))
                .orderSum(((int) Double.parseDouble(info.sum)))
                .placeTitle("place_name")
                .build();

        getSupportFragmentManager().beginTransaction()
                .replace(container.getId(), f)
                .commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager sfm = getSupportFragmentManager();
        if (sfm.getBackStackEntryCount() > 0) {
            sfm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
