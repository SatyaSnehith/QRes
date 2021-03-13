package com.p04.qres;

import com.p04.qres.data.Contact;
import com.p04.qres.data.Email;
import com.p04.qres.data.Phone;
import com.p04.qres.data.Sms;
import com.p04.qres.data.Url;
import com.p04.qres.data.Wifi;

public class Decoder {
    private String text;
    private boolean isPlainText, isUrl, isPhone, isEmail, isSms, isWifi, isContact;

    public Decoder(String text) {
        this.text = text;
        init();
        isPlainText = !(isUrl || isPhone || isSms || isEmail || isWifi || isContact);
    }

    private void init() {
        isUrl = isUrl();
        isEmail = isEmail();
        isPhone = isPhone();
        isSms = isSms();
        isWifi = isWifi();
        isContact = isContactInformation();
    }

    public boolean isPlainText() {
        return isPlainText;
    }

    public boolean isUrl() {
        return text.startsWith("http://") || text.startsWith("https://") || text.startsWith("URLTO:") || text.startsWith("urlto:");
    }

    public boolean isEmail() {
        return text.startsWith("mailto:");
    }

    public boolean isPhone() {
        return text.startsWith("tel:");
    }

    public boolean isSms() {
        return text.startsWith("smsto:") || text.startsWith("SMSTO:");
    }

    public boolean isWifi() {
        return text.startsWith("WIFI:") || text.startsWith("wifi:");
    }

    public boolean isContactInformation() {
        return text.startsWith("MECARD:");
    }

    public Url getUrl() {
        Url url = new Url(text);
        if (text.toLowerCase().startsWith("urlto:")) {
            url.setUrl(text.substring(6));
        } else {
            url.setUrl(text);
        }
        return url;
    }

    public Email getEmail() {
        Email email = null;
        if (text.toLowerCase().startsWith("mailto:")) {
            email = new Email(text);
            email.setEmail(text.substring(7));
        }
        return email;
    }

    public Phone getPhone() {
        Phone phone = null;
        if (text.toLowerCase().startsWith("tel:")) {
            phone = new Phone(text);
            phone.setNumber(text.substring(4));
        }
        return phone;
    }

    public Sms getSms() {
        Sms sms = null;
        if (text.toLowerCase().startsWith("smsto:")) {
            sms = new Sms(text);
            String data = text.substring(6);
            int colPos = data.indexOf(':');
            if (colPos > -1) {
                String[] list = data.split(":");
                sms.setNumber(list[0]);
                sms.setMessage(list[1]);
            } else {
                sms.setNumber(data);
            }
        }
        return sms;
    }

    public Wifi getWifi() {
        Wifi wifi = null;
        if (text.startsWith("WIFI:")) {
            wifi = new Wifi(text);
            String data = text.substring(5);
            int colPos = data.indexOf(';');
            if (colPos > -1) {
                String[] list = data.split(";");
                for (String s: list) {
                    int cPos = s.indexOf(':');
                    if (cPos > 0) {
                        String[] kv = s.split(":");
                        switch (kv[0].charAt(0)) {
                            case 'S':
                                wifi.setSsid(kv[1]);
                                break;
                            case 'P':
                                wifi.setPassword(kv[1]);
                                break;
                            case 'T':
                                switch (kv[1]) {
                                    case Wifi.WEP:
                                        wifi.setType(Wifi.WEP);
                                        break;
                                    case Wifi.WPA:
                                        wifi.setType(Wifi.WPA);
                                        break;
                                }
                                break;
                        }
                    }
                }
            }
        }
        return wifi;
    }

    public Contact getContact() {
        Contact contact = null;
        if (text.startsWith("MECARD:")) {
            contact = new Contact(text);
            String data = text.substring(7);
            int colPos = data.indexOf(';');
            if (colPos > -1) {
                String[] list = data.split(";");
                for (String s : list) {
                    int cPos = s.indexOf(':');
                    if (cPos > 0) {
                        String[] kv = s.split(":");
                        switch (kv[0]) {
                            case "N":
                                contact.setName(kv[1]);
                                break;
                            case "ORG":
                                contact.setCompany(kv[1]);
                                break;
                            case "TEL":
                                contact.setPhone(kv[1]);
                                break;
                            case "URL":
                                contact.setWebsite(kv[1]);
                                break;
                            case "EMAIL":
                                contact.setEmail(kv[1]);
                                break;
                            case "ADR":
                                contact.setAddress(kv[1]);
                                break;
                            case "NOTE":
                                contact.setNote(kv[1]);
                                break;
                        }
                    }
                }
            }
        }
        return contact;
    }
}
