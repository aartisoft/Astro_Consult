package in.astroconsult.astroconsult.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.astroconsult.astroconsult.BlogAdapter;
import in.astroconsult.astroconsult.BlogDetailFragment;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Interface.RecyclarViewInterface;
import in.astroconsult.astroconsult.ProfileFragment;
import in.astroconsult.astroconsult.R;
import in.astroconsult.astroconsult.RecyclerAdapter;
import in.astroconsult.astroconsult.Response.BlogResponse;
import in.astroconsult.astroconsult.Response.GetAstrologerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Blog extends Fragment implements RecyclarViewInterface {

    RecyclerView blogRecycler;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    BlogAdapter blogAdapter;
    ArrayList<BlogResponse> user = new ArrayList<>();

    public Blog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog, container, false);

        blogRecycler = view.findViewById(R.id.recyclerBlog);

        blogRecycler = (RecyclerView)view.findViewById(R.id.recyclerBlog);

        blogRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        blogRecycler.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        blogAdapter = new BlogAdapter();

        productSub();

        return view;
    }

    public void productSub() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();

        Call<List<BlogResponse>> call = ApiClient.getCliet().blogResponse(api_key,"_articles","Articles");
        call.enqueue(new Callback<List<BlogResponse>>() {
            @Override
            public void onResponse(Call<List<BlogResponse>> call, Response<List<BlogResponse>> response) {

                if (response.isSuccessful())
                {
                    user.clear();
                    user.addAll(response.body());
                    //List<BlogResponse> user = response.body();
                    blogAdapter.setData(user);
                    blogRecycler.setAdapter(blogAdapter);
                    click();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<BlogResponse>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "No Data"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void click()
    {
        blogAdapter.setOnClickListener(this);
    }

    @Override
    public void recyclerViewListClicked(String type, int position, int child_position) {

    }

    @Override
    public void recyclerViewlongClicked(String type, int position, int child_position) {

    }

    @Override
    public void itemclick(String type, int position, int child_position) {
        FragmentTransaction transaction = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
        BlogDetailFragment someFragment = new BlogDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("posis",position);
        bundle.putString("From", "searchflag");
        bundle.putParcelableArrayList("searches", user);
        someFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, someFragment, "SearchProfileSummaryFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        //Toast.makeText(getContext(), "hhh"+position, Toast.LENGTH_SHORT).show();

    }
}
