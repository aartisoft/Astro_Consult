package in.astroconsult.astroconsult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.astroconsult.astroconsult.Interface.RecyclarViewInterface;
import in.astroconsult.astroconsult.Response.GetAstrologerResponse;
import in.astroconsult.astroconsult.Response.HomeAstroResponse;
import in.astroconsult.astroconsult.Response.SpecialityHomeResponse;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private List<HomeAstroResponse> ListSub;
    //private int rowLayout;
    //private String name;
    /*RecyclarViewInterface recyclarViewInterface;
    public void setOnClickListener(RecyclarViewInterface onclicklistner) {
        this. recyclarViewInterface = onclicklistner;
    }*/


    public CustomAdapter() {
        //this.context = context;
        //this.modelListSub = modelList;
        //this.rowLayout = rowLayout;
    }

    public void specialityData(List<HomeAstroResponse> ListSub)
    {
        this.ListSub = ListSub;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CustomAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_data_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomAdapter.MyViewHolder holder, final int position) {
        holder.astrologerName.setText(ListSub.get(position).getName());
        Picasso.get().load(ListSub.get(position).getIcon()).into(holder.astrologerImage);

        //Picasso.get().load(modelListSub.get(position).getPhoto()).into(holder.astrologerImage);

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclarViewInterface.itemclick("",position,0);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return ListSub.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView astrologerName;
        ImageView astrologerImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            astrologerImage = (ImageView)itemView.findViewById(R.id.image);
            astrologerName = (TextView)itemView.findViewById(R.id.title);
        }
    }
}
