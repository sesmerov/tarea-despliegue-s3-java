package manejoS3;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;


public class hostS3Website {
    public static void main(String[] args) throws Exception {
        // Inputs - nombre del bucket name, región
        String bucketName = args[0];
        Region labRegion = Region.of(args[1]);
        String indexPage = args[2];
        String errorPage = args[3];

        // Crear S3 service client
        S3Client s3 = S3Client.builder()
                .region(labRegion)
                .build();

        // Configurar el bucket
        setWebsiteConfig(s3, bucketName, indexPage, errorPage);
        s3.close(); //Cerrar S3 service client

    }



    private static void setWebsiteConfig(S3Client s3, String bucketName, String indexPage, String errorPage) throws Exception{

        try {
            // Configuración del site utilizando páginas Index y Error
            WebsiteConfiguration websiteConfig = WebsiteConfiguration.builder()
                    .indexDocument(IndexDocument.builder().suffix(indexPage).build())
                    .errorDocument(ErrorDocument.builder().key(errorPage).build())
                    .build();



            // Construir la request utilizando el nombre del bucket y la configuración del site
            PutBucketWebsiteRequest putBucketWebsiteRequest = PutBucketWebsiteRequest.builder()
                    .bucket(bucketName)
                    .websiteConfiguration(websiteConfig)
                    .build();



            // Realizar la request para aplicar la configuración al bucket
            s3.putBucketWebsite(putBucketWebsiteRequest);


            System.out.println("Configuración del sitio: ");
            GetBucketWebsiteRequest getBucketWebsiteRequest = GetBucketWebsiteRequest.builder()
                    .bucket(bucketName)
                    .build();

            GetBucketWebsiteResponse getBucketWebsiteResponse = s3.getBucketWebsite(getBucketWebsiteRequest);


            System.out.println("    Index doc: " + getBucketWebsiteResponse.indexDocument());
            System.out.println("    Error doc: " + getBucketWebsiteResponse.errorDocument());
            System.out.println("\nUtiliza este link para acceder al sitio una vez que hayas ajustado los permisos: ");
            System.out.println("    http://" + bucketName + ".s3-website-" + Region.of("us-east-1") + ".amazonaws.com");

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.out.println("Error en las rutas de index y/o error");
        }

    }
}
