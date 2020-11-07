package in.astroconsult.astroconsult.ui.slideshow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import in.astroconsult.astroconsult.R;

import java.util.ArrayList;
import java.util.List;

public class SlideShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        ImageSlider imageSlider = findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://i10.dainikbhaskar.com/thumbnails/813x548/web2images/www.bhaskar.com/2020/01/11/jyotish-astrology_1578766223.jpg", "Foxes live wild in the city.", true));
        slideModels.add(new SlideModel("http://devbhoomisamvad.com/wp-content/uploads/2018/05/astrology.jpg"));
        slideModels.add(new SlideModel("http://www.magbook.in/Article/15/70472/Img_70472.png", "The population of elephants is decreasing in the world."));
        imageSlider.setImageList(slideModels, true);
    }
}
