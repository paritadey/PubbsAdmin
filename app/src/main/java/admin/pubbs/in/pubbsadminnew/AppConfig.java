package admin.pubbs.in.pubbsadminnew;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LORD on 8/23/2017.
 */

public class AppConfig extends Application {

    public static void alertMsg(Context context, String msg){
        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", null)
                .show();
    }

    static List<Rates> rates=new ArrayList<>();

    public static List<Rates> getRates() {
        return rates;
    }

    public static void setRates(List<Rates> rates) {
        AppConfig.rates = rates;
    }
}
