package br.com.fib.unirota.UploadImage;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class UploadImageService {
  private final String IMGBB_URL = "https://api.imgbb.com/1/upload?key=";
  private final String CLIENT_API_KEY = "c2adaf16edbb013c862a05b0e67c8aaf";

  private final RestTemplate restTemplate;

  public UploadImageService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public String uploadImagemBase64(String imageData) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
    form.add("image", imageData);

    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(form, headers);
    ResponseEntity<ImgbbUploadResponse> response = restTemplate.exchange(
        IMGBB_URL + CLIENT_API_KEY,
        HttpMethod.POST,
        request,
        ImgbbUploadResponse.class);

    if (response.getStatusCode().is2xxSuccessful()) {
      System.out.println("Imagem salva com sucesso: " + response.getBody().data());
    } else {
      System.err.println("Erro ao salvar imagem: " + response.getStatusCode());
    }

    return response.getBody().data().url();
  }
}
