package ggtec.lei_concursospublicos.Outros;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ggtec.lei_concursospublicos.R;
import ggtec.lei_concursospublicos.api_antiga.Trecho;

public class PainelEdicao {

    private View root;
    private static PainelEdicao painelEdicao;
    private LinearLayout linearLayoutPainel;
    private LinearLayout linearLayoutMarcacao;
    private LinearLayout linearLayoutComentario;
    private LinearLayout linearLayoutPlayer;
    private LinearLayout linearLayoutEdicao;

    private ImageView btnComentario;
    private ImageView btnMarcacao;
    private ImageView btnPlayer;

    int colorAcent = Color.parseColor("#FFFFFF");
    int colorAccentLight = Color.parseColor("#FFE57F");

    //painel comentario
    ImageView btnPublico;
    ImageView btnPrivado;
    Boolean comentarioPublico = true;
    EditText editTextComentario;

    //painel marcacao
    ImageView btnBranco;
    ImageView btnAmarelo;
    ImageView btnAzul;
    ImageView btnVerde;
    ImageView btnVermelho;
    static Boolean isShowMarcao = false;
    static int getColorSelect = R.color.marcacao_amarelo;

    //player
    LinearLayout btnPlayerItem;
    LinearLayout btnPlayerControler;

    public static PainelEdicao getInstance(View painel) {
        if (painelEdicao == null) {
            painelEdicao = new PainelEdicao(painel);
        }
        return painelEdicao;
    }

    public static PainelEdicao getInstanceBasic(){
        return painelEdicao;
    }

    public static PainelEdicao getNewInstance(View view){
        return new PainelEdicao(view);
    }

    private PainelEdicao(View root) {
        linearLayoutPainel = (LinearLayout) root;
        linearLayoutComentario = (LinearLayout) linearLayoutPainel.findViewById(R.id.toolbar_comentario);
        linearLayoutMarcacao = (LinearLayout) linearLayoutPainel.findViewById(R.id.toolbar_marcacao);
        linearLayoutPlayer = (LinearLayout) linearLayoutPainel.findViewById(R.id.toolbar_player);
        linearLayoutEdicao = (LinearLayout) linearLayoutPainel.findViewById(R.id.toolbar_edicao);
        hide();

        btnComentario = (ImageView) linearLayoutPainel.findViewById(R.id.btn_comentario);
        btnMarcacao = (ImageView) linearLayoutPainel.findViewById(R.id.btn_marcacao);
        btnPlayer = (ImageView) linearLayoutPainel.findViewById(R.id.btn_player);

        btnPublico = (ImageView) linearLayoutPainel.findViewById(R.id.btn_comentPublic);
        btnPrivado = (ImageView) linearLayoutPainel.findViewById(R.id.btn_comentPrivado);
        editTextComentario = (EditText) linearLayoutPainel.findViewById(R.id.edit_comentario);

        btnBranco = (ImageView) linearLayoutPainel.findViewById(R.id.color_br);
        btnAmarelo = (ImageView) linearLayoutPainel.findViewById(R.id.color_am);
        btnAzul = (ImageView) linearLayoutPainel.findViewById(R.id.color_az);
        btnVerde = (ImageView) linearLayoutPainel.findViewById(R.id.color_vd);
        btnVermelho = (ImageView) linearLayoutPainel.findViewById(R.id.color_vm);

        btnPlayerItem = (LinearLayout) linearLayoutPainel.findViewById(R.id.player_item);
        btnPlayerControler = (LinearLayout) linearLayoutPainel.findViewById(R.id.player_controller);
    }

    public void show() {
        hide();
        unitBtns();
        linearLayoutEdicao.setVisibility(View.VISIBLE);
    }

    public void hide() {
        linearLayoutEdicao.setVisibility(View.GONE);
        linearLayoutComentario.setVisibility(View.GONE);
        linearLayoutMarcacao.setVisibility(View.GONE);
        linearLayoutPlayer.setVisibility(View.GONE);
        isShowMarcao = false;
    }

    private void unitBtns(){
        btnComentario.setColorFilter(colorAcent);
        btnMarcacao.setColorFilter(colorAcent);
        btnPlayer.setColorFilter(colorAcent);
    }

    public void indexComentario(Trecho trecho) {
        btnShows(R.id.btn_comentario);
        if (trecho.getComentario() != null) {
            editTextComentario.setText(trecho.getComentario().getTexto());
            if (trecho.getComentario().getPublico() || trecho.getComentario().getPublico() == null) {
                btnTipoComentario(btnPublico);
            } else {
                btnTipoComentario(btnPrivado);
            }
        } else if (trecho.getTipo() < 9) {
            editTextComentario.setHint("Comente : " + trecho.getClasse());
        }
    }

    public void btnShows(int tipo) {
        show();
        if (tipo == R.id.btn_comentario) {
            btnComentario.setColorFilter(colorAccentLight);
            linearLayoutComentario.setVisibility(View.VISIBLE);
        }
        if (tipo == R.id.btn_marcacao) {
            btnMarcacao.setColorFilter(colorAccentLight);
            linearLayoutMarcacao.setVisibility(View.VISIBLE);
            isShowMarcao = true;
        }
        if (tipo == R.id.btn_player) {
            btnPlayer.setColorFilter(colorAccentLight);
            linearLayoutPlayer.setVisibility(View.VISIBLE);
            btnPlayerItem.setVisibility(View.VISIBLE);
            btnPlayerControler.setVisibility(View.VISIBLE);
        }
        if (tipo == R.id.player_item) {
            btnPlayer.setColorFilter(colorAccentLight);
            linearLayoutPlayer.setVisibility(View.VISIBLE);
            btnPlayerItem.setVisibility(View.VISIBLE);
            btnPlayerControler.setVisibility(View.GONE);
        }
        if (tipo == R.id.player_controller) {
            btnPlayer.setColorFilter(colorAccentLight);
            linearLayoutPlayer.setVisibility(View.VISIBLE);
            btnPlayerItem.setVisibility(View.GONE);
            btnPlayerControler.setVisibility(View.VISIBLE);
        }
    }

    //painel marcação
    public void btnTipoComentario(View view) {
        if (view.getId() == R.id.btn_comentPublic) {
            btnPublico.setImageResource(R.drawable.ic_lock_open_black_36dp);
            btnPrivado.setImageResource(R.drawable.ic_lock_outline_white_18dp);
            comentarioPublico = true;
        }
        if (view.getId() == R.id.btn_comentPrivado) {
            btnPublico.setImageResource(R.drawable.ic_lock_open_white_18dp);
            btnPrivado.setImageResource(R.drawable.ic_lock_outline_black_36dp);
            comentarioPublico = false;
        }
    }


    public Bundle getComentario() {
        Bundle valor = new Bundle();
        valor.putString("comentario", editTextComentario.getText().toString());
        valor.putBoolean("publico", comentarioPublico);
        return valor;
    }

    //painel marcação
    public void selectColor(View view){
        btnBranco.setImageResource(R.drawable.ic_color_br_24dp);
        btnAmarelo.setImageResource(R.drawable.ic_color_am_24dp);
        btnAzul.setImageResource(R.drawable.ic_color_az_24dp);
        btnVerde.setImageResource(R.drawable.ic_color_vd_24dp);
        btnVermelho.setImageResource(R.drawable.ic_color_vm_24dp);
        int id = view.getId();
        getColorSelect = R.color.marcacao_amarelo;
        if(id == R.id.color_br){
            btnBranco.setImageResource(R.drawable.ic_color_br_select_24dp);
            getColorSelect = R.color.marcacao_branco;
        }
        if(id == R.id.color_am){
            btnAmarelo.setImageResource(R.drawable.ic_color_am_select_24dp);
            getColorSelect = R.color.marcacao_amarelo;
        }
        if(id == R.id.color_az){
            btnAzul.setImageResource(R.drawable.ic_color_az_select_24dp);
            getColorSelect = R.color.marcacao_azul;
        }
        if(id == R.id.color_vd){
            btnVerde.setImageResource(R.drawable.ic_color_vd_select_24dp);
            getColorSelect = R.color.marcacao_verde;
        }
        if(id == R.id.color_vm){
            btnVermelho.setImageResource(R.drawable.ic_color_vm_select_24dp);
            getColorSelect = R.color.marcacao_vermelho;
        }
    }

    public static int parseColorSigla(String sigla){
        int result = R.color.marcacao_branco;
        if(sigla.equalsIgnoreCase("Y")){
            result = R.color.marcacao_amarelo;
        }
        if(sigla.equalsIgnoreCase("B")){
            result = R.color.marcacao_azul;
        }
        if(sigla.equalsIgnoreCase("G")){
            result = R.color.marcacao_verde;
        }
        if(sigla.equalsIgnoreCase("R")){
            result = R.color.marcacao_vermelho;
        }
        return result;
    }

    public static Boolean isShowMarcacao(){
        return isShowMarcao;
    }

    public static int getColorSelect(){
        return getColorSelect;
    }

    public static String getColorSigla(int color){
        String result = "W";
        if(color == R.color.marcacao_amarelo){
            result = "Y";
        }
        if(color == R.color.marcacao_azul){
            result = "B";
        }
        if(color == R.color.marcacao_verde){
            result = "G";
        }
        if(color == R.color.marcacao_vermelho){
            result = "R";
        }
        return result;
    }
}