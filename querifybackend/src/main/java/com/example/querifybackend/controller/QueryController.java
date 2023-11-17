package com.example.querifybackend.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class QueryController {
    public static void main(String[] args) throws InterruptedException, IOException {
        String keyPath = "src/main/resources/querifyproject-a63c94bb2d7f.json";

        GoogleCredentials credentials = GoogleCredentials.fromStream(Files.newInputStream(Paths.get(keyPath)));

        BigQuery bigquery = BigQueryOptions.newBuilder().setCredentials(credentials).setProjectId("bigquery-public-data").build().getService();
        String datasetName = "usfs_fia";
        DatasetId datasetId = DatasetId.of(datasetName);
        bigquery.listTables(datasetId).iterateAll().forEach(table -> {
            // Recarga la tabla para asegurarte de que la información del esquema esté actualizada
            table = table.reload();

            System.out.println("Table ID: " + table.getTableId().getTable());

            // Get the table schema
            Schema schema = table.getDefinition().getSchema();

            // Check if the schema is not null
            if (schema != null) {
                // Print each field (column) in the schema
                for (Field field : schema.getFields()) {
                    System.out.println("" + field.getName());
                }
            } else {
                System.out.println("No schema found for this table.");
            }
        });

    }
}
