package manejoS3;


import software.amazon.awssdk.regions.Region;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

    Scanner sc = new Scanner(System.in);
    boolean salir = false;
    Region labRegion = Region.of("us-east-1"); // Region por defecto

    while(!salir){
        System.out.println("\n MENU DE CREACION DE SITIO WEB ESTATICO - S3");
        System.out.println("1. Crear Bucket");
        System.out.println("2. Deshabilitar bloqueo de acceso publico.");
        System.out.println("3. Subir fichero");
        System.out.println("4. Descargar fichero");
        System.out.println("5. Permitir lectura publica del bucket (Politica GetObject) (NECESARIO DESHABILITAR BLOQUEO DE ACCESO PUBLICO PREVIAMENTE)");
        System.out.println("6. Indicar index/error para sitio web estático");
        System.out.println("7. Salir");
        System.out.println("Indica una de las opciones:");
        String opcion = sc.nextLine();

        switch(opcion){
            case "1":
                System.out.println("Indica nombre del Bucket:");
                String bucketNameCreate = sc.nextLine();
                String[] argumentosCreate = { bucketNameCreate, labRegion.toString()}; // Meto los argumentos en un array para pasarselos al main de crear bucket

                CrearBucket.main(argumentosCreate);
                break;
                case "2":
                System.out.println("Indica nombre del Bucket para deshabilitar bloqueo de acceso publico (DEBE EXISTIR):");
                String bucketNameBlock = sc.nextLine();
                String[] argumentosBlock = {bucketNameBlock,labRegion.toString()};

                DisableBlockPublicAccess.main(argumentosBlock);
                break;
            case "3":
                System.out.println("Indica nombre del Bucket para CARGAR (DEBE EXISTIR):");
                String bucketNameUpload = sc.nextLine();
                System.out.println("Indica nombre que tendrá el objeto a CARGAR:");
                String objectKeyUpload = sc.nextLine();
                System.out.println("Indica ruta del fichero a CARGAR + nombre (Ej. C:\\Users\\User\\OneDrive\\Fotos\\photo.png)");
                String filePath = sc.nextLine();
                String[] argumentosUpload =  {bucketNameUpload, labRegion.toString(), objectKeyUpload, filePath};

                UploadObject.main(argumentosUpload);
                break;
            case "4":
                System.out.println("Indica nombre del Bucket para DESCARGAR (DEBE EXISTIR):");
                String bucketNameGet = sc.nextLine();
                System.out.println("Indica nombre del objeto a DESCARGAR (DEBE EXISTIR):");
                String objectKeyGet = sc.nextLine();
                System.out.println("Indica ruta donde DESCARGAR:");
                String fileRouteGet = sc.nextLine();
                System.out.println("Indica nombre con el que descargar DESCARGAR:");
                String fileNameGet = sc.nextLine();

                String[] argumentosGet = {bucketNameGet,labRegion.toString(),objectKeyGet,fileRouteGet,fileNameGet};

                GetObject.main(argumentosGet);
                break;
            case "5":
                System.out.println("Indica nombre del bucket para aplicar politica GetObject:");
                String bucketNamePolicy = sc.nextLine();
                String[] argumentosPolicy = {bucketNamePolicy,labRegion.toString()};

                SetBucketPolicy.main(argumentosPolicy);
                break;
                case "6":
                System.out.println("Indica nombre del Bucket para configurar INDEX y ERROR (DEBE EXISTIR):");
                String bucketNameHost = sc.nextLine();
                System.out.println("Indica ruta de INDEX:");
                String indexPage = sc.nextLine();
                System.out.println("Indica ruta de ERROR:");
                String errorPage = sc.nextLine();
                String[] argumentosHost = {bucketNameHost,labRegion.toString(),indexPage,errorPage};

                hostS3Website.main(argumentosHost);
                break;
            case "7":
                salir=true;
                System.out.println("PROGRAMA FINALIZADO");
                break;
            default:
                System.out.println("Solo números entre 1 y 7");
        }
    }
}

}
