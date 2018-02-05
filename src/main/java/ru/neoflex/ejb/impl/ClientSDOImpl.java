package ru.neoflex.ejb.impl;

import commonj.sdo.DataObject;
import commonj.sdo.helper.XMLDocument;
import commonj.sdo.helper.XMLHelper;
import commonj.sdo.helper.XSDHelper;
import ru.neoflex.ejb.ClientSDO;
import ru.neoflex.entity.ClientInformation;

import javax.ejb.Stateless;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ClientSDOImpl implements ClientSDO {

    private String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe";
    private String jdbcUsername = "OLEG";
    private String jdbcPassword = "123456";
    private Connection jdbcConnection;

//    private String jdbcURL = "jdbc:mysql://localhost:3306/bank";
//    private String jdbcUsername = "root";
//    private String jdbcPassword = "root";
//    private Connection jdbcConnection;


    private static final String CH_MODEL = "C:\\WrkPlace\\nfbank\\cl.xsd";
    private static final String CH_NAMESPACE = "http://www.example.com/CH";
    private static final String CH_XML = "C:\\WrkPlace\\nfbank\\clll.xml";


    public void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                //   Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    public void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    @Override
    public void addClient(DataObject dataObject) {
        try {
            connect();
            DataObject cli = dataObject.getDataObject("Client");

            long id = (int) cli.get("id");
            String name = cli.get("name").toString();
            String lastname = cli.get("lastname").toString();
            String birth = cli.get("birth").toString();
            String password = cli.get("password").toString();
            long clientaccount = (int) cli.get("clientaccount");


            PreparedStatement preparedStatement = jdbcConnection.prepareStatement("INSERT INTO OLEG.CLIENT (id,name,lastname,birth,password,clientAccount) VALUES (?,?,?,?,?,?) ");
            //  PreparedStatement preparedStatement = jdbcConnection.prepareStatement("INSERT INTO clientinformation(id,name,lastname,birth,password,clientAccount) VALUES (?,?,?,?,?,?) ");
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastname);
            preparedStatement.setString(4, birth);
            preparedStatement.setString(5, password);
            preparedStatement.setLong(6, clientaccount);
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updClient(DataObject dataObject) throws IOException {
        try {
            connect();
//
            DataObject cli = dataObject.getDataObject("Client");

            long id = (int) cli.get("id");
            String name = cli.get("name").toString();
            String lastname = cli.get("lastname").toString();
            String birth = cli.get("birth").toString();
            String password = cli.get("password").toString();
            long clientaccount = (int) cli.get("clientaccount");
//Создаем хмл для проверки
//            OutputStream stream = new FileOutputStream(CH_XML);
//            XMLHelper.INSTANCE.save(cli, CH_NAMESPACE, "Client", stream);

            PreparedStatement preparedStatement = jdbcConnection.prepareStatement("UPDATE OLEG.CLIENT SET name=?,lastname=?,birth=?,password=?,clientAccount=? WHERE id=?");
            //  PreparedStatement preparedStatement = jdbcConnection.prepareStatement("UPDATE clientinformation SET name=?,lastname=?,birth=?,password=?,clientAccount=? WHERE id=?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, birth);
            preparedStatement.setString(4, password);
            preparedStatement.setLong(5, clientaccount);
            preparedStatement.setLong(6, id);
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteClient(DataObject dataObject) {
        try {
            connect();

            DataObject cli = dataObject.getDataObject("Client");
            long id = (int) cli.get("id");

            PreparedStatement preparedStatement = jdbcConnection.prepareStatement("DELETE  FROM OLEG.CLIENT WHERE id =?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<ClientInformation> getAllClient() {
        List<ClientInformation> clients = new ArrayList<>();

        try {
            connect();
            Statement statement = jdbcConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM OLEG.CLIENT");
            //  ResultSet resultSet = statement.executeQuery("SELECT * FROM clientinformation");
            while (resultSet.next()) {
                ClientInformation client = new ClientInformation();
                client.setId(resultSet.getLong("id"));
                client.setName(resultSet.getString("name"));
                client.setLastname(resultSet.getString("lastname"));
                client.setBirth(resultSet.getString("birth"));
                client.setPassword(resultSet.getString("password"));
                client.setClientAccount(resultSet.getLong("clientAccount"));
                clients.add(client);

            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return clients;
    }

    @Override
    public ClientInformation getClient(long id) {
        ClientInformation client = null;
        try {
            connect();

            PreparedStatement preparedStatement = jdbcConnection.prepareStatement("SELECT * FROM OLEG.CLIENT WHERE id = ?");
            //   PreparedStatement preparedStatement = jdbcConnection.prepareStatement("SELECT * FROM clientinformation WHERE id = ?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                String birth = resultSet.getString("birth");
                String password = resultSet.getString("password");
                long clientAccount = resultSet.getLong("clientAccount");
                client = new ClientInformation(id, name, lastname, birth, password, clientAccount);

            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

}
