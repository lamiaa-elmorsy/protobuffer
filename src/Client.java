import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.lab.protobuf.Message.AddressBook;
import com.lab.protobuf.Message.Person;

public class Client {
	// This function fills in a Person message based on user input.
	static Person PromptForAddress(BufferedReader stdIn, DataOutputStream out)
			throws IOException {

		Person.Builder person = Person.newBuilder();

		System.out.println("Enter Person ID: ");
		person.setId(Integer.parseInt(stdIn.readLine()));
		// Send ID To server
		out.writeUTF("" + person.getId());

		System.out.println("Enter Person Name: ");
		person.setName(stdIn.readLine());
		// Send Name To server
		out.writeUTF(person.getName());

		System.out.println("Enter Person Email (blank for none): ");
		String email = stdIn.readLine();
		if (email.length() > 0) {
			person.setEmail(email);
			// Send Emailto Server
			out.writeUTF(person.getEmail());
		}
		return person.build();
	}

	public static void main(String[] args) {
		// String hostname = args[0];
		// int port = Integer.parseInt(args[1]);

		String hostname = "localhost";
		int port = 3000;
		
		Person.Builder person = Person.newBuilder();
		AddressBook.Builder addressbook = AddressBook.newBuilder();

		try {
			Socket client = new Socket(hostname, port);
			System.out.println("Just connected to "
					+ client.getRemoteSocketAddress());

			// Client output => to server
			DataOutputStream out = new DataOutputStream(
					client.getOutputStream());
			// Client input => from server
			DataInputStream in = new DataInputStream(client.getInputStream());
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));

			addressbook.addPerson(PromptForAddress(stdIn, out));

			System.out.println("Thanks message has been sent to server");
			System.out.println("-----------");
			System.out.println(in.readUTF());
			
			System.out.println("List of people in address book");
			System.out.println(addressbook.getPersonList());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/* Note */
// PrintWriter doesn't work with me
