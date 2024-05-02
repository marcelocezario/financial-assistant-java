package br.dev.mhc.financialassistant.common.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static br.dev.mhc.financialassistant.common.utils.Utils.isIntegerNumber;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class URIUtils {

    public static URI buildUri(Long id) {
        requireNonNull(id);
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

    public static String findNextSegmentAfterPath(String url, String path) {
        requireNonNull(url);
        requireNonNull(path);
        if (!url.startsWith("/")) {
            url = "/" .concat(url);
        }
        if (!path.startsWith("/")) {
            path = "/" .concat(path);
        }
        if (!path.endsWith("/")) {
            path = path.concat("/");
        }
        if (!url.contains(path)) {
            return null;
        }
        int index = url.indexOf(path);
        String subPath = url.substring(index + path.length());
        return subPath.split("/")[0];
    }

    public static Long findIdAfterPath(String url, String path) {
        var id = findNextSegmentAfterPath(url, path);
        if (nonNull(id) && isIntegerNumber(id)) {
            return Long.parseLong(id);
        }
        return null;
    }

}
