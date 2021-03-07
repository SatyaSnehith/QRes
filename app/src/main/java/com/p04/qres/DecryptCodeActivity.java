package com.p04.qres;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DecryptCodeActivity extends Activity {
    private String data;
    private TextView dTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypter);
        init();
        dTextView.setText(data);
    }

    private void init() {
        data = getIntent().getStringExtra("data");
        dTextView = findViewById(R.id.data_tv);
    }
}