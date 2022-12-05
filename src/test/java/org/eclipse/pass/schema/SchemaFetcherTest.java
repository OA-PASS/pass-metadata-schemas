package org.eclipse.pass.schema;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dataconservancy.pass.client.PassClient;
import org.dataconservancy.pass.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class SchemaFetcherTest {

    private PassClient passClientMock;
    private Repository repository1mock;
    private Repository repository2mock;

    @BeforeEach
    void setup() {
        passClientMock = Mockito.mock(PassClient.class);
        repository1mock = Mockito.mock(Repository.class);
        repository2mock = Mockito.mock(Repository.class);
    }

    @Test
    void getSchemaFromUriTest() throws StreamReadException, DatabindException, IOException, URISyntaxException {
        SchemaFetcher s = new SchemaFetcher(passClientMock);
        URI uri = new URI("https://example.com/pass-metadata-schemas/example/schemas/schema1.json");
        String expectedJsonString = "{\r\n" + "    \"$schema\": \"http://example.org/schema\",\r\n"
                + "    \"$id\": \"http://example.org/foo\",\r\n" + "    \"title\": \"foo\",\r\n"
                + "    \"description\": \"foo schema\",\r\n" + "    \"$comment\": \"one\",\r\n"
                + "    \"a\": \"1\",\r\n" + "    \"x\": {\r\n" + "        \"title\": \"X\",\r\n"
                + "        \"description\": \"a letter\",\r\n" + "        \"$comment\": \"displays good\",\r\n"
                + "        \"type\": \"letter\"\r\n" + "    },\r\n" + "    \"array\": [\"a\", \"b\", \"c\"],\r\n"
                + "    \"aa\": \"b\",\r\n" + "    \"cc\": [\"d\", \"e\"]\r\n" + "}";
        ObjectMapper map = new ObjectMapper();
        JsonNode expected = map.readTree(expectedJsonString);
        assertEquals(expected, s.getSchemaFromUri(uri));
    }

    @Test
    void getRepositorySchemasTest() throws StreamReadException, DatabindException, IOException, URISyntaxException {
        when(passClientMock.findByAttribute(Repository.class, "@id", new URI("repository1")))
                .thenReturn(new URI("uri_to_repository1"));
        when(passClientMock.findByAttribute(Repository.class, "@id", new URI("repository2")))
                .thenReturn(new URI("uri_to_repository2"));
        when(passClientMock.readResource(new URI("uri_to_repository1"), Repository.class)).thenReturn(repository1mock);
        when(passClientMock.readResource(new URI("uri_to_repository2"), Repository.class)).thenReturn(repository2mock);
        List<URI> r1_schemas_list = Arrays.asList(
                new URI[] { new URI("/example_schemas/schema1.json"), new URI("/example_schemas/schema2.json") });
        List<URI> r2_schemas_list = Arrays.asList(
                new URI[] { new URI("/example_schemas/schema2.json"), new URI("/example_schemas/schema3.json") });
        when(repository1mock.getSchemas()).thenReturn(r1_schemas_list);
        when(repository2mock.getSchemas()).thenReturn(r2_schemas_list);
        SchemaFetcher s = new SchemaFetcher(passClientMock);
        String expectedJsonSchema1 = "{\r\n" + "    \"$schema\": \"http://example.org/schema\",\r\n"
                + "    \"$id\": \"http://example.org/foo\",\r\n" + "    \"title\": \"foo\",\r\n"
                + "    \"description\": \"foo schema\",\r\n" + "    \"$comment\": \"one\",\r\n"
                + "    \"a\": \"1\",\r\n" + "    \"x\": {\r\n" + "        \"title\": \"X\",\r\n"
                + "        \"description\": \"a letter\",\r\n" + "        \"$comment\": \"displays good\",\r\n"
                + "        \"type\": \"letter\"\r\n" + "    },\r\n" + "    \"array\": [\"a\", \"b\", \"c\"],\r\n"
                + "    \"aa\": \"b\",\r\n" + "    \"cc\": [\"d\", \"e\"]\r\n" + "}";

        String expectedJsonSchema2 = "{\r\n" + "    \"$schema\": \"http://example.org/schema\",\r\n"
                + "    \"$id\": \"http://example.org/bar\",\r\n" + "    \"title\": \"bar\",\r\n"
                + "    \"description\": \"bar schema\",\r\n" + "    \"$comment\": \"two\",\r\n"
                + "    \"b\": \"2\",\r\n" + "    \"x\": {\r\n" + "        \"title\": \"x\",\r\n"
                + "        \"description\": \"an awesome letter\",\r\n"
                + "        \"$comment\": \"displays nicely\",\r\n" + "        \"type\": \"letter\"\r\n" + "    },\r\n"
                + "    \"array\": [\"b\", \"c\", \"d\"],\r\n"
                + "    \"complexarray\": [{\"a\": [\"b\", {\"c\": \"d\"}]}, \"e\"],\r\n" + "    \"aa\": \"b\",\r\n"
                + "    \"cc\": [\"e\", \"f\", \"g\"]\r\n" + "}";
        ObjectMapper map = new ObjectMapper();
        JsonNode expectedschema1 = map.readTree(expectedJsonSchema1);
        JsonNode expectedschema2 = map.readTree(expectedJsonSchema2);
        List<JsonNode> expected = new ArrayList<JsonNode>(Arrays.asList(expectedschema1, expectedschema2));
        List<JsonNode> result = s.getRepositorySchemas("repository1");
        assertEquals(expected, result);
    }

    @Test
    void getSchemasTest() {

    }

}
