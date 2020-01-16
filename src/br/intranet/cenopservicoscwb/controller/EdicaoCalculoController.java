/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.intranet.cenopservicoscwb.controller;

import br.com.intranet.cenopservicoscwb.model.calculo.MotorCalculoPoupanca;
import br.com.intranet.cenopservicoscwb.model.entidade.Arquivo;
import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
import br.com.intranet.cenopservicoscwb.model.entidade.CalculoPcond;
import br.com.intranet.cenopservicoscwb.model.entidade.Cliente;
import br.com.intranet.cenopservicoscwb.model.entidade.Expurgo;
import br.com.intranet.cenopservicoscwb.model.entidade.Funcionario;
import br.com.intranet.cenopservicoscwb.model.entidade.Honorario;
import br.com.intranet.cenopservicoscwb.model.entidade.Indice;
import br.com.intranet.cenopservicoscwb.model.entidade.JuroRemuneratorio;
import br.com.intranet.cenopservicoscwb.model.entidade.Metodologia;
import br.com.intranet.cenopservicoscwb.model.entidade.Mora;
import br.com.intranet.cenopservicoscwb.model.entidade.Multa;
import br.com.intranet.cenopservicoscwb.model.entidade.Npj;
import br.com.intranet.cenopservicoscwb.model.entidade.PeriodoCalculo;
import br.com.intranet.cenopservicoscwb.model.entidade.PlanoEconomico;
import br.com.intranet.cenopservicoscwb.model.entidade.ProtocoloGsv;
import br.intranet.cenopservicoscwb.dao.CalculoDAO;
import br.intranet.cenopservicoscwb.dao.CalculoPcondDAO;
import br.intranet.cenopservicoscwb.dao.ClienteDAO;
import br.intranet.cenopservicoscwb.dao.ExpurgoDAO;
import br.intranet.cenopservicoscwb.dao.FuncionarioDAO;
import br.intranet.cenopservicoscwb.dao.IndiceDAO;
import br.intranet.cenopservicoscwb.dao.MetodologiaDAO;
import br.intranet.cenopservicoscwb.dao.NpjDAO;
import br.intranet.cenopservicoscwb.dao.PlanoEconomicoDAO;
import br.intranet.cenopservicoscwb.dao.ProtocoloGsvDAO;
import br.intranet.cenopservicoscwb.util.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import main.MainApp;

/**
 * FXML Controller class
 *
 * @author f5078775
 */
public class EdicaoCalculoController extends  AbstractController implements Initializable {

   
    private CalculoPcond calculoPcond;
    private Indice indice;
    private Cliente cliente;
    private ClienteDAO clienteDAO;
    private Npj npj;
    private Calculo calculo;
    private Metodologia metodologia;
    private Expurgo expurgo;
    private MetodologiaDAO<Metodologia, Object> metodologiaDAO;
    private ExpurgoDAO<Expurgo, Object> expurgoDAO;
    private PlanoEconomico planoEconomico;
    private PlanoEconomicoDAO<PlanoEconomico, Object> planoEconomicoDAO;
    private NpjDAO<Npj,Object> npjDAO;
    private IndiceDAO<Indice, Object> indiceDAO;
    private CalculoDAO<Calculo, Object> calculoDAO;
    private CalculoPcondDAO<CalculoPcond,Object> calculoPcondDAO;
    private ProtocoloGsv protocoloGsv;
    private ProtocoloGsvDAO<ProtocoloGsv,Object> protocoloGsvDAO;
    private Multa multa;
    private Honorario honorario;
    private Mora mora;
    private JuroRemuneratorio juroRemuneratorio;
    private Arquivo arquivo;
    private PeriodoCalculo periodoCalculo;
    private Funcionario funcionario;
    private FuncionarioDAO<Funcionario, Object> funcionarioDAO;
    
    
    

  
    
    
    
    
    private JFXButton btn_buscarTable;
    private JFXButton btn_buscarTable1;
    @FXML
    private JFXComboBox<Metodologia> cmbMetodologia;
    @FXML
    private JFXComboBox<PlanoEconomico> cmbPlanoEconomico;
    @FXML
    private JFXComboBox<String> cmbBanco;
    @FXML
    private JFXTextField txtAgencia;
    @FXML
    private JFXTextField txtConta;
    @FXML
    private JFXTextField txtSaldoBase;
    @FXML
    private JFXComboBox<Expurgo> cmbExpurgo;
    
    @FXML
    private JFXTextField txtCpjCnpj;
    @FXML
    private JFXTextField txtDiaBase;
    @FXML
    private JFXTextField txtJurMora;
    @FXML
    private JFXTextField txtInitJurRem;
    @FXML
    private Label lblValorFinal;
    @FXML
    private JFXCheckBox ckbPcond;
    @FXML
    private JFXTextField txtFimJurRem;
    @FXML
    private GridPane grdEdicao;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private JFXButton btCalcular;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
       
        
         setFuncionarioDAO(new FuncionarioDAO<>());
         setNpjDAO(new NpjDAO<>());
         setClienteDAO(new ClienteDAO());
         setCalculoDAO(new CalculoDAO<>());
         setProtocoloGsvDAO(new ProtocoloGsvDAO<>());
         setIndiceDAO(new IndiceDAO<>());
        
        
       
         setPlanoEconomicoDAO(new PlanoEconomicoDAO<>());
         setExpurgoDAO(new ExpurgoDAO<>());
        
         setMetodologiaDAO(new MetodologiaDAO<>());
         setMetodologia(getCmbMetodologia().getValue());
        
         List<Metodologia> listaMetodologia = new ArrayList<>();
         listaMetodologia = getMetodologiaDAO().getListaTodos();
         
        
        getCmbExpurgo().getItems().addAll(getExpurgoDAO().getListaTodos());
        getCmbMetodologia().getItems().addAll(listaMetodologia);
        getCmbPlanoEconomico().getItems().addAll(getPlanoEconomicoDAO().getListaTodos());
        getCmbBanco().getItems().addAll("BB","BESC","BNC");
        
    }    

   

    
    
    
    
    
    
    /**
     * @return the metodologia
     */
    public Metodologia getMetodologia() {
        return metodologia;
    }

    /**
     * @param metodologia the metodologia to set
     */
    public void setMetodologia(Metodologia metodologia) {
        this.metodologia = metodologia;
    }

    /**
     * @return the metodologiaDAO
     */
    public MetodologiaDAO<Metodologia, Object> getMetodologiaDAO() {
        return metodologiaDAO;
    }

    /**
     * @param metodologiaDAO the metodologiaDAO to set
     */
    public void setMetodologiaDAO(MetodologiaDAO<Metodologia, Object> metodologiaDAO) {
        this.metodologiaDAO = metodologiaDAO;
    }

    /**
     * @return the btn_buscarTable
     */
    public JFXButton getBtn_buscarTable() {
        return btn_buscarTable;
    }

    /**
     * @param btn_buscarTable the btn_buscarTable to set
     */
    public void setBtn_buscarTable(JFXButton btn_buscarTable) {
        this.btn_buscarTable = btn_buscarTable;
    }

    /**
     * @return the btn_buscarTable1
     */
    public JFXButton getBtn_buscarTable1() {
        return btn_buscarTable1;
    }

    /**
     * @param btn_buscarTable1 the btn_buscarTable1 to set
     */
    public void setBtn_buscarTable1(JFXButton btn_buscarTable1) {
        this.btn_buscarTable1 = btn_buscarTable1;
    }

    /**
     * @return the cmbMetodologia
     */
    public JFXComboBox<Metodologia> getCmbMetodologia() {
        return cmbMetodologia;
    }

    /**
     * @param cmbMetodologia the cmbMetodologia to set
     */
    public void setCmbMetodologia(JFXComboBox<Metodologia> cmbMetodologia) {
        this.cmbMetodologia = cmbMetodologia;
    }

    /**
     * @return the planoEconomico
     */
    public PlanoEconomico getPlanoEconomico() {
        return planoEconomico;
    }

    /**
     * @param planoEconomico the planoEconomico to set
     */
    public void setPlanoEconomico(PlanoEconomico planoEconomico) {
        this.planoEconomico = planoEconomico;
    }

    /**
     * @return the planoEconomicoDAO
     */
    public PlanoEconomicoDAO<PlanoEconomico, Object> getPlanoEconomicoDAO() {
        return planoEconomicoDAO;
    }

    /**
     * @param planoEconomicoDAO the planoEconomicoDAO to set
     */
    public void setPlanoEconomicoDAO(PlanoEconomicoDAO<PlanoEconomico, Object> planoEconomicoDAO) {
        this.planoEconomicoDAO = planoEconomicoDAO;
    }

    /**
     * @return the cmbPlanoEconomico
     */
    public JFXComboBox<PlanoEconomico> getCmbPlanoEconomico() {
        return cmbPlanoEconomico;
    }

    /**
     * @param cmbPlanoEconomico the cmbPlanoEconomico to set
     */
    public void setCmbPlanoEconomico(JFXComboBox<PlanoEconomico> cmbPlanoEconomico) {
        this.cmbPlanoEconomico = cmbPlanoEconomico;
    }

    /**
     * @return the cmbBanco
     */
    public JFXComboBox<String> getCmbBanco() {
        return cmbBanco;
    }

    /**
     * @param cmbBanco the cmbBanco to set
     */
    public void setCmbBanco(JFXComboBox<String> cmbBanco) {
        this.cmbBanco = cmbBanco;
    }

    /**
     * @return the txtAgencia
     */
    public JFXTextField getTxtAgencia() {
        return txtAgencia;
    }

    /**
     * @param txtAgencia the txtAgencia to set
     */
    public void setTxtAgencia(JFXTextField txtAgencia) {
        this.txtAgencia = txtAgencia;
    }

    /**
     * @return the txtConta
     */
    public JFXTextField getTxtConta() {
        return txtConta;
    }

    /**
     * @param txtConta the txtConta to set
     */
    public void setTxtConta(JFXTextField txtConta) {
        this.txtConta = txtConta;
    }

    /**
     * @return the txtSaldoBase
     */
    public JFXTextField getTxtSaldoBase() {
        return txtSaldoBase;
    }

    /**
     * @param txtSaldoBase the txtSaldoBase to set
     */
    public void setTxtSaldoBase(JFXTextField txtSaldoBase) {
        this.txtSaldoBase = txtSaldoBase;
    }

    private void btnMostrarTeste(ActionEvent event) throws Exception {// Metodo de teste 
        
       
        
        
        
    }

    /**
     * @return the expurgo
     */
    public Expurgo getExpurgo() {
        return expurgo;
    }

    /**
     * @param expurgo the expurgo to set
     */
    public void setExpurgo(Expurgo expurgo) {
        this.expurgo = expurgo;
    }

    /**
     * @return the expurgoDAO
     */
    public ExpurgoDAO<Expurgo, Object> getExpurgoDAO() {
        return expurgoDAO;
    }

    /**
     * @param expurgoDAO the expurgoDAO to set
     */
    public void setExpurgoDAO(ExpurgoDAO<Expurgo, Object> expurgoDAO) {
        this.expurgoDAO = expurgoDAO;
    }

    /**
     * @return the cmbExpurgo
     */
    public JFXComboBox<Expurgo> getCmbExpurgo() {
        return cmbExpurgo;
    }

    /**
     * @param cmbExpurgo the cmbExpurgo to set
     */
    public void setCmbExpurgo(JFXComboBox<Expurgo> cmbExpurgo) {
        this.cmbExpurgo = cmbExpurgo;
    }

    /**
     * @return the txtCpjCnpj
     */
    public JFXTextField getTxtCpjCnpj() {
        return txtCpjCnpj;
    }

    /**
     * @param txtCpjCnpj the txtCpjCnpj to set
     */
    public void setTxtCpjCnpj(JFXTextField txtCpjCnpj) {
        this.txtCpjCnpj = txtCpjCnpj;
    }

    /**
     * @return the txtDiaBase
     */
    public JFXTextField getTxtDiaBase() {
        return txtDiaBase;
    }

    /**
     * @param txtDiaBase the txtDiaBase to set
     */
    public void setTxtDiaBase(JFXTextField txtDiaBase) {
        this.txtDiaBase = txtDiaBase;
    }

    /**
     * @return the txtJurMora
     */
    public JFXTextField getTxtJurMora() {
        return txtJurMora;
    }

    /**
     * @param txtJurMora the txtJurMora to set
     */
    public void setTxtJurMora(JFXTextField txtJurMora) {
        this.txtJurMora = txtJurMora;
    }

    /**
     * @return the txtInitJurRem
     */
    public JFXTextField getTxtInitJurRem() {
        return txtInitJurRem;
    }

    /**
     * @param txtInitJurRem the txtInitJurRem to set
     */
    public void setTxtInitJurRem(JFXTextField txtInitJurRem) {
        this.txtInitJurRem = txtInitJurRem;
    }

    /**
     * @return the lblValorFinal
     */
    public Label getLblValorFinal() {
        return lblValorFinal;
    }

    /**
     * @param lblValorFinal the lblValorFinal to set
     */
    public void setLblValorFinal(Label lblValorFinal) {
        this.lblValorFinal = lblValorFinal;
    }

    /**
     * @return the ckbPcond
     */
    public JFXCheckBox getCkbPcond() {
        return ckbPcond;
    }

    /**
     * @param ckbPcond the ckbPcond to set
     */
    public void setCkbPcond(JFXCheckBox ckbPcond) {
        this.ckbPcond = ckbPcond;
    }

    /**
     * @return the txtFimJurRem
     */
    public JFXTextField getTxtFimJurRem() {
        return txtFimJurRem;
    }

    /**
     * @param txtFimJurRem the txtFimJurRem to set
     */
    public void setTxtFimJurRem(JFXTextField txtFimJurRem) {
        this.txtFimJurRem = txtFimJurRem;
    }

    /**
     * @return the grdEdicao
     */
    public GridPane getGrdEdicao() {
        return grdEdicao;
    }

    /**
     * @param grdEdicao the grdEdicao to set
     */
    public void setGrdEdicao(GridPane grdEdicao) {
        this.grdEdicao = grdEdicao;
    }

    /**
     * @return the txtNome
     */
    public JFXTextField getTxtNome() {
        return txtNome;
    }

    /**
     * @param txtNome the txtNome to set
     */
    public void setTxtNome(JFXTextField txtNome) {
        this.txtNome = txtNome;
    }

    /**
     * @return the calculo
     */
    public Calculo getCalculo() {
        return calculo;
    }

    /**
     * @param calculo the calculo to set
     */
    public void setCalculo(Calculo calculo) {
        this.calculo = calculo;
    }
    
    public void passarCalculo(TelaPrincipalController tp, Calculo calculo){
        
        
        
        setCalculo(calculo);
        
        getCmbMetodologia().setValue(getCalculo().getMetodologia());
        getTxtCpjCnpj().setText(getCalculo().getCliente().getCpf());
        getTxtNome().setText(getCalculo().getCliente().getNomeCliente());
        getCmbBanco().setValue(getCalculo().getNomeBanco());
        getTxtAgencia().setText(getCalculo().getNumeroAgencia().toString());
        getTxtConta().setText(getCalculo().getNumeroConta());
        getCmbPlanoEconomico().setValue(getCalculo().getPlanoEconomico());
        getTxtSaldoBase().setText(Utils.converterToMoneySaldoBase(getCalculo().getSaldoBase().toString()));
        getTxtDiaBase().setText(getCalculo().getDiaBase().toString());
        getTxtJurMora().setText(Utils.converteData(getCalculo().getMora().getDataInicio()));
        getTxtInitJurRem().setText(Utils.converteData(getCalculo().getJuroRemuneratorio().getDataInicio()));
        getTxtFimJurRem().setText(Utils.converteData(getCalculo().getJuroRemuneratorio().getDataFinal()));
        getCmbExpurgo().setValue(getCalculo().getExpurgo());
        getLblValorFinal().setText(Utils.converterToMoneySaldoBase(getCalculo().getValorFinal().toString()));
        getCkbPcond().setSelected(getCalculo().isPcond());
        
        
        
        
        
        
        
    }

    /**
     * @return the npj
     */
    public Npj getNpj() {
        return npj;
    }

    /**
     * @param npj the npj to set
     */
    public void setNpj(Npj npj) {
        this.npj = npj;
    }

    /**
     * @return the npjDAO
     */
    public NpjDAO<Npj,Object> getNpjDAO() {
        return npjDAO;
    }

    /**
     * @param npjDAO the npjDAO to set
     */
    public void setNpjDAO(NpjDAO<Npj,Object> npjDAO) {
        this.npjDAO = npjDAO;
    }

    
    
    
    
    public void complementarDadosCalculo(Calculo calculo) {

//        FacesContext fc = FacesContext.getCurrentInstance();
//        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

        //Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");

        try {

            if (calculo.getMetodologia().getId() == 3) {
                calculo.getMora().setDataInicio(new Date("07/01/1994"));
                calculo.setPcond(false);
                Indice indice = getIndiceDAO().getEm().find(Indice.class, 3);
                calculo.getListaPeriodoCalculo().get(0).setIndice(indice);
                calculo.setJuroRemuneratorio(new JuroRemuneratorio());
            }

            if (calculo.getMetodologia().getId() == 4) {
                calculo.getJuroRemuneratorio().setDataInicio(calculo.getListaPeriodoCalculo().get(0).getDataInicioCalculo());
                calculo.getJuroRemuneratorio().setDataFinal(calculo.getListaPeriodoCalculo().get(0).getDataFinalCalculo());
                Expurgo expurgoParaApadecoJuroRem = getExpurgoDAO().getEm().find(Expurgo.class, 2);
                calculo.setExpurgo(expurgoParaApadecoJuroRem);
                calculo.setPcond(false);
            }

            calculo.setDataRealizacaoCalculo(Calendar.getInstance().getTime());
            calculo.getCliente().setNomeCliente(calculo.getCliente().getNomeCliente().toUpperCase());

            Cliente cliente = getClienteDAO().localizarCliente(calculo.getCliente().getCpf());
            if (cliente != null) {
                calculo.setCliente(cliente);
            }

            Funcionario funcionario = getFuncionarioDAO().localizarFuncionarioPorChave(MainApp.getFunci().getChaveFunci());
            if (MainApp.getFunci().getNomeFuncao() == null) {
                funcionario.setNomeGerente("");
            } else {
                funcionario.setNomeGerente(MainApp.getFunci().getNomeGerente());
            }

            funcionario.setNomeFunci(MainApp.getFunci().getNomeFunci());
            funcionario.setNomeFuncao(MainApp.getFunci().getNomeFuncao());
            getCalculo().setFuncionario(funcionario);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the clienteDAO
     */
    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    /**
     * @param clienteDAO the clienteDAO to set
     */
    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    private void alterarClienteCalculoPcond(CalculoPcond calculoPcond) {

         try {

            if (getCalculo().getId() != null) {
                Cliente cliente = getClienteDAO().localizarCliente(getCalculo().getCliente().getCpf());
                if (cliente != null) {
                    getCalculo().setCliente(cliente);

                } else {

                    setCliente(new Cliente());
                    getCliente().setNomeCliente(getCalculo().getCliente().getNomeCliente());
                    getCliente().setCpf(getCalculo().getCliente().getCpf());
                    getCalculo().setCliente(getCliente());

                }

            }

            

        } catch (Exception e) {
           // Util.mensagemErro(Util.getMensagemErro(e));
        }





    }

    /**
     * @return the indice
     */
    public Indice getIndice() {
        return indice;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(Indice indice) {
        this.indice = indice;
    }

    /**
     * @return the indiceDAO
     */
    public IndiceDAO<Indice, Object> getIndiceDAO() {
        return indiceDAO;
    }

    /**
     * @param indiceDAO the indiceDAO to set
     */
    public void setIndiceDAO(IndiceDAO<Indice, Object> indiceDAO) {
        this.indiceDAO = indiceDAO;
    }

    private void alterarParametrosParaPcond(CalculoPcond calculoParaPcond) {
        
        calculoParaPcond.getListaPeriodoCalculo().get(0).setDataFinalCalculo(br.com.intranet.cenopservicoscwb.model.util.Utils.getDataHoraAtualMysqlDate());
        calculoParaPcond.getListaPeriodoCalculo().get(0).setIndice(getIndiceDAO().localizar(1));
        calculoParaPcond.setMora(calculoParaPcond.getMora());
        Honorario honorarioPcond = new Honorario();
        calculoParaPcond.setPlanoEconomico(getPlanoEconomicoDAO().localizar(1));

        Mora mora = new Mora();

        if (calculoParaPcond.getNomeBanco().equals("BB")) {
            mora.setDataInicio(new Date("06/08/1993"));

        } else if (calculoParaPcond.getNomeBanco().equals("BNC")) {
            mora.setDataInicio(new Date("06/21/1993"));

        } else {
            mora.setDataInicio(new Date("05/19/1993"));
        }

        calculoParaPcond.setMora(mora);

        calculoParaPcond.setExpurgo(getExpurgoDAO().localizar(2));
        calculoParaPcond.setJuroRemuneratorio(new JuroRemuneratorio());

        
    }

    
     public void atualizarCalculo(Calculo calculo) {

        if (getCalculoDAO().atualizar(calculo)) {

            //Util.mensagemInformacao(getCalculoDAO().getMensagem());
            
           
            
        } else {
           // Util.mensagemErro(getCalculoDAO().getMensagem());

        }

    }

    /**
     * @return the calculoPcond
     */
    public CalculoPcond getCalculoPcond() {
        return calculoPcond;
    }

    /**
     * @param calculoPcond the calculoPcond to set
     */
    public void setCalculoPcond(CalculoPcond calculoPcond) {
        this.calculoPcond = calculoPcond;
    }

    /**
     * @return the calculoDAO
     */
    public CalculoDAO<Calculo, Object> getCalculoDAO() {
        return calculoDAO;
    }

    /**
     * @param calculoDAO the calculoDAO to set
     */
    public void setCalculoDAO(CalculoDAO<Calculo, Object> calculoDAO) {
        this.calculoDAO = calculoDAO;
    }

    /**
     * @return the calculoPcondDAO
     */
    public CalculoPcondDAO<CalculoPcond,Object> getCalculoPcondDAO() {
        return calculoPcondDAO;
    }

    /**
     * @param calculoPcondDAO the calculoPcondDAO to set
     */
    public void setCalculoPcondDAO(CalculoPcondDAO<CalculoPcond,Object> calculoPcondDAO) {
        this.calculoPcondDAO = calculoPcondDAO;
    }

    /**
     * @return the protocoloGsv
     */
    public ProtocoloGsv getProtocoloGsv() {
        return protocoloGsv;
    }

    /**
     * @param protocoloGsv the protocoloGsv to set
     */
    public void setProtocoloGsv(ProtocoloGsv protocoloGsv) {
        this.protocoloGsv = protocoloGsv;
    }

    /**
     * @return the protocoloGsvDAO
     */
    public ProtocoloGsvDAO<ProtocoloGsv,Object> getProtocoloGsvDAO() {
        return protocoloGsvDAO;
    }

    /**
     * @param protocoloGsvDAO the protocoloGsvDAO to set
     */
    public void setProtocoloGsvDAO(ProtocoloGsvDAO<ProtocoloGsv,Object> protocoloGsvDAO) {
        this.protocoloGsvDAO = protocoloGsvDAO;
    }
    
    
    
    
    
     public void salvarCalculo(Calculo calculo) {

        if (getCalculoDAO().salvar(calculo)) {
            //Util.mensagemInformacao(getCalculoDAO().getMensagem());

        } else {

            //Util.mensagemErro(getCalculoDAO().getMensagem());

        }

    }
     
     
     
     
      public void novo() {
          
            Calculo calculoUltimaLinha = getProtocoloGsv().getListaCalculo().get(getProtocoloGsv().getListaCalculo().size() - 1);
            Calculo calculo = new Calculo();
            setCalculo(calculo);

            calculo.setMetodologia(calculoUltimaLinha.getMetodologia());
            calculo.setCliente(new Cliente());
            calculo.setNomeBanco(calculoUltimaLinha.getNomeBanco());
            calculo.setFuncionario(mainApp.getFunci());

            calculo.setPlanoEconomico(calculoUltimaLinha.getPlanoEconomico());

            PeriodoCalculo periodoCalculo = new PeriodoCalculo();
            periodoCalculo.setIndice(calculoUltimaLinha.getListaPeriodoCalculo().get(0).getIndice());
            periodoCalculo.setDataInicioCalculo(calculoUltimaLinha.getListaPeriodoCalculo().get(0).getDataInicioCalculo());
            periodoCalculo.setDataFinalCalculo(calculoUltimaLinha.getListaPeriodoCalculo().get(0).getDataFinalCalculo());
            calculo.adicionarPeriodoCalculo(periodoCalculo);

            Mora mora = new Mora();
            mora.setDataInicio(calculoUltimaLinha.getMora().getDataInicio());
            calculo.setMora(mora);

            JuroRemuneratorio juroRemuneratorio = new JuroRemuneratorio();
            juroRemuneratorio.setDataInicio(calculoUltimaLinha.getJuroRemuneratorio().getDataInicio());
            juroRemuneratorio.setDataFinal(calculoUltimaLinha.getJuroRemuneratorio().getDataFinal());
            calculo.setJuroRemuneratorio(juroRemuneratorio);

            calculo.setExpurgo(calculoUltimaLinha.getExpurgo());

            getProtocoloGsv().adicionarCalculo(calculo);
          

    

    }
      
      
      
      public void salvar() {

        if (getNpjDAO().atualizar(getNpj())) {
          //  Util.mensagemInformacao(getNpjDAO().getMensagem());

        } else {

           // Util.mensagemErro(getNpjDAO().getMensagem());
        }

    }

    /**
     * @return the multa
     */
    public Multa getMulta() {
        return multa;
    }

    /**
     * @param multa the multa to set
     */
    public void setMulta(Multa multa) {
        this.multa = multa;
    }

    /**
     * @return the honorario
     */
    public Honorario getHonorario() {
        return honorario;
    }

    /**
     * @param honorario the honorario to set
     */
    public void setHonorario(Honorario honorario) {
        this.honorario = honorario;
    }

    /**
     * @return the mora
     */
    public Mora getMora() {
        return mora;
    }

    /**
     * @param mora the mora to set
     */
    public void setMora(Mora mora) {
        this.mora = mora;
    }

    /**
     * @return the juroRemuneratorio
     */
    public JuroRemuneratorio getJuroRemuneratorio() {
        return juroRemuneratorio;
    }

    /**
     * @param juroRemuneratorio the juroRemuneratorio to set
     */
    public void setJuroRemuneratorio(JuroRemuneratorio juroRemuneratorio) {
        this.juroRemuneratorio = juroRemuneratorio;
    }

    /**
     * @return the arquivo
     */
    public Arquivo getArquivo() {
        return arquivo;
    }

    /**
     * @param arquivo the arquivo to set
     */
    public void setArquivo(Arquivo arquivo) {
        this.arquivo = arquivo;
    }

    /**
     * @return the periodoCalculo
     */
    public PeriodoCalculo getPeriodoCalculo() {
        return periodoCalculo;
    }

    /**
     * @param periodoCalculo the periodoCalculo to set
     */
    public void setPeriodoCalculo(PeriodoCalculo periodoCalculo) {
        this.periodoCalculo = periodoCalculo;
    }

    @FXML
    private void avaliarParaSalvar(ActionEvent event) throws Exception {
        
        getCalculo().setMetodologia(getCmbMetodologia().getValue());
        getCalculo().getCliente().setCpf(getTxtCpjCnpj().getText());
        getCalculo().getCliente().setNomeCliente(getTxtNome().getText());
        getCalculo().setNomeBanco(getCmbBanco().getValue());
        getCalculo().setNumeroConta(getTxtConta().getText());
        getCalculo().setNumeroAgencia(Integer.parseInt(getTxtAgencia().getText()));
        getCalculo().setSaldoBase(new BigDecimal(Utils.tratarVariavel(getTxtSaldoBase().getText())));
        getCalculo().setDiaBase(Integer.parseInt(getTxtDiaBase().getText()));
        getCalculo().getMora().setDataInicio(Utils.formataData(getTxtJurMora().getText()));
        getCalculo().getJuroRemuneratorio().setDataInicio(Utils.formataData(getTxtInitJurRem().getText()));
        getCalculo().getJuroRemuneratorio().setDataFinal(Utils.formataData(getTxtFimJurRem().getText()));
        getCalculo().setExpurgo(getCmbExpurgo().getValue());
        getCalculo().setPcond(getCkbPcond().isSelected());
        
        
        
        
        
         setCalculo(getCalculo());

        if (getCalculo().getMetodologia().getId() == 2) {
            getCalculo().setDiaBase(br.com.intranet.cenopservicoscwb.model.util.Utils.getDia(getCalculo().getListaPeriodoCalculo().get(0).getDataInicioCalculo()));
        }

        if (getCalculo().getMora().getDataInicio().after(getCalculo().getListaPeriodoCalculo().get(getCalculo().getListaPeriodoCalculo().size() - 1).getDataFinalCalculo())) {
            //Util.mensagemErro("Data de mora não pode ser superior à data de atualização do cálculo.");
            return;
        }

        //GerarPdf gerarPdf = new GerarPdf();

        if (!calculo.getProtocoloGsv().getNpj().equals(getNpjDAO().localizar(calculo.getProtocoloGsv().getNpj().getNrPrc()))) {
           // Util.mensagemErro("O protocolo " + getCalculo().getProtocoloGsv().getCdPrc().toString() + " " + "já está associado a outro NPJ:  " + getProtocoloGsvDAO().localizar(getProtocoloGsv().getCdPrc()).getNpj().getNrPrc().toString());
            return;
        }

        if (getCalculo().getId() == null) {
            complementarDadosCalculo(getCalculo());
            MotorCalculoPoupanca motorCalculoPoupanca = new MotorCalculoPoupanca();

            switch (getCalculo().getMetodologia().getId()) {
                case 1:
                    motorCalculoPoupanca.calcular(getCalculo());
                    break;
                case 2:
                    motorCalculoPoupanca.calcularPj(getCalculo());
                    break;
                case 3:
                    motorCalculoPoupanca.calcularDiferencaApadecoCumprimentoSentenca(getCalculo());
                    break;
                case 4:
                    motorCalculoPoupanca.calcular(getCalculo());
                    break;
                default:
                  //  Util.mensagemErro(getCalculo().getMetodologia().getNomeMetodologia() + "não é uma metodologia válida");
                    return;
            }

            //gerarPdf.gerarDocumentoResumo(getCalculo().getProtocoloGsv());

            if (getCalculo().isPcond() == true) {

                CalculoPcond calculoPcond = new CalculoPcond();
                calculoPcond.setSaldoBase(getCalculo().getSaldoBase());
                calculoPcond.setMetodologia(getCalculo().getMetodologia());
                calculoPcond.setCliente(getCalculo().getCliente());
                alterarClienteCalculoPcond(calculoPcond);
                calculoPcond.setFuncionario(getCalculo().getFuncionario());
                calculoPcond.setNomeBanco(getCalculo().getNomeBanco());
                calculoPcond.setNumeroAgencia(getCalculo().getNumeroAgencia());
                calculoPcond.setNumeroConta(getCalculo().getNumeroConta());
                calculoPcond.setDiaBase(getCalculo().getDiaBase());
                calculoPcond.setDataRealizacaoCalculo(getCalculo().getDataRealizacaoCalculo());
                calculoPcond.setPlanoEconomico(getCalculo().getPlanoEconomico());

                PeriodoCalculo periodocalculoPcond = new PeriodoCalculo();
                periodocalculoPcond.setDataInicioCalculo(getCalculo().getListaPeriodoCalculo().get(0).getDataInicioCalculo());
                periodocalculoPcond.setDataFinalCalculo((getCalculo().getListaPeriodoCalculo().get(getCalculo().getListaPeriodoCalculo().size() - 1).getDataFinalCalculo()));
                calculoPcond.adicionarPeriodoCalculo(periodocalculoPcond);

                calculoPcond.setExpurgo(getExpurgoDAO().localizar(2));
                calculoPcond.setProtocoloGsv(getCalculo().getProtocoloGsv());
                alterarParametrosParaPcond(calculoPcond);
                motorCalculoPoupanca.calcularPcond(calculoPcond);
                //salvarCalculoPcond(calculoPcond);
                getCalculo().setCalculoPcond(calculoPcond);
                atualizarCalculo(getCalculo());
                //gerarPdf.gerarDocumentoResumoPcond(getCalculoPcondDAO().localizarCalculoPcondPorProtocolo(getProtocoloGsv().getCdPrc()));
               // novo();

            } else {
                salvarCalculo(getCalculo());
               // novo();
            }

        } else {

//            excluirPdfCalculo(getCalculo());
//            excluirPdfCalculoPcond(getCalculo());
            complementarDadosCalculo(getCalculo());

            MotorCalculoPoupanca motorCalculoPoupanca = new MotorCalculoPoupanca();

            switch (getCalculo().getMetodologia().getId()) {
                case 1:
                    motorCalculoPoupanca.calcular(getCalculo());
                    break;
                case 2:
                    motorCalculoPoupanca.calcularPj(getCalculo());
                    break;
                case 3:
                    motorCalculoPoupanca.calcularDiferencaApadecoCumprimentoSentenca(getCalculo());
                    break;
                case 4:
                    motorCalculoPoupanca.calcular(getCalculo());
                    break;
                default:
                   // Util.mensagemErro(getCalculo().getMetodologia().getNomeMetodologia() + "não é uma metodologia válida");
                    return;
            }

           // gerarPdf.gerarDocumentoResumo(getCalculo().getProtocoloGsv());

            if (getCalculo().isPcond() == true) {

                CalculoPcond calculoPcond = new CalculoPcond();
                calculoPcond.setSaldoBase(getCalculo().getSaldoBase());
                calculoPcond.setMetodologia(getCalculo().getMetodologia());
                calculoPcond.setCliente(getCalculo().getCliente());
                alterarClienteCalculoPcond(calculoPcond);
                calculoPcond.setFuncionario(getCalculo().getFuncionario());
                calculoPcond.setNomeBanco(getCalculo().getNomeBanco());
                calculoPcond.setNumeroAgencia(getCalculo().getNumeroAgencia());
                calculoPcond.setNumeroConta(getCalculo().getNumeroConta());
                calculoPcond.setDiaBase(getCalculo().getDiaBase());
                calculoPcond.setDataRealizacaoCalculo(getCalculo().getDataRealizacaoCalculo());
                calculoPcond.setPlanoEconomico(getCalculo().getPlanoEconomico());

                PeriodoCalculo periodocalculoPcond = new PeriodoCalculo();
                periodocalculoPcond.setDataInicioCalculo(getCalculo().getListaPeriodoCalculo().get(0).getDataInicioCalculo());
                periodocalculoPcond.setDataFinalCalculo((getCalculo().getListaPeriodoCalculo().get(getCalculo().getListaPeriodoCalculo().size() - 1).getDataFinalCalculo()));
                calculoPcond.adicionarPeriodoCalculo(periodocalculoPcond);

                calculoPcond.setExpurgo(getExpurgoDAO().localizar(2));
                calculoPcond.setProtocoloGsv(getCalculo().getProtocoloGsv());
                alterarParametrosParaPcond(calculoPcond);

                motorCalculoPoupanca.calcularPcond(calculoPcond);
                //salvarCalculoPcond(calculoPcond);
                getCalculo().setCalculoPcond(calculoPcond);
                atualizarCalculo(getCalculo());

                //gerarPdf.gerarDocumentoResumoPcond(getCalculoPcondDAO().localizarCalculoPcondPorProtocolo(getProtocoloGsv().getCdPrc()));
               // novo();

            } else {
                atualizarCalculo(getCalculo());
               // novo();
            }
        }
       
        
        
        atualizaFormularioCalculo();
    }
    
    
    public void atualizaFormularioCalculo(){
        getCmbMetodologia().setValue(getCalculo().getMetodologia());
        getTxtCpjCnpj().setText(getCalculo().getCliente().getCpf());
        getTxtNome().setText(getCalculo().getCliente().getNomeCliente());
        getCmbBanco().setValue(getCalculo().getNomeBanco());
        getTxtAgencia().setText(getCalculo().getNumeroAgencia().toString());
        getTxtConta().setText(getCalculo().getNumeroConta());
        getCmbPlanoEconomico().setValue(getCalculo().getPlanoEconomico());
        getTxtSaldoBase().setText(Utils.converterToMoneySaldoBase(getCalculo().getSaldoBase().toString()));
        getTxtDiaBase().setText(getCalculo().getDiaBase().toString());
        getTxtJurMora().setText(Utils.converteData(getCalculo().getMora().getDataInicio()));
        getTxtInitJurRem().setText(Utils.converteData(getCalculo().getJuroRemuneratorio().getDataInicio()));
        getTxtFimJurRem().setText(Utils.converteData(getCalculo().getJuroRemuneratorio().getDataFinal()));
        getCmbExpurgo().setValue(getCalculo().getExpurgo());
        getLblValorFinal().setText(Utils.converterToMoneySaldoBase(getCalculo().getValorFinal().toString()));
        getCkbPcond().setSelected(getCalculo().isPcond());
    }

  
    
    void passarNpjProtocolo(TelaPrincipalController telaPrincipalController, Npj npj, ProtocoloGsv protocoloGSV) {
        
        setProtocoloGsv(protocoloGSV);
        setNpj(npj);
        
        novo();
    }

    /**
     * @return the funcionario
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    /**
     * @return the funcionarioDAO
     */
    public FuncionarioDAO<Funcionario, Object> getFuncionarioDAO() {
        return funcionarioDAO;
    }

    /**
     * @param funcionarioDAO the funcionarioDAO to set
     */
    public void setFuncionarioDAO(FuncionarioDAO<Funcionario, Object> funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

    /**
     * @return the btCalcular
     */
    public JFXButton getBtCalcular() {
        return btCalcular;
    }

    /**
     * @param btCalcular the btCalcular to set
     */
    public void setBtCalcular(JFXButton btCalcular) {
        this.btCalcular = btCalcular;
    }
    
    

}
