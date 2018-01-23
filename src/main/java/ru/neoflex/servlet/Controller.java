package ru.neoflex.servlet;

import ru.neoflex.ejb.ClientManagment;
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
            throws SQLException, IOException, ServletException {
        List<ClientInformation> listClient = clientManagment.getAllClient();
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
            throws SQLException, ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        ClientInformation client = clientManagment.getClient(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ClientForm.jsp");
        request.setAttribute("client", client);
        dispatcher.forward(request, response);

    }

    private void insertClient(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        String name = request.getParameter("title");
        String lastname = request.getParameter("author");
        String birth = (request.getParameter("price"));
        String password = request.getParameter("password");
        long clientAccount = Long.parseLong(request.getParameter("clientAccount"));
        ClientInformation client = new ClientInformation(name, lastname, birth, password, clientAccount);
        clientManagment.addClient(client);
        response.sendRedirect("list");
    }

    private void updateClient(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        long id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("title");
        String lastname = request.getParameter("author");
        String birth = request.getParameter("price");
        String password = request.getParameter("password");
        long clientAccount = Long.parseLong(request.getParameter("clientAccount"));

        ClientInformation client = new ClientInformation(id, name, lastname, birth, password, clientAccount);
        clientManagment.updClient(client);
        response.sendRedirect("list");
    }

    private void deleteClient(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Integer.parseInt(request.getParameter("id"));
        clientManagment.deleteClient(id);
        response.sendRedirect("list");

    }
}
