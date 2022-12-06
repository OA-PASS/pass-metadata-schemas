package org.eclipse.pass.schema;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class SchemaServletTest {

    @Test
    void readTextTest() throws IOException {
        SchemaServlet s = new SchemaServlet();
        String text_list = "http://example.org/foo1" + "\nhttp://example.org/bar1" + "\nhttp://example.org/foo2"
                + "\nhttp://example.org/bar2";
        List<String> expected = Arrays.asList(new String[] { "http://example.org/foo1", "http://example.org/bar1",
            "http://example.org/foo2", "http://example.org/bar2" });
        Reader text_string = new StringReader(text_list);
        BufferedReader text_bufferedReader = new BufferedReader(text_string);
        assertEquals(expected, s.readText(text_bufferedReader));
    }

    @Test
    void readJsonTest() throws Exception {
        SchemaServlet s = new SchemaServlet();
        String json_list = "[\"http://example.org/foo\", \"http://example.org/bar\"]";
        List<String> expected = Arrays.asList(new String[] { "http://example.org/foo", "http://example.org/bar" });
        Reader json_string = new StringReader(json_list);
        BufferedReader json_bufferedReader = new BufferedReader(json_string);
        assertEquals(expected, s.readJson(json_bufferedReader));
    }

}
