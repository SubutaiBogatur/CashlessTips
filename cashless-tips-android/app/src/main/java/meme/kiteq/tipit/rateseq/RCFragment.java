package meme.kiteq.tipit.rateseq;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import meme.kiteq.tipit.R;
import meme.kiteq.tipit.model.ExposedRegisteredFn;
import meme.kiteq.tipit.model.ExposedRegisteredInn;
import meme.kiteq.tipit.model.ExposedWaiter;

@EFragment(R.layout.rc_fragment)
public class RCFragment extends Fragment {
    public static final String ID_TAG = "RCFragment";
    private static final String TAG = "RCFragment";
    @FragmentArg
    String placeTitle, orderDate;

    @FragmentArg
    int orderSum;

    @FragmentArg
    int rating;

    @FragmentArg
    @IdRes
    int container;

    @FragmentArg
    ExposedRegisteredFn targetFn;
    @FragmentArg
    ExposedRegisteredInn targetInn;
    @FragmentArg
    ArrayList<ExposedWaiter> targetWaiters;

    int tipIndex;

    @ViewById
    StarView star_view;

    @ViewById
    TipView tip_view;

    @ViewById
    EditText comment;

    @ViewById
    LinearLayout tip_block, sum_block;

    @ViewById
    TextView place_name, sum_header, date_header, sum_calc_from, sum_calc_calcd, waiter_name;

    @ViewById
    Button cancel, commit;

    @ViewById
    View waiter_card;

    @Bean
    RateRequester requester;

    CompositeDisposable cd = new CompositeDisposable();
    private ExposedWaiter currentWaiter;

    @AfterViews
    void init() {
        place_name.setText(placeTitle);
        sum_header.setText("Сумма: " + orderSum);
        date_header.setText("Дата: " + orderDate);
        sum_calc_from.setText("От: " + orderSum + "Р");

        if (targetWaiters.size() < 1) {
            waiter_card.setVisibility(View.GONE);
        } else {
            onWaiterChanged(targetWaiters.get(0));
        }

        star_view.setRate(rating);
        tip_view.setPercentInt(0);
        tipIndex = 0;

        star_view.setOnRated(rating -> {
            this.rating = rating;
            runVisibility();
        });

        tip_view.onTipSelected(tipIndex -> {
            this.tipIndex = tipIndex;
            runVisibility();
        });

        cancel.setOnClickListener(v -> getActivity().finish());

        commit.setOnClickListener(v -> {
            collectAndProceed();
        });

        runVisibility();
    }

    private void runVisibility() {
        if (rating < 3) {
            tipIndex = 0;
            tip_block.setVisibility(View.GONE);
        } else {
            tip_block.setVisibility(View.VISIBLE);
        }

        if (tipIndex == 0) {
            sum_block.setVisibility(View.GONE);
        } else {
            sum_block.setVisibility(View.VISIBLE);
            sum_calc_calcd.setText("Чаевые: " + calculatePrc() + "Р");
        }
    }

    private int calculatePrc() {
        int tipPrc = TipView.PRCS_VALS[tipIndex];
        int tipSum = orderSum * tipPrc / 100;

        return tipSum;
    }

    private void collectAndProceed() {
        int rating = star_view.getRating();
        int tipPercent = (rating < 3) ? 0 : TipView.PRCS_VALS[tip_view.getPercentInd()];
        String comment = this.comment.getText().toString();

        cd.add(
                requester.submitResult(targetInn.inn, calculatePrc(), rating, comment, currentWaiter == null ? null : Integer.toString(currentWaiter.id))
                        .subscribe(this::onOk, this::onError)
        );
    }

    private void onError(Throwable throwable) {
        Toast.makeText(getContext(), "Что-то пошло не так!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onError: ", throwable);
    }

    private void onOk() {
        OKFragment f = OKFragment_.builder()
                .build();

        getFragmentManager().beginTransaction()
                .replace(container, f)
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .commit();
    }


    public void onWaiterChanged(ExposedWaiter item) {
        currentWaiter = item;
        waiter_name.setText("Официант: " + item.name);
    }

    @Click(R.id.waiter_card)
    void onWaiterPick() {
        WaiterPickFragment wpf = WaiterPickFragment_.builder()
                .waiterList(targetWaiters)
                .build();

        getFragmentManager()
                .beginTransaction()
                .add(container, wpf)
                .addToBackStack("__waiter_pick")
                .commit();
    }

    @Click(R.id.card_stub)
    void onStubClick() {
        Toast.makeText(getContext(), "Мы ещё не умеем платить настоящие деньги и не собираем данные ваших банковских карт :>", Toast.LENGTH_SHORT).show();
    }
}
