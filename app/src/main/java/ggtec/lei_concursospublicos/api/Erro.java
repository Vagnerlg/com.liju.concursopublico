package ggtec.lei_concursospublicos.api;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import ggtec.lei_concursospublicos.api_antiga.Debug;

/**
 * Created by Vagner on 19/07/2016.
 */
class Erro implements Response.ErrorListener  {

    static final String TIMEOUT = "Timeout";
    static final String SERVERDONW = "ServerDonw";
    static final String NO_NETWORK = "NetworkError";
    static final String NO_CONNECTION = "connection";
    static final String FAILURE_AUTH = "Authentication Failure";
    static final String ERROR_OUTROS = "outrosErros";
    static final String ERROR_PARSE = "ErroParse";
    static final String ERROR_JSON = "erro json";

    private Resposta resposta;

    Erro(Resposta resposta){
        this.resposta = resposta;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String data = new String(error.networkResponse.data);
        String codigo = ERROR_OUTROS;
        String msg = "Erro inesperado";

        if (error instanceof TimeoutError) {
            codigo = TIMEOUT;
            msg = "Tempo limite expirado";
        } else if (error instanceof ServerError) {
            codigo = SERVERDONW;
            msg = "Servidor não respondeu tente mais tarde";
        } else if (error instanceof AuthFailureError) {
            codigo = FAILURE_AUTH;
            try {
                JSONObject jsonObjecterro = new JSONObject(data);
                msg = "Resposta de servidor com erro";
            } catch (JSONException e1) {
                codigo = ERROR_JSON;
                msg = "Erro json de (erro)";
                e1.printStackTrace();
            }
        } else if (error instanceof NetworkError) {
            codigo = NO_NETWORK;
            msg = "Conexão com a internet não encontrada.";
        } else if (error instanceof NoConnectionError) {
            codigo = NO_CONNECTION;
            msg = "Conexão com a internet não encontrada.";
        } else if (error instanceof ParseError) {
            codigo = ERROR_PARSE;
            msg = "Ocorreu um erro inesperado";
        } else {
            codigo = ERROR_OUTROS;
            msg ="Ocorreu um erro inesperado";
        }
        resposta.resposta(data,codigo,msg);
        Debug.d(codigo+" "+msg+" "+data);
    }
}