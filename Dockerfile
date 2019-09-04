FROM scratch
WORKDIR /files

COPY target/classes/logging.properties ./conf/
COPY target/payment.jar target/libs/* ./lib/
