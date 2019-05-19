package controller;

// Java implementation for a client 
// Save file as Client.java 

import java.io.*;
import java.net.*;
import java.util.Scanner;

// Client class 
public class Client {

	public DataInputStream dis;
	public DataOutputStream dos;
	public Scanner scn;
	public Socket s = null;

	public Client() throws IOException {
		this.scn = new Scanner(System.in);
		
		// getting localhost ip
		InetAddress ip = InetAddress.getByName("localhost");

		// establish the connection with server port 5056
		try {
			this.s = new Socket(ip, 5056);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// obtaining input and out streams
		this.dis = new DataInputStream(this.s.getInputStream());
		this.dos = new DataOutputStream(this.s.getOutputStream());
	}

	public static void main(String[] args) throws IOException {
		try {
			Client client = new Client();

			// the following loop performs the exchange of
			// information between client and client handler
			while (true) {
				System.out.println(client.dis.readUTF());
				String tosend = client.scn.nextLine();
				client.dos.writeUTF(tosend);

				// If client sends exit,close this connection
				// and then break from the while loop
				if (tosend.equals("Exit")) {
					System.out.println("Closing this connection : " + client.s);
					client.s.close();
					System.out.println("Connection closed");
					break;
				}

				// printing date or time as requested by client
				String received = client.dis.readUTF();
				System.out.println(received);
			}

			// closing resources
			client.scn.close();
			client.dis.close();
			client.dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}