package br.dev.mhc.financialassistant.dev.test;

import com.google.gson.Gson;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("rawtypes")
@Profile("test")
@RestController
@RequestMapping(value = "/test")
public record TestController(Gson gson) {

    @GetMapping
    public ResponseEntity teste() {
        var teste = "{\"qualquerCoisa\": \"testando\"}";
        return ResponseEntity.ok(teste);
    }
}
