package ru.neoflex.ejb;

import commonj.sdo.DataObject;
import ru.neoflex.entity.ClientInformation;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

@Local
public interface ClientManagment {

    DataObject addClient(long id, String name, String lastname, String birth, String password, long clientAccount) throws SQLException, IOException;

    DataObject updClient(String name, String lastname, String birth, String password, long clientAccount,long id) throws SQLException, IOException;

    byte[]  deleteClient(long id) throws IOException;

    void sendHistory(byte[] bytes, String action);
}
