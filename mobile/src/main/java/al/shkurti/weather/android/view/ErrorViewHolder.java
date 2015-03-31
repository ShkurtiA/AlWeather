package al.shkurti.weather.android.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import al.shkurti.weather.android.R;

/**
 * Created by Armando Shkurti on 2015-03-31.
 */
public class ErrorViewHolder extends RecyclerView.ViewHolder{

    public LinearLayout linearLayout;
    public ErrorViewHolder(View itemView) {
        super(itemView);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.error_view_container);
    }

}
