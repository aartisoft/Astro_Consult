package in.astroconsult.astroconsult.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import androidx.fragment.app.Fragment;
import in.astroconsult.astroconsult.AstroWalletFragment;
import in.astroconsult.astroconsult.Chat.ConversationActivity;
import in.astroconsult.astroconsult.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

    MeowBottomNavigation meo;
    private final static int ASTRO_ACCOUNT_ID = 1;
    private final static int ASTRO_BLOG_ID = 2;
    private final static int ASTRO_HOME_ID = 3;
    private final static int ASTRO_WALLET_ID = 4;
    private final static int ASTRO_HELP_ID = 5;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        meo = (MeowBottomNavigation) view.findViewById(R.id.bottom_nav_astro);
        meo.add(new MeowBottomNavigation.Model(1, R.drawable.ic_account));
        meo.add(new MeowBottomNavigation.Model(2, R.drawable.ic_blog));
        meo.add(new MeowBottomNavigation.Model(3, R.drawable.ic_home));
        meo.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_chat));
        meo.add(new MeowBottomNavigation.Model(5, R.drawable.ic_wallet));


        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case ASTRO_ACCOUNT_ID:
                        loadFragment( new AstroAccountFragment());

                        //select_fragment = new AstroAccountFragment();
                        //Toast.makeText(getActivity(), "sele1", Toast.LENGTH_SHORT).show();
                        break;

                    case ASTRO_BLOG_ID:
                        loadFragment( new AstroBlogFragment());

                         //select_fragment = new AstroBlogFragment();
                        //Toast.makeText(getActivity(), "sele2", Toast.LENGTH_SHORT).show();

                        break;

                    case ASTRO_HOME_ID:

                        loadFragment( new AstroHomeFragment());
                        //Toast.makeText(getActivity(), "sele3", Toast.LENGTH_SHORT).show();

                        //   select_fragment = new AstroHomeFragment();
                        break;

                    case ASTRO_WALLET_ID:
                        startActivity(new Intent(getContext(), ConversationActivity.class));
                        //loadFragment( new AstroWalletFragment());
                        //Toast.makeText(getActivity(), "sele4", Toast.LENGTH_SHORT).show();

                        // select_fragment = new AstroWalletFragment();
                        break;

                    case ASTRO_HELP_ID:
                        //Toast.makeText(getActivity(), "se5", Toast.LENGTH_SHORT).show();
                        loadFragment( new SearchFragment());
                 //       select_fragment = new SearchFragment();
                        break;
                }
            }
        });

        meo.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });
        Fragment select_fragment = null;
        select_fragment = new AstroHomeFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_astro, select_fragment).commit();
        loadFragment( new AstroHomeFragment());

       meo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment select_fragment = null;
              /*  switch (item.getId()) {
                    case ASTRO_ACCOUNT_ID:
                        loadFragment( new AstroAccountFragment());

                        //select_fragment = new AstroAccountFragment();
                        Toast.makeText(getActivity(), "sele1", Toast.LENGTH_SHORT).show();
                        break;

                    case ASTRO_BLOG_ID:
                        loadFragment( new AstroBlogFragment());

                       // select_fragment = new AstroBlogFragment();
                        Toast.makeText(getActivity(), "sele2", Toast.LENGTH_SHORT).show();

                        break;

                    case ASTRO_HOME_ID:
                        loadFragment( new AstroHomeFragment());
                        Toast.makeText(getActivity(), "sele3", Toast.LENGTH_SHORT).show();

                     //   select_fragment = new AstroHomeFragment();
                        break;

                    case ASTRO_WALLET_ID:
                        loadFragment( new SearchFragment());
                        Toast.makeText(getActivity(), "sele4", Toast.LENGTH_SHORT).show();

                       // select_fragment = new AstroWalletFragment();
                        break;

                    case ASTRO_HELP_ID:
                        Toast.makeText(getActivity(), "se5", Toast.LENGTH_SHORT).show();
                        loadFragment( new SearchFragment());
                      select_fragment = new SearchFragment();
                        break;
                }*/
              //  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_astro, select_fragment).commit();
            }
        });



        return view;
    }



    private void loadFragment(Fragment fragment) {
        //switching fragment


        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_astro, fragment).commit();

    }
}