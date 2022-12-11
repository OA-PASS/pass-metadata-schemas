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

import java.net.URISyntaxException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import org.dataconservancy.pass.client.PassClient;
import org.dataconservancy.pass.client.PassClientFactory;

/**
 * The SchemaService class handles the business logic of the metadata schema
 * service. It can be used to get a merged schema composed of all the schemas
 * relevant to the repositories that a PASS submission must be published to.
 */
public class SchemaService {

    private PassClient client;
    private List<String> repository_list;
    private String next;

    public SchemaService() {
        client = PassClientFactory.getPassClient();
    }

    // Used in unit tests for inserting a mock client
    public SchemaService(PassClient client) {
        this.client = client;
    }

    /**
     * Get a merged schema composed of all the repository schemas provided as input
     *
     * @param repositoryUris List of repository URIs containing schemas to be merged
     * @return JsonSchema merged schema
     * @throws URISyntaxException
     */
    JsonNode getMergedSchema() throws URISyntaxException {

        // Create a SchemaFetcher instance to get the schemas from the repository URIs
        SchemaFetcher f = new SchemaFetcher(client);
        List<JsonNode> repository_schemas = f.getSchemas(repository_list);
        SchemaMerger m = new SchemaMerger(repository_schemas);
        JsonNode mergedSchema = m.mergeSchemas();

        // (By default, schemas should be merged)
        // Create a SchemaMerger instance to merge the schemas returned by SchemaFetcher
        // If the merge fails, return individual schemas

        return mergedSchema;
    }

    public void setRepositoryList(List<String> repository_list) {
        this.repository_list = repository_list;
    }

}
