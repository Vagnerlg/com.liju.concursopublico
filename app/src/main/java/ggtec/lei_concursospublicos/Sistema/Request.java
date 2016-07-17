package ggtec.lei_concursospublicos.Sistema;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ggtec.lei_concursospublicos.Outros.BaseAnalytics;

/**
 * Created by Vagner on 16/12/2015.
 */
public class Request extends StringRequest {
    public static final String TIMEOUT = "Timeout";
    public static final String SERVERDONW = "ServerDonw";
    public static final String NO_NETWORK = "NetworkError";
    public static final String NO_CONNECTION = "connection";
    public static final String FAILURE_AUTH = "Authentication Failure";
    public static final String ERROR_OUTROS = "outrosErros";
    public static final String ERROR_PARSE = "ErroParse";
    public static final String SUCESSO = "Sucess";
    public static final String ERRO_RESPONSE = "response_erro";
    public static final String ERROR_JSON = "erro json";


    public Link link;

    @Override
    protected Map<String, String> getParams() {return  link.post();}

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/x-www-form-urlencoded");
        //params.put("Keep-Alive", "timeout=20, max=30");
        return params;
    }

    protected Request(final Link link, final RespJsonObj listernerListLei) {
        super(Method.POST, link.getURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("response")) {
                        JSONObject jsonObjectResponse = jsonObject.getJSONObject("response");
                        listernerListLei.response(jsonObjectResponse, SUCESSO, "");
                        Debug.d("Sucess");
                    } else {
                        listernerListLei.response(jsonObject.getJSONObject("erro"), ERRO_RESPONSE, response);
                        Debug.d("Erro response");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listernerListLei.response(null, ERROR_JSON, response);
                    Debug.d("Erro Json de (Response)");
                    //api.openweathermap.org/data/2.5/weather?id=7521912&APPID=d036cf2a377d14531d5144a1343b4e77
                    /*
                    {"id": 7521912, "name": "Consolação", "cod": 200 }
                     */
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String e = ERROR_OUTROS;
                if (error instanceof TimeoutError) {
                    e = TIMEOUT;
                    listernerListLei.response(null, TIMEOUT, "Tempo limite expirado");
                    Debug.d("Erro Time Out");
                } else if (error instanceof ServerError) {
                    e = SERVERDONW;
                    listernerListLei.response(null, SERVERDONW, "Servidor não respondeu tente mais tarde");
                    Debug.d("Server Donw");
                } else if (error instanceof AuthFailureError) {
                    String respErro = new String(error.networkResponse.data);
                    try {
                        JSONObject jsonObjecterro = new JSONObject(respErro);
                        e = FAILURE_AUTH;
                        listernerListLei.response(jsonObjecterro.getJSONObject("erro"), FAILURE_AUTH, jsonObjecterro.getJSONObject("erro").getString("mensagem"));
                        Debug.d("Resposta de servidor com erro");
                    } catch (JSONException e1) {
                        listernerListLei.response(null, ERROR_JSON, respErro);
                        Debug.d("Erro json de (erro)");
                        e1.printStackTrace();
                    }

                } else if (error instanceof NetworkError) {
                    e = NO_NETWORK;
                    listernerListLei.response(null, NO_NETWORK, "Conexão com a internet não encontrada.");
                    Debug.d("Sem internet");
                } else if (error instanceof NoConnectionError) {
                    e = NO_CONNECTION;
                    listernerListLei.response(null, NO_CONNECTION, "Conexão com a internet não encontrada.");
                    Debug.d("Sem conexao");
                } else if (error instanceof ParseError) {
                    e = ERROR_PARSE;
                    listernerListLei.response(null, ERROR_PARSE, "Ocorreu um erro inesperado");
                    Debug.d("Erro de parse erro");
                } else {
                    listernerListLei.response(null, ERROR_OUTROS, "Ocorreu um erro inesperado");
                    Debug.d("Erro sem explicação");
                }
                Debug.d("Erro Voley " + e);
            }
        });
        this.link = link;
    }

    protected interface RespJsonObj{
        void response(JSONObject jsonObject,String erro,String mensagem);
    }
}
