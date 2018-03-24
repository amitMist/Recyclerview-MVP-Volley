package amit.recruitment.com.earthquake;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by amit on 3/22/18.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    TextView tvEqDate;
    TextView tvEqDepth;
    TextView tvEqMagnitude;
    LinearLayout mBackgroundView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        tvEqDate = (TextView) itemView.findViewById(R.id.tvEqDate);
        tvEqDepth = (TextView) itemView.findViewById(R.id.tvEqDepth);
        tvEqMagnitude = (TextView) itemView.findViewById(R.id.tvEqMagnitude);
        mBackgroundView = (LinearLayout) itemView.findViewById(R.id.backgroundView);
    }
}
