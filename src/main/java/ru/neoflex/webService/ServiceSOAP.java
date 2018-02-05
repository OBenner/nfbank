package ru.neoflex.webService;

import ru.neoflex.entity.ClientInformation;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebService
public interface ServiceSOAP {

    @WebMethod
    boolean addClient(long id,String name, String lastname, String birth, String password, long clientAccount) ;

    @WebMethod
    boolean updClient(long id,String name, String lastname, String birth, String password, long clientAccount);

    @WebMethod
    boolean deleteClient(long id) throws IOException;

    @WebMethod
    List<ClientInformation> getAllClient();

    @WebMethod
    ClientInformation getClient(long id);
}
