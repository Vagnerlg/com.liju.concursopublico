package ggtec.lei_concursospublicos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import ggtec.lei_concursospublicos.Dialog.DialogoCadastro;
import ggtec.lei_concursospublicos.api_antiga.Usuario;

public class ActivityPremium extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_premium);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detalhes Premium");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void sendPlano(View view){
        if(Usuario.getInstance(getBaseContext()).isCadastrado()) {
            Intent intent = new Intent(getBaseContext(), ActivityCarrinhoDeCompra.class);
            if (view.getId() == R.id.btn_plano_1) {
                intent.putExtra(ActivityCarrinhoDeCompra.KEY_PLANO, 1);
            } else if (view.getId() == R.id.btn_plano_3) {
                intent.putExtra(ActivityCarrinhoDeCompra.KEY_PLANO, 3);
            } else if (view.getId() == R.id.btn_plano_6) {
                intent.putExtra(ActivityCarrinhoDeCompra.KEY_PLANO, 6);
            } else if (view.getId() == R.id.btn_plano_12) {
                intent.putExtra(ActivityCarrinhoDeCompra.KEY_PLANO, 12);
            }
            startActivity(intent);
        }else{
            new DialogoCadastro().show(getSupportFragmentManager(),"dialogo");
        }
    }
}