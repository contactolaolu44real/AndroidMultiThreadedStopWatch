package com.example.olaoluolarewaju.stopwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private CheckBox radioBtn;
    private TextView textView;
    private Button radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        radioBtn = (CheckBox) findViewById(R.id.checkBox);
        textView = (TextView) findViewById(R.id.textView2);
        radio = (Button) findViewById(R.id.Radio);

        radioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String pmessage= (String) getIntent().getExtras().get("key");
                    textView.setText(pmessage);
            }
        });
        radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioBtn.isSelected()) {
                    textView.setText("Akano");
                }
            }
        });

    }
}
