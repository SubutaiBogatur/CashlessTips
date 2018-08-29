package meme.kiteq.tipit.model;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;

public class CheckInfo implements Serializable {
    public final String t;
    public final String sum;
    public final String fn;
    public final String fp;
    public final String i;
    public final String n;

    public CheckInfo(String contents) {
        Uri fake = Uri.parse("scheme://none/none?" + contents);

        t = fake.getQueryParameter("t");
        sum = fake.getQueryParameter("s");
        fn = fake.getQueryParameter("fn");
        fp = fake.getQueryParameter("fp");
        i = fake.getQueryParameter("i");
        n = fake.getQueryParameter("n");
    }

    public boolean valid() {
        return fn != null && fp != null && n != null && t != null;
    }

    public int timeDescrewed() {
        return (int) time();
    }

    public long time() {
        Calendar c = timeProper();

        return c.getTimeInMillis();
    }

    @NonNull
    public Calendar timeProper() {
        int yyyy = Integer.parseInt(t.substring(0, 4));
        int MM = Integer.parseInt(t.substring(4, 6));
        int dd = Integer.parseInt(t.substring(6, 8));
        // T
        int hh = Integer.parseInt(t.substring(9, 11));
        int mm = Integer.parseInt(t.substring(11, 13));
//        int ss = Integer.parseInt(t.substring(13, 15));
        int ss = 0;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, yyyy);
        c.set(Calendar.MONTH, MM - 1);
        c.set(Calendar.DAY_OF_MONTH, dd);
        c.set(Calendar.HOUR_OF_DAY, hh);
        c.set(Calendar.MINUTE, mm);
        c.set(Calendar.SECOND, ss);
        return c;
    }
}
