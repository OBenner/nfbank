package ru.neoflex.ejb;

import commonj.sdo.DataObject;
import ru.neoflex.entity.ClientInformation;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

@Local
public interface ClientSDO {
    void addClient(DataObject dataObject);

    void updClient(DataObject dataObject) ;

    void deleteClient(byte[] bytes,String action);

    List<ClientInformation> getAllClient();

    ClientInformation getClient(long id);
}
