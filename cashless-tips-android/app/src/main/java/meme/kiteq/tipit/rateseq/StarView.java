package meme.kiteq.tipit.rateseq;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import meme.kiteq.tipit.R;

@EViewGroup(R.layout.star_view)
public class StarView extends LinearLayout {
    @ViewById
    ImageView star_1, star_2, star_3, star_4, star_5;
    ImageView[] stars;
    private OnRated listener;
    private int rating = 0;

    public int getRating() {
        return rating;
    }

    public interface OnRated {
        void onRated(int how);
    }

    public StarView(Context context) {
        super(context);
    }

    public StarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void init() {
        stars = new ImageView[]{star_1, star_2, star_3, star_4, star_5};
        for (int i = 0; i < stars.length; i++) {
            ImageView s = stars[i];
            s.setImageResource(R.drawable.ic_star_border_black_24dp);
            final int finalI = i;
            s.setOnClickListener(v -> {
                setRate(finalI + 1);
                if (listener != null) {
                    listener.onRated(finalI + 1);
                }
            });
        }
    }

    void setRate(int howMuch) {
        rating = howMuch;
        for (int i = 0; i < stars.length; i++) {
            if (i <= howMuch - 1) {
                stars[i].setImageResource(R.drawable.ic_star_black_24dp);
            } else {
                stars[i].setImageResource(R.drawable.ic_star_border_black_24dp);
            }
        }
    }

    void setOnRated(OnRated r) {
        this.listener = r;
    }
}
