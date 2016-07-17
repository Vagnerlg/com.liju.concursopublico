package ggtec.lei_concursospublicos.Sistema;

import android.content.Context;
import android.os.Build;

import com.android.volley.DefaultRetryPolicy;

import org.json.JSONObject;

import java.util.HashMap;

import ggtec.lei_concursospublicos.Outros.BaseAnalytics;

/**
 * Created by Vagner on 16/12/2015.
 */
public class Link {

    //private static final String BASE_URL = "sandbox";
    private static final String BASE_URL = "api";
    private static final String BASE_PG_LEI = "/lei";
    private static final String BASE_PG_INFO_LEI = "/infolei";
    private static final String BASE_PG_USUARIO = "/usuario";
    private static final String BASE_PG_FALECONOSCO = "/faleconosco";
    private static final String BASE_PG_PAY = "/pay";
    private String url;
    private Context context;

    private HashMap<String, String> post;
    private long timeStamp = 0;
    private String tabNum;
    private int limit;

    public Link(){
        post = new HashMap<String, String>();
        post.put("token", "...");
        timeStamp = System.currentTimeMillis();
    }

    public Link(Context context){
        this.context = context;
        post = new HashMap<String, String>();
        post.put("token", "946e8b81550f74434a04d4293a0a36c9");
        timeStamp = System.currentTimeMillis();
    }

    private long performance(){
        return System.currentTimeMillis() - timeStamp;
    }

    public void pay(String tokem, String metodoDePagamento, String userID,String plano){
        post.put("token2", tokem);
        post.put("id",userID);
        post.put("meiodepagamento",metodoDePagamento);
        post.put("plano",plano);
        url = BASE_URL+BASE_PG_PAY+"/pre_mercado_pago";
    }

    protected Link tituloArtigoComentarioMarcacao(String tab_num, String userID){
        post.put("tab_num", tab_num);
        tabNum = tab_num;
        post.put("user",userID);
        url = BASE_URL+BASE_PG_LEI+"/titulo_artigo_comentarios_marcacoes";
        return this;
    }

    protected Link titulos(String tab_num, String userID){
        post.put("tab_num", tab_num);
        tabNum = tab_num;
        post.put("user",userID);
        url = BASE_URL+BASE_PG_LEI+"/titulos";
        return this;
    }

    protected Link artigos(String tab_num, String userID){
        post.put("tab_num", tab_num);
        tabNum = tab_num;
        post.put("user",userID);
        url = BASE_URL+BASE_PG_LEI+"/artigos";
        return this;
    }

    protected Link comentarios(String tab_num, String userID){
        post.put("tab_num", tab_num);
        tabNum = tab_num;
        post.put("user",userID);
        url = BASE_URL+BASE_PG_LEI+"/comentarios";
        return this;
    }

    protected Link marcacoes(String tab_num, String userID){
        post.put("tab_num", tab_num);
        tabNum = tab_num;
        post.put("user",userID);
        url = BASE_URL+BASE_PG_LEI+"/marcacoes";
        return this;
    }

    public Link faleConosco(String nome, String email, String texto){
        post.put("nome", nome);
        post.put("email",email);
        post.put("texto",texto);
        url = BASE_URL+BASE_PG_FALECONOSCO+"/enviar";
        return this;
    }

    protected Link pesquisa(String numero, String ano, String descricao){
        post.put("numero", numero);
        post.put("ano",ano);
        post.put("descricao",descricao);
        url = BASE_URL+BASE_PG_INFO_LEI+"/pesquisa";
        return this;
    }


    protected Link pesquisaNaLei(String tab_num, String textoPesquisa,String user){
        post.put("tab_num", tab_num);
        post.put("user",user);
        tabNum = tab_num;
        post.put("pesquisa",textoPesquisa);
        url = BASE_URL+BASE_PG_LEI+"/pesquisa";
        return this;
    }


    protected Link leiMaisInfo(String tab_num, int limit, String userID){
        post.put("tab_num", tab_num);
        tabNum = tab_num;
        post.put("limit", limit + "");
        this.limit = limit;
        post.put("user",userID);
        url = BASE_URL+BASE_PG_LEI+"/lei_mais_info";
        return this;
    }

    protected Link setComentario(String tab_num,String lei_ID, int userID,String comentario, Boolean publico){
        post.put("tab_num", tab_num);
        tabNum = tab_num;
        post.put("lei_ID", lei_ID);
        post.put("user",userID+"");
        if(publico){
            post.put("publico","1");
        }else {
            post.put("publico","0");
        }
        post.put("comentario",comentario);
        url = BASE_URL+BASE_PG_LEI+"/set_comentario";
        return this;
    }

    protected Link setMarcacao(String tab_num,String lei_ID, int userID,String marcacao){
        post.put("tab_num", tab_num);
        tabNum = tab_num;
        post.put("lei_ID", lei_ID);
        post.put("user",userID+"");
        post.put("marcacao",marcacao);
        url = BASE_URL+BASE_PG_LEI+"/set_marcacao";
        return this;
    }

    protected Link lei(String tab_num, int limit, String userID){
        post.put("tab_num", tab_num);
        tabNum = tab_num;
        post.put("limit", limit + "");
        this.limit = limit;
        post.put("user",userID);
        url = BASE_URL+BASE_PG_LEI+"/lei";
        return this;
    }

    protected Link maisAcessadas(){
        url = BASE_URL+BASE_PG_INFO_LEI+"/mais_acessadas";
        return this;
    }

    protected Link cadastroWebService(String nome,String email,String senha){
        url = BASE_URL+BASE_PG_USUARIO+"/cadastro";
        post.put("nome",nome);
        post.put("email",email);
        post.put("senha",senha);
        post.put("modelo", Build.MODEL);
        post.put("marca",Build.MANUFACTURER);
        post.put("serial",Build.SERIAL);
        return this;
    }

    protected Link loginWebService(String email,String senha){
        url = BASE_URL+BASE_PG_USUARIO+"/logar";
        post.put("email",email);
        post.put("senha",senha);
        post.put("modelo", Build.MODEL);
        post.put("marca",Build.MANUFACTURER);
        post.put("serial",Build.SERIAL);
        return this;
    }

    protected Link getPrazo(String idSistema){
        url = BASE_URL+BASE_PG_USUARIO+"/get_prazo";
        post.put("idSistema",idSistema);
        return this;
    }

    protected Link setNomeUser(int idSistema,String senha,String nome){
        url = BASE_URL+BASE_PG_USUARIO+"/set_nome";
        post.put("id",idSistema+"");
        post.put("senha",senha);
        post.put("nome",nome);
        return this;
    }

    protected Link setEmailUser(int idSistema,String senha,String email){
        url = BASE_URL+BASE_PG_USUARIO+"/set_email";
        post.put("id",idSistema+"");
        post.put("senha",senha);
        post.put("email", email);
        return this;
    }

    public Link send(final OnJSONObject on){
        Request request = new Request(this, new Request.RespJsonObj() {
            @Override
            public void response(JSONObject jsonObject, String erro,String mensagem) {
                if(erro.equalsIgnoreCase(Request.SUCESSO)){
                    on.resp(jsonObject);
                }else{
                    on.erro(erro, mensagem);
                    BaseAnalytics.getInstance(context).evento(BaseAnalytics.CAT_ERRO,erro,mensagem);
                }
                Debug.d("Performance "+performance());
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                        30000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Internet.getInstance(context).add(request);
        return this;
    }

    public int getLimit(){
        return this.limit;
    }

    public String getTabNum(){
        return this.tabNum;
    }
    public String getURL(){
        return url;
    }

    public HashMap<String, String> post(){
        return post;
    }

    public interface OnJSONObject{
        void resp(JSONObject jsonObject);
        void erro(String tipo,String mensagem);
    }
}
