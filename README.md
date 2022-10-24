# PASS metadata schemas

[![Build Status](https://travis-ci.com/OA-PASS/metadata-schemas.svg?branch=master)](https://travis-ci.com/OA-PASS/metadata-schemas)

This repository contains JSON schemas and example data intended to describe PASS submission metadata as per the [schemas for forms and validation](https://docs.google.com/document/d/1sLWGZR4kCvQVGv-TA5x8ny-AxL3ChBYNeFYW1eACsDw/edit) design, as well as a [schema service](https://docs.google.com/document/d/1Ki6HUYsEkKPduungp5gHmr7T_YrQUiaTipjorcSnf4s/edit) that will retrieve,
dereference, and place in the correct order all schemas relevant to a given set of pass Repositories.

## Schemas

The JSON schemas herein describe the JSON metadata payload of PASS [submission](https://eclipse-pass.github.io/pass-data-model/documentation/Submission.html) entities.  They serve two purposes
    1. Validation of submission metadata
    2. Generation of forms in the PASS user interface

These schemas follow a defined structure where properties in `/definitions/form/properties` are intended to be displayed by a UI, e.g.

    {
        "title": "Example schema",
        "description": "NIHMS-specific metadata requirements",
        "$id": "https://eclipse-pass.github.io/pass-metadata-schemas/schemas/jhu/example.json",
        "$schema": "http://json-schema.org/draft-07/schema#",
        "type": "object",
        "definitions": {
            "form": {
                "title": "Please provide the following information",
                "type": "object",
                "properties": {
                    "journal": {
                        "$ref": "global.json#/properties/journal"
                    },
                    "ISSN": {
                        "$ref": "global.json#/properties/ISSN"
                    }
                }
            },
        },
        "allOf": [
            {
                "$ref": "global.json#"
            },
            {
                "$ref": "#/definitions/form"
            }
        ]
    }

A pass [repository](https://eclipse-pass.github.io/pass-data-model/documentation/Repository.html) entity represents a target repository where
submissions may be submitted.  Each repository may link to one or more JSON schemas that define the repository's metadata requirements.
In terms of expressing a desired user interface experience, one may observe a general pattern of pointing to a "common" schema containing ubiquitous fields, and additionally pointing to a "repository-specific" schema containing any additional fields that are unique to a given repository.

As a concrete example, the NIHMS repository may point to the [common.json](jhu/common.json) schema, as well as the [nihms.json](jhu/nihms.json)
schema.

## Schema service

The schema service is an http service that accepts a list of PASS [repository](https://eclipse-pass.github.io/pass-data-model/documentation/Repository.html) entity URIs as `application/json` or newline delimited `text/plain`, in a POST request.  for example:

    [
        "http://pass.jhu.edu/fcrepo/rest/repositories/foo",
        "http://pass.jhu.edu/fcrepo/rest/repositories/bar",
    ]

For each repository, the schema service will retrieve the list of schemas relevant to the repository, place that list in the correct order (so
that schemas that provide the most dependencies are displayed first), and resolves all `$ref` references that might appear in the schema.

The endpoint for this service is /schemaservice. 

In the previous Go implementation, a Boolean `merge` query parameter was provided. This parameter has been removed. 
By default, all schemas will be merged into a single union schema. If the service is unable to merge schemas together, it will attempt to return the un-merged list of schemas.

If the service is able to successfully return schemas, it will respond with `200 0K` status. Otherwise, it will respond with `409 Conflict`status.

The result is an `application/json` response that contains a JSON list of schemas.

