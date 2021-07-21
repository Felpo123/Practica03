import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Servidor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el puerto a escuchar: ");
        int puerto = scanner.nextInt();                        //obtenemos el puerto a escuchar.


        try {                                                              //try y catch para controlar la ejecución del subprograma
            ServerSocket serverSocket = new ServerSocket(puerto);          // declaramos un objeto ServerSocket para realizar la comunicación

            Socket cliente = serverSocket.accept(); //El servidor espera que se conecte un cliente

            System.out.println("Se conecto un Cliente");

            DataInputStream dataInputStream = new DataInputStream(cliente.getInputStream());     //  objeto DataInputStream para ayudarnos a leer los datos del cliente
            DataOutputStream dataOutputStream = new DataOutputStream(cliente.getOutputStream());  // objeto DataoutputStream para ayudarnos a enviar datos al cliente

            String mensajeCliente = "";  //String para almacenar lo enviado por el cliente.



            while (!mensajeCliente.equals("exit")) {

                mensajeCliente = dataInputStream.readUTF();      // leemos los datos el cliente en formato UTF-8 modificado.

                switch (mensajeCliente){                         //manejamos el mensaje del cliente
                    case "1":
                        File file = new File("src/main/java/archivosServidor");   //creamos la instancia al directorio archivosServidor.

                        System.out.println(mostrarContenido(file));

                        dataOutputStream.writeUTF(mostrarContenido(file));                //Enviamos al cliente lo que entrega el metodo.
                        break;
                    case "2":
                        file = new File("src/main/java/archivosServidor");       //hacemos referencia al archivosServidor.

                        dataOutputStream.writeUTF("Ingrese el nombre del archivo a duplicar:");

                        String respuesta = dataInputStream.readUTF();

                        copiarArchivo(file,respuesta);

                        System.out.println("Se Duplico:" +respuesta);
                        dataOutputStream.writeUTF("Se Duplico:" +respuesta +" con exito");

                        break;
                    case "3":
                        file = new File("src/main/java/archivosServidor");

                        dataOutputStream.writeUTF("Ingrese el nombre del archivo a eliminar");
                        respuesta = dataInputStream.readUTF();

                        File archivo = new File(file, respuesta);
                        archivo.delete();                                            //Eliminamos el archivo  indicado por este nombre de ruta abstracto.

                        System.out.println("Se Borro:"+respuesta);
                        dataOutputStream.writeUTF("Se Borro: "+respuesta + " con exito");
                        break;
                    default: dataOutputStream.writeUTF("Que deseas realizar? \n 1.Ver Listado de Archivos \n 2. Duplicar Archivo \n 3. Eliminar Archivos");
                    break;
                }
            }
            dataInputStream.close();
            dataOutputStream.close();
            cliente.close();

        }catch (IOException e){
            e.printStackTrace();}
    }

    public static String mostrarContenido(File file) {
        String[] contenidoDirectorio = file.list();               // String[] que guarda los archivos  en el directorio indicado por este nombre de ruta abstracto.
        String archivos = "";
        for (int i = 0; i < contenidoDirectorio.length; i++)      //  guardamos  el contenidoDirectorio en una String.
            archivos = archivos + "\n" + contenidoDirectorio[i];
        return archivos;
    }

    public static void copiarArchivo(File filePadre, String respuesta )throws IOException {

        File archivo= new File(filePadre, respuesta);                      //instancia del archivo que se desea copiar
        File archivoCopia = new File(filePadre,respuesta+"-copy");    //instancia del nuevo archivo.

        InputStream inputStream = new FileInputStream(archivo);            //abrimos una conexion con el archivo a copiar
        OutputStream  outputStream = new FileOutputStream(archivoCopia);   //creamos una secuencia de salida de archivo para escribir en el archivoCopia

        byte[] buffer = new byte[1024];   //arreglo de byte que nos va ayudar a guardar el contenido de archivo

        int length;
        while ((length = inputStream.read(buffer)) > 0) {   //con la ayuda de un ciclo while, al mismo tiempo que leemos,
            outputStream.write(buffer, 0, length);      // escribimos el contenido usando el método write de FileOutputStream
        }
        inputStream.close();
        outputStream.close();
    }
}