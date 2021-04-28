package no.entra.bacnet;

import no.entra.bacnet.apdu.Apdu;
import no.entra.bacnet.npdu.Npdu;
import no.entra.bacnet.services.Service;

public interface BacnetResponse<T> {

    int statusCode();
    Service getService();
    Integer getInvokeId();
    Npdu getNpdu();
    Apdu getApdu();
    boolean expectingReply();
    boolean isSegmented();
    boolean moreSegmentsFollows();

 /*
  int statusCode();

    HttpRequest request();

    Optional<HttpResponse<T>> previousResponse();

    HttpHeaders headers();

    T body();

    Optional<SSLSession> sslSession();

    URI uri();

    Version version();
  */
}
