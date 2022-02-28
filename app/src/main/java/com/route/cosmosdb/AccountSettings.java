// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.route.cosmosdb;
import com.azure.cosmos.implementation.apachecommons.lang.StringUtils;

/**
 * Contains the account configurations for Sample.
 * 
 * For running tests, you can pass a customized endpoint configuration in one of the following
 * ways:
 * <ul>
 * <li>-DACCOUNT_KEY="[your-key]" -ACCOUNT_HOST="[your-endpoint]" as JVM
 * command-line option.</li>
 * <li>You can set ACCOUNT_KEY and ACCOUNT_HOST as environment variables.</li>
 * </ul>
 * 
 * If none of the above is set, emulator endpoint will be used.
 * Emulator http cert is self signed. If you are using emulator, 
 * make sure emulator https certificate is imported
 * to java trusted cert store:
 * https://docs.microsoft.com/en-us/azure/cosmos-db/local-emulator-export-ssl-certificates
 */
public class AccountSettings {
    // Replace MASTER_KEY and HOST with values from your Azure Cosmos DB account.
    // The default values are credentials of the local emulator, which are not used in any production environment.
    // <!--[SuppressMessage("Microsoft.Security", "CS002:SecretInNextLine")]-->

    private static String accountName = "vinylpixels-routeme-test";
    private static String mainAdress = ".documents.azure.com";
//    private static String key = "s0RzhQ3WHPDkQE0ymX4iVGVWsP5UP6kk42zYCQcLyPanEn4QfVYgELl2IH9QKryeyoIbUPx6xWKMuxP3OOybYg=="; //Unity
//    private static String key = "s7ZB1PnsAFxkpdIVgS4sUZE6W9rt3xPxF1Yq819mU3GERy5f6OyxlCFpvvpfjiSqS1Qopt5Zj6smjfPB0uEOTg==";
    private static String key = "zGZVcvEnFSvNKa61Yr3UeofdlVhYhQlMIIGHkWaMJZnghohRYcv5l1h18JnKS8VixLrKaVcxPLbMxn0xvZuVkQ==";
    public static String databaseName = "RouteMeData";
    //public static String databaseName = "Routes";
    public static String containerName = "RoutesData";
    private static String resourcePath = "/dbs/" + databaseName + "/colls/" + containerName + "/docs";
//    private static String host = "https://routeme.documents.azure.com:443/";
    private static String host = "https://3569c573-86aa-4895-9555-26a2ca2736cb.documents.azure.com:443/";

    //https://vinylpixels-routeme-test.documents.azure.com/dbs/Routes/colls/RoutesData/docs

    public static String MASTER_KEY =
            System.getProperty("ACCOUNT_KEY", 
                    StringUtils.defaultString(StringUtils.trimToNull(
                            System.getenv().get("ACCOUNT_KEY")),
                            key));

    public static String HOST =
            System.getProperty("ACCOUNT_HOST",
                    StringUtils.defaultString(StringUtils.trimToNull(
                            System.getenv().get("ACCOUNT_HOST")),
                            host));
//                            "https://" + accountName + mainAdress + resourcePath));
//                            "https://f217dafc-0ee0-4-231-b9ee.documents.azure.com:443/"));


}
