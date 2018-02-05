//package ru.neoflex.webService.impl;
//
//import ru.neoflex.ejb.ClientManagment;
//import ru.neoflex.entity.ClientInformation;
//import ru.neoflex.webService.ServiceSOAP;
//
//import javax.annotation.PostConstruct;
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.jws.WebService;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import java.sql.SQLException;
//import java.util.List;
//
//@WebService(endpointInterface = "ru.neoflex.webService.ServiceSOAP")
//@Stateless
//public class ServiceSOAPImpl implements ServiceSOAP {
//
//    @EJB
//    private ClientManagment client;
//
////    @PostConstruct
////    private void initClientManager() {
////        try {
////            client = (ClientManagment)
////                    new InitialContext().lookup("ClientManagment");
////        } catch (NamingException e) {
////
////        }
////    }
//
//    @Override
//    public void addClient(String name, String lastname, String birth, String password, long clientAccount) throws SQLException {
//
//    }
//
//    @Override
//    public void updClient(String name, String lastname, String birth, String password, long clientAccount, long id) throws SQLException {
//
//    }
//
//    @Override
//    public void deleteClient(long id) {
//        client.deleteClient(id);
//    }
//
//    @Override
//    public List<ClientInformation> getAllClient() {
//        return client.getAllClient();
//    }
//
//    @Override
//    public ClientInformation getClient(long id) {
//        return client.getClient(id);
//    }
//}
