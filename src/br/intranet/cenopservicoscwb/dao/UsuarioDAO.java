package br.intranet.cenopservicoscwb.dao;;
import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
import br.com.intranet.cenopservicoscwb.model.util.Utils;
import br.intranet.cenopservicoscwb.conexao.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UsuarioDAO {
    
   public Funcionario getFuncionario(String chave) throws ClassNotFoundException, SQLException {
       
        Connection con = ConnectionFactory.conectar("calc_poupanca");
        Statement busca = con.createStatement();
        String sql = "SELECT A.*,B.cd_area,C.Quadro, spf_dados.nm_gerente_funci(A.matriculaF, '4750') AS gerente FROM arh.v_funcionarios A " +
                        "LEFT JOIN gestao_equipes.tb_uor_area B " +
                        "ON A.UOR_Posicao=B.uor " +
                        "LEFT JOIN spf_dados.tb_arh_base C " +
                        "ON A.matriculaF = C.tx_mtc_fun " +
                        "WHERE A.matriculaF=TRIM('" + chave + "')";

        ResultSet tabela = busca.executeQuery(sql);

      Funcionario funci = new Funcionario();
              

        try {
            if (tabela.next()) {
                funci.setChaveFunci(chave);
                funci.setNomeFunci(tabela.getString("nome"));
                funci.setNomeFuncao(tabela.getString("nfuncao"));
                funci.setMatriculaFunci(Integer.parseInt(Utils.tratarVariavel(chave)));
                funci.setNomeGerente(tabela.getString("gerente"));
                funci.setCodigoFuncao(tabela.getInt("funcao"));
                
            } else {
                funci.setChaveFunci(chave);
//                funci.setNomeFunci(nomeFuncionario);
//                funci.setMatriculaFunci(Integer.parseInt(Utils.tratarVariavel(chave)));

            }
            tabela.close();
        } catch (SQLException e) {
        } finally{
            try{tabela.close();} catch(SQLException e){}
            try{busca.close();} catch(SQLException e){}
            try{con.close();} catch(SQLException e){}
        }
        
        
              
    

//    funci.setChaveFunci("F5078775");
//    funci.setNomeFunci("funci teste nao continuar");
//    funci.setCodigoFuncao(7001);
//    funci.setMatriculaFunci(5078775);
//    funci.setNomeFuncao("ANALISTA");
//    funci.setNomeGerente("CRISTIANO");

    return funci;
    }
}
