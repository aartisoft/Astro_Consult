package in.astroconsult.astroconsult.Chat;

/**
 * Created by shivam sharma on 10/03/2020.
 */

public class Users
{
    public String name;
    public String image;
    public String status;
    public String thumb_image;

    public Users()
    {
       //whithout this empty constructor app may crash
    }


    public Users(String name, String image, String status, String thumb_image) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.thumb_image = thumb_image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }
}
