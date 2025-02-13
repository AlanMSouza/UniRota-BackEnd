package br.com.fib.unirota.Pusher;

import java.io.IOException;
import java.time.LocalDateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class LocalDateTimeTypeAdpter extends TypeAdapter<LocalDateTime> {
  @Override
  public void write(JsonWriter out, LocalDateTime value) throws IOException {
    if (value == null) {
      out.nullValue();
    } else {
      out.value(value.toString());
    }
  }

  @Override
  public LocalDateTime read(JsonReader in) throws IOException {
    if (in.peek() == null) {
      in.nextNull();
      return null;
    }
    return LocalDateTime.parse(in.nextString());
  }
}
