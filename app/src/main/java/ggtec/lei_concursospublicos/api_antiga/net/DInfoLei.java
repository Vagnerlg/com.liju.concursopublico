package ggtec.lei_concursospublicos.api_antiga.net;

/**
 * Created by Vagner on 19/07/2016.
 */
class DInfoLei extends Body{

    DInfoLei(){
        super();
        url = "infolei";
    }

    DInfoLei codigo(){
        get = "codigo";
        return this;
    }

    DInfoLei estatuto(){
        get = "estatuto";
        return this;
    }
    protected void iniciar(JsonBase resposta){
        iniciar(resposta);
    }
}
