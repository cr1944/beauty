package ryancheng.beauty;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Create time: 2015/7/15.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.VH> implements Action1<List<Entity>> {
    private List<Entity> data;
    private Context context;

    public MainAdapter(Context context) {
        this.context = context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_main_item, parent, false);
        VH vh = new VH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Entity entity = data.get(position);
        holder.titleView.setText(entity.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void call(List<Entity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class VH extends RecyclerView.ViewHolder {
        @Bind(R.id.pic)
        ImageView imageView;
        @Bind(R.id.title)
        TextView titleView;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
