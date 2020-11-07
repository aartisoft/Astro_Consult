package in.astroconsult.astroconsult.Model;

import android.net.Uri;

public class ImageStoreModel {
    Uri Image;

    public ImageStoreModel(Uri image) {
        Image = image;
    }

    public Uri getImage() {
        return Image;
    }

    public void setImage(Uri image) {
        Image = image;
    }
}
