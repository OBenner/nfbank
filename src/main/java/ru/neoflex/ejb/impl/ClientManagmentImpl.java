package ru.neoflex.ejb.impl;

import ru.neoflex.ejb.ClientManagment;
import ru.neoflex.entity.ClientInformation;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ClientManagmentImpl implements ClientManagment {
    private String jdbcURL = "jdbc:mysql://localhost:3306/bank";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";
    private Connection jdbcConnection;

    public void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
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
    public void addClient(ClientInformation client)   {
        try {
        connect();


        PreparedStatement preparedStatement = jdbcConnection.prepareStatement("INSERT INTO clientinformation (name,lastname,birth,password,clientAccount) VALUES (?,?,?,?,?) ");
        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getLastname());
        preparedStatement.setString(3, client.getBirth());
        preparedStatement.setString(4, client.getPassword());
        preparedStatement.setLong(5, client.getClientAccount());
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
    public void updClient(ClientInformation client) {
        try {
            connect();

            PreparedStatement preparedStatement = jdbcConnection.prepareStatement("UPDATE clientinformation SET name=?,lastname=?,birth=?,password=?,clientAccount=? WHERE id=?");
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getLastname());
            preparedStatement.setString(3, client.getBirth());
            preparedStatement.setString(4, client.getPassword());
            preparedStatement.setLong(5, client.getClientAccount());
            preparedStatement.setLong(6, client.getId());
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
    public void deleteClient(long id) {
        try {
            connect();
            PreparedStatement preparedStatement = jdbcConnection.prepareStatement("DELETE  FROM clientinformation WHERE id =?");
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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM clientinformation");
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

            PreparedStatement preparedStatement = jdbcConnection.prepareStatement("SELECT * FROM clientinformation WHERE id = ?");
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
