package ryancheng.beauty;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Create time: 2015/7/15.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.VH> {
    private List<Entity> data;
    private Context context;

    public MainAdapter(Context context) {
        this.context = context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_main_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Entity entity = data.get(position);
        holder.titleView.setText(Html.fromHtml(entity.title));
        Picasso.with(context).load(entity.picUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Entity> data) {
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
