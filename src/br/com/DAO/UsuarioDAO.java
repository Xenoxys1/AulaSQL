package br.com.DAO;

import br.com.DTO.UsuarioDTO;
import br.com.Views.TelaPrincipal;
import br.com.Views.TelaUsuarios;
import java.sql.*;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void inserirUsuario(UsuarioDTO objUsuarioDTO) {
        String sql = "insert into tb_usuarios(id_usuario, nomeUsuario, loginUsuario, senhaUsuario) values(?, ?, ?, ?)";
        conexao = new ConexaoDAO().conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_usuario());
            pst.setString(2, objUsuarioDTO.getNomeUsuario());
            pst.setString(3, objUsuarioDTO.getLoginUsuario());
            pst.setString(4, objUsuarioDTO.getSenhaUsuario());

            int res = pst.executeUpdate();
            if (res == 1) {
                JOptionPane.showMessageDialog(null, "Cadastro feito com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar.");
            }
            pst.close();

        } catch (Exception e) {
            if (e.getMessage().contains("tb_usuarios.PRIMARY")) {
                JOptionPane.showMessageDialog(null, "ID já está em uso.");
            } else if (e.getMessage().contains("tb_usuarios.loginUsuario_UNIQUE")) {
                JOptionPane.showMessageDialog(null, "Login já está em uso.");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao inserir usuário: " + e);
            }
        }

    }

    public void pesquisar(UsuarioDTO objUsuarioDTO) {
        String sql = "select * from tb_usuarios where id_usuario = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_usuario());
            rs = pst.executeQuery();
            if (rs.next()) {
                TelaUsuarios.txtNomeUsuario.setText(rs.getString(2));
                TelaUsuarios.txtLoginUsuario.setText(rs.getString(3));
                TelaUsuarios.txtSenhaUsuario.setText(rs.getString(4));
                conexao.close();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado.");
                limpar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Método Pesquisar: " + e);
            limpar();
        }
    }

    public void logar(UsuarioDTO objUsuarioDTO) {
        String sql = "select * from tb_usuarios where loginUsuario = ? and senhaUsuario = ?";
        conexao = new ConexaoDAO().conector();

        try {
            pst = conexao.prepareStatement(sql);
            //pst.setString(1, txtUsuario.getText());
            //pst.setString(2, txtSenha.getText());
            pst.setString(1, objUsuarioDTO.getLoginUsuario());
            pst.setString(2, objUsuarioDTO.getSenhaUsuario());

            rs = pst.executeQuery();

            if (rs.next()) {
                TelaPrincipal principal = new TelaPrincipal();
                principal.setVisible(true);
                conexao.close();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválidos");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Método Login: " + e);
        }
    }

    public void editar(UsuarioDTO objUsuarioDTO) {
        String sql = "update tb_usuarios set nomeUsuario = ?, loginUsuario = ?, senhaUsuario = ? where id_usuario = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(4, objUsuarioDTO.getId_usuario());
            pst.setString(1, objUsuarioDTO.getNomeUsuario());
            pst.setString(2, objUsuarioDTO.getLoginUsuario());
            pst.setString(3, objUsuarioDTO.getSenhaUsuario());

            int add = pst.executeUpdate();
            if (add > 0) {
                conexao.close();
                JOptionPane.showMessageDialog(null, "Usuário editado com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não existe.");
                conexao.close();
                limpar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Método Editar: " + e);
        }
    }

    public void apagar(UsuarioDTO objUsuarioDTO) {
        String sql = "delete from tb_usuarios where id_usuario = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_usuario());
            int add = pst.executeUpdate();
            if (add > 0) {
                conexao.close();
                JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso.");
                limpar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Método Apagar: " + e);
        }
    }

    public void limpar() {
        TelaUsuarios.txtIdUsuario.setText(null);
        TelaUsuarios.txtNomeUsuario.setText(null);
        TelaUsuarios.txtLoginUsuario.setText(null);
        TelaUsuarios.txtSenhaUsuario.setText(null);
    }

}
