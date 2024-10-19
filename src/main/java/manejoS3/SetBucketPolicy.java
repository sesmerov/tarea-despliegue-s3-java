package manejoS3;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class SetBucketPolicy {
    public static void main(String[] args) {

        String bucketName = args[0];
        String labRegion = args[1];
        String bucketPolicyText = "{\n" +
                "    \"Version\": \"2012-10-17\",\n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Sid\": \"AllowPublicRead\",\n" +
                "            \"Effect\": \"Allow\",\n" +
                "            \"Principal\": \"*\",\n" +
                "            \"Action\": \"s3:GetObject\",\n" +
                "            \"Resource\": \"arn:aws:s3:::" + bucketName + "/*\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        S3Client s3 = S3Client.builder()
                .region(Region.of(labRegion))
                .build();

        try {
            PutBucketPolicyRequest policyReq = PutBucketPolicyRequest.builder()
                    .bucket(bucketName)
                    .policy(bucketPolicyText)
                    .build();

            s3.putBucketPolicy(policyReq);
            System.out.println("Aplicada política correctamente al bucket: " + bucketName);
        } catch (S3Exception e) {
            System.err.println("Error al aplicar política al bucket '" + bucketName + "': " + e.awsErrorDetails().errorMessage());
        } finally {
            s3.close();
        }
    }


}
