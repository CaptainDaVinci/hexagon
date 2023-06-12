
module com.hexagonkt.http_client_netty {

    requires transitive com.hexagonkt.http;
    requires transitive com.hexagonkt.http_client;
    requires io.netty.transport;

    exports com.hexagonkt.http.client.netty;
}
