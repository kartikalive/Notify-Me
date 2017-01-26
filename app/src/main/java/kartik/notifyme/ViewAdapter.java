package kartik.notifyme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DHEERAJ on 22-01-2017.
 */

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {
    ArrayList<InternRowInfo> internRowInfos;
    LayoutInflater inflater;
    public ViewAdapter(Context context, ArrayList<InternRowInfo> internRowInfos){
        inflater=LayoutInflater.from(context);
        this.internRowInfos=internRowInfos;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.intern_row_data,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InternRowInfo rowinfo=internRowInfos.get(position);
        holder.theading.setText(rowinfo.CompanyHeading);
        holder.tname.setText(rowinfo.Companyname);
        holder.ttime.setText(rowinfo.PostTime);
        holder.tdate.setText(rowinfo.Date);
    }

    @Override
    public int getItemCount() {
        return internRowInfos.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tdate;
        TextView ttime;
        TextView tname;
        TextView theading;
        public MyViewHolder(View itemView) {
            super(itemView);
            tdate=(TextView) itemView.findViewById(R.id.date);
            ttime=(TextView) itemView.findViewById(R.id.time);
            tname=(TextView) itemView.findViewById(R.id.name);
            theading=(TextView) itemView.findViewById(R.id.heading);
        }
    }
}
