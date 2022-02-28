package com.route.cosmosdb;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.CosmosException;
import com.azure.cosmos.models.CosmosContainerProperties;
import com.azure.cosmos.models.CosmosContainerResponse;
import com.azure.cosmos.models.CosmosDatabaseResponse;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.models.ThroughputProperties;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.route.model.Families;
import com.route.model.Family;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CosmosDB {



    private CosmosDatabase database;
    private CosmosContainer container;

    private CosmosClient client;

    private static CosmosDB sCosmosDB;

    public static CosmosDB getInstance() throws Exception {
        if(sCosmosDB == null) {
            sCosmosDB = new CosmosDB();
        }
        return sCosmosDB;
    }

    private CosmosDB() throws Exception {
        initCosmos();
    }


    private void initCosmos() throws Exception {


        System.out.println("Using Azure Cosmos DB endpoint: " + AccountSettings.HOST);

        ArrayList<String> preferredRegions = new ArrayList<String>();
        preferredRegions.add("West US");
String key = "AccountEndpoint=https://routeme.documents.azure.com:443/;AccountKey=s7ZB1PnsAFxkpdIVgS4sUZE6W9rt3xPxF1Yq819mU3GERy5f6OyxlCFpvvpfjiSqS1Qopt5Zj6smjfPB0uEOTg==;";

String keyP = "067EBKlYuJILfprtW48rUPpnZlTc9ewqdvNTD51BUVcXHFTbicc5VupYAjvTcVA2iJih3K50xjAWv18Gx6sFCg==";

AzureKeyCredential credential = new AzureKeyCredential(keyP);

        //  Create sync client
        client = new CosmosClientBuilder()
                .credential(credential)
                .endpoint(AccountSettings.HOST)
                .key(AccountSettings.MASTER_KEY)
                .preferredRegions(preferredRegions)
                .userAgentSuffix("CosmosDBJavaQuickstart")
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                .buildClient();
//                .buildClient();

        /*client = new CosmosClientBuilder()
                .endpoint(AccountSettings.HOST)
                .key(AccountSettings.MASTER_KEY)
                .preferredRegions(preferredRegions)
//                .userAgentSuffix("CosmosDBJavaQuickstart")
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                .buildClient();*/

        createDatabaseIfNotExists();
        createContainerIfNotExists();
        scaleContainer();

        //  Setup family items to create
        ArrayList<Family> familiesToCreate = new ArrayList<>();
        familiesToCreate.add(Families.getAndersenFamilyItem());
        familiesToCreate.add(Families.getWakefieldFamilyItem());
        familiesToCreate.add(Families.getJohnsonFamilyItem());
        familiesToCreate.add(Families.getSmithFamilyItem());

        createFamilies(familiesToCreate);

        System.out.println("Reading items.");
        readItems(familiesToCreate);

        System.out.println("Querying items.");
        queryItems();
    }

    private void createDatabaseIfNotExists() throws Exception {
        System.out.println("Create database " + AccountSettings.databaseName + " if not exists.");

        //  Create database if not exists
        CosmosDatabaseResponse databaseResponse = client.createDatabaseIfNotExists(AccountSettings.databaseName);
        database = client.getDatabase(databaseResponse.getProperties().getId());

        System.out.println("Checking database " + database.getId() + " completed!\n");
    }

    private void createContainerIfNotExists() throws Exception {
        System.out.println("Create container " + AccountSettings.containerName + " if not exists.");

        //  Create container if not exists
        CosmosContainerProperties containerProperties =
                new CosmosContainerProperties(AccountSettings.containerName, "/partitionKey");

        CosmosContainerResponse containerResponse = database.createContainerIfNotExists(containerProperties);
        container = database.getContainer(containerResponse.getProperties().getId());

        System.out.println("Checking container " + container.getId() + " completed!\n");
    }

    private void scaleContainer() throws Exception {
        System.out.println("Scaling container " + AccountSettings.containerName + ".");

        try {
            // You can scale the throughput (RU/s) of your container up and down to meet the needs of the workload. Learn more: https://aka.ms/cosmos-request-units
            ThroughputProperties currentThroughput = container.readThroughput().getProperties();
            int newThroughput = currentThroughput.getManualThroughput() + 100;
            container.replaceThroughput(ThroughputProperties.createManualThroughput(newThroughput));
            System.out.println("Scaled container to " + newThroughput + " completed!\n");
        } catch (CosmosException e) {
            if (e.getStatusCode() == 400)
            {
                System.err.println("Cannot read container throuthput.");
                System.err.println(e.getMessage());
            }
            else
            {
                throw e;
            }
        }
    }



    private void createFamilies(List<Family> families) throws Exception {
        double totalRequestCharge = 0;
        for (Family family : families) {

            //  Create item using container that we created using sync client

            //  Using appropriate partition key improves the performance of database operations
            CosmosItemResponse item = container.createItem(family, new PartitionKey(family.getPartitionKey()), new CosmosItemRequestOptions());

            //  Get request charge and other properties like latency, and diagnostics strings, etc.
            System.out.println(String.format("Created item with request charge of %.2f within" +
                            " duration %s",
                    item.getRequestCharge(), item.getDuration()));
            totalRequestCharge += item.getRequestCharge();
        }
        System.out.println(String.format("Created %d items with total request " +
                        "charge of %.2f",
                families.size(),
                totalRequestCharge));
    }

    private void readItems(ArrayList<Family> familiesToCreate) {
        //  Using partition key for point read scenarios.
        //  This will help fast look up of items because of partition key
        /*familiesToCreate.forEach(family -> {
            try {
                CosmosItemResponse<Family> item = container.readItem(family.getId(), new PartitionKey(family.getPartitionKey()), Family.class);
                double requestCharge = item.getRequestCharge();
                Duration requestLatency = item.getDuration();
                System.out.println(String.format("Item successfully read with id %s with a charge of %.2f and within duration %s",
                        item.getItem().getId(), requestCharge, requestLatency));
            } catch (CosmosException e) {
                e.printStackTrace();
                System.err.println(String.format("Read Item failed with %s", e));
            }
        });*/
    }

    private void queryItems() {
        // Set some common query options
        int preferredPageSize = 10;
        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        //  Set populate query metrics to get metrics around query executions
        queryOptions.setQueryMetricsEnabled(true);

        CosmosPagedIterable<Family> familiesPagedIterable = container.queryItems(
                "SELECT * FROM Family WHERE Family.partitionKey IN ('Andersen', 'Wakefield', 'Johnson')", queryOptions, Family.class);

        /*familiesPagedIterable.iterableByPage(preferredPageSize).forEach(cosmosItemPropertiesFeedResponse -> {
            System.out.println("Got a page of query result with " +
                    cosmosItemPropertiesFeedResponse.getResults().size() + " items(s)"
                    + " and request charge of " + cosmosItemPropertiesFeedResponse.getRequestCharge());

            System.out.println("Item Ids " + cosmosItemPropertiesFeedResponse
                    .getResults()
                    .stream()
                    .map(Family::getId)
                    .collect(Collectors.toList()));
        });*/
    }
}
