package no.entra.bacnet;

import no.entra.bacnet.services.Service;

public interface BacnetResponse<T> {

    int statusCode();
    Service getService();
    Integer getInvokeId();
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
