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

        try{
            Socket socketCliente = new Socket(ipDestino,puertoDestino);

            System.out.println("Conexion establecida");
            DataOutputStream dataOutputStream = new DataOutputStream(socketCliente.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socketCliente.getInputStream());
            String mensaje = "";

            while(!mensaje.equals("exit")){
                mensaje = scanner.nextLine();
                dataOutputStream.writeUTF(mensaje);
                System.out.println(dataInputStream.readUTF());
            }

        }catch (ConnectException e){
            System.err.println("Error: La conexion fue rechazada");
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
