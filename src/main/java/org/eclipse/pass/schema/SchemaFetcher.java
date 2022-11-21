/*
 * Copyright 2022 Johns Hopkins University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.pass.schema;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.dataconservancy.pass.model.Repository;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Fetches the schemas from a list of repository URIs and creates a
 * corresponding list of SchemaInstance objects
 */
public class SchemaFetcher {

    private List<String> repository_uris;

    public SchemaFetcher(List<String> repository_uris) {
        this.repository_uris = repository_uris;
    }

    /**
     * Get all SchemaInstance objects corresponding to the repository URIs
     *
     * @return List<SchemaInstance> ArraryList of relevant SchemaInstance objects
     */
    private List<SchemaInstance> getSchemas() {

        List<SchemaInstance> schemas = new ArrayList<SchemaInstance>();

        // for each repository:
        for (String repository_uri : repository_uris) {
            // add the schemas from that repository to the list of all schemas required
            List<SchemaInstance> repository_schemas = getRepositorySchemas(repository_uri);
            schemas.addAll(repository_schemas);
        }

        Collections.sort(schemas);

        // for each schema:
        // dereferenceSchema()

        return null;
    }

    /**
     * Gets the Repository PASS entity at the URI and generates the corresponding
     * SchemaInstance objects
     *
     * @return List<SchemaInstance> schemas from the repository
     */
    private List<SchemaInstance> getRepositorySchemas(String repositoryUri) {

        // Create a Repository PASS entity from the repositoryUri
        // Get the schema URIs from the Repository using the getSchemas() method
        // Call resolveSchemaUri() for each schema URI and create a list of
        // returned SchemaInstance objects
        ObjectMapper mapper = new ObjectMapper();
        Repository r1;
        List<SchemaInstance> repository_schemas = new ArrayList<SchemaInstance>();

        try {
            r1 = mapper.readValue(new URL(repositoryUri), Repository.class);
            List<URI> schema_uris = r1.getSchemas();
            for (URI schema_uri : schema_uris) {
                repository_schemas.add(toLocalSchemaUri(schema_uri));
            }
        } catch (Exception e) {
            // *** Note: stub with more specific errors was generated here; removing for
            // simplicity until error handling decision is made ***
            e.printStackTrace();
        }

        return repository_schemas;
    }

    /**
     * Gets the schema at the URI and creates a corresponding SchemaInstance object
     *
     * @return SchemaInstance schema at URI
     * @throws IOException
     * @throws DatabindException
     * @throws StreamReadException
     */
    private SchemaInstance toLocalSchemaUri(URI schema_uri) throws StreamReadException, DatabindException, IOException {

        // Given the schema's $id url, go to the corresponding local json file
        // by loading it as a resource stream based on the last 2 parts of the $id
        // Create a SchemaInstance object from the json file and return it
        String path = schema_uri.getPath();
        String[] path_segments = path.split("/pass-metadata-schemas");
        String path_to_schema = "." + path_segments[1];
        SchemaInstance schema = getSchema(path_to_schema);
        return schema;
    }

    public static SchemaInstance getSchema(String path) throws IOException {
        InputStream schema_json = SchemaFetcher.class.getResourceAsStream("/schemas/harvard/" + path);
        if (schema_json == null) {
            System.out.println("File not found: " + path);
        }
        ObjectMapper objmapper = new ObjectMapper();
        JsonNode schema_obj = objmapper.readTree(schema_json);
        return new SchemaInstance(schema_obj);
    }

}
