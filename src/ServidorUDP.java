import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServidorUDP {
    public static void main(String[] args) {
        final int puerto = 12345; // Puerto en el que escuchará el servidor
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(puerto);
            System.out.println("Servidor esperando conexiones en el puerto " + puerto + "...");

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                socket.receive(receivePacket); // Espera a recibir un paquete del cliente

                String mensaje = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetAddress clienteDireccion = receivePacket.getAddress();
                int clientePuerto = receivePacket.getPort();

                // Nuevo hilo para manejar la solicitud del cliente
                Thread clienteThread = new ClienteHandler(socket, mensaje, clienteDireccion, clientePuerto);
                clienteThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close(); // Cierra el socket del servidor cuando decidas detener el servidor
            }
        }
    }
}
