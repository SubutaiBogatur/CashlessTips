package meme.kiteq.tipit.rateseq;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import meme.kiteq.tipit.R;
import meme.kiteq.tipit.model.ExposedWaiter;

@EFragment(R.layout.activity_waiter_select)
public class WaiterPickFragment extends Fragment {
    @FragmentArg
    ArrayList<ExposedWaiter> waiterList;

    @ViewById
    RecyclerView recycler;
    @AfterViews
    void init() {
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        WaiterSelectorAdapter adapter = new WaiterSelectorAdapter(waiterList, item -> {
            RCFragment rcf = (RCFragment) getFragmentManager()
                    .findFragmentByTag(RCFragment.ID_TAG);

            getFragmentManager()
                    .popBackStack();

            rcf.onWaiterChanged(item);
        });
        recycler.setAdapter(adapter);
    }
}
