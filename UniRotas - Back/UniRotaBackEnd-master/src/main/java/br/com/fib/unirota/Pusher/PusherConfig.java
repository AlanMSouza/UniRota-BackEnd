package br.com.fib.unirota.Pusher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pusher.rest.Pusher;

@Configuration
public class PusherConfig {
  @Value("${pusher.appId}")
  private String appId;

  @Value("${pusher.key}")
  private String key;

  @Value("${pusher.secret}")
  private String secret;

  @Value("${pusher.cluster}")
  private String cluster;

  @Bean
  Pusher pusher() {
    Pusher pusher = new Pusher(appId, key, secret);
    pusher.setCluster(cluster);
    pusher.setEncrypted(true);
    pusher.setDataMarshaller(new GsonDataMarshaller());
    return pusher;
  }
}
