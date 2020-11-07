package in.astroconsult.astroconsult.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import in.astroconsult.astroconsult.R;

public class CourseFragment extends Fragment {

    public CourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_course, container, false);
        return v;
    }

   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.course_item,menu);
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.notification).setVisible(false);
        for (int i = 0; i<menu.size(); i++)
        {
            MenuItem item = menu.getItem(i);
            SpannableString spannableString = new SpannableString(menu.getItem(i).getTitle().toString());
            spannableString.setSpan(new ForegroundColorSpan(Color.WHITE),0,spannableString.length(),0);
            item.setTitle(spannableString);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.allCourse:
                //Toast.makeText(getContext(), "All Course", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),FavoriteCourse.class);
                startActivity(intent);
                return true;

            case R.id.favCourse:
                Intent intent1 = new Intent(getContext(),FavoriteCourse.class);
                startActivity(intent1);
                return true;

            case R.id.archivedCourse:
                Intent intent2 = new Intent(getContext(),FavoriteCourse.class);
                startActivity(intent2);
                return true;

            case R.id.DownCourse:
                Intent intent3 = new Intent(getContext(),FavoriteCourse.class);
                startActivity(intent3);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
