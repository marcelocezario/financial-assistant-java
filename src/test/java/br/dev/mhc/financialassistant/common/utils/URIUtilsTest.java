package br.dev.mhc.financialassistant.common.utils;

import org.junit.jupiter.api.Test;

import static br.dev.mhc.financialassistant.common.utils.URIUtils.findNextSegmentAfterPath;
import static br.dev.mhc.financialassistant.common.utils.URIUtils.findUuidAfterPath;
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
    public void findIdAfterPath_shouldReturnUuidAfterPath() {
        String url = "/app/users/1/categories/2";
        assertEquals(1, findUuidAfterPath(url, "users"));
        assertEquals(1, findUuidAfterPath(url, "/users"));
        assertEquals(1, findUuidAfterPath(url, "users/"));
        assertEquals(1, findUuidAfterPath(url, "/users/"));
        assertEquals(2, findUuidAfterPath(url, "/categories"));
        assertNull(findUuidAfterPath(url, "app"));
        assertNull(findUuidAfterPath(url, "/app"));
        assertNull(findUuidAfterPath(url, "app/"));
        assertNull(findUuidAfterPath(url, "user"));
        assertNull(findUuidAfterPath(url, "/user"));
        assertNull(findUuidAfterPath(url, "user/"));
        assertNull(findUuidAfterPath(url, "/user/"));
        assertNull(findUuidAfterPath(url, "/currencies"));

        String url2 = "app/users/1/categories/2";
        assertEquals(1, findUuidAfterPath(url, "users"));
        assertEquals(1, findUuidAfterPath(url, "/users"));
        assertEquals(1, findUuidAfterPath(url, "users/"));
        assertEquals(1, findUuidAfterPath(url, "/users/"));
        assertEquals(2, findUuidAfterPath(url, "/categories"));

        String url3 = "app/users/1,56/categories/2.569";
        assertNull(findUuidAfterPath(url3, "users"));
        assertNull(findUuidAfterPath(url3, "categories"));
    }

}
