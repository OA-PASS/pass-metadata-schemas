package org.eclipse.pass.schema;

import java.util.List;
import javax.json.JsonObject;

/**
 * The SchemaService class handles the business logic of the metadata schema
 * service. It can be used to get a merged schema composed of all the schemas
 * relevant to the repositories that a PASS submission must be published to.
 */
public class SchemaService {

    /**
     * Get a merged schema composed of all the repository schemas provided as input
     *
     * @param repositoryUris List of repository URIs containing schemas to be merged
     * @return JsonSchema merged schema
     */
    private JsonObject getMergedSchema(List<String> repositoryUris) {

        // Create UriReader instance to verify the validity of the input URIs

        // Create a SchemaFetcher instance to get the schemas from the repository URIs

        // (By default, schemas should be merged)
        // Create a SchemaMerger instance to merge the schemas returned by SchemaFetcher
        // If the merge fails, return individual schemas

        return null;
    }

}
