package in.astroconsult.astroconsult.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.util.ArrayList;
import java.util.List;

import in.astroconsult.astroconsult.Chat.ConversationActivity;
import in.astroconsult.astroconsult.Constants;
import in.astroconsult.astroconsult.R;
import in.astroconsult.astroconsult.RecyclerAdapter;
import in.astroconsult.astroconsult.Response.HomeAstroResponse;
import in.astroconsult.astroconsult.ui.Blog;
import in.astroconsult.astroconsult.ui.Help;
import in.astroconsult.astroconsult.ui.HomeOfBottom;
import in.astroconsult.astroconsult.ui.MyAccount;
import in.astroconsult.astroconsult.ui.Wallet;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    MeowBottomNavigation meo;
    private final static int MYACCOUNT_ID = 1;
    private final static int BLOG_ID = 2;
    private final static int HOME_ID = 3;
    private final static int WALLET_ID = 4;
    private final static int HELP_ID = 5;

    Fragment select_fragment = null;

    ArrayList<HomeAstroResponse> user = new ArrayList<>();
    RecyclerAdapter recyclerAdapter;

    public Home() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerAdapter = new RecyclerAdapter();

        meo = v.findViewById(R.id.bottom_nav);
        meo.add(new MeowBottomNavigation.Model(1, R.drawable.ic_account));
        meo.add(new MeowBottomNavigation.Model(2, R.drawable.ic_blog));
        meo.add(new MeowBottomNavigation.Model(3, R.drawable.ic_home));
        meo.add(new MeowBottomNavigation.Model(4, R.drawable.ic_wallet));
        meo.add(new MeowBottomNavigation.Model(5, R.drawable.ic_help));

        select_fragment = new HomeOfBottom();
        Constants.toolbar.setTitle("");
        Constants.search_area.setHint("Search Astrouser");
        Constants.search_area.setVisibility(View.VISIBLE);

        //init();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                select_fragment).detach(select_fragment).attach(select_fragment).commit();

        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
                                       @Override
                                       public void onClickItem(MeowBottomNavigation.Model item) {

                                           switch (item.getId()) {
                                               case MYACCOUNT_ID:
                                                   select_fragment = new MyAccount();
                                                   Constants.toolbar.setTitle("Account");
                                                   Constants.search_area.setVisibility(View.GONE);
                                                   break;
                                               case BLOG_ID:
                                                   select_fragment = new Blog();
                                                   Constants.toolbar.setTitle("");
                                                   Constants.search_area.setHint("Search Blog");
                                                   Constants.search_area.setVisibility(View.VISIBLE);
                                                   break;
                                               case HOME_ID:
                                                   select_fragment = new HomeOfBottom();
                                                   Constants.toolbar.setTitle("");
                                                   Constants.search_area.setHint("Search AstroLoger");
                                                   Constants.search_area.setVisibility(View.VISIBLE);
                                                   break;
                                               case WALLET_ID:
                                                   select_fragment = new Wallet();
                                                   Constants.toolbar.setTitle("Wallet");
                                                   Constants.search_area.setVisibility(View.GONE);
                                                   break;
                                               case HELP_ID:
                                                   select_fragment = new Help();
                                                   Constants.toolbar.setTitle("Help 24X7");
                                                   Constants.search_area.setVisibility(View.GONE);
                                                   //startActivity(new Intent(getContext(), ConversationActivity.class));
                                                   break;
                                           }
                                           if(select_fragment!=null)
                                           getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, select_fragment).commit();
                                       }
                                   });

        meo.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });


        meo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment select_fragment = null;
                /*switch (item.getId()) {
                    case MYACCOUNT_ID:
                        select_fragment = new MyAccount();
                        Constants.toolbar.setTitle("Account");
                        Constants.search_area.setVisibility(View.GONE);
                        break;
                    case BLOG_ID:
                        select_fragment = new Blog();
                        Constants.toolbar.setTitle("");
                        Constants.search_area.setHint("Search Blog");
                        Constants.search_area.setVisibility(View.VISIBLE);
                        break;
                    case HOME_ID:
                        select_fragment = new HomeOfBottom();
                        Constants.toolbar.setTitle("");
                        Constants.search_area.setHint("Search AstroLoger");
                        Constants.search_area.setVisibility(View.VISIBLE);
                        break;
                    case WALLET_ID:
                        select_fragment = new Wallet();
                        Constants.toolbar.setTitle("Wallet");
                        Constants.search_area.setVisibility(View.GONE);
                        break;
                    case HELP_ID:
                        select_fragment = new Help();
                        Constants.toolbar.setTitle("Help 24X7");
                        Constants.search_area.setVisibility(View.GONE);
                        break;
                }*/

            }
        });
        return v;
    }
   /* private void init()
    {
        Constants.search_area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    List<HomeAstroResponse> listFilter = new ArrayList<>();
                    //List<TopLawyerListViewModel> modelList1 = new ArrayList<>();
                    for (HomeAstroResponse model : user) {
                        if (model.getName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                            listFilter.add(model);
                    }
                    recyclerAdapter.updateList(listFilter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }*/
}
