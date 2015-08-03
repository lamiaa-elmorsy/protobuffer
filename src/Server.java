import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		// int port = Integer.parseInt(args[0]);
		int port = 3000;
		try {
			// to listen on specific port
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Waiting for client on port "
					+ serverSocket.getLocalPort() + "...");
			// this waits until the client starts up a connection
			Socket server = serverSocket.accept();
			System.out.println("Just connected to "
					+ server.getRemoteSocketAddress());

			DataOutputStream out = new DataOutputStream(server.getOutputStream()); // to client
			DataInputStream in = new DataInputStream(server.getInputStream()); // from client 
			
			//Data received from Client
			//readUTF .. to read from DataInputStream the Data Comes from client
			int id = Integer.parseInt(in.readUTF());
			System.out.println("Message Received From Person ID = "+id);
			
			String name = in.readUTF();
			System.out.println("Message Received From Person Name = "+name);
			
			String email = in.readUTF();
			System.out.println("Message Received From Person Email= "+email);

			out.writeUTF("From Server: "+email);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
