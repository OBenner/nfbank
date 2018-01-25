package ru.neoflex.webService;

import ru.neoflex.entity.ClientInformation;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.sql.SQLException;
import java.util.List;

@WebService
public interface ServiceSOAP {

    @WebMethod
    void addClient(String name, String lastname, String birth, String password, long clientAccount) throws SQLException;

    @WebMethod
    void updClient(String name, String lastname, String birth, String password, long clientAccount,long id) throws SQLException;

    @WebMethod
    void deleteClient(long id);

    @WebMethod
    List<ClientInformation> getAllClient();

    @WebMethod
    ClientInformation getClient(long id);
}
