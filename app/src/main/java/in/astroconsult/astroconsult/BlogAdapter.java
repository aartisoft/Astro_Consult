package in.astroconsult.astroconsult;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.astroconsult.astroconsult.Interface.RecyclarViewInterface;
import in.astroconsult.astroconsult.Response.BlogResponse;
import in.astroconsult.astroconsult.Response.GetAstrologerResponse;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.MyViewHolder> {

    private Context context;
    private List<BlogResponse> modelListSub;
    //private int rowLayout;
    //private String name;
    RecyclarViewInterface recyclarViewInterface;
    public void setOnClickListener(RecyclarViewInterface onclicklistner) {
        this. recyclarViewInterface = onclicklistner;
    }

    public BlogAdapter() {
        //this.context = context;
        //this.modelListSub = modelList;
        //this.rowLayout = rowLayout;
    }

    public void setData(List<BlogResponse> modelListSub)
    {
        this.modelListSub = modelListSub;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BlogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new BlogAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.blog_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final BlogAdapter.MyViewHolder holder, final int position) {

        String date = modelListSub.get(position).getDate();
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Integer.parseInt(date)*1000L);
        String date1 = DateFormat.format("dd-MM-yyyy",calendar).toString();

        holder.blogName.setText(modelListSub.get(position).getTitle());
        holder.blogTitle.setText(modelListSub.get(position).getSlug());
        holder.blogDate.setText(date1);
        Picasso.get().load(modelListSub.get(position).getThumbnail().getImgUrl()).into(holder.blogImage);

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

        TextView blogName,blogTitle,blogDate;
        ImageView blogImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            blogDate = (TextView)itemView.findViewById(R.id.blogDate);
            blogName = (TextView)itemView.findViewById(R.id.blogHeader);
            blogTitle = (TextView)itemView.findViewById(R.id.blogText);
            blogImage = (ImageView)itemView.findViewById(R.id.blogImage);
        }
    }
}
