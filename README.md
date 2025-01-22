# SecureChat

SecureChat is a **peer-to-peer (P2P) encrypted chat application** that allows users to send and receive messages securely using **RSA end-to-end encryption**. Each user hosts their own local MySQL database to store chat history, ensuring privacy and control over their data.

---

## Features

- **End-to-End Encryption**: Messages are encrypted using RSA encryption, ensuring only the intended recipient can read them.
- **Peer-to-Peer Communication**: No central server is required; users communicate directly with each other.
- **Local Database**: Each user stores their chat history in their own local MySQL database.
- **User-Friendly GUI**: A simple and intuitive graphical user interface for sending and receiving messages.

---

## Prerequisites

Before running SecureChat, ensure you have the following installed:

1. **Java Development Kit (JDK) 8 or later**:
   - Download and install the JDK from [here](https://www.oracle.com/java/technologies/javase-downloads.html).
   - Verify the installation by running:
     ```bash
     java -version
     javac -version
     ```

2. **MySQL Database**:
   - Install MySQL from [here](https://dev.mysql.com/downloads/installer/).
   - Set up the `ChatApp` database and `Messages` table using the provided SQL script.

3. **MySQL Connector/J**:
   - Download the MySQL Connector/J JAR file from [here](https://dev.mysql.com/downloads/connector/j/).
   - Place the JAR file in the `lib/` folder of your project.

4. **Eclipse IDE (Optional)**:
   - If you want to run the application from Eclipse, download it from [here](https://www.eclipse.org/downloads/).

---

## Setup

### 1. Set Up the Database

1. Open MySQL Command Line or MySQL Workbench.
2. Run the following SQL script to create the database and table:

   ```sql
   CREATE DATABASE IF NOT EXISTS ChatApp;

   USE ChatApp;

   CREATE TABLE IF NOT EXISTS Messages (
       id INT AUTO_INCREMENT PRIMARY KEY,
       sender VARCHAR(255) NOT NULL,
       message TEXT NOT NULL,
       is_sent BOOLEAN NOT NULL,
       timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );

3. Verify the setup by running:
   ```sql
   SHOW DATABASES;
   USE ChatApp;
   SHOW TABLES;
   DESCRIBE Messages;


### 2. Set Up the Project:

1. Clone the Repository:
  ```bash
  git clone https://github.com/your-username/SecureChat.git
  cd SecureChat
```

## Add MySQL Connector/J to the Project

1. Place the MySQL Connector/J JAR file in the `lib/` folder.
2. Add the JAR file to the project's build path in your IDE.

## Update Database Credentials

Open `DatabaseHandler.java` and update the database URL, username, and password:

```java
dbHandler.connectToDB("jdbc:mysql://localhost:3306/ChatApp", "your-username", "your-password");
```

## Running the Application

### 1. Launch the Application

1. **Run the Application**:
   - Open the project in your IDE (e.g., Eclipse).
   - Run the `ChatAppLauncher` class.

2. **Start the Server**:
   - The application will start a server on port `1234` by default.

3. **Connect to Another Peer**:
   - In the GUI, enter the IP address and port of the peer you want to connect to.
   - Click `Connect`.

### 2. Send and Receive Messages

1. **Send a Message**:
   - Type your message in the text field and click `Send`.
   - The message will be encrypted and sent to the connected peer.

2. **Receive a Message**:
   - Incoming messages will be decrypted and displayed in the chat window.

3. **View Chat History**:
   - All sent and received messages are saved in the local MySQL database.


## Troubleshooting

1. **Database Connection Issues**:
   - Ensure MySQL is running.
   - Verify the database URL, username, and password in `DatabaseHandler.java`.

2. **Network Issues**:
   - Ensure both peers are on the same network or can reach each other over the internet.
   - Use the correct IP address and port when connecting.

3. **Encryption Issues**:
   - Ensure the RSA keys are generated and exchanged correctly.
   - Verify the encryption and decryption methods in `RSAUtil.java`.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## Contact

For questions, feedback, or support, feel free to reach out:

- **Email**: [youssefnahdi95@gmail.com](mailto:youssefnahdi95@gmail.com)
- **GitHub Issues**: [Open an issue](https://github.com/sonofsparda24/SecureChat/issues)
- **Contributions**: Contributions are welcome! Please open an issue or submit a pull request.
