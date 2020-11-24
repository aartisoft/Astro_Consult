package in.astroconsult.astroconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import in.astroconsult.astroconsult.Chat.FirebaseAuthUtil;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.pajo.ResponseModel;
import in.astroconsult.astroconsult.ui.BookFragment;
import in.astroconsult.astroconsult.ui.BookmarkFragment;
import in.astroconsult.astroconsult.ui.CourseFragment;
import in.astroconsult.astroconsult.ui.MoreFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ServerValue;
import com.squareup.picasso.Picasso;

public class AstroHome extends AppCompatActivity {
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro_home);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        drawerLayout = findViewById(R.id.drawer_layout_astro);
        navigationView = findViewById(R.id.navigationViewAstro);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MoreFragment()).commit();
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_home_astro:
                        item.setChecked(true);
                        toolbar.setTitle("Home");
                        //Toast.makeText(Home.this, "Assignment", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new MoreFragment()).commit();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_gallery_astro:
                        item.setChecked(true);
                        toolbar.setTitle("About Us");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new CourseFragment()).commit();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_slideshow_astro:
                        item.setChecked(true);
                        toolbar.setTitle("Privacy Policy");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new BookmarkFragment()).commit();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_refer_astro:
                        item.setChecked(true);
                        toolbar.setTitle("Refer and Earn");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new BookFragment()).commit();
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            /*case R.id.notification:
                Toast.makeText(Home.this, "Notification", Toast.LENGTH_SHORT).show();
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuthUtil.setCurrentUserOnline(ServerValue.TIMESTAMP);
    }
}