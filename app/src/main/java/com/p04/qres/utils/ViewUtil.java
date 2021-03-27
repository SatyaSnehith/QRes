package com.p04.qres.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.p04.qres.R;
import com.p04.qres.data.Contact;
import com.p04.qres.data.Email;
import com.p04.qres.data.Phone;
import com.p04.qres.data.Sms;
import com.p04.qres.data.Text;
import com.p04.qres.data.Url;
import com.p04.qres.data.Wifi;

import static android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE;

public class ViewUtil {
    private static TextView getTextView(Context context, String text, boolean isKey) {
        TextView textView = new TextView(context);
        textView.setText(text);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (isKey) {
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textView.setGravity(Gravity.END);
            layoutParams.setMarginEnd(UiUtil.getDimen(context, R.dimen.key_margin));
        } else {
//            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            textView.setMarqueeRepeatLimit(-1);
            textView.setInputType(TYPE_TEXT_FLAG_MULTI_LINE);
            textView.setSingleLine(false);
//            textView.setSelected(true);
        }
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    private static TableRow getTableRow(Context context, String key, String value) {
        TableRow row = new TableRow(context);
        row.addView(getTextView(context, key, true));
        row.addView(getTextView(context, value, false));
        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, UiUtil.getDimen(context, R.dimen.row_margin));
        row.setLayoutParams(layoutParams);
        return row;
    }

    public static TextView getTextView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setText(text);
        return textView;
    }

    public static TextView getEmailView(Context context, Email email) {
        TextView textView = new TextView(context);
        textView.setText(email.getEmail());
        return textView;
    }

    public static TextView getUrlView(Context context, Url url) {
        TextView textView = new TextView(context);
        textView.setText(url.getUrl());
        return textView;
    }

    public static TextView getPhoneView(Context context, Phone phone) {
        TextView textView = new TextView(context);
        textView.setText(phone.getNumber());
        return textView;
    }

    public static TableLayout getSmsView(Context context, Sms sms) {
        TableLayout tableLayout = new TableLayout(context);
        String num = sms.getNumber();
        if (num != null && num.length() > 0) {
            tableLayout.addView(getTableRow(context, "Phone number:", num));
        }
        String msg = sms.getMessage();
        if (msg != null && msg.length() > 0) {
            tableLayout.addView(getTableRow(context, "Message:", msg));
        }
        return tableLayout;
    }

    public static TableLayout getWifiView(Context context, Wifi wifi) {
        TableLayout tableLayout = new TableLayout(context);
        String ssid = wifi.getSsid();
        if (ssid != null && ssid.length() > 0) {
            tableLayout.addView(getTableRow(context, "SSID:", ssid));
        }
        String pass = wifi.getPassword();
        if (pass != null && pass.length() > 0) {
            tableLayout.addView(getTableRow(context, "Password:", pass));
        }
        String sec = wifi.getType();
        if (sec != null && sec.length() > 0) {
            tableLayout.addView(getTableRow(context, "Security:", sec));
        }
        return tableLayout;
    }

    public static TableLayout getContactView(Context context, Contact contact) {
        TableLayout tableLayout = new TableLayout(context);
        String name = contact.getName();
        if (name != null && name.length() > 0) {
            tableLayout.addView(getTableRow(context, "Name:", name));
        }
        String company = contact.getCompany();
        if (company != null && company.length() > 0) {
            tableLayout.addView(getTableRow(context, "Company:", company));
        }
        String phone = contact.getPhone();
        if (phone != null && phone.length() > 0) {
            tableLayout.addView(getTableRow(context, "Phone number:", phone));
        }
        String email = contact.getEmail();
        if (email != null && email.length() > 0) {
            tableLayout.addView(getTableRow(context, "Email:", email));
        }
        String address = contact.getAddress();
        if (address != null && address.length() > 0) {
            tableLayout.addView(getTableRow(context, "Address:", address));
        }
        String url = contact.getAddress();
        if (url != null && url.length() > 0) {
            tableLayout.addView(getTableRow(context, "Website:", url));
        }
        String note = contact.getNote();
        if (note != null && note.length() > 0) {
            tableLayout.addView(getTableRow(context, "Note:", note));
        }
        return tableLayout;
    }
}
