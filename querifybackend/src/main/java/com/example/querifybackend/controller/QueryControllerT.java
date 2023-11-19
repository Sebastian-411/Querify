package com.example.querifybackend.controller;

import com.example.querifybackend.config.BigQueryConnection;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.UUID;

public class QueryControllerT {
    public static void main(String[] args) throws InterruptedException {

        BigQuery bigquery = BigQueryConnection.getInstance().bigquery;
        String query = "SELECT inventory_year, state_code FROM `bigquery-public-data.usfs_fia.condition` WHERE inventory_year=2021 LIMIT 10";
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());


        TableResult result = queryJob.getQueryResults();

        System.out.println(result);
    }
}
