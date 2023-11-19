package com.example.querifybackend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BigQueryConnection {

    private static BigQueryConnection instance;
    public BigQuery bigquery;



    private BigQueryConnection() {
        try {
            String keyPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(keyPath));
            String projectId = "querifyproject"; // project id

            this.bigquery = BigQueryOptions.newBuilder().setCredentials(credentials).setProjectId(projectId).build().getService();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BigQueryConnection getInstance() {
        if (instance == null) {
            instance = new BigQueryConnection();
        }
        return instance;
    }

    public List<Map<String, Object>> getData(String query) throws InterruptedException {
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = (getInstance().bigquery).create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        queryJob = queryJob.waitFor();

        List<Map<String, Object>> resultData = new ArrayList<>();

        if (queryJob.getStatus().getError() == null) {
            // Obtener el esquema de la consulta
            TableResult queryResult = queryJob.getQueryResults();
            Schema schema = queryResult.getSchema();

            for (FieldValueList row : queryResult.iterateAll()) {
                Map<String, Object> rowData = new HashMap<>();
                for (int i = 0; i < row.size(); i++) {
                    Field field = schema.getFields().get(i);
                    rowData.put(field.getName(), row.get(i).getValue());
                }
                resultData.add(rowData);
            }
        } else {
            System.err.println("Error en la consulta: " + queryJob.getStatus().getError());
        }

        return resultData;
    }

}
