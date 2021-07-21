import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la dirección ip a conectar: ");
        String ipDestino = scanner.nextLine();
        System.out.println("Ingrese el puerto: ");
        int puertoDestino = scanner.nextInt();
        System.out.println("Conectando....");

        try{  //try y catch para controlar la ejecución del subprograma

            Socket socketCliente = new Socket(ipDestino,puertoDestino);      // objeto socket para realizar la comunicación  con la ip de destino y el
                                                                             // puerto que vamos a utilizar para la comunicación.

            System.out.println("Conexion establecida");

            DataOutputStream dataOutputStream = new DataOutputStream(socketCliente.getOutputStream()); // objeto DataOutputStream para ayudarnos a enviar datos al servidor.
            DataInputStream dataInputStream = new DataInputStream(socketCliente.getInputStream());    // objeto DataOutputStream para ayudarnos a enviar datos al servidor.
            String mensaje = "";

            while(!mensaje.equals("exit")){
                mensaje = scanner.nextLine();
                dataOutputStream.writeUTF(mensaje);                              //enviamos el mensaje al servidor
                System.out.println(dataInputStream.readUTF());                   //imprimimos por pantalla la respuesta del servidor.
            }

        }catch (ConnectException e){
            System.err.println("Error: La conexion fue rechazada");
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
