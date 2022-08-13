package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class ChatFormController implements Initializable {
    public TextArea txtMessageArea;
    public TextField txtMessage;
    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;

    String messageIn="";
    String newLine=System.lineSeparator();
    public void btnSendMessage(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(txtMessage.getText().trim());
        String reply="";
        reply=txtMessage.getText();
        txtMessageArea.appendText(newLine+"Server :"+reply.trim());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(()->{
            try {

                serverSocket = new ServerSocket(5000);
                System.out.println("Server Started");
                socket=serverSocket.accept();
                System.out.println("Client Accepted!");

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while(!messageIn.equals("end")){
                    messageIn = dataInputStream.readUTF();
                    txtMessageArea.appendText(newLine+"Client :"+messageIn.trim());
                }
            } catch (IOException e) {

            }
        }).start();
    }
}
