package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection())
        {
            // remove this
            try {
                try (Statement st = conn.createStatement()) {
                    st.execute("DROP TABLE IF EXISTS Clients");
                    //st.execute("CREATE TABLE Clients (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL, age INT)");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            ClientDAOImpl2 dao = new ClientDAOImpl2(conn, "Clients");
            dao.createTable(Client.class);

            Client c = new Client("test", 12);
            Client c1 = new Client("nest", 13);
            Client c2 = new Client("pest", 14);
            Client c3 = new Client("best", 15);
            dao.add(c);
            dao.add(c1);
            dao.add(c2);
            dao.add(c3);
            System.out.println();
            System.out.println(c.getId());
            System.out.println(c3.getId());
            // int id = c.getId();


            List<Client> list = dao.getAll(Client.class);
            for (Client cli : list)
                System.out.println(cli);
            System.out.println();

            List<Client> list1 = dao.getAll(Client.class, "age");
            for (Client cli : list1)
                System.out.println(cli);

            list.get(0).setAge(55);
            dao.update(list.get(0));

            /*

            List<Client> list = dao.getAll(Client.class, "name", "age");
            List<Client> list = dao.getAll(Client.class, "age");
            for (Client cli : list)
                System.out.println(cli);

             */

            dao.delete(list.get(0));
        }
    }
}
