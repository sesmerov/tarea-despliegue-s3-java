package manejoS3;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class GetObject {

    public static void main(String[] args) throws Exception {

        // Read inputs - bucket name, lab region
        String bucketName = args[0];
        Region labRegion = Region.of(args[1]);
        String objectKey = args[2];


        // Create S3 service client

        S3Client s3 = S3Client.builder()
                .region(labRegion)
                .build();

        // Recuperamos el archivo del bucket

        obtenerS3Objects(s3, bucketName, objectKey);

        s3.close(); //Close S3 service client

    }


    private static void obtenerS3Objects(S3Client s3, String bucketName, String objectKey) {

        try {
            // Build request using bucket name and key to download object
            // TODO 7 BEGIN

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            // TODO 7 END
            System.out.println("Retrieving notes.txt from bucket...");

            // Get response in bytes
            ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(getObjectRequest);
            byte[] data = objectBytes.asByteArray();

            // PART 2

            //Write to a local file on your disk
            File localFile = new File(objectKey);
            OutputStream os = new FileOutputStream(localFile);
            os.write(data);
            os.close();

            System.out.println("    Object downloaded from S3 is written to: " + localFile.getAbsolutePath());


            } catch (Exception e) {
                e.printStackTrace();

            }



    }
}
