/*Copyright 2022 Johns Hopkins University**Licensed under the Apache License,Version 2.0(the"License");*you may not use this file except in compliance with the License.*You may obtain a copy of the License at**http://www.apache.org/licenses/LICENSE-2.0
**Unless required by applicable law or agreed to in writing,software*distributed under the License is distributed on an"AS IS"BASIS,*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.*See the License for the specific language governing permissions and*limitations under the License.*/

package org.eclipse.pass.schema;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.dataconservancy.pass.client.PassClient;
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

    private PassClient client;

    public SchemaFetcher(PassClient client) {
        this.client = client;
    }

    /**
     * Get all SchemaInstance objects corresponding to the repository URIs
     *
     * @return List<SchemaInstance> ArrayList of relevant SchemaInstance objects
     * @throws URISyntaxException
     */
    List<JsonNode> getSchemas(List<String> repository_uris) throws URISyntaxException {

        List<JsonNode> schemas = new ArrayList<JsonNode>();

        // for each repository:
        for (String repository_uri : repository_uris) {
            // add the schemas from that repository to the list of all schemas required
            List<JsonNode> repository_schemas = getRepositorySchemas(repository_uri);
            for (JsonNode schema : repository_schemas) {
                if (!repository_schemas.contains(schema)) // only add schemas that are not already in list
                    repository_schemas.add(schema);
            }
        }

        // dereference each of the schemas
        for (int i = 0; i < schemas.size(); i++) {
            SchemaInstance s = new SchemaInstance(schemas.get(i)); // needs to be reviewed after decision is made re.
                                                                   // sorting
            s.dereference(schemas.get(i), "");
            schemas.set(i, s.getSchema());
        }

        return schemas;
    }

    /**
     * Gets the Repository PASS entity at the URI and generates the corresponding
     * SchemaInstance objects
     *
     * @return List<SchemaInstance> schemas from the repository
     * @throws URISyntaxException
     */
    List<JsonNode> getRepositorySchemas(String repositoryUri) throws URISyntaxException {

        URI uri_r1 = client.findByAttribute(Repository.class, "@id", new URI(repositoryUri));
        Repository r1 = client.readResource(uri_r1, Repository.class);
        List<JsonNode> repository_schemas = new ArrayList<JsonNode>();

        try {
            List<URI> schema_uris = r1.getSchemas();
            for (URI schema_uri : schema_uris) {
                repository_schemas.add(getSchemaFromUri(schema_uri));
            }
        } catch (Exception e) { // *** Note: stub with more specific errors was generated here; removing
            // for simplicity until error handling decision is made ***
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
    JsonNode getSchemaFromUri(URI schema_uri) throws StreamReadException, DatabindException, IOException {

        // Given the schema's $id url, go to the corresponding local json file
        // by loading it as a resource stream based on the last 2 parts of the $id
        // Create a SchemaInstance object from the json file and return it
        String path = schema_uri.getPath();
        String[] path_segments = path.split("/pass-metadata-schemas");
        String path_to_schema = path_segments[path_segments.length - 1];
        JsonNode schema = getLocalSchema(path_to_schema);
        return schema;
    }

    public static JsonNode getLocalSchema(String path) throws IOException {
        InputStream schema_json = SchemaFetcher.class.getResourceAsStream(path);
        if (schema_json == null) {
            System.out.println("File not found: " + path);
        }
        ObjectMapper objmapper = new ObjectMapper();
        JsonNode schema_obj = objmapper.readTree(schema_json);
        return schema_obj;
    }

}