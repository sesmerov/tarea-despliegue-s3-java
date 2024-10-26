package manejoS3;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.util.Scanner;


public class CrearBucket {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        //----- Nombre del bucket y región en la que funcionamos
        String bucketName = args[0];
        Region labRegion = Region.of(args[1]);
        /* ----------------------------------------------------------------------------
            Creación de un cliente de servicio s3: objeto para manejar s3 desde código
           ----------------------------------------------------------------------------
        */
        S3Client s3 = S3Client.builder()
                .region(labRegion)
                .build();

        //------------Comprobamos si existe el bucket, utilizamos HeadBucket

        if (!bucketExisting(s3, bucketName)) {
            createBucket(s3, bucketName);   // Creación del bucket
        }else{
            System.out.println("¿Quieres cambiar el nombre del bucket? S/N");
            String option = sc.nextLine().toUpperCase();

            while(!option.equals("S") && !option.equals("N")){
                System.out.println("Introduce S o N");
                option = sc.nextLine().toUpperCase();
            }

            if(option.equals("S")){
                System.out.println("Indica nombre del Bucket:");
                bucketName = sc.nextLine();
                createBucket(s3, bucketName);
            }else{
                System.out.println("Operación cancelada.");
            }
        }

        s3.close();  //Cerramos el cliente S3
    }

    public static boolean bucketExisting(S3Client s3, String bucketName) {
        boolean check = true;
        System.out.println("Comenzando operación con Head Bucket... ");
        try {
            /*
            Creamos un objeto HeadBucket que nos va a permitir saber si el bucket existe y tenemos permisos
            */
            HeadBucketRequest request = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            HeadBucketResponse result = s3.headBucket(request);

            if (result.sdkHttpResponse().statusCode() == 200) {
                System.out.println("    El Bucket ya existe! ");

            }
        }

        catch (AwsServiceException awsEx) {
            switch (awsEx.statusCode()) {
                case 404:
                    System.out.println("    No existe el bucket.");
                    check = false;
                    break;

                case 400 :
                    System.out.println("    Intento de acceso al bucket desde una región distinta a la del bucket.");break;

                case 403 :
                    System.out.println("    Error de permisos al intentar acceder al bucket.");break;
            }
        }
        return check;

    }
    public static void createBucket(S3Client s3Client, String bucketName) {
        System.out.format("\nCreando el bucket: %s\n\n", bucketName);
        try {
        /*--------------------------------------------------------
                           Creación de S3 waiter objects
                          -----------------------------------------
        */
            S3Waiter s3Waiter = s3Client.waiter();

        /*---------------------------------------------------------------
                            Construimos la  request para crear el bucket
                            -----------------------------------------
        */
            CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();


            s3Client.createBucket(bucketRequest);

            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            System.out.format("Esperando hasta que se cree el bucket.... ");

            // Esperará hasta que esté listo e imprime la respuesta

            WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
            waiterResponse.matched().response().ifPresent(System.out::println);
            System.out.format("    Bucket \"%s\" está listo\n",bucketName);


        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

        }catch(Exception e){
            System.err.println(e.getMessage());
        }

    }
}