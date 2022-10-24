package org.eclipse.pass.schema;

import java.util.List;

/**
 * Fetches the schemas from a list of repository URIs and creates a
 * corresponding list of SchemaInstance objects
 */
public class SchemaFetcher {

    private List<String> repo_uris;

    /**
     * Get all SchemaInstance objects corresponding to the repository URIs
     *
     * @return List<SchemaInstance> ArraryList of relevant SchemaInstance objects
     */
    private List<SchemaInstance> getSchemas() {

        // for each repository:
        // loadSchemasFromRepo()

        // sort schemas (using SchemaInstance's compareTo() method)

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

        return null;
    }

    /**
     * Gets the schema at the URI and creates a corresponding SchemaInstance object
     *
     * @return SchemaInstance schema at URI
     */
    private SchemaInstance resolveSchemaUri(String schemaUri) {

        // Given the schema's $id url, go to the corresponding local json file
        // by loading it as a resource stream based on the last 2 parts of the $id
        // Create a SchemaInstance object from the json file and return it

        return null;
    }

    /**
     * Dereferences a schema by resolving all of its references
     *
     * @param schema SchemaInstance object to be dereferenced
     * @return SchemaInstance dereferenced schema
     */
    private SchemaInstance dereferenceSchema(SchemaInstance schema) {

        // Create new DereferenceSchema object and use its functionality
        // to dereference the given schema

        return null;
    }

}
