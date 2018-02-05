package ru.neoflex.jms;

import commonj.sdo.DataObject;
import ru.neoflex.ejb.ClientManagment;
import ru.neoflex.ejb.ClientSDO;
import ru.neoflex.entity.ClientInformation;

import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;
import java.io.ByteArrayInputStream;


@MessageDriven(mappedName = "jms/Queue")
public class Auditor implements MessageListener {
    @EJB
    private ClientSDO clientSDO;

    @Override
    public void onMessage(Message message) {
        try {
         //   ObjectMessage messageSdo = (ObjectMessage) message;
            BytesMessage messageSdo = (BytesMessage)message;
            byte b[] = new byte[(int) messageSdo.getBodyLength()];
            messageSdo.readBytes(b);

            String action = messageSdo.getStringProperty("action");
            switch (action) {
                case "add":
                    clientSDO.addClient((DataObject) messageSdo);
                    break;
                case "update":
                    clientSDO.updClient((DataObject) messageSdo);
                    break;
                case "delete":
                    clientSDO.deleteClient(b,action);
                    break;
                default:
                    System.out.println("Exception");
                    break;
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }


}
