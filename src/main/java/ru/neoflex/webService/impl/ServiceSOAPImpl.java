package ru.neoflex.webService.impl;

import commonj.sdo.DataObject;
import ru.neoflex.ejb.ClientManagment;
import ru.neoflex.ejb.ClientSDO;
import ru.neoflex.entity.ClientInformation;
import ru.neoflex.webService.ServiceSOAP;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebService(endpointInterface = "ru.neoflex.webService.ServiceSOAP")
public class ServiceSOAPImpl implements ServiceSOAP {

    @EJB
    private ClientSDO client;
    @EJB
    private ClientManagment clientManagment;

    @PostConstruct
    private void initClientManager() {
        try {
            client = (ClientSDO) new InitialContext().lookup("ClientSDO");
            clientManagment = (ClientManagment) new InitialContext().lookup("ClientManagment");
        } catch (NamingException e) {

        }
    }

    @Override
    public boolean addClient(long id, String name, String lastname, String birth, String password, long clientAccount) {
        DataObject dataObjectClient = null;
        try {
            dataObjectClient = clientManagment.addClient(id, name, lastname, birth, password, clientAccount);
            clientManagment.sendHistory(dataObjectClient, "add");
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean updClient(long id, String name, String lastname, String birth, String password, long clientAccount)  {
        DataObject dataObjectClient = null;
        try {
            dataObjectClient = clientManagment.addClient(id, name, lastname, birth, password, clientAccount);
            clientManagment.sendHistory(dataObjectClient, "update");
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteClient(long id)  {
        DataObject dataObjectClient = null;
        try {
            dataObjectClient = clientManagment.deleteClient(id);
            clientManagment.sendHistory(dataObjectClient, "delete");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<ClientInformation> getAllClient() {
        return client.getAllClient();
    }

    @Override
    public ClientInformation getClient(long id) {
        return client.getClient(id);
    }
}
