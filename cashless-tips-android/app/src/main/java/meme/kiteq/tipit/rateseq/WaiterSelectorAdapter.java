package meme.kiteq.tipit.rateseq;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import meme.kiteq.tipit.R;
import meme.kiteq.tipit.model.ExposedWaiter;

class WaiterSelectorAdapter extends RecyclerView.Adapter<WaiterSelectorAdapter.VH> {

    private final ArrayList<ExposedWaiter> items;
    private final WaiterSelect select;

    public interface WaiterSelect {
        void onSelect(ExposedWaiter waiter);
    }

    public WaiterSelectorAdapter(ArrayList<ExposedWaiter> items, WaiterSelect select) {
        this.items = items;
        this.select = select;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.waiter_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.bind(items.get(i), select);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView waiter_name;
        View root;
        WaiterSelect select;
        public VH(@NonNull View itemView) {
            super(itemView);

            root = itemView;
            waiter_name = itemView.findViewById(R.id.waiter_name);
        }


        public void bind(ExposedWaiter exposedWaiter, WaiterSelect select) {
            waiter_name.setText(exposedWaiter.name);
            root.setOnClickListener(v -> {
                select.onSelect(exposedWaiter);
            });
        }
    }
}
