package br.com.fib.unirota.Pusher;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pusher.rest.marshaller.DataMarshaller;

public class GsonDataMarshaller implements DataMarshaller {

  private Gson gson = new Gson();

  public GsonDataMarshaller() {
    this.gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdpter())
        .create();
  }

  @Override
  public String marshal(Object data) {
    return gson.toJson(data);
  }
}
