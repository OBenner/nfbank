package ru.neoflex.ejb.impl;

import commonj.sdo.DataObject;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.HelperContext;
import commonj.sdo.helper.XMLHelper;
import commonj.sdo.helper.XSDHelper;
import ru.neoflex.ejb.ClientManagment;
import ru.neoflex.ejb.ClientSDO;
import ru.neoflex.entity.ClientInformation;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.*;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ClientManagmentImpl implements ClientManagment {
    private static final String CH_MODEL = "C:\\WrkPlace\\nfbank\\cl.xsd";
    private static final String CH_NAMESPACE = "http://www.example.com/CH";
    private static final String CH_XML = "C:\\WrkPlace\\nfbank\\cll.xml";

    @EJB
   private ClientSDO clientSDO;

    @Resource(name = "jms/crud")
    private ConnectionFactory connectionFactory;
    @Resource(name = "jms/Queue")
    private Destination destination;



    @Override
    public DataObject addClient(long id, String name, String lastname, String birth, String password, long clientAccount) throws IOException {
        FileInputStream fis = new FileInputStream(CH_MODEL);
        XSDHelper.INSTANCE.define(fis, null);
        fis.close();
        DataObject cll =
                DataFactory.INSTANCE.create(CH_NAMESPACE, "Client");

        DataObject client = cll.createDataObject("Client");
        client.setString("action", "add");
        client.setString("id", String.valueOf(id));
        client.set("name", name);
        client.set("lastname",lastname);
        client.set("birth", birth);
        client.set("password", password);
        client.setString("clientaccount", String.valueOf(clientAccount));

//        OutputStream stream = new FileOutputStream(CH_XML);
//        XMLHelper.INSTANCE.save(cll, CH_NAMESPACE, "Client", stream);

        return cll;
    }

    @Override
    public DataObject updClient(String name, String lastname, String birth, String password, long clientAccount, long id) throws SQLException, IOException {
        FileInputStream fis = new FileInputStream(CH_MODEL);
        XSDHelper.INSTANCE.define(fis, null);
        fis.close();
        DataObject cll =
                DataFactory.INSTANCE.create(CH_NAMESPACE, "Client");

        DataObject client = cll.createDataObject("Client");
        client.setString("action", "upd");
        client.setString("id", String.valueOf(id));
        client.set("name", name);
        client.set("lastname",lastname);
        client.set("birth", birth);
        client.set("password", password);
        client.setString("clientaccount", String.valueOf(clientAccount));


//        OutputStream stream = new FileOutputStream(CH_XML);
//        XMLHelper.INSTANCE.save(cll, CH_NAMESPACE, "Client", stream);
        return cll;
    }

    @Override
    public  DataObject  deleteClient(long id) throws IOException {
        ClientInformation clientInformation = clientSDO.getClient(id);

        FileInputStream fis = new FileInputStream(CH_MODEL);
        XSDHelper.INSTANCE.define(fis, null);
        fis.close();
        DataObject cll =
                DataFactory.INSTANCE.create(CH_NAMESPACE, "Client");

        DataObject client = cll.createDataObject("Client");
        client.setString("action", "delete");
        client.setString("id", String.valueOf(clientInformation.getId()));
        client.set("name", clientInformation.getName());
        client.set("lastname",clientInformation.getLastname());
        client.set("birth", clientInformation.getBirth());
        client.set("password", clientInformation.getPassword());
        client.setString("clientaccount", String.valueOf(clientInformation.getClientAccount()));

//       OutputStream stream = new FileOutputStream(CH_XML);
//        XMLHelper.INSTANCE.save(cll, CH_NAMESPACE, "Client", stream);

        return cll;

    }


    public void sendHistory(DataObject client, String action)  {
        try {


            javax.jms.Connection connectionJMS = connectionFactory.createConnection();
            Session session = connectionJMS.createSession(true, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);

            ObjectMessage message = session.createObjectMessage();
            message.setObject(client);
            message.setStringProperty("action",action);
            connectionJMS.start();
           producer.send( message);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
