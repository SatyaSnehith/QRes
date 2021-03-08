package com.p04.qres;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;

public class DecryptCodeActivity extends Activity {
    private Barcode barcode;
    private boolean decodeCardOpened, decryptCardOpened;
    private ImageView decodeArrowImageView;
    private LinearLayout decodeCardLayout, decodeTitleLayout, decodeDataLayout,decodeCopyButton;
    private TextView dTextView;

    private ImageView decryptArrowImageView;
    private LinearLayout decryptCardLayout, decryptTitleLayout, decryptDataLayout, decryptCopyButton;
    private Spinner spinner;
    private ArrayList<String> schemeList;
    private ArrayAdapter<String> schemeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypter);
        init();

        decodeTitleLayout.setOnClickListener(this::onClickDecodeTitle);
        dTextView.setText(barcode.displayValue);
        decodeCopyButton.setOnClickListener(this::copyDecodeData);

        decryptTitleLayout.setOnClickListener(this::onClickDecryptTitle);
        decryptCopyButton.setOnClickListener(this::copyDecryptData);
        schemeList.add("base64");
        schemeList.add("base64");
        schemeList.add("base64");
        schemeAdapter.setDropDownViewResource(R.layout.scheme_spinner_item);
        spinner.setAdapter(schemeAdapter);
    }

    private void init() {
        decodeCardOpened = true;
        decryptCardOpened = true;
        barcode = getIntent().getParcelableExtra("data");
        dTextView = findViewById(R.id.decode_data_tv);
        decodeCardLayout = findViewById(R.id.decode_card_ll);
        decodeTitleLayout = findViewById(R.id.decode_card_title);
        decodeDataLayout = findViewById(R.id.decode_data_ll);
        decodeArrowImageView = findViewById(R.id.decode_arrow_iv);
        decodeCopyButton = findViewById(R.id.decode_copy_button);

        decryptCardLayout = findViewById(R.id.decrypt_card_ll);
        decryptTitleLayout = findViewById(R.id.decrypt_card_title);
        decryptDataLayout = findViewById(R.id.decrypt_data_ll);
        decryptArrowImageView = findViewById(R.id.decrypt_arrow_iv);
        decryptCopyButton = findViewById(R.id.decrypt_copy_button);
        spinner = findViewById(R.id.decrypt_scheme_spinner);
        schemeList = new ArrayList<>();
        schemeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, schemeList);
    }

    private void copyDecodeData(View view) {
        copyToClipBoard(barcode.displayValue);
        Toast.makeText(this, R.string.text_copy, Toast.LENGTH_SHORT).show();
    }

    private void copyDecryptData(View view) {

    }

    private void copyToClipBoard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("QRes", text);
        clipboard.setPrimaryClip(clip);
    }

    private void onClickDecodeTitle(View v) {
        if (decodeCardOpened) {
            closeDecodeCard();
            decodeCardOpened = false;
        } else {
            openDecodeCard();
            decodeCardOpened = true;
        }
    }

    private void openDecodeCard() {
        // delaying view visibility animation
        TransitionManager.beginDelayedTransition(decodeCardLayout);

        // animating transition
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(200);
        TransitionManager.beginDelayedTransition(decryptCardLayout, autoTransition);

        decodeDataLayout.setVisibility(View.VISIBLE);

        // Arrow animation
        RotateAnimation rotate = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(200);
        rotate.setFillAfter(false);
        rotate.setInterpolator(new LinearInterpolator());
        decodeArrowImageView.startAnimation(rotate);
    }

    private void closeDecodeCard() {
        // delaying view visibility animation
        TransitionManager.beginDelayedTransition(decodeCardLayout, new Visibility() {
            @Override
            public boolean isVisible(TransitionValues values) {
                return false;
            }
        });

        // animating transition
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(200);
        TransitionManager.beginDelayedTransition(decryptCardLayout, autoTransition);

        decodeDataLayout.setVisibility(View.GONE);

        // Arrow animation
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(200);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new LinearInterpolator());
        decodeArrowImageView.startAnimation(rotate);
    }

    private void onClickDecryptTitle(View v) {
        if (decryptCardOpened) {
            closeDecryptCard();
            decryptCardOpened = false;
        } else {
            openDecryptCard();
            decryptCardOpened = true;
        }
    }

    private void openDecryptCard() {
        // delaying view visibility animation
        TransitionManager.beginDelayedTransition(decryptCardLayout);

        decryptDataLayout.setVisibility(View.VISIBLE);

        // Arrow animation
        RotateAnimation rotate = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(200);
        rotate.setFillAfter(false);
        rotate.setInterpolator(new LinearInterpolator());
        decryptArrowImageView.startAnimation(rotate);
    }

    private void closeDecryptCard() {
        // delaying view visibility animation
        TransitionManager.beginDelayedTransition(decryptCardLayout, new Visibility() {
            @Override
            public boolean isVisible(TransitionValues values) {
                return false;
            }
        });

        decryptDataLayout.setVisibility(View.GONE);

        // Arrow animation
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(200);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new LinearInterpolator());
        decryptArrowImageView.startAnimation(rotate);
    }

}