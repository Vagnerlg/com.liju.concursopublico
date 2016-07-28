package ggtec.lei_concursospublicos.api_antiga.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Vagner on 15/12/2015.
 */
public class Internet {
    private static RequestQueue queue;

    protected static RequestQueue getInstance(Context con){
        if(queue == null){
            queue = Volley.newRequestQueue(con);
        }
        return queue;
    }
}
