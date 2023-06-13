
module com.hexagonkt.http_client_netty {

    requires transitive com.hexagonkt.http;
    requires transitive com.hexagonkt.http_client;
    requires io.netty.transport;
    requires io.netty.codec.http;
    requires io.netty.buffer;

    exports com.hexagonkt.http.client.netty;
}
