package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientChatFormController implements Initializable {
    public TextArea txtMessageArea;
    public TextField txtMessage;
    static Socket socket = null;
    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;

    String newLine=System.lineSeparator();
    public void btnSendMessage(ActionEvent actionEvent) throws IOException {
        String reply="";
        reply=txtMessage.getText();
        dataOutputStream.writeUTF(reply);
        txtMessageArea.appendText(newLine+"Client : "+reply.trim());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("localhost", 5000);
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    String messageIn = "";

                    while (!messageIn.equals("end")) {
                        messageIn = dataInputStream.readUTF();
                        txtMessageArea.appendText(newLine+"Serve : " + messageIn.trim());

                    }
                } catch (IOException e) {

                }
            }
        }).start();
    }
}
