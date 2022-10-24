package org.eclipse.pass.schema;

import java.util.List;

/**
 * The SchemaInstance class represents a schema map, read from a schema URI.
 */
public class SchemaInstance implements Comparable {

    @Override
    public int compareTo(Object o) {
        // TODO: Not implemented yet

        // Call countFormProperties() and findDeps() to determine order

        return 0;
    }

    /**
     * Counts the number of form properties in the schema
     *
     * @return int number of properties
     */
    private int countFormProperties() {
        // TODO: Not implemented yet
        return 0;
    }

    /**
     * Finds dependencies of this schema on other schemas
     *
     * @return List<String> List of URIs to schemas depended on by this schema
     */
    private List<String> findDeps() {
        // TODO: Not implemented yet
        return null;
    }

}
