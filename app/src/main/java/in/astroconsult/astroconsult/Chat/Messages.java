package in.astroconsult.astroconsult.Chat;

/**
 * Created by shivam sharma on 10/03/2020.
 */

public class Messages
{
    private String message;
    private String type;
    private long time;
    private String seen;
    private String from;

    public Messages()
    {
        //empty constructor required.
    }

    public Messages(String message, String type, long time, String seen) {
        this.message = message;
        this.type = type;
        this.time = time;
        this.seen = seen;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

/*
    public boolean isSeen() {
        return seen;
    }
*/

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
