/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.intranet.cenopservicoscwb.dao;

import br.intranet.cenopservicoscwb.conexao.ConnectionFactory;
import br.intranet.cenopservicoscwb.model.entidades.Pessoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author f5078775
 */
public class PessoaDAO {
    
    String bancoDados = "teste_javafx";
    
    public List<Pessoa> buscar() {
        Pessoa  pessoa = null;
        List<Pessoa> pessoas = new ArrayList<>();
        try {
            Connection con = ConnectionFactory.conectar(bancoDados);
            String sql = "select * from teste_javafx.pessoa";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
           

            while (rs.next()) {

                 pessoa = new Pessoa();

                pessoa.setIdade(rs.getInt("idade"));
                pessoa.setNome(rs.getString("nome"));
               

                pessoas.add(pessoa);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }
         return pessoas;  
      }

    
    
    
}
