package manejoS3;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;


import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class UploadObject {
    public static void main(String[] args) throws Exception {

        // Inputs de lectura: - nombre del bucket y región

        String bucketName = args[0];
        Region labRegion = Region.of(args[1]);
        String objectKey = args[2];
        String filePath = args[3];

        // Creamos el cliente del servicio s3
        S3Client s3 = S3Client.builder()
                .region(labRegion)
                .build();

        // Subimos el fichero con datos iniciales
        putS3Object(s3, bucketName, objectKey, filePath);

        s3.close(); //Cerramos el cliente del servicio
    }



    private static void putS3Object(S3Client s3, String bucketName, String objectKey, String filePath) {

        try {
        /*---------------------------------------------------------
                           Asignación de los metadatos habituales
                           ----------------------------------------
        */
            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal2", "datos de prueba");

        /*---------------------------------------------------------------------------------
                  Creación de la request utilizando el nombre del bucket, clave y metadatos
                  --------------------------------------------------------------------------
        */
            PutObjectRequest putObject = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .metadata(metadata)
                    .build();

            System.out.format("\n Subiendo fichero desde: \"%s\"", new File(filePath).getAbsolutePath());



            // Procesamos la request utilizando el archivo a subir
            PutObjectResponse response = s3.putObject(putObject,
                    RequestBody.fromFile(Paths.get(filePath)));

            System.out.format("\n\n Subida completada.\n     Tag: %s \n", response.eTag());

        } catch (S3Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }
}
