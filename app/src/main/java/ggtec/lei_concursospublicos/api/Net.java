package ggtec.lei_concursospublicos.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vagner on 20/07/2016.
 */
class Net extends StringRequest {
    private Map<String, String> post;

    public Net(String url, Map<String, String> post, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, url, listener, errorListener);
        this.post = post;
    }

    @Override
    protected Map<String, String> getParams() {return  post;}

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/x-www-form-urlencoded");
        return params;
    }
}
