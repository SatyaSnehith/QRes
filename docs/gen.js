var image = document.getElementById("code");
var selectedEncType = document.getElementById("selectedEncTypeText");
var codeTypeSelected = document.getElementById("codeTypeSelectedText");
var wifiTypeSelected = document.getElementById("wifiTypeSelectedText");
var encOptions = document.getElementById("encTypeOptions");
var codeOptions = document.getElementById("codeTypeOptions");
var wifiOptions = document.getElementById("wifiTypeOptions");
var encTypes = ['Base64', 'Ceasar cipher', 'Vigenere cipher'];
var codeTypes = ['Text', 'URL', 'Email address', 'Phone number', 'SMS', 'Wifi network', 'Contact information'];
var wifiTypes = ['WEP', 'WPA/WPA2', 'none'];
let typeText = document.getElementById("typeText");
let typeUrl = document.getElementById("typeUrl");
let typeEmail = document.getElementById("typeEmail");
let typePhone = document.getElementById("typePhone");
let typeSms = document.getElementById("typeSms");
let typeWifi = document.getElementById("typeWifi");
let typeCeasar = document.getElementById("typeCeasar");
let typeVigenere = document.getElementById("typeVigenere");
let typeContactInformation = document.getElementById("typeContactInformation");
var inputText = document.getElementById('inputText');
var inputUrl = document.getElementById('inputUrl');
var inputEmail = document.getElementById('inputEmail');
var inputPhone = document.getElementById('inputPhone');
var inputPhoneSms = document.getElementById('inputPhoneSms');
var inputSms = document.getElementById('inputSms');
var inputSsid = document.getElementById('inputSsid');
var inputPassword = document.getElementById('inputPassword');
var inputName = document.getElementById('inputName');
var inputCompany = document.getElementById('inputCompany');
var inputPhoneConInfo = document.getElementById('inputPhoneConInfo');
var inputEmailConInfo = document.getElementById('inputEmailConInfo');
var inputAddress = document.getElementById('inputAddress');
var inputUrlConInfo = document.getElementById('inputUrlConInfo');
var inputNote = document.getElementById('inputNote');

var inputCeasar = document.getElementById('inputCeasar');
var inputVigenere = document.getElementById('inputVigenere');

var invalidateText = document.getElementById("invalidateText");
var invalidateItem = document.getElementById("invalidateItem");
var QRItem = document.getElementById("QRItem");

var encodedContent = document.getElementById("encodeContent");
var encryptedContent = document.getElementById("encryptedContent");

let codeTypeMap = new Map();
var encodeTypeMap = new Map();
let encTypeMap = new Map();
var encryptMap = new Map();

var qrcode = new QRCode(document.getElementById("code"), {width: 200, height: 200});

function hideAllEncTypes() {
    typeCeasar.style.display = 'none';
    typeVigenere.style.display = 'none';
}

function hideAllCodeTypes() {
    typeText.style.display = 'none';
    typeUrl.style.display = 'none';
    typeEmail.style.display = 'none';
    typePhone.style.display = 'none';
    typeSms.style.display = 'none';
    typeWifi.style.display = 'none';
    typeContactInformation.style.display = 'none';
}

function hideQRItem() {
    QRItem.style.display = 'none';
}

function hideInvalidateItem() {
    invalidateItem.style.display = 'none';
}

function showQRItem() {
    QRItem.style.display = 'block';
}

function showInvalidateItem() {
    invalidateItem.style.display = 'block';
}

function escapeText(selector) {
    return selector.replace(/(\,|\.|\/|\:|\;)/g, function($1, $2) {
        return "\\" + $2;
    });
}


function init() {

    var encOptions = document.getElementsByClassName('encTypeOption');
    for (var i = 0; i < encOptions.length; ++i) {
        encOptions[i].innerHTML = encTypes[i];
    }
    var codeOptions = document.getElementsByClassName('codeTypeOption');
    for (var i = 0; i < codeOptions.length; ++i) {
        codeOptions[i].innerHTML = codeTypes[i];
    }
    var wifiOptions = document.getElementsByClassName('wifiTypeOption');
    for (var i = 0; i < wifiOptions.length; ++i) {
        wifiOptions[i].innerHTML = wifiTypes[i];
    }
    typeText.style.display = 'block';

    codeTypeMap[codeTypes[0]] = function() {
        hideAllCodeTypes();
        typeText.style.display = 'block';
    };
    codeTypeMap[codeTypes[1]] = function() {
        hideAllCodeTypes();
        typeUrl.style.display = 'block';
    };

    codeTypeMap[codeTypes[2]] = function() {
        hideAllCodeTypes();
        typeEmail.style.display = 'block';
    };

    codeTypeMap[codeTypes[3]] = function() {
        hideAllCodeTypes();
        typePhone.style.display = 'block';
    };

    codeTypeMap[codeTypes[4]] = function() {
        hideAllCodeTypes();
        typeSms.style.display = 'block';
    };

    codeTypeMap[codeTypes[5]] = function() {
        hideAllCodeTypes();
        typeWifi.style.display = 'block';
    };

    codeTypeMap[codeTypes[6]] = function() {
        hideAllCodeTypes();
        typeContactInformation.style.display = 'block';
    };

    encodeTypeMap[codeTypes[0]] = function() {
        var value = inputText.value;
        if (value.length > 0)
            return value;
        else return null;
    };

    encodeTypeMap[codeTypes[1]] = function() {
        var value = inputUrl.value;
        if (value.length > 0)
            return value;
        else return null;    
    };

    encodeTypeMap[codeTypes[2]] = function() {
        var value = inputEmail.value;
        console.log(value);
        if (value.length > 0)
            return "mailto:" + value;
        else return null;  
    };

    encodeTypeMap[codeTypes[3]] = function() {
        var value = inputPhone.value;
        if (value.length > 0)
            return "tel:" + value;
        else return null;  
    };

    encodeTypeMap[codeTypes[4]] = function() {
        var value = inputPhoneSms.value;
        if (value.length > 0) {
            var encoded = "smsto:" + value;
            var message = inputSms.value;
            if (message.length > 0) {
                encoded += ":" + message;
            }
            return encoded;
        }
        else return null;  
    };

    encodeTypeMap[codeTypes[5]] = function() {
        var ssid = escapeText(inputSsid.value);
        if (ssid.length > 0) {
            var encoded = "WIFI:S:" + ssid + ";";
            console.log(encoded);
            var t = wifiTypes[0];
            switch(wifiTypeSelected.innerHTML) {
                case wifiTypes[1]:
                    t = "WPA";
                    break;
                case wifiTypes[2]:
                    t = null;
                    break;
            }
            if (t != null) {
                encoded += "T:" + t + ";";
            }
            var password = escapeText(inputPassword.value);
            if (password.length > 0) {
                encoded += "P:" + password + ";"
            }
            return encoded + ';';
        } else {
            return null;
        }
    };

    encodeTypeMap[codeTypes[6]] = function() {
        var name = escapeText(inputName.value);
        if (name.length > 0) {
            var encoded = "MECARD:N:" + name + ";"
            var org = escapeText(inputCompany.value);
            if (org.length > 0) {
                encoded += "ORG:" + org + ';';
            }
            var phone = escapeText(inputPhoneConInfo.value);
            if (phone.length > 0) {
                encoded += "TEL:" + phone + ';';
            }
            var url = escapeText(inputUrlConInfo.value);
            if (url.length > 0) {
                encoded += "URL:" + url + ';';
            }
            var email = escapeText(inputEmailConInfo.value);
            if (email.length > 0) {
                encoded += "TEL:" + email + ';';
            }
            var address = escapeText(inputAddress.value);
            if (address.length > 0) {
                encoded += "ADR:" + address + ';';
            }
            var note = escapeText(inputNote.value);
            if (note.length > 0) {
                encoded += "NOTE:" + note + ';';
            }
            return encoded + ';';
        } else {
            return null;
        }
    };

    encTypeMap[encTypes[0]] = function() {
        hideAllEncTypes();
    };

    encTypeMap[encTypes[1]] = function() {
        hideAllEncTypes();
        typeCeasar.style.display = 'block';
    };

    encTypeMap[encTypes[2]] = function() {
        hideAllEncTypes();
        typeVigenere.style.display = 'block';
    };

    encryptMap[encTypes[0]] = function(text) {
        if (text == null) return null;
        return encryptBase64(text);
    }

    encryptMap[encTypes[1]] = function(text) {
        if (text == null) return null;
        var key = parseInt(inputCeasar.value);
        console.log(key);
        if (!isNaN(key)) {
            return encryptCeasar(text, key);
        } else {
            return null;
        }
    }

    encryptMap[encTypes[2]] = function(text) {
        if (text == null) return null;
        var key = inputVigenere.value;
        return encryptVigenere(text, key);
    }
}

init();

function encryptBase64(text) {
    return window.btoa(text);
}

function getAlphabetIndex(code) {
    if (code >= 97 && code <= 122) {
        return code - 97;
    } else if (code >= 65 && code <= 90) {
        return code - 65;
    }
    return -1;
}

function addToChar(code, num) {
    if (code >= 97 && code <= 122) {
        code += num;
        if (code > 122) {
            code -= 26;
        }
        return String.fromCharCode(code);
    } else if (code >= 65 && code <= 90) {
        code += num;
        if (code > 90) {
            code -= 26;
        }
        return String.fromCharCode(code);
    } else {
        return String.fromCharCode(code);
    }
}

function encryptCeasar(text, key) {
    var encryptedText = "";
    key = key % 26;
    for (var i = 0; i < text.length; ++i) {
        var code = text.charCodeAt(i);
        encryptedText += addToChar(code, key);
    }
    return encryptedText;
}

function encryptVigenere(text, key) {
    var encryptedText = "";
    var textLen = text.length;
    var keyLen = key.length;
    for (var i = 0; i < textLen; ++i) {
        var code = text.charCodeAt(i);
        encryptedText += addToChar(code, getAlphabetIndex(key.charCodeAt(i % keyLen)));
    }
    return encryptedText;
}

function generate() {
    hideQRItem();
    hideInvalidateItem();
    var encodedValue = encodeTypeMap[codeTypeSelected.innerHTML]();
    var encryptedValue = encryptMap[selectedEncType.innerHTML](encodedValue);
    console.log(encryptedValue);
    if (encryptedValue == null) {
        showInvalidateItem();
        if (encodedValue == null)
            invalidateText.innerHTML = "Enter atleast 1 character in first field";
        else 
            invalidateText.innerHTML = "Enter any value as key";    
        return;
    }
    showQRItem();
    encodedContent.innerHTML = encodedValue;
    encryptedContent.innerHTML = encryptedValue;
    if (encryptedValue.length > 0) {
        var value = encodeURI(encryptedValue);
        qrcode.makeCode(value);
    }
}
function selectEncType(element) {
    selectedEncType.innerHTML = element.innerHTML;
    encTypeMap[element.innerHTML]();

 }
function selectCodeType(element) {
    codeTypeSelected.innerHTML = element.innerHTML;
    codeTypeMap[element.innerHTML]();
}
function selectCodeWifiType(element) {
    wifiTypeSelected.innerHTML = element.innerHTML;
}
function openEncDrop() {
    encOptions.style.width = document.getElementById('encTypeSelect').offsetWidth + 'px';
    encOptions.style.display = "block";
}
function closeEncDrop() {
    encOptions.style.display = "none";
}
function openCodeDrop() {
    codeOptions.style.width = document.getElementById('codeTypeSelect').offsetWidth + 'px';
    codeOptions.style.display = "block";
}
function closeCodeDrop() {
    codeOptions.style.display = "none";
}
function openWifiDrop() {
    wifiOptions.style.width = document.getElementById('wifiTypeSelect').offsetWidth + 'px';
    wifiOptions.style.display = "block";
}
function closeWifiDrop() {
    wifiOptions.style.display = "none";
}

window.onclick = function(event) {
    if (event.target.matches('#selectedEncType,#selectedEncTypeText,#encArrow')) {
        openEncDrop();
        closeCodeDrop();
        closeWifiDrop();
    } else if(event.target.matches('#codeTypeSelected,#codeTypeSelectedText,#codeArrow')) {
        openCodeDrop();
        closeEncDrop();
        closeWifiDrop();
    } else if(event.target.matches('#wifiTypeSelected,#wifiTypeSelectedText,#wifiArrow')) {
        openWifiDrop();
        closeEncDrop();
        closeCodeDrop();
    } else {
        closeEncDrop();
        closeCodeDrop();
        closeWifiDrop();
    }
}