package meme.kiteq.tipit.rateseq;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import meme.kiteq.tipit.R;
import meme.kiteq.tipit.model.ExposedRegisteredFn;
import meme.kiteq.tipit.model.ExposedRegisteredInn;
import meme.kiteq.tipit.model.ExposedWaiter;

@EFragment(R.layout.rate_fragment)
public class RateFragment extends Fragment {
    @FragmentArg
    @IdRes int container;
    @FragmentArg
    String orderDate, placeTitle;
    @FragmentArg
    int orderSum;

    @FragmentArg
    ExposedRegisteredFn targetFn;
    @FragmentArg
    ExposedRegisteredInn targetInn;
    @FragmentArg
    ArrayList<ExposedWaiter> targetWaiters;

    @ViewById
    StarView rate_view;


    @AfterViews
    void init() {
        rate_view.setOnRated(this::go);
    }

    private void go(int i) {
        RCFragment f = RCFragment_.builder()
                .orderDate(orderDate)
                .orderSum(orderSum)
                .placeTitle(placeTitle)
                .rating(i)
                .container(container)
                .targetFn(targetFn)
                .targetInn(targetInn)
                .targetWaiters(targetWaiters)
                .build();

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(container, f, RCFragment.ID_TAG)
                .commit();
    }


}
