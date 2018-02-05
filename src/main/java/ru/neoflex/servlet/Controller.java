package ru.neoflex.servlet;

import commonj.sdo.DataObject;
import ru.neoflex.ejb.ClientManagment;
import ru.neoflex.ejb.ClientSDO;
import ru.neoflex.ejb.impl.ClientManagmentImpl;
import ru.neoflex.ejb.impl.ClientSDOImpl;
import ru.neoflex.entity.ClientInformation;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ClientSDO clientSDO;
    @EJB
    private ClientManagment clientManagment;


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertClient(request, response);
                    break;
                case "/delete":
                    deleteClient(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateClient(request, response);
                    break;
                default:
                    listClient(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listClient(HttpServletRequest request, HttpServletResponse response)
            throws  IOException, ServletException {
        List<ClientInformation> listClient = clientSDO.getAllClient();
        request.setAttribute("listClient", listClient);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ClientList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("ClientForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws  ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        ClientInformation client = clientSDO.getClient(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ClientForm.jsp");
        request.setAttribute("client", client);
        dispatcher.forward(request, response);

    }

    private void insertClient(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        String birth = (request.getParameter("birth"));
        String password = request.getParameter("password");
        long clientAccount = Long.parseLong(request.getParameter("clientAccount"));

        DataObject dataObjectClient = clientManagment.addClient(id, name, lastname, birth, password, clientAccount);
     //   clientManagment.sendHistory(dataObjectClient,"add");
        response.sendRedirect("list");
    }

    private void updateClient(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        long id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        String birth = request.getParameter("birth");
        String password = request.getParameter("password");
        long clientAccount = Long.parseLong(request.getParameter("clientAccount"));

        DataObject dataObjectClient = clientManagment.updClient(name, lastname, birth, password, clientAccount, id);
       // clientManagment.sendHistory(dataObjectClient,"update");
        response.sendRedirect("list");
    }

    private void deleteClient(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Integer.parseInt(request.getParameter("id"));

        clientManagment.sendHistory(clientManagment.deleteClient(id),"delete");
        response.sendRedirect("list");

    }
}
