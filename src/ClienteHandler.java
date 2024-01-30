import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class ClienteHandler extends Thread {
    private DatagramSocket socket;
    private String mensaje;
    private InetAddress clienteDireccion;
    private int clientePuerto;

    public ClienteHandler(DatagramSocket socket, String mensaje, InetAddress clienteDireccion, int clientePuerto) {
        this.socket = socket;
        this.mensaje = mensaje;
        this.clienteDireccion = clienteDireccion;
        this.clientePuerto = clientePuerto;
    }

    @Override
    public void run() {
        try {
            int numero = Integer.parseInt(mensaje);
            int cuadrado = numero * numero;
            String respuesta = "El cuadrado de " + numero + " es " + cuadrado;
            byte[] sendData = respuesta.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clienteDireccion, clientePuerto);

            socket.send(sendPacket); // Envía la respuesta al cliente
        } catch (NumberFormatException e) {
            System.err.println("El cliente envió un número inválido: " + mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}