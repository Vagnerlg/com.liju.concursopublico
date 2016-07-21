package ggtec.lei_concursospublicos.api_antiga.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vagner on 19/07/2016.
 */
class StringResposta extends StringRequest {

    private Map<String, String> post;

    public StringResposta(String url, Map<String, String> post, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);
        this.post = post;
    }

    @Override
    protected Map<String, String> getParams() {return  post;}

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/x-www-form-urlencoded");
        //params.put("Keep-Alive", "timeout=20, max=30");
        return params;
    }

}
