package br.dev.mhc.financialassistant.common.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static java.util.Objects.requireNonNull;

public class URIUtils {

    public static URI buildUri(Long id) {
        requireNonNull(id);
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }
}
