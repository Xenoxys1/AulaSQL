package br.com.DAO;

import java.sql.*;
import javax.swing.JOptionPane;

public class ConexaoDAO {

    public static Connection conector() {
        java.sql.Connection conexao = null;

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bdprojetoaula";
        String user = "root";
        String password = "arthur12";

        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Conexao falhou: " + e.getMessage());
            return null;
        }

    }

}
