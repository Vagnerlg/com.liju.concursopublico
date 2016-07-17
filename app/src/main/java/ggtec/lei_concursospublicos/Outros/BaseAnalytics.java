package ggtec.lei_concursospublicos.Outros;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import ggtec.lei_concursospublicos.Sistema.Usuario;

/**
 * Created by Vagner on 07/12/2015.
 */
public class BaseAnalytics{
    private static GoogleAnalytics analytics;
    private static Tracker tracker;
    public static BaseAnalytics baseAnalytics;
    private Context context;
    public static String CAT_INICIO = "Inicio Activity";
    public static String CAT_SISTEMA = "Sistema";
    public static String CAT_USUARIO = "Usuario";
    public static String CAT_ERRO = "Erro";




    private BaseAnalytics(Context context) {
        this.context = context;
        analytics = GoogleAnalytics.getInstance(context);
        analytics.setLocalDispatchPeriod(1800);
        tracker = analytics.newTracker("UA-71142400-1"); // Replace with actual tracker/property Id
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
    }

    public static BaseAnalytics getInstance(Context context){
        if(baseAnalytics == null){
            baseAnalytics = new BaseAnalytics(context);
        }
        return baseAnalytics;
    }

    public static BaseAnalytics getBaseInstance(){
        return baseAnalytics;
    }

    public void evento(String categoria,String action,String label){
        if(Usuario.getInstance(context).isCadastrado()){
            tracker.set("&uid", Usuario.getInstance(context).getId()+"");
        }
        HitBuilders.EventBuilder hit = new HitBuilders.EventBuilder();
        hit.setCategory(categoria)
                .setAction(action);
        if(label != null){
            hit.setLabel(label);
        }
        tracker.send(hit.build());
    }
}