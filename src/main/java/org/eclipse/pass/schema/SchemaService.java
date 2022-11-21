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

import java.io.BufferedReader;
import java.util.List;
import javax.json.JsonObject;

/**
 * The SchemaService class handles the business logic of the metadata schema
 * service. It can be used to get a merged schema composed of all the schemas
 * relevant to the repositories that a PASS submission must be published to.
 */
public class SchemaService {

    private BufferedReader input;

    public SchemaService(BufferedReader input) {
        this.input = input;
    }

    /**
     * Get a merged schema composed of all the repository schemas provided as input
     *
     * @param repositoryUris List of repository URIs containing schemas to be merged
     * @return JsonSchema merged schema
     */
    private JsonObject getMergedSchema(List<String> repositoryUris) {

        // Create UriReader instance to verify the validity of the input URIs
        // May not be necessary now that the schemas are local
        // UriReader uriReader = new UriReader(input);

        // Create a SchemaFetcher instance to get the schemas from the repository URIs

        // (By default, schemas should be merged)
        // Create a SchemaMerger instance to merge the schemas returned by SchemaFetcher
        // If the merge fails, return individual schemas

        return null;
    }

}
