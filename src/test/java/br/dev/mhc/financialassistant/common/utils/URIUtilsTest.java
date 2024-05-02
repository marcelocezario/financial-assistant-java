package br.dev.mhc.financialassistant.common.utils;

import org.junit.jupiter.api.Test;

import static br.dev.mhc.financialassistant.common.utils.URIUtils.findIdAfterPath;
import static br.dev.mhc.financialassistant.common.utils.URIUtils.findNextSegmentAfterPath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class URIUtilsTest {

    @Test
    public void findNextSegmentAfterPath_shouldReturnNextSegmentURI() {
        String url = "/app/users/1/categories/2";
        assertEquals("users", findNextSegmentAfterPath(url, "app"));
        assertEquals("users", findNextSegmentAfterPath(url, "/app"));
        assertEquals("users", findNextSegmentAfterPath(url, "app/"));
        assertEquals("1", findNextSegmentAfterPath(url, "users"));
        assertEquals("1", findNextSegmentAfterPath(url, "/users"));
        assertEquals("1", findNextSegmentAfterPath(url, "users/"));
        assertEquals("1", findNextSegmentAfterPath(url, "/users/"));
        assertEquals("2", findNextSegmentAfterPath(url, "/categories"));
        assertNull(findNextSegmentAfterPath(url, "user"));
        assertNull(findNextSegmentAfterPath(url, "/user"));
        assertNull(findNextSegmentAfterPath(url, "user/"));
        assertNull(findNextSegmentAfterPath(url, "/user/"));
        assertNull(findNextSegmentAfterPath(url, "/currencies"));

        String url2 = "app/users/1/categories/2";
        assertEquals("users", findNextSegmentAfterPath(url, "app"));
        assertEquals("users", findNextSegmentAfterPath(url, "/app"));
        assertEquals("users", findNextSegmentAfterPath(url, "app/"));
        assertEquals("1", findNextSegmentAfterPath(url, "users"));
        assertEquals("1", findNextSegmentAfterPath(url, "/users"));
        assertEquals("1", findNextSegmentAfterPath(url, "users/"));
        assertEquals("1", findNextSegmentAfterPath(url, "/users/"));
        assertEquals("2", findNextSegmentAfterPath(url, "/categories"));
    }

    @Test
    public void findIdAfterPath_shouldReturnIdAfterPath() {
        String url = "/app/users/1/categories/2";
        assertEquals(1, findIdAfterPath(url, "users"));
        assertEquals(1, findIdAfterPath(url, "/users"));
        assertEquals(1, findIdAfterPath(url, "users/"));
        assertEquals(1, findIdAfterPath(url, "/users/"));
        assertEquals(2, findIdAfterPath(url, "/categories"));
        assertNull(findIdAfterPath(url, "app"));
        assertNull(findIdAfterPath(url, "/app"));
        assertNull(findIdAfterPath(url, "app/"));
        assertNull(findIdAfterPath(url, "user"));
        assertNull(findIdAfterPath(url, "/user"));
        assertNull(findIdAfterPath(url, "user/"));
        assertNull(findIdAfterPath(url, "/user/"));
        assertNull(findIdAfterPath(url, "/currencies"));

        String url2 = "app/users/1/categories/2";
        assertEquals(1, findIdAfterPath(url, "users"));
        assertEquals(1, findIdAfterPath(url, "/users"));
        assertEquals(1, findIdAfterPath(url, "users/"));
        assertEquals(1, findIdAfterPath(url, "/users/"));
        assertEquals(2, findIdAfterPath(url, "/categories"));

        String url3 = "app/users/1,56/categories/2.569";
        assertNull(findIdAfterPath(url3, "users"));
        assertNull(findIdAfterPath(url3, "categories"));
    }

}
