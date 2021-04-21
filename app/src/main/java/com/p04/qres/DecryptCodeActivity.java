package com.p04.qres;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.p04.qres.utils.CryptUtil;
import com.p04.qres.utils.ViewUtil;

import java.util.ArrayList;

public class DecryptCodeActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private String text, dText;
    private boolean decodeCardOpened, decryptCardOpened;
    private ImageView backButton;
    private ImageView decodeArrowImageView;
    private LinearLayout decodeCardLayout, decodeTitleLayout, decodeDataLayout,decodeCopyButton;
    private LinearLayout decodeLayout;
    private TextView typeTextView, dTextView;
   private Dialog dialog;


    private ImageView decryptArrowImageView;
    private LinearLayout decryptCardLayout, decryptTitleLayout, decryptDataLayout, decryptCopyButton;
    private LinearLayout decryptLayout;
    private Spinner spinner;
    private ArrayList<String> schemeList;
    private ArrayAdapter<String> schemeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypter);
        init();

        backButton.setOnClickListener(this::onBackPressed);

        decodeTitleLayout.setOnClickListener(this::onClickDecodeTitle);

        setData(text, true);

        decodeCopyButton.setOnClickListener(this::copyDecodeData);

        decryptTitleLayout.setOnClickListener(this::onClickDecryptTitle);
        decryptCopyButton.setOnClickListener(this::copyDecryptData);
        schemeList.add("Base64");
        schemeList.add("Password protected");
        schemeAdapter.setDropDownViewResource(R.layout.scheme_spinner_item);
        spinner.setAdapter(schemeAdapter);
        spinner.setOnItemSelectedListener(this);

    }

    private void setData(String text, boolean isDecode) {
        int typeId;
        View typeView;
        Decoder decoder = new Decoder(text);
        if (decoder.isUrl()) {
            Log.i("TAG", "setDecodeData: URL");
            typeId = R.string.url;
            typeView = ViewUtil.getUrlView(this, decoder.getUrl());
        } else if (decoder.isEmail()) {
            Log.i("TAG", "setDecodeData: EMAIL");
            typeId = R.string.email;
            typeView = ViewUtil.getEmailView(this, decoder.getEmail());
        } else if (decoder.isPhone()) {
            Log.i("TAG", "setDecodeData: PHONE");
            typeId = R.string.phone_number;
            typeView = ViewUtil.getPhoneView(this, decoder.getPhone());
        } else if (decoder.isSms()) {
            Log.i("TAG", "setDecodeData: SMS");
            typeId = R.string.sms;
            typeView = ViewUtil.getSmsView(this, decoder.getSms());
        } else if (decoder.isWifi()) {
            Log.i("TAG", "setDecodeData: WIFI");
            typeId = R.string.wifi;
            typeView = ViewUtil.getWifiView(this, decoder.getWifi());
        } else if (decoder.isContactInformation()) {
            Log.i("TAG", "setDecodeData: CONTACT INFORMATION");
            typeId = R.string.contact_information;
            typeView = ViewUtil.getContactView(this, decoder.getContact());
        } else {
            Log.i("TAG", "setDecodeData: TEXT");
            typeId = R.string.text;
            typeView = ViewUtil.getTextView(this, text);
        }
        if(isDecode) {
            typeTextView.setText(typeId);
            decodeLayout.removeAllViews();
            decodeLayout.addView(typeView);
        } else {
            dTextView.setText(typeId);
            decryptLayout.removeAllViews();
            decryptLayout.addView(typeView);
        }
    }

    private void init() {
        decodeCardOpened = true;
        decryptCardOpened = true;
        text = getIntent().getStringExtra("data");
        dText = "";
        backButton = findViewById(R.id.back_button);
        decodeCardLayout = findViewById(R.id.decode_card_ll);
        decodeTitleLayout = findViewById(R.id.decode_card_title);
        typeTextView = findViewById(R.id.decode_type_tv);
        dTextView = findViewById(R.id.decrypt_type_tv);
        decodeLayout = findViewById(R.id.decode_data);
        decodeDataLayout = findViewById(R.id.decode_data_ll);
        decodeArrowImageView = findViewById(R.id.decode_arrow_iv);
        decodeCopyButton = findViewById(R.id.decode_copy_button);

        decryptCardLayout = findViewById(R.id.decrypt_card_ll);
        decryptTitleLayout = findViewById(R.id.decrypt_card_title);
        decryptDataLayout = findViewById(R.id.decrypt_data_ll);
        decryptArrowImageView = findViewById(R.id.decrypt_arrow_iv);
        decryptLayout = findViewById(R.id.decrypt_data);
        decryptCopyButton = findViewById(R.id.decrypt_copy_button);
        spinner = findViewById(R.id.decrypt_scheme_spinner);
        schemeList = new ArrayList<>();
        schemeAdapter = new ArrayAdapter<>(this, android.R. layout.simple_spinner_item, schemeList);
    }

    private String decrypt() {
        dText = "";
        try {
            dText = CryptUtil.decryptBase64(this.text);
        } catch (Exception e) {
        }
        Log.i("TAG", "decrypt: " + dText);
        return dText;
    }

    private String decryptPassword(String key) {
        dText = "";
        try {
            dText = CryptUtil.withPassword(this.text, key);
        } catch (Exception e) {
        }
        Log.i("TAG", "decryptPassword: " + dText);
        return dText;
    }

    private void onBackPressed(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                setData(decrypt(), false);
                break;
            case 1:
                if (dialog == null) {
                    dialog = new Dialog(DecryptCodeActivity.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setContentView(R.layout.input_password_dialog);
                    dialog.findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setData(decryptPassword(((EditText)dialog.findViewById(R.id.input_et)).getText().toString()), false);
                            dialog.dismiss();
                        }
                    });
                }
                dialog.show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void copyDecodeData(View view) {
        copyToClipBoard(text);
        Toast.makeText(this, R.string.text_copy, Toast.LENGTH_SHORT).show();
    }

    private void copyDecryptData(View view) {
        copyToClipBoard(dText);
        Toast.makeText(this, R.string.text_copy, Toast.LENGTH_SHORT).show();
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