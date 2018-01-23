package ru.neoflex.ejb;

import ru.neoflex.entity.ClientInformation;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

@Local
public interface ClientManagment {

    void addClient(ClientInformation client) throws SQLException;

    void updClient(ClientInformation client) throws SQLException;

    void deleteClient(long id);

    List<ClientInformation> getAllClient();

    ClientInformation getClient(long id);
}
