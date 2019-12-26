/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.intranet.cenopservicoscwb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author f3295813
 */
public class ConsultaSQL {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void inserir(String npj, String matricula, String tipoSumula) throws Throwable {

//        Conector con = new Conector();
//        String sql = "INSERT INTO subsidio_cartao.tb_subsidiocartao_log (  npj, cpf, inscricao_pasep, txt_tipoSumula, tx_mtc_fun, data) "
//                + " VALUES ('" + npj + "','" + tipoSumula + "', '" + matricula + "', NOW());";
//
//        try {
//
//            PreparedStatement stInsert = con.conectar().prepareStatement(sql);
//
//            try {
//
//                stInsert.execute(sql);
//
//            } catch (SQLException e) {
//                System.out.println(e);
//            }
//
//            stInsert.close();
//            con.fechar();
//
//        } catch (SQLException e) {
//            System.out.println(e);
//        }

    }

    public boolean confirmaVersao(int codigoFerramenta, String versao) throws Throwable {

        Conector con = new Conector();

        String versaoBanco = "";

        String sqlBuscaVesao = "select tb_ctl_vrs.nm_vrs, tb_app.nm_app from controle_vers.tb_ctl_vrs left join controle_vers.tb_app "
                + "on  tb_ctl_vrs.fk_cd_app = tb_app.cd_app where tb_app.cd_app= ' " + codigoFerramenta +  " '  order by dt_vrs desc limit 1 ";

        java.sql.Statement stm = con.conectarVerificarVersao().createStatement();

        try {
            ResultSet rs = stm.executeQuery(sqlBuscaVesao);

          if(rs != null && rs.next()){   
            versaoBanco = rs.getString("nm_vrs");
          }
        } catch (SQLException e) {
            System.out.println(e);
        }

        if (versaoBanco.equals(versao)) {

            con.fechar();

            return true;

        } else {

            con.fechar();

            return false;

        }

    }

}
