package ru.neoflex.ejb;

import commonj.sdo.DataObject;
import ru.neoflex.entity.ClientInformation;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Local
public interface ClientSDO {
    void addClient(DataObject dataObject);

    void updClient(DataObject dataObject) throws IOException;

    void deleteClient(DataObject dataObject);

    List<ClientInformation> getAllClient();

    ClientInformation getClient(long id);
}
