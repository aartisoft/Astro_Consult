package in.astroconsult.astroconsult;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import in.astroconsult.astroconsult.Response.BlogResponse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class BlogDetailFragment extends Fragment {

    ImageView blogImage;
    TextView header,title;
    ArrayList<BlogResponse> user = new ArrayList<>();
    int position;

    public BlogDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog_detail, container, false);

        header = view.findViewById(R.id.blogDetailHeader);
        title = view.findViewById(R.id.blogDetailText);
        blogImage = view.findViewById(R.id.blogDetailImage);

        Bundle bundle = getArguments();

        if(bundle != null) {
            //Productid = bundle.getString("key");
            String key1 = bundle.getString("From");

            if (key1.contains("searchflag")) {

                if (key1.equalsIgnoreCase("searchflag")) {

                    user = bundle.getParcelableArrayList("searches");
                    position = bundle.getInt("posis");

                    header.setText(user.get(position).getTitle());
                    title.setText(user.get(position).getSlug());

                    Picasso.get().load(user.get(position).getThumbnail().getImgUrl()).into(blogImage);
                }
            }
        }


        return view;
    }
}