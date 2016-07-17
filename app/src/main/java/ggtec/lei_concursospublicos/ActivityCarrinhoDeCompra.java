package ggtec.lei_concursospublicos;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ggtec.lei_concursospublicos.Sistema.Debug;
import ggtec.lei_concursospublicos.Sistema.Usuario;

/**
 * Created by Vagner on 24/02/2016.
 */
public class ActivityCarrinhoDeCompra extends AppCompatActivity{
    public static final String KEY_PLANO = "user";
    public static final String BASE_URL = "http://vagnergoncalves.info/sis/index.php/pay/pre_mercado_pago/";
    private WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int plano = 0;
        if(intent != null){
            plano = intent.getIntExtra(KEY_PLANO,1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        setContentView(R.layout.layout_carrinho_de_compra);

        web = (WebView) findViewById(R.id.webView);
        WebSettings webconfig = web.getSettings();
        webconfig.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.addJavascriptInterface(this, "app_online");
        web.loadUrl(BASE_URL + Usuario.getInstance(getBaseContext()).getId() + "/" + plano);
        Debug.d("URL PLANO "+ plano);
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
            return;
        }
        super.onBackPressed();
    }
    @JavascriptInterface
    public void fechar_activity(){
        finish();
    }
}
