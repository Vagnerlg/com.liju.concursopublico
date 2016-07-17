package ggtec.lei_concursospublicos.Sistema;

import android.content.Context;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Vagner on 02/12/2015.
 */
public class ListaLei extends Lista {

    private String tab_num;
    private Context context;
    private int limitresp = -1;
    private int lote = -1;
    private int totalItens = -1;
    private View activity = null;


    public ListaLei(Context context,View tratamento, String tab_num) {
        this.activity = tratamento;
        this.context = context;
        this.tab_num = tab_num;
    }

    public int getLimit() {
        return this.limitresp;
    }

    private void setLimit(int limit) {
        this.limitresp = limit;
    }

    public int getLote() {
        return this.lote;
    }

    private void setLote(int lote) {
        this.lote = lote;
    }

    public int getTotalItens() {
        return this.totalItens;
    }

    private void setTotalIntens(int total) {
        this.totalItens = total;
    }

    public void getLei(int limit, final OnItemLei resp) {
        addViewRoot(activity);
        iniciarLoad();
        new Link(context)
                .lei(tab_num, limit, Usuario.getInstance(context).getId()+"")
                .send(new Link.OnJSONObject() {
                    @Override
                    public void resp(JSONObject jsonObject) {
                        try {
                            if (!jsonObject.isNull("total_itens")) {
                                setTotalIntens(jsonObject.getInt("total_itens"));
                            }
                            if (!jsonObject.isNull("limit_inicial")) {
                                setLimit(jsonObject.getInt("limit_inicial"));
                            }
                            if (!jsonObject.isNull("lote_maximo")) {
                                setLote(jsonObject.getInt("lote_maximo"));
                            }
                            JSONArray jsonArrayLei = jsonObject.getJSONArray("lei");
                            resp.resp(parseJsonLei(jsonArrayLei));
                            jsonObject = null;
                            jsonArrayLei = null;
                            envarResposta();
                        } catch (JSONException e) {
                            enviarErro("erro inexperado");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void erro(String tipo, String mensagem) {
                        enviarErro(mensagem);
                    }
                });
    }

    public void getLeiBack(int limit, final OnItemLei resp) {
        new Link(context)
                .lei(tab_num, limit, Usuario.getInstance(context).getId() + "")
                .send(new Link.OnJSONObject() {
                    @Override
                    public void resp(JSONObject jsonObject) {
                        try {
                            if (!jsonObject.isNull("total_itens")) {
                                setTotalIntens(jsonObject.getInt("total_itens"));
                            }
                            if (!jsonObject.isNull("limit_inicial")) {
                                setLimit(jsonObject.getInt("limit_inicial"));
                            }
                            if (!jsonObject.isNull("lote_maximo")) {
                                setLote(jsonObject.getInt("lote_maximo"));
                            }
                            JSONArray jsonArrayLei = jsonObject.getJSONArray("lei");
                            resp.resp(parseJsonLei(jsonArrayLei));
                            jsonObject = null;
                            jsonArrayLei = null;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void erro(String tipo, String mensagem) {

                    }
                });
    }


    public void getLeiMaisInfo(int limit, final OnItemInfoLei onInfoLei) {
        addViewRoot(activity);
        iniciarLoad();
        new Link(context)
                .leiMaisInfo(tab_num, limit, Usuario.getInstance(context).getId() + "")
                .send(new Link.OnJSONObject() {
                    @Override
                    public void resp(JSONObject jsonObject) {
                        try {
                            JSONObject jsonInfoLei = jsonObject.getJSONObject("info_lei");
                            ItemInfoLei infoLei = parseJsonInfolei(jsonInfoLei);
                            if (!jsonObject.isNull("total_itens")) {
                                setTotalIntens(jsonObject.getInt("total_itens"));
                            }
                            if (!jsonObject.isNull("limit_inicial")) {
                                setLimit(jsonObject.getInt("limit_inicial"));
                            }
                            if (!jsonObject.isNull("lote_maximo")) {
                                setLote(jsonObject.getInt("lote_maximo"));
                            }
                            JSONArray jsonArrayLei = jsonObject.getJSONArray("lei");
                            onInfoLei.resp(parseJsonLei(jsonArrayLei), infoLei);
                            jsonInfoLei = null;
                            jsonArrayLei = null;
                            infoLei = null;
                            envarResposta();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            enviarErro("erro inesperado");
                        }
                    }

                    @Override
                    public void erro(String tipo, String mensagem) {
                        enviarErro(mensagem);
                    }
                });
    }

    public void getTituloArtigosComentarioMarcacao( final OnTitArtComMar resp) {
        addViewRoot(activity);
        iniciarLoad();
        new Link(context)
                .tituloArtigoComentarioMarcacao(tab_num, Usuario.getInstance(context).getId() + "")
                .send(new Link.OnJSONObject() {
                    @Override
                    public void resp(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArrayTitulo = jsonObject.getJSONArray("titulo");
                            JSONArray jsonArrayArtigo = jsonObject.getJSONArray("artigo");
                            JSONArray jsonArrayComentario = jsonObject.getJSONArray("comentarios_user");
                            JSONArray jsonArrayMarcacao = jsonObject.getJSONArray("marcacoes_user");
                            ArrayList<ItemLei> titulos = parseJsonLei(jsonArrayTitulo);
                            ArrayList<ItemLei> artigos = parseJsonLei(jsonArrayArtigo);
                            ArrayList<ItemLei> comentario = parseJsonLei(jsonArrayComentario);
                            ArrayList<ItemLei> marcacao = parseJsonLei(jsonArrayMarcacao);
                            resp.resp(titulos, artigos, comentario, marcacao);
                            jsonObject = null;
                            jsonArrayArtigo = null;
                            jsonArrayComentario = null;
                            jsonArrayTitulo = null;
                            jsonArrayMarcacao = null;
                            titulos = null;
                            artigos = null;
                            comentario = null;
                            marcacao = null;
                            envarResposta();
                        } catch (JSONException e) {
                            enviarErro("erro inesperado");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void erro(String tipo, String mensagem) {
                        enviarErro(mensagem);
                    }
                });
    }

    public void getTitulos( final OnItemLei resp) {
        addViewRoot(activity);
        iniciarLoad();
        new Link(context)
                .titulos(tab_num, Usuario.getInstance(context).getId() + "")
                .send(new Link.OnJSONObject() {
                    @Override
                    public void resp(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArrayTitulo = jsonObject.getJSONArray("titulo");
                            ArrayList<ItemLei> titulos = parseJsonLei(jsonArrayTitulo);
                            resp.resp(titulos);
                            envarResposta();
                        } catch (JSONException e) {
                            enviarErro("erro inesperado");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void erro(String tipo, String mensagem) {
                        enviarErro(mensagem);
                    }
                });
    }

    public void getArtigos( final OnItemLei resp) {
        addViewRoot(activity);
        iniciarLoad();
        new Link(context)
                .artigos(tab_num, Usuario.getInstance(context).getId() + "")
                .send(new Link.OnJSONObject() {
                    @Override
                    public void resp(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArrayArtigo = jsonObject.getJSONArray("artigo");
                            ArrayList<ItemLei> artigos = parseJsonLei(jsonArrayArtigo);
                            resp.resp(artigos);
                            envarResposta();
                        } catch (JSONException e) {
                            enviarErro("erro inesperado");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void erro(String tipo, String mensagem) {
                        enviarErro(mensagem);
                    }
                });
    }

    public void getComentarios( final OnItemLei resp) {
        if(Usuario.getInstance(context).isCadastrado()) {
            addViewRoot(activity);
            iniciarLoad();
            new Link(context)
                    .comentarios(tab_num, Usuario.getInstance(context).getId() + "")
                    .send(new Link.OnJSONObject() {
                        @Override
                        public void resp(JSONObject jsonObject) {
                            try {
                                JSONArray jsonArrayComentario = jsonObject.getJSONArray("comentarios_user");
                                ArrayList<ItemLei> comentario = parseJsonLei(jsonArrayComentario);
                                resp.resp(comentario);
                                envarResposta();
                            } catch (JSONException e) {
                                enviarErro("erro inesperado");
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void erro(String tipo, String mensagem) {
                            enviarErro(mensagem);
                        }
                    });
        }else {
            resp.resp(new ArrayList<ItemLei>());
        }
    }

    public void getMarcacoes( final OnItemLei resp) {
        if(Usuario.getInstance(context).isCadastrado()) {
            addViewRoot(activity);
            iniciarLoad();
            new Link(context)
                    .tituloArtigoComentarioMarcacao(tab_num, Usuario.getInstance(context).getId() + "")
                    .send(new Link.OnJSONObject() {
                        @Override
                        public void resp(JSONObject jsonObject) {
                            try {
                                JSONArray jsonArrayMarcacao = jsonObject.getJSONArray("marcacoes_user");
                                ArrayList<ItemLei> marcacao = parseJsonLei(jsonArrayMarcacao);
                                resp.resp(marcacao);
                                envarResposta();
                            } catch (JSONException e) {
                                enviarErro("erro inesperado");
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void erro(String tipo, String mensagem) {
                            enviarErro(mensagem);
                        }
                    });
        }else {
            resp.resp(new ArrayList<ItemLei>());
        }
    }

    public void pesquisa(String pesquisa, final OnItemLei resp) {
        addViewRoot(activity);
        iniciarLoad();
        new Link(context)
                .pesquisaNaLei(tab_num, pesquisa, Usuario.getInstance(context).getId()+"")
                .send(new Link.OnJSONObject() {
                    @Override
                    public void resp(JSONObject jsonObject) {
                        try {
                            if (!jsonObject.isNull("num_result")) {
                                setTotalIntens(jsonObject.getInt("num_result"));
                            }
                            JSONArray jsonArrayLei = jsonObject.getJSONArray("lei");
                            resp.resp(parseJsonLei(jsonArrayLei));
                            jsonObject = null;
                            jsonArrayLei = null;
                            envarResposta();
                        } catch (JSONException e) {
                            enviarErro("erro inesperado");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void erro(String tipo, String mensagem) {
                        enviarErro(mensagem);
                    }
                });
    }

    private ArrayList<ItemLei> parseJsonLei(JSONArray jsonArrayLei) {
        ArrayList<ItemLei> itensLei = new ArrayList<>();
        try {
            for (int i = 0; jsonArrayLei.length() > i; i++) {
                ItemLei item = new ItemLei();
                if (jsonArrayLei.getJSONObject(i).has("ID")) {
                    item.setID(jsonArrayLei.getJSONObject(i).getInt("ID"));
                }
                if (jsonArrayLei.getJSONObject(i).has("lei_ID")) {
                    item.setLeiID(jsonArrayLei.getJSONObject(i).getString("lei_ID"));
                }
                if (jsonArrayLei.getJSONObject(i).has("Tipo")) {
                    item.setTipo(jsonArrayLei.getJSONObject(i).getInt("Tipo"));
                }
                if (jsonArrayLei.getJSONObject(i).has("Class")) {
                    item.setClasse(jsonArrayLei.getJSONObject(i).getString("Class"));
                }
                if (jsonArrayLei.getJSONObject(i).has("Texto")) {
                    item.setTexto(jsonArrayLei.getJSONObject(i).getString("Texto"));
                }
                if (jsonArrayLei.getJSONObject(i).has("Marcacao")) {
                    if (!jsonArrayLei.getJSONObject(i).isNull("Marcacao")) {
                        item.setMarcacao(jsonArrayLei.getJSONObject(i).getString("Marcacao"));
                    }
                }

                if (jsonArrayLei.getJSONObject(i).has("comentario")) {
                    JSONArray jsonComentarios = jsonArrayLei.getJSONObject(i).getJSONArray("comentario");
                    for (int j = 0; jsonComentarios.length() > j; j++) {
                        Comentario comentario = new Comentario();
                        if (jsonComentarios.getJSONObject(j).has("ID")) {
                            comentario.setID(jsonComentarios.getJSONObject(j).getInt("ID"));
                        }
                        if (jsonComentarios.getJSONObject(j).has("usuario_ID")) {
                            comentario.setUsuarioID(jsonComentarios.getJSONObject(j).getInt("usuario_ID"));
                        }
                        if (jsonComentarios.getJSONObject(j).has("lei_ID")) {
                            comentario.setLeiID(jsonComentarios.getJSONObject(j).getString("lei_ID"));
                        }
                        if (jsonComentarios.getJSONObject(j).has("Data")) {
                            comentario.setData(jsonComentarios.getJSONObject(j).getString("Data"));
                        }
                        if (jsonComentarios.getJSONObject(j).has("Texto")) {
                            comentario.setTexto(jsonComentarios.getJSONObject(j).getString("Texto"));
                        }
                        if (jsonComentarios.getJSONObject(j).has("Alteracao")) {
                            comentario.setAlteracao(jsonComentarios.getJSONObject(j).getString("Alteracao"));
                        }
                        if (jsonComentarios.getJSONObject(j).has("Posistivo")) {
                            if(jsonComentarios.getJSONObject(j).isNull("Posistivo")){
                                comentario.setPositivo(0);
                            }else {
                                comentario.setPositivo(jsonComentarios.getJSONObject(j).getInt("Posistivo"));
                            }

                        }
                        if (jsonComentarios.getJSONObject(j).has("Negativo")) {
                            if (jsonComentarios.getJSONObject(j).isNull("Negativo")) {
                                comentario.setNegativo(0);
                            }else {
                                comentario.setNegativo(jsonComentarios.getJSONObject(j).getInt("Negativo"));
                            }
                        }
                        if (jsonComentarios.getJSONObject(j).has("Publico")) {
                            if(!jsonComentarios.getJSONObject(j).isNull("Publico")){
                             int publico = jsonComentarios.getJSONObject(j).getInt("Publico");
                                if(publico == 1){
                                    comentario.setPublico(true);
                                }else {
                                    comentario.setPublico(false);
                                }
                            }else {
                                comentario.setPublico(false);
                            }
                        }else {
                            comentario.setPublico(false);
                        }
                        if (jsonComentarios.getJSONObject(j).getInt("usuario_ID") == Usuario.getInstance(context).getId()) {
                            item.setComentario(comentario);
                        } else {
                            item.addComentarioPublico(comentario);
                        }
                    }
                }
                itensLei.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itensLei;
    }

    public interface OnItemLei {
        void resp(ArrayList<ItemLei> itemLeis);
    }

    public interface OnItemInfoLei {
        void resp(ArrayList<ItemLei> itemLeis, ItemInfoLei infoLei);
    }
    public interface OnTitArtComMar {
        void resp(ArrayList<ItemLei> titulo,ArrayList<ItemLei> atigo,ArrayList<ItemLei> comentario,ArrayList<ItemLei> marcacao);
    }
}