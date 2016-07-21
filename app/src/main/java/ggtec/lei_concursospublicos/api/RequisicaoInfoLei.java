package ggtec.lei_concursospublicos.api;


/**
 * Created by Vagner on 20/07/2016.
 */
public class RequisicaoInfoLei extends Cabecalho {

    public RequisicaoInfoLei(){
        super();
        url = "infolei";
    }

    public RequisicaoInfoLei codigo(){
        get = "codigo";
        return this;
    }

    public RequisicaoInfoLei estatuto(){
        get = "estatuto";
        return this;
    }

    protected void iniciar(RespostaInfoLei resposta){
        super.iniciar(resposta);
    }

}
