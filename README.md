# bacnet-typelib
Domain objects, mappers and builders for Bacnet.

## Inspiration
Inspired by java.net.HttpClient and HttpRequest/HttpResponse
```
java.net.HttpClient client = HttpClient.newHttpClient();
java.net.HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("http://openjdk.java.net/"))
      .build();
java.net.HttpResponse client.send(..) 
or
CompletableFuture<HttpResponse<T>> client.sendAsync

```
Response
```
CompletableFuture<HttpResponse<T>> client.sendAsync
jdk.internal.net.http.HeaderParser
jdk.internal.net.http.HttpRequestImpl
```
Link will be the combination of sendTo Address, and InvokeId. The response will be received from the
sendTo Address, now as sender Address.

Step 1 will have to be more simple though.

## Parse Response

```
Read from Socket, convert byte to string.
String hexString = ..fromByte(..)
ParserResult<T> result = BvlcParser.parse(hexString)
```
