package meme.kiteq.tipit.rateseq;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import meme.kiteq.tipit.R;

@EViewGroup(R.layout.rate_view)
public class TipView extends LinearLayout {
    @ViewById
    CheckedTextView prc0, prc5, prc7, prc10, prc14;
    public static int[] PRCS_VALS = new int[]{0, 5, 7, 10, 14};
    CheckedTextView[] prcs;
    private OnTipSelected listener;
    private int percentSelected = 0;

    public interface OnTipSelected {
        void onTipSelected(int percent);
    }

    public TipView(Context context) {
        super(context);
    }

    public TipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void init() {
        prcs = new CheckedTextView[]{prc0, prc5, prc7, prc10, prc14};
        for (int i = 0; i < prcs.length; i++) {
            CheckedTextView s = prcs[i];
            final int finalI = i;
            s.setOnClickListener(v -> {
                int prcsVal = PRCS_VALS[finalI];
                setPercentInt(finalI);
                if (listener != null) {
                    listener.onTipSelected(finalI);
                }
            });
        }
    }

    void setPercentInt(int index) {
        percentSelected = index;
        for (int i = 0; i < prcs.length; i++) {
            prcs[i].setChecked(i == index);
        }
    }

    void onTipSelected(OnTipSelected r) {
        this.listener = r;
    }

    public int getPercentInd() {
        return percentSelected;
    }
}
