package ggtec.lei_concursospublicos.Eventos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ggtec.lei_concursospublicos.ActivityLei;
import ggtec.lei_concursospublicos.Adapter.AdapterItemInfoLei;
import ggtec.lei_concursospublicos.Outros.BaseAnalytics;
import ggtec.lei_concursospublicos.api_antiga.ItemInfoLei;

/**
 * Created by Vagner on 24/11/2015.
 */
public class onClickLei implements View.OnClickListener {

    private Context context;

    public onClickLei(Context con){
        context = con;
    }

    @Override
    public void onClick(View v) {
        ItemInfoLei itemMenu = (ItemInfoLei) v.getTag(AdapterItemInfoLei.TAG_ITEM_MENU);
        String origem = "";
        origem = (String) v.getTag(AdapterItemInfoLei.TAG_ORIGEM);
        if(itemMenu != null){
            Intent in = new Intent(context,ActivityLei.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle itemMenuB = new Bundle();
            itemMenuB.putString("tab_num",itemMenu.getTab_num());
            itemMenuB.putString("descricao",itemMenu.getDescricao());
            itemMenuB.putString("tipoLeiHumano",itemMenu.getTipoLeiHumano());
            itemMenuB.putString("anoFormat",itemMenu.getNumAnoFormat());
            in.putExtras(itemMenuB);
            context.startActivity(in);
            if(BaseAnalytics.getBaseInstance() != null){
                BaseAnalytics.getBaseInstance().evento(
                        BaseAnalytics.CAT_USUARIO,
                        itemMenu.getTab_num(),
                        origem);
            }
        }
    }
}