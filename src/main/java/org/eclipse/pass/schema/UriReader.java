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

/**
 * The UriReader class reads a request body containing a list of URIs and parses
 * each to ensure it is a valid URI
 */
public class UriReader {

    private List<String> repository_uris;

    public UriReader(BufferedReader repository_uris) {
        // TODO
    }

    /**
     * Handles JSON requests with comma-separated list of URIs and checks if each
     * URI is valid
     *
     * @return true or false based on whether or not URI is valid
     */
    private boolean readJson() {
        // TODO: Not implemented yet
        return false;
    }

    /**
     * Handles JSON requests with newline-separated list of URIs and checks if each
     * URI is valid
     *
     * @return true or false based on whether or not URI is valid
     */
    private boolean readText() {
        // TODO: Not implemented yet
        return false;
    }

}
