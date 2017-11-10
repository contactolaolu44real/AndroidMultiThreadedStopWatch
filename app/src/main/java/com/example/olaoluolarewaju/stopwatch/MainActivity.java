package com.example.olaoluolarewaju.stopwatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG =MainActivity.class.getSimpleName();
    private static final int EXIT_ID = 2;
    private static final int DEVELOPER = 3;
    private static final int SETTINGS = 4;
    Button buttonStart, buttonStop;
    ImageView imageView;
    private boolean mStopLoop;
    int count=0;
    private TextView textViewThreadCount;
    Handler handler;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart= (Button) findViewById(R.id.buttonThreadStrater);
        buttonStop= (Button) findViewById(R.id.buttonStopThread);
        textViewThreadCount = (TextView) findViewById(R.id.textViewThreadCount);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);

        handler= new Handler(getApplicationContext().getMainLooper());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonThreadStrater:
                mStopLoop=true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mStopLoop){
                            try {
                                Thread.sleep(1000);
                                count++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.i(TAG,"Thread id in while loop: "+Thread.currentThread().getId()+" Count: "+count);
                           handler.post(new Runnable() {
                               @Override
                               public void run() {
                                   if(count<60) {
                                       textViewThreadCount.setText("Time spent is: " + count+"s");
                                   } else{
                                       textViewThreadCount.setText("Time spent is: "+Integer.parseInt(""+count/60)+"min"+count%60+"s");
                                   }    MainActivity.this.setRequestedOrientation(Configuration.ORIENTATION_LANDSCAPE);
                               }
                           });

                        }
                    }
                }).start();

                break;
            case R.id.buttonStopThread:
                mStopLoop=false;
                count=-1;
                break;
        }

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        dialog= new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("ExitApps")
                .setMessage("Are you sure you want to exit")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(1);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {        ;
        menu.add(1, EXIT_ID,Menu.NONE,"Exit");
        menu.add(1, DEVELOPER,Menu.NONE,"Developer");
        menu.add(1, SETTINGS,Menu.NONE,"Settings");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case EXIT_ID:
                onBackPressed();
                break;
            case DEVELOPER:
                dialog= new AlertDialog.Builder(MainActivity.this);
                imageView= new ImageView(MainActivity.this);
                imageView.setMaxHeight(50);
                imageView.setMaxWidth(50);
                imageView.setImageResource(R.drawable.developer);
                dialog.setView(imageView)
                        .setTitle("Developer Viewer")
                        .setMessage("Click yes to exit developers dialog and no to exit apps")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onBackPressed();
                            }
                        }).show();
                break;
            case SETTINGS:
                Intent intent= new Intent(getApplicationContext(),Settings.class);
                intent.putExtra("key",textViewThreadCount.getText().toString());
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
