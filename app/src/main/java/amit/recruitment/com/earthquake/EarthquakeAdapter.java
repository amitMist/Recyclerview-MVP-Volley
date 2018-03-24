package amit.recruitment.com.earthquake;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import amit.recruitment.com.earthquake.data.Earthquake;

/**
 * Created by amit on 3/23/18.
 */

public class EarthquakeAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private Context mContext;
    private List<Earthquake> mDatalist = new ArrayList<Earthquake>();
    DateFormat dateFormat;

    public EarthquakeAdapter(Context mContext, List<Earthquake> list) {
        this.mContext = mContext;
        this.mDatalist = list;
        dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        Earthquake eq = mDatalist.get(position);
        holder.tvEqDate.setText(dateFormat.format(eq.getDatetime()));
        holder.tvEqDepth.setText("Depth: "+eq.getDepth());
        holder.tvEqMagnitude.setText(eq.getMagnitude()+"!");

        if(eq.getMagnitude()>=8.0){
            holder.mBackgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorMagnitude));
        }else{
            holder.mBackgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.cardview_light_background));
        }
    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }
}
