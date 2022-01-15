package com.example.shoku;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.MessageFormat;
import java.util.ArrayList;

public class Backend {
    private static String ACCOUNT_NAME = "shokumotsu";
    private static String ACCOUNT_KEY = "i0LzSam/2SXQGnVgvKGRZDD3madtDvkNujfBWsNLdJ6w2sKKPcc1q5Eer7g+hsa2cCuQliP/qHWOCGEeOpXRaw==";
    private static String END_POINT = "core.windows.net";
    private static String PROTOCOL = "https";
    private static String format = "DefaultEndpointsProtocol={0};AccountName={1};AccountKey={2};EndpointSuffix={3}";

    private static CloudStorageAccount storageAccount = null;
    private static CloudBlobClient blobClient = null;
    private static CloudBlobContainer container = null;

//    public static void main(String[] args) {
//        initAzure("shokumotsu");
//        listBlobs();
//        /*
//        downloadFile("Alpha_uc_lc.svg","-1-");
//        File payload = new File("Alpha_uc_lc.svg");
//        if(payload.exists()) {
//        	try{
//	        	System.out.println("start uploading");
//	        	uploadFile(payload);
//	        	System.out.println("uploading complete");
//        	}
//        	catch(Exception e) {
//        		System.out.println(e);
//        	}
//        }
//        */
//    }




    public static ArrayList<String> listBlobs() {
        if (container == null) initAzure("shokumotsu");
        // first parameter is the prefix
        // second is whether listing out storage blobs
        ArrayList<String> results=new ArrayList<>();
        Iterable<ListBlobItem> blobItems = container.listBlobs(null, true);
        for (ListBlobItem blobItem : blobItems) {
            String uri = blobItem.getUri().toString();
//            int index = uri.lastIndexOf('/')+1;
//            results.add(uri.substring(index));
            results.add(uri);
            System.out.println(uri);
//            System.out.println(uri.substring(index));
        }
        return results;
    }

    public static void uploadFile(File file) {
        if (container == null) initAzure("shokumotsu");
        try {
            // construct BlockBlob and upload
            CloudBlockBlob blob = container.getBlockBlobReference(file.getName());
            blob.uploadFromFile(file.getPath());
            // upload checks
            blob.downloadAttributes();
            long blobSize = blob.getProperties().getLength();
            long localSize = file.length();
            if (blobSize != localSize) {
                System.out.println("Check Failed...Uploading Failure");
                blob.deleteIfExists();
            } else {
                System.out.println("Uploading Successful");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String downloadFile(String blobPath, String targetPath) {
        if (container == null) initAzure("shokumotsu");
        int index = blobPath.lastIndexOf('/')+1;
//        results.add(uri.substring(index));
        String fullPath = targetPath.concat('/' + blobPath.substring(index));
        String testPath = "reference.txt";
        File file = new File(testPath);
        String absolutePath = file.getAbsolutePath();
        try {
            CloudBlockBlob blob = container.getBlockBlobReference(blobPath);
            blob.downloadToFile(testPath);
            return fullPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void initAzure(String containerName) {
        try {
            storageAccount = CloudStorageAccount.parse(MessageFormat.format(format, PROTOCOL, ACCOUNT_NAME, ACCOUNT_KEY, END_POINT));
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference(containerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
