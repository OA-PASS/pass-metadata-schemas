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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class SchemaInstanceTest {

    ObjectMapper map = new ObjectMapper();

    String one = "{\r\n" + "        \"$id\": \"http://example.org/schemas/one.json\",\r\n"
            + "        \"definitions\": {\r\n" + "            \"form\": {\r\n" + "                \"properties\": {\r\n"
            + "                    \"foo\": \"bar\"\r\n" + "                }\r\n" + "            }\r\n"
            + "        }\r\n" + "    }";

    String two = "{\r\n" + "        \"$id\": \"http://example.org/schemas/two.json\",\r\n"
            + "        \"definitions\": {\r\n" + "            \"form\": {\r\n" + "                \"properties\": {\r\n"
            + "                    \"foo\": {\"$ref\": \"one.json#/definitions/form/properties/foo\"},\r\n"
            + "                    \"bar\": \"baz\",\r\n"
            + "                    \"baz\": {\"$ref\": \"#/definitions/form/properties/bar\"}\r\n"
            + "                }\r\n" + "            }\r\n" + "        }\r\n" + "    }";

    String three = "{\r\n" + "        \"$id\": \"http://example.org/schemas/three.json\",\r\n"
            + "        \"definitions\": {\r\n" + "            \"form\": {\r\n" + "                \"properties\": {\r\n"
            + "                    \"foo\": {\"$ref\": \"one.json#/definitions/form/properties/foo\"},\r\n"
            + "                    \"bar\": {\"$ref\": \"two.json#/definitions/form/properties/foo\"},\r\n"
            + "                    \"baz\": \"value\"\r\n" + "                }\r\n" + "            }\r\n"
            + "        }\r\n" + "    }";

    String four = "{\r\n" + "        \"$id\": \"http://example.org/schemas/four.json\",\r\n"
            + "        \"definitions\": {\r\n" + "            \"form\": {\r\n" + "                \"properties\": {\r\n"
            + "                    \"foo2\": {\"$ref\": \"one.json#/definitions/form/properties/foo\"},\r\n"
            + "                    \"bar2\": {\"$ref\": \"two.json#/definitions/form/properties/foo\"},\r\n"
            + "                    \"baz\": \"value\"\r\n" + "                }\r\n" + "            }\r\n"
            + "        }\r\n" + "    }";

    String five = "{\r\n" + "        \"$id\": \"http://example.org/schemas/five.json\",\r\n"
            + "        \"definitions\": {\r\n" + "            \"form\": {\r\n" + "                \"properties\": {\r\n"
            + "                    \"one\": 1,\r\n" + "                    \"two\": 2\r\n" + "                }\r\n"
            + "            }\r\n" + "        }\r\n" + "    }";

    String six = "{\r\n" + "        \"$id\": \"http://example.org/schemas/six.json\",\r\n"
            + "        \"definitions\": {\r\n" + "            \"form\": {\r\n" + "                \"properties\": {\r\n"
            + "                    \"one\": 1\r\n" + "                }\r\n" + "            }\r\n" + "        }\r\n"
            + "    }";

    String seven = "{\r\n" + "        \"$id\": \"http://example.org/schemas/seven.json\"\r\n" + "    }";

    @SuppressWarnings("unchecked")
    @Test
    void testSort() throws JsonMappingException, JsonProcessingException {
        SchemaInstance schema_one = new SchemaInstance(map.readTree(one));
        SchemaInstance schema_two = new SchemaInstance(map.readTree(two));
        SchemaInstance schema_three = new SchemaInstance(map.readTree(three));
        SchemaInstance schema_four = new SchemaInstance(map.readTree(four));
        SchemaInstance schema_five = new SchemaInstance(map.readTree(five));
        SchemaInstance schema_six = new SchemaInstance(map.readTree(six));
        SchemaInstance schema_seven = new SchemaInstance(map.readTree(seven));
        ArrayList<SchemaInstance> toSort = new ArrayList<SchemaInstance>(Arrays.asList(schema_five, schema_two,
                schema_seven, schema_one, schema_six, schema_three, schema_four));
        ArrayList<SchemaInstance> expected = new ArrayList<SchemaInstance>(Arrays.asList(schema_one, schema_two,
                schema_three, schema_four, schema_five, schema_six, schema_seven));
        Collections.sort(toSort);
        toSort.forEach(str -> {
            System.out.println(str.getName());
        });
        assertEquals(toSort, expected);
    }

    @Test
    void dereferenceTest() throws JsonMappingException, JsonProcessingException {
        String example_schema_json = "{\r\n" + "        \"$id\": \"http://example.org/cows/test.json\",\r\n"
                + "        \"foo\": null,\r\n" + "        \"authors\": {\r\n"
                + "                    \"$ref\": \"global.json#/properties/authors\"\r\n" + "                },\r\n"
                + "        \"title\": {\r\n" + "                    \"$ref\": \"global.json#/properties/title\"\r\n"
                + "                }\r\n" + "    }";

        String expected = "{\r\n" + "    \"$id\": \"http://example.org/cows/test.json\",\r\n" + "    \"foo\": null,\r\n"
                + "    \"authors\": {\r\n" + "        \"type\": \"array\",\r\n"
                + "        \"title\": \"Authors of this article or manuscript\",\r\n"
                + "        \"description\": \"List of authors and their associated ORCIDS, if available\",\r\n"
                + "        \"uniqueItems\": true,\r\n" + "        \"items\": {\r\n"
                + "            \"type\": \"object\",\r\n" + "            \"title\": \"Author\",\r\n"
                + "            \"properties\": {\r\n" + "                \"author\": {\r\n"
                + "                    \"type\": \"string\"\r\n" + "                },\r\n"
                + "                \"orcid\": {\r\n" + "                    \"type\": \"string\"\r\n"
                + "                }\r\n" + "            },\r\n" + "            \"required\": [\"author\"]\r\n"
                + "        }\r\n" + "    },\r\n" + "    \"title\": {\r\n" + "        \"type\": \"string\",\r\n"
                + "        \"title\": \"Article / Manuscript Title\",\r\n"
                + "        \"description\": \"The title of the individual article or manuscript that was submitted\"\r\n"
                + "    }\r\n" + "}";

        SchemaInstance testSchema = new SchemaInstance(map.readTree(example_schema_json));
        SchemaInstance expectedSchema = new SchemaInstance(map.readTree(expected));
        testSchema.dereference();
        assertEquals(testSchema.getSchema(), expectedSchema.getSchema());
    }
}
