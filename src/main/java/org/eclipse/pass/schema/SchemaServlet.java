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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class SchemaServlet This class handles the web request
 * handling of GET and POST requests from the client. It interacts with the
 * SchemaService class, which handles the business logic
 */
@WebServlet("/schemaservice")
public class SchemaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private List<String> repository_list;
    private String next;

    /**
     * Servlet constructor.
     */
    public SchemaServlet() {
        repository_list = new ArrayList<String>();
    }

    protected List<String> readText(BufferedReader r) throws IOException {
        // r.readLine(); // skip the first line containing header information
        while ((next = r.readLine()) != null) {
            repository_list.add(next);
        }
        return repository_list;
    }

    protected List<String> readJson(BufferedReader r) throws Exception {
        // r.readLine(); // skip the first line containing header information
        String json_list = r.readLine();
        ObjectMapper o = new ObjectMapper();
        repository_list = o.readValue(json_list, new TypeReference<ArrayList<String>>() {
        });
        if ((next = r.readLine()) != null) {
            throw new Exception("Too many lines");
        }
        return repository_list;
    }

    /**
     * Handle POST requests by invoking the SchemaService to handle the business
     * logic of generating a merged schema from the list of relevant repository
     * schemas to a PASS submission
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        setCommonHeaders(response);

        // Create SchemaService instance to handle business logic
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

        if (request.getContentType() == "text/plain") {
            repository_list = readText(br);
        } else {
            try {
                repository_list = readJson(br);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        SchemaService s = new SchemaService(repository_list);

        JsonNode mergedSchema;
        try {
            mergedSchema = s.getMergedSchema();
            // Encode resulting schema(s) into a JSON response object
            ObjectMapper m = new ObjectMapper();
            String jsonResponse = m.writeValueAsString(mergedSchema);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(jsonResponse);
            out.flush();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Sets standard headers that are common to both GET and POST responses
     *
     * @param request
     * @param response
     */
    private void setCommonHeaders(HttpServletResponse response) {
        response.setHeader("Accept-Post", "application/json, text/plain");
        response.setHeader("Server", "PASS schema service");
    }

}
