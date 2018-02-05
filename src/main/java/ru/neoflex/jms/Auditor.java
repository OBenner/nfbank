package ru.neoflex.jms;

import commonj.sdo.DataObject;
import ru.neoflex.ejb.ClientManagment;
import ru.neoflex.ejb.ClientSDO;
import ru.neoflex.entity.ClientInformation;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;


@MessageDriven(mappedName = "jms/Queue")
public class Auditor implements MessageListener {
    @EJB
    private ClientSDO clientSDO;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage messageSdo = (ObjectMessage) message;

            String action = messageSdo.getStringProperty("action");

            switch (action) {
                case "add":
                    clientSDO.addClient((DataObject) messageSdo.getObject());
                    break;
                case "update":
                    clientSDO.updClient((DataObject) messageSdo.getObject());
                    break;
                case "delete":
                    clientSDO.deleteClient((DataObject) messageSdo.getObject());
                    break;
                default:
                    System.out.println("Exception");
                    break;
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
