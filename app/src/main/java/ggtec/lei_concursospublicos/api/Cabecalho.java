package ggtec.lei_concursospublicos.api;

import java.util.HashMap;

/**
 * Created by Vagner on 20/07/2016.
 */
class Cabecalho {

    protected HashMap<String,String> post = new HashMap<>();
    private String BASE = "...";
    protected String url = "";
    protected String get = "";
    private long timeStamp = 0;

    Cabecalho(){
        post.put("token", "...");
        timeStamp = System.currentTimeMillis();
    }

    protected void iniciar(Resposta resposta){
        new Requisicao().get(BASE+"/"+url+"/"+get).post(post).iniciar(resposta);
    }

}
