package test;

import org.aeonbits.owner.Config;


@Config.Sources({ "file:src/main/resources/config.properties" })
public interface ApplicationConfig extends Config{

//    @Config.Key("server.port")
//    int port();

    @Config.Key("server.base")
    String base();
    @Config.Key("server.base2")
    String base2();

    @Config.Key("server.host")
    String host();
}
