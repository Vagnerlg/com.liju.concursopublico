package ggtec.lei_concursospublicos.api;

import com.android.volley.Response;
import java.util.HashMap;

import ggtec.lei_concursospublicos.api_antiga.Debug;


/**
 * Created by Vagner on 19/07/2016.
 */
class Requisicao {

    static final String SUCESS = "sucesso";
    //private static final String BASE_URL = "sandbox";
    private static final String BASE_URL = "api";
    private String url = "";
    private HashMap<String, String> post;
    private long timeStamp = 0;

    Requisicao(){
        timeStamp = System.currentTimeMillis();
    }

    Requisicao post(HashMap<String, String> post){
        this.post = post;
        this.post.put("token", "...");
        return this;
    }

    Requisicao get(String url){
        this.url = url;
        return this;
    }

    void iniciar(final Resposta resposta){
        Net net = new Net("",post,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String msg = "Resposta do servidor com sucesso";
                Data odata = new Data();
                odata.setData(response,SUCESS,msg);
                resposta.resposta(odata);
                Debug.d(msg);
            }
        }, new Erro(resposta));
    }
}
