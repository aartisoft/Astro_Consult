package in.astroconsult.astroconsult;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.astroconsult.astroconsult.Interface.RecyclarViewInterface;
import in.astroconsult.astroconsult.Response.GetAstrologerResponse;
import in.astroconsult.astroconsult.Response.HomeAstroResponse;
import in.astroconsult.astroconsult.Response.SpecialityHomeResponse;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<GetAstrologerResponse> modelListSub;
    RecyclarViewInterface recyclarViewInterface;

    public void setOnClickListener(RecyclarViewInterface onclicklistner) {
        this. recyclarViewInterface = onclicklistner;
    }

    public RecyclerAdapter() {
        //this.context = context;
        //this.modelListSub = modelList;
        //this.rowLayout = rowLayout;
    }

    public void setData(List<GetAstrologerResponse> modelListSub)
    {
        this.modelListSub = modelListSub;
        Log.d("Print", String.valueOf(modelListSub.size()));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new RecyclerAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_main_page,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.astrologerName.setText(modelListSub.get(position).getName());
        holder.astrologerExperience.setText(modelListSub.get(position).getExperience());
        holder.astrologerCharge.setText("Rs. "+modelListSub.get(position).getCcharge()+" /Min.");
        holder.astrolgerLocation.setText(modelListSub.get(position).getLocation());
        holder.astrologerRating.setText(modelListSub.get(position).getRating());
        Picasso.get().load(modelListSub.get(position).getPhoto()).into(holder.astrologerImage);

          /*if (modelListSub.get(position).getAstrologers() == null)
          {
              holder.linearLayout.setVisibility(View.GONE);
              Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
          }
          else
          {
              holder.linearLayout.setVisibility(View.VISIBLE);

          }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclarViewInterface.itemclick("",position,0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelListSub.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView astrologerName,astrologerExperience,astrologerRating,astrologerCharge,astrolgerLocation;
        ImageView astrologerImage;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            astrologerImage = (ImageView)itemView.findViewById(R.id.imageRecycle);
            astrologerName = (TextView)itemView.findViewById(R.id.nametxt);
            astrologerExperience = (TextView)itemView.findViewById(R.id.experince);
            astrolgerLocation = (TextView)itemView.findViewById(R.id.locationtxt);
            astrologerRating = (TextView)itemView.findViewById(R.id.P1_Home_Rating);
            astrologerCharge = (TextView)itemView.findViewById(R.id.feetxt);
            linearLayout = itemView.findViewById(R.id.linearData);
        }
    }

    public void updateList(List<GetAstrologerResponse> modelList1)
    {
        this.modelListSub = modelList1;
        notifyDataSetChanged();
    }
}
