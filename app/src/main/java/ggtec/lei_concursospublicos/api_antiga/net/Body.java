package ggtec.lei_concursospublicos.api_antiga.net;

import java.util.HashMap;

import ggtec.lei_concursospublicos.api.Request;

/**
 * Created by Vagner on 19/07/2016.
 */
abstract class Body {

    protected HashMap<String,String> post = new HashMap<>();
    private String BASE = "...";
    protected String url = "";
    protected String get = "";
    private long timeStamp = 0;

    Body(){
        post.put("token", "...");
        timeStamp = System.currentTimeMillis();
    }

    protected void iniciar(Resposta resposta){
        new Request().get(BASE+"/"+url+"/"+get).post(post).iniciar(resposta);
   }

}
