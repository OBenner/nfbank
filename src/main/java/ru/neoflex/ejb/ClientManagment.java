package ru.neoflex.ejb;

import ru.neoflex.entity.ClientInformation;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

@Local
public interface ClientManagment {

    void addClient(String name, String lastname, String birth, String password, long clientAccount) throws SQLException;

    void updClient(String name, String lastname, String birth, String password, long clientAccount,long id) throws SQLException;

    void deleteClient(long id);

    List<ClientInformation> getAllClient();

    ClientInformation getClient(long id);
}
