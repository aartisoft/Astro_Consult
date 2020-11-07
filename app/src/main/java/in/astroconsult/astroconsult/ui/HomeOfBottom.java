package in.astroconsult.astroconsult.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import in.astroconsult.astroconsult.Constants;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Interface.RecyclarViewInterface;
import in.astroconsult.astroconsult.ProfileFragment;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import in.astroconsult.astroconsult.CustomAdapter;
import in.astroconsult.astroconsult.ListData;

import in.astroconsult.astroconsult.R;
import in.astroconsult.astroconsult.RecyclerAdapter;
import in.astroconsult.astroconsult.Response.GetAstrologerResponse;
import in.astroconsult.astroconsult.Response.HomeAstroResponse;
import in.astroconsult.astroconsult.Response.SpecialityHomeResponse;
import in.astroconsult.astroconsult.Response.StateResponse;
import in.astroconsult.astroconsult.TouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeOfBottom extends Fragment implements RecyclarViewInterface {

    public HomeOfBottom() {
        // Required empty public constructor
    }

    RecyclerView recycler_view,astroRecycle;
    FrameLayout frameLayout;
    NestedScrollView ns;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    RecyclerAdapter recyclerAdapter;
    CustomAdapter customAdapter;
    ArrayList<HomeAstroResponse> user = new ArrayList();
    ArrayList<GetAstrologerResponse> astrologers = new ArrayList<>();
    ArrayList<HomeAstroResponse> speciality = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home_of_bottom, container, false);
        recycler_view = inflate.findViewById(R.id.recycler_view);
        astroRecycle = inflate.findViewById(R.id.astroRecycle);
        final ImageSlider imageSlider = inflate.findViewById(R.id.image_slider);
        frameLayout = inflate.findViewById(R.id.fragment_container_1);
        ns =  inflate.findViewById(R.id.nestedScrollHome1);

        astroRecycle = (RecyclerView)inflate.findViewById(R.id.astroRecycle);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        astroRecycle.setLayoutManager(gridLayoutManager);

        recycler_view = (RecyclerView)inflate.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recycler_view.setLayoutManager(layoutManager);

        recyclerAdapter = new RecyclerAdapter();
        customAdapter = new CustomAdapter();

        productSub();

        speciality();

        init();

        List<SlideModel> slideModels = new ArrayList<>();
        //slideModels.add(new SlideModel("https://i10.dainikbhaskar.com/thumbnails/813x548/web2images/www.bhaskar.com/2020/01/11/jyotish-astrology_1578766223.jpg", "Foxes live wild in the city.", true));
        slideModels.add(new SlideModel(R.drawable.slide2));
        slideModels.add(new SlideModel(R.drawable.slider1));
        //slideModels.add(new SlideModel("http://www.magbook.in/Article/15/70472/Img_70472.png"));
        //slideModels.add(new SlideModel("http://www.magbook.in/Article/15/70472/Img_70472.png", "The population of elephants is decreasing in the world."));
        imageSlider.setImageList(slideModels, false);
        //initializeEventsList();
        return inflate;
    }

    public void productSub() {

        try
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();

            Call<HomeAstroResponse> call = ApiClient.getCliet().specialityHome(api_key);
            call.enqueue(new Callback<HomeAstroResponse>() {
                @Override
                public void onResponse(Call<HomeAstroResponse> call,Response<HomeAstroResponse> response) {

                    if (response.isSuccessful())
                    {
                       // user.clear();
                        // user.add(response.body());
                        astrologers.clear();
                        astrologers.addAll(response.body().getAstrologers());
                        int d = user.size();
                        Log.d("Result",String.valueOf(d));
                        recyclerAdapter.setData(astrologers);
                        astroRecycle.setAdapter(recyclerAdapter);
                        click();
                        progressDialog.dismiss();
                    }

                    else
                    {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<HomeAstroResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "No Data"+t, Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
        }

    }

    public void speciality() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();

        Call<HomeAstroResponse> call = ApiClient.getCliet().specialityHome(api_key);
        call.enqueue(new Callback<HomeAstroResponse>() {
            @Override
            public void onResponse(Call<HomeAstroResponse> call,Response<HomeAstroResponse> response) {

                if (response.isSuccessful())
                {
                    speciality.clear();
                    speciality.add(response.body());
                    Log.d("heyy",response.body().getSlug());
                    customAdapter.specialityData(speciality);
                    recycler_view.setAdapter(customAdapter);
                    //click();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<HomeAstroResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "No Data"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void click()
    {
        recyclerAdapter.setOnClickListener(this);
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
        ProfileFragment someFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("posis",position);
        bundle.putString("From", "searchflag");
        bundle.putParcelableArrayList("searches", astrologers);
        someFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, someFragment, "SearchProfileSummaryFragment");
        transaction.addToBackStack(null);
        transaction.commit();


        //Toast.makeText(getContext(), "hhh"+position, Toast.LENGTH_SHORT).show();

    }


    private void init()
    {
        Constants.search_area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                recyclerAdapter.updateList(astrologers);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    List<GetAstrologerResponse> listFilter = new ArrayList<>();
                    //List<TopLawyerListViewModel> modelList1 = new ArrayList<>();
                    for (GetAstrologerResponse model : astrologers) {
                        if (model.getName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                            listFilter.add(model);
                    }
                    recyclerAdapter.updateList(listFilter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //recyclerAdapter.updateList(user);
            }
        });
    }
}
