To create apk use the sign_certificate file with the password:
key:delivery
password:delivery123

TIP:
To generate certificate fingerprint run keytool.exe located in java's bin directory with the following cmd
keytool.exe -list -alias delivery -keystore "F:\Projects\FoodDelivery\delivery2go\release\doc\release.keystroke" -storepass delivery123 -keypass delivery123 >> F:\Projects\FoodDelivery\delivery2go\release\doc\release.keystore.sha1
