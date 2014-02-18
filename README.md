spring-boot-embedded-jetty-ssl
==============================
Working example of spring boot with embedded jetty9 running ssl with a self-signed cert from project root, keystore.p12, created with:

keytool -genkey -alias jetty -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650

Example uses password localhost.

See src/test/java/demo/SampleJettyApplicationTests.java
