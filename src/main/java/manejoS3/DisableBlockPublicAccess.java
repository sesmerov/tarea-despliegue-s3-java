package manejoS3;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PublicAccessBlockConfiguration;
import software.amazon.awssdk.services.s3.model.PutPublicAccessBlockRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class DisableBlockPublicAccess {
    public static void main(String[] args) {
        String bucketName = args[0];
        Region region = Region.of(args[1]);
    S3Client s3 = S3Client.builder().region(region).build();

        try {
            PublicAccessBlockConfiguration configuration =
                    PublicAccessBlockConfiguration.builder()
                            .blockPublicAcls(false)
                            .ignorePublicAcls(false)
                            .blockPublicPolicy(false)
                            .restrictPublicBuckets(false)
                            .build();

            PutPublicAccessBlockRequest request =
                    PutPublicAccessBlockRequest.builder()
                            .bucket(bucketName)
                            .publicAccessBlockConfiguration(configuration)
                            .build();

            s3.putPublicAccessBlock(request);
            System.out.println("Acceso público DESHABILITADO correctamente: " + bucketName);
        } catch (S3Exception e) {
            System.err.println("ERROR deshabilitando bloqueo de acceso público: " + e.awsErrorDetails().errorMessage());
        } finally {
            s3.close();
        }
    }}
