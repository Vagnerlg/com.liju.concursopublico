package ggtec.lei_concursospublicos;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ggtec.lei_concursospublicos.Eventos.OnIntListerner;
import ggtec.lei_concursospublicos.Service.MediaPlayerService;



public class ActivityPlayer extends AppCompatActivity {


    private final String MY_AD_UNIT_ID = "ca-app-pub-4903827553237664/1964563231";
    private Intent intent;
    private Boolean alternador = true;
    private ImageView playPause;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;

    LinearLayout conteinerTexto;

    MediaPlayerService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_player);
        playPause = (ImageView) findViewById(R.id.play_pause);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        conteinerTexto = (LinearLayout) findViewById(R.id.conteiner_texto);
        conteinerTexto.setVisibility(View.GONE);

        textView1 = (TextView) findViewById(R.id.player_texto_1);
        textView2 = (TextView) findViewById(R.id.player_texto_2);
        textView3 = (TextView) findViewById(R.id.player_texto_3);
        textView4 = (TextView) findViewById(R.id.player_texto_4);
        textView5 = (TextView) findViewById(R.id.player_texto_5);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, MediaPlayerService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            mService.setListener(null);
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void controller(View view){
        intent = new Intent(getApplicationContext(),MediaPlayerService.class);
        if(view.getId() == R.id.play_pause){
            if(alternador){
                playPause.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                intent.setAction(MediaPlayerService.ACTION_PAUSE);
            }else {
                playPause.setImageResource(R.drawable.ic_pause_white_48dp);
                intent.setAction(MediaPlayerService.ACTION_PLAY);
            }
            alternador = !alternador;
        }
        if(view.getId() == R.id.stop){
            playPause.setImageResource(R.drawable.ic_play_arrow_white_48dp);
            alternador = false;
            intent.setAction(MediaPlayerService.ACTION_STOP);
            finish();
        }
        if(view.getId() == R.id.previous){
            intent.setAction(MediaPlayerService.ACTION_PREVIOUS);
        }
        if(view.getId() == R.id.next){
            intent.setAction(MediaPlayerService.ACTION_NEXT);
        }
        startService(intent);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            mService = binder.getService();
            mService.setListener(new OnIntListerner() {
                @Override
                public void response(final int poiter) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            conteinerTexto.setVisibility(View.VISIBLE);
                            int size = MediaPlayerService.allTrecho.size();
                            if(poiter > 2 && size > poiter) {
                                textView1.setText(MediaPlayerService.allTrecho.get(poiter - 2).getTexto());
                            }
                            if(poiter > 1 && size > poiter) {
                                textView2.setText(MediaPlayerService.allTrecho.get(poiter - 1).getTexto());
                            }
                            textView3.setText(MediaPlayerService.allTrecho.get(poiter).getTexto());
                            if(size > (poiter + 1)) {
                                textView4.setText(MediaPlayerService.allTrecho.get(poiter + 1).getTexto());
                            }
                            if(size > (poiter + 2)) {
                                textView5.setText(MediaPlayerService.allTrecho.get(poiter +2).getTexto());
                            }
                        }
                    });
                }
            });
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public void velocidadeMenos(View view){
        if(mBound){
            mService.maisLento();
        }
    }

    public void velocidadeMais(View view){
        if(mBound){
            mService.maisRapido();
        }
    }
}
