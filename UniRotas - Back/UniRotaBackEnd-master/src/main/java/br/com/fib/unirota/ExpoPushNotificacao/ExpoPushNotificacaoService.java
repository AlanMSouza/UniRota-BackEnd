package br.com.fib.unirota.ExpoPushNotificacao;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

@Service
public class ExpoPushNotificacaoService {

  private final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";

  private final RestTemplate restTemplate;

  public ExpoPushNotificacaoService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void sendPushNotification(String expoPushToken, String title, String body) {
    Map<String, Object> message = new HashMap<>();
    message.put("to", expoPushToken);
    message.put("title", title);
    message.put("body", body);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, Object>> request = new HttpEntity<>(message, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(EXPO_PUSH_URL, request, String.class);

    if (response.getStatusCode().is2xxSuccessful()) {
      System.out.println("Notificação enviada com sucesso: " + response.getBody());
    } else {
      System.err.println("Erro ao enviar notificação: " + response.getStatusCode());
    }
  }

}
