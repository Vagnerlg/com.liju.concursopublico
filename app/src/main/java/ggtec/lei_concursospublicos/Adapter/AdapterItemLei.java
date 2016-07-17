package ggtec.lei_concursospublicos.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ggtec.lei_concursospublicos.Eventos.onColorMarcacao;
import ggtec.lei_concursospublicos.Outros.PainelEdicao;
import ggtec.lei_concursospublicos.R;
import ggtec.lei_concursospublicos.Sistema.Config;
import ggtec.lei_concursospublicos.Sistema.Debug;
import ggtec.lei_concursospublicos.Sistema.ItemLei;

/**
 * Created by Vagner on 02/12/2015.
 */
public class AdapterItemLei extends RecyclerView.Adapter<AdapterItemLei.ViewHolder> {

    Context context;
    public ArrayList<ItemLei> list = null;
    private int limit;
    private float dp;
    public static String ORIGEM_MENU_COMENTARIO = "Menu comentário";
    public static String ORIGEM_MENU_MARCACAO = "Menu marcação";
    public static String ORIGEM_ACITIVITY_LEI = "Acitivity lei";
    public static String ORIGEM_ACITIVITY_PESQUISA_NA_LEI = "Acitivity pesquisa na lei";
    private String origem;
    public int fontSize = 16;
    public float extraLine = 1.25f;
    public float paddingLine = 1.45f;
    public float getExtraLine2 = fontSize * 0.25f;
    private boolean comentOpen = false;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mItemLei;

        public ViewHolder(LinearLayout cv) {
            super(cv);
            mItemLei = cv;
        }
    }

    public AdapterItemLei(Context con, ArrayList<ItemLei> lista, String origem) {
        context = con;
        list = lista;
        dp = context.getResources().getDisplayMetrics().density;
        this.origem = origem;
        fontSize = Config.getInstance(con).getFonte();
    }

    @Override
    public AdapterItemLei.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = null;
        v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lei, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (list != null) {
            String texto = "" + list.get(position).getTexto();

            LinearLayout root = (LinearLayout) holder.mItemLei.findViewById(R.id.box_item_lei);
            LinearLayout msgEdicao = (LinearLayout) holder.mItemLei.findViewById(R.id.msg_edicao);
            LinearLayout boxTrechoLei = (LinearLayout) holder.mItemLei.findViewById(R.id.box_trecho_lei);
            FrameLayout linhaParagrafo = (FrameLayout) holder.mItemLei.findViewById(R.id.linha_paragrafo);
            final TextView mTrecho = (TextView) holder.mItemLei.findViewById(R.id.texto_trecho_lei);
            final LinearLayout boxMarcacao = (LinearLayout) holder.mItemLei.findViewById(R.id.box_marcacao);
            LinearLayout boxComentario = (LinearLayout) holder.mItemLei.findViewById(R.id.box_comentario);
            TextView mTextoComentario = (TextView) holder.mItemLei.findViewById(R.id.text_comentario);
            ImageView imagemComentario = (ImageView) holder.mItemLei.findViewById(R.id.image_comentario);
            LinearLayout boxComentarioPublico = (LinearLayout) holder.mItemLei.findViewById(R.id.box_comentarioPublico);
            TextView mTextoComentarioPublico = (TextView) holder.mItemLei.findViewById(R.id.text_comentario_publico);

            root.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            if (origem.equalsIgnoreCase(ORIGEM_ACITIVITY_LEI)) {
                root.setTag(position);
            } else {
                root.setTag(list.get(position).getID());
            }

            msgEdicao.setVisibility(View.GONE);
            linhaParagrafo.setVisibility(View.GONE);
            boxTrechoLei.setPadding((int) (20 * dp), (int) (4 * dp), (int) (20 * dp), (int) (4 * dp));
            mTrecho.setVisibility(View.GONE);
            mTrecho.setTextSize((float) fontSize);
            mTrecho.setLineSpacing(paddingLine, extraLine);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mTrecho.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            }
            mTrecho.setTypeface(Typeface.SERIF, Typeface.NORMAL);
            mTrecho.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
            boxMarcacao.setVisibility(View.GONE);
            boxComentario.setVisibility(View.GONE);
            mTextoComentario.setText(null);
            mTextoComentario.setTextSize((float) fontSize);
            boxComentarioPublico.setVisibility(View.GONE);
            mTextoComentarioPublico.setText(null);
            mTextoComentarioPublico.setTextSize((float) fontSize);
            boxMarcacao.removeAllViews();

            if (origem.equalsIgnoreCase(ORIGEM_MENU_COMENTARIO)) {
                mTrecho.setMaxLines(2);
                mTrecho.setEllipsize(TextUtils.TruncateAt.END);
            } else {
                mTrecho.setMaxLines(1000);
                mTrecho.setEllipsize(null);
            }

            if (list.get(position).getTipo() <= 4) {
                mTrecho.setText(texto);
                mTrecho.setTextSize((float) fontSize + 6);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    mTrecho.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                mTrecho.setTypeface(Typeface.SERIF, Typeface.BOLD);
                mTrecho.setVisibility(View.VISIBLE);
                mTrecho.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (list.get(position).getTipo() == 5) {
                mTrecho.setText(texto);
                mTrecho.setVisibility(View.VISIBLE);
            } else if (list.get(position).getTipo() == 8) {
                mTrecho.setText(texto);
                mTrecho.setVisibility(View.VISIBLE);
                linhaParagrafo.setVisibility(View.VISIBLE);
                boxTrechoLei.setPadding((int) (4 * dp), (int) (4 * dp), (int) (20 * dp), (int) (4 * dp));
            } else if (list.get(position).getTipo() == 6) {
                mTrecho.setText(texto);
                mTrecho.setVisibility(View.VISIBLE);
                boxTrechoLei.setPadding((int) (44 * dp), (int) (4 * dp), (int) (20 * dp), (int) (4 * dp));
            } else if (list.get(position).getTipo() == 7) {
                mTrecho.setText(texto);
                mTrecho.setVisibility(View.VISIBLE);
                boxTrechoLei.setPadding((int) (68 * dp), (int) (4 * dp), (int) (20 * dp), (int) (4 * dp));
            } else if (list.get(position).getTipo() == 10) {
                mTrecho.setText(texto);
                mTrecho.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
                mTrecho.setVisibility(View.VISIBLE);
            } else {
                mTrecho.setText(texto);
                mTrecho.setVisibility(View.VISIBLE);
            }

            if (list.get(position).getMarcacao() != null || list.get(position).getSelecionado() && !origem.equalsIgnoreCase(ORIGEM_MENU_COMENTARIO)) {
                mTrecho.post(new Runnable() {
                    @Override
                    public void run() {
                        prepareMarcacao(position, boxMarcacao, list.get(position).getSelecionado(), mTrecho);
                    }
                });
            }

            if (list.get(position).getComentario() != null) {
                mTextoComentario.setText(list.get(position).getComentario().getTexto());
                boxComentario.setVisibility(View.VISIBLE);
                if (list.get(position).getComentario().getPublico()) {
                    imagemComentario.setImageResource(R.drawable.ic_lock_open_white_18dp);
                } else {
                    imagemComentario.setImageResource(R.drawable.ic_lock_outline_white_18dp);
                }
            }
            if (!list.get(position).getComentariosPublico().isEmpty() && origem.equalsIgnoreCase(ORIGEM_ACITIVITY_LEI)) {
                String coment = "";
                for (int i = 0; list.get(position).getComentariosPublico().size() > i; i++) {
                    coment += list.get(position).getComentariosPublico().get(i).getTexto() + "\n";
                }
                boxComentarioPublico.setVisibility(View.VISIBLE);
                if(comentOpen){
                    mTextoComentarioPublico.setText(coment);
                }else{
                    mTextoComentarioPublico.setText(null);
                }
                boxComentarioPublico.setTag(position);
            }
            if (list.get(position).getSelecionado() && origem.equalsIgnoreCase(ORIGEM_ACITIVITY_LEI)) {
                root.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                msgEdicao.setVisibility(View.VISIBLE);
            }

            this.limit = position;
        }
    }

    public int getCreateAdapter() {
        return this.limit;
    }

    public void setComentOpen(){
        comentOpen = !comentOpen;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    LinearLayout selectMarcacao;

    private void prepareMarcacao(final int position, final LinearLayout boxMarcacao, final Boolean selecionda, final TextView textoLei) {

        int height = textoLei.getHeight();
        int width = textoLei.getWidth();
        int marc = -1;

        String marcacao = "";
        String texto = list.get(position).getTexto();

        LinearLayout objPalavra = null;

        final LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        par.height = height;
        par.width = width;
        Debug.d("height " + height + " width " + width);
        boxMarcacao.setVisibility(View.VISIBLE);
        boxMarcacao.setLayoutParams(par);

        if (list.get(position).getMarcacao() != null) {
            marc = 0;
            marcacao = list.get(position).getMarcacao();
        }

        objPalavra = new LinearLayout(context);
        objPalavra.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        objPalavra.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < texto.length(); i++) {
            String letra = String.valueOf(texto.charAt(i));
            TextView objLetra = letra(letra, list.get(position).getTipo());
            if (marc != -1 && marc < marcacao.length()) {
                objLetra.setBackgroundColor(
                        context.getResources().getColor(
                                PainelEdicao.parseColorSigla(String.valueOf(marcacao.charAt(marc)))
                        )
                );
                if (marcacao.charAt(marc) != 'W') {
                    objLetra.setTag(String.valueOf(marcacao.charAt(marc)));
                }
                 marc++;
            }
            if (letra.equalsIgnoreCase(" ")) {
                if (objPalavra != null && objLetra != null) {
                    objPalavra.addView(objLetra);
                    boxMarcacao.addView(objPalavra);
                }
                objPalavra = new LinearLayout(context);
                objPalavra.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                objPalavra.setOrientation(LinearLayout.HORIZONTAL);
            } else {
                if (objPalavra != null && objLetra != null) {
                    objPalavra.addView(objLetra);
                }
            }
        }
        if (objPalavra != null) {
            boxMarcacao.addView(objPalavra);
        }
        boxMarcacao.post(new Runnable() {
            @Override
            public void run() {
                int total = par.width;
                int x = 0;
                ArrayList<LinearLayout> linhas = new ArrayList<LinearLayout>();

                LinearLayout linha;
                linha = new LinearLayout(context);
                linha.setOrientation(LinearLayout.HORIZONTAL);
                linha.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                while (boxMarcacao.getChildCount() > 0) {
                    LinearLayout palavra = (LinearLayout) boxMarcacao.getChildAt(0);
                    x = x + boxMarcacao.getChildAt(0).getWidth();
                    if (x > total) {
                        x = boxMarcacao.getChildAt(0).getWidth();
                        if (list.get(position).getTipo() <= 4) {
                            linha.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        linha.setPadding(0, (int) getExtraLine2, 0, 0);
                        linhas.add(linha);
                        linha = new LinearLayout(context);
                        linha.setOrientation(LinearLayout.HORIZONTAL);
                        linha.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                    boxMarcacao.removeViewAt(0);
                    if (list.get(position).getTipo() <= 4) {
                        linha.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    linha.setPadding(0, (int) getExtraLine2, 0, 0);
                    linha.addView(palavra);
                }
                if (x > 0) {
                    linhas.add(linha);
                }
                linha = null;
                Log.d("LINHAS", linhas.size() + "");
                for (int i = 0; i < linhas.size(); i++) {
                    if (selecionda) {
                        linhas.get(i).setOnTouchListener(new onColorMarcacao(context));
                    }
                    boxMarcacao.addView(linhas.get(i));
                }
            }
        });
        //boxMarcacao.removeAllViews();
        selectMarcacao = boxMarcacao;

        textoLei.setVisibility(View.GONE);
        textoLei.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private TextView letra(String letra,int tipo){
        TextView objLetra = new TextView(context);
        objLetra.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
        objLetra.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        objLetra.setTextSize(fontSize);
        objLetra.setTypeface(Typeface.SERIF, Typeface.NORMAL);
        objLetra.setText(letra);
        if (tipo <= 4) {
            objLetra.setTextSize((float) fontSize + 6);
            objLetra.setTypeface(Typeface.SERIF, Typeface.BOLD);
            objLetra.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        return objLetra;
    }

    public View getViewMarcacao() {
        return selectMarcacao;
    }

    public int getLimit() {
        return this.limit;
    }
}