/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.intranet.cenopservicoscwb.controller;

import CurrencyField.TextFieldFormatter;
import br.com.intranet.cenopservicoscwb.model.calculo.MotorCalculoPoupanca;
import br.com.intranet.cenopservicoscwb.model.entidade.Arquivo;
import br.com.intranet.cenopservicoscwb.model.entidade.Atualizacao;
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
import br.com.intranet.cenopservicoscwb.model.pdf.GerarPdf;
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
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import main.MainApp;

/**
 * FXML Controller class
 *
 * @author f5078775
 */
public class EdicaoCalculoController extends AbstractController implements Initializable {

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
    private NpjDAO<Npj, Object> npjDAO;
    private IndiceDAO<Indice, Object> indiceDAO;
    private CalculoDAO<Calculo, Object> calculoDAO;
    private CalculoPcondDAO<CalculoPcond, Object> calculoPcondDAO;
    private ProtocoloGsv protocoloGsv;
    private ProtocoloGsvDAO<ProtocoloGsv, Object> protocoloGsvDAO;
    private Multa multa;
    private Honorario honorario;
    private Mora mora;
    private JuroRemuneratorio juroRemuneratorio;
    private Arquivo arquivo;
    private PeriodoCalculo periodoCalculo;
    private Funcionario funcionario;
    private FuncionarioDAO<Funcionario, Object> funcionarioDAO;
    private Map<String, BigDecimal> valorIndiceMap;
    private BigDecimal saldoNaDataBase;
    
    static private Map<String, BigDecimal> valorIndiceUmMap;
    static  private Map<String, BigDecimal> valorIndiceDoisMap;
    static private Map<String, BigDecimal> valorIndiceTresMap;
    static private Map<String, BigDecimal> valorIndiceQuatroMap;
    static private Map<String, BigDecimal> valorIndiceCincoMap;

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
    @FXML
    private ComboBox<Indice> cmbIndice;
    @FXML
    private JFXTextField txtDataInicioCorrecao;
    @FXML
    private JFXTextField txtDataFinalCorrecao;

    private TelaPrincipalController tp;
    
    @FXML
    private JFXTextField txtCpfCnpj;

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
        setCalculoPcondDAO(new CalculoPcondDAO<>());

        setPlanoEconomicoDAO(new PlanoEconomicoDAO<>());
        setExpurgoDAO(new ExpurgoDAO<>());

        setMetodologiaDAO(new MetodologiaDAO<>());
        setMetodologia(getCmbMetodologia().getValue());

        List<Metodologia> listaMetodologia = new ArrayList<>();
        listaMetodologia = getMetodologiaDAO().getListaTodos();

        getCmbExpurgo().getItems().addAll(getExpurgoDAO().getListaTodos());
        getCmbMetodologia().getItems().addAll(listaMetodologia);
        getCmbPlanoEconomico().getItems().addAll(getPlanoEconomicoDAO().getListaTodos());
        getCmbBanco().getItems().addAll("BB", "BESC", "BNC");
        getCmbIndice().getItems().addAll(getIndiceDAO().getListaTodos());

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

    public void passarCalculo(TelaPrincipalController tp, Calculo calculo) {

        this.setTp(tp);

        if(calculo==null){
            return;
        }
        setCalculo(calculo);
        getCalculo().setListaAtualizacao(new ArrayList<>());
        setNpj(calculo.getProtocoloGsv().getNpj());
        setProtocoloGsv(calculo.getProtocoloGsv());
        
       atualizaFormularioCalculo();
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
    public NpjDAO<Npj, Object> getNpjDAO() {
        return npjDAO;
    }

    /**
     * @param npjDAO the npjDAO to set
     */
    public void setNpjDAO(NpjDAO<Npj, Object> npjDAO) {
        this.npjDAO = npjDAO;
    }

    public void complementarDadosCalculo(Calculo calculo) {

//        FacesContext fc = FacesContext.getCurrentInstance();
//        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        //Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");
        try {

            
             switch (calculo.getListaPeriodoCalculo().get(0).getIndice().getId()) {

                case 1:
                    setValorIndiceMap(getValorIndiceUmMap());
                    break;
                case 2:
                    setValorIndiceMap(getValorIndiceDoisMap());
                    break;
                case 3:
                    setValorIndiceMap(getValorIndiceTresMap());
                    break;
                case 4:
                    setValorIndiceMap(getValorIndiceQuatroMap());
                    break;
                case 5:
                    setValorIndiceMap(getValorIndiceCincoMap());
                    break;
            }
            
            
            
            
            
            
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

        getCalculoDAO().atualizar(calculo);

           
      

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
    public CalculoPcondDAO<CalculoPcond, Object> getCalculoPcondDAO() {
        return calculoPcondDAO;
    }

    /**
     * @param calculoPcondDAO the calculoPcondDAO to set
     */
    public void setCalculoPcondDAO(CalculoPcondDAO<CalculoPcond, Object> calculoPcondDAO) {
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
    public ProtocoloGsvDAO<ProtocoloGsv, Object> getProtocoloGsvDAO() {
        return protocoloGsvDAO;
    }

    /**
     * @param protocoloGsvDAO the protocoloGsvDAO to set
     */
    public void setProtocoloGsvDAO(ProtocoloGsvDAO<ProtocoloGsv, Object> protocoloGsvDAO) {
        this.protocoloGsvDAO = protocoloGsvDAO;
    }

    public void salvarCalculo(Calculo calculo) {

        getCalculoDAO().salvar(calculo);
          
    }

    public void novo() {


        if (getProtocoloGsv().getListaCalculo().size() > 0) {
            Calculo calculoUltimaLinha = getProtocoloGsv().getListaCalculo().get(getProtocoloGsv().getListaCalculo().size() - 1);
            Calculo calculo = new Calculo();
            setCalculo(calculo);
            getCalculo().setProtocoloGsv(getProtocoloGsv());

            calculo.setMetodologia(calculoUltimaLinha.getMetodologia());
            calculo.setCliente(new Cliente());
            calculo.setNumeroAgencia(calculoUltimaLinha.getNumeroAgencia());
            calculo.setNomeBanco(calculoUltimaLinha.getNomeBanco());
            calculo.setFuncionario(mainApp.getFunci());
            calculo.setSaldoBase(calculoUltimaLinha.getSaldoBase());

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

           // getProtocoloGsv().adicionarCalculo(calculo);
            atualizaFormularioCalculo();

           

        }

        if (getProtocoloGsv() != null && getProtocoloGsv().getListaCalculo().size() >= 1) {
            setNpj(getProtocoloGsv().getNpj());
            setProtocoloGsv(getProtocoloGsv());
            getNpj().adicionarProtocolo(getProtocoloGsv());
            if (getProtocoloGsv().getMulta() != null) {

                setMulta(getProtocoloGsv().getMulta());
                getProtocoloGsv().setMulta(getMulta());
                setHonorario(getProtocoloGsv().getHonorario());
                getProtocoloGsv().setHonorario(getProtocoloGsv().getHonorario());
            } else {
                getProtocoloGsv().setMulta(getMulta());
                getProtocoloGsv().setHonorario(getHonorario());
            }
 
            return;

        } else {

            //salvar();
            setCalculo(new Calculo());
           getCalculo().setListaAtualizacao(new ArrayList<Atualizacao>());
            getCalculo().setCliente(new Cliente());
            // getCliente().adicionarCalculo(getCalculo());
            getCalculo().setMora(new Mora());
            getCalculo().setJuroRemuneratorio(new JuroRemuneratorio());
            getCalculo().setArquivo(new Arquivo());
            //getProtocoloGsv().adicionarCalculo(getCalculo());
            getProtocoloGsv().setNpj(getNpj());
            getCalculo().setPlanoEconomico(getPlanoEconomicoDAO().getEm().find(PlanoEconomico.class, 1));
            setPeriodoCalculo(new PeriodoCalculo());
            getPeriodoCalculo().setIndice(getIndiceDAO().getEm().find(Indice.class, 1));
            getCalculo().setExpurgo(getExpurgoDAO().getEm().find(Expurgo.class, 1));
            getCalculo().setMetodologia(getMetodologiaDAO().getEm().find(Metodologia.class, 1));
            
            
        }

        getProtocoloGsv().adicionarCalculo(getCalculo());
        getCalculo().adicionarPeriodoCalculo(getPeriodoCalculo());
        atualizaFormularioCalculo();
    }

//      public void salvar() {
//
//        if (getNpjDAO().atualizar(getNpj())) {
//          //  Util.mensagemInformacao(getNpjDAO().getMensagem());
//
//        } else {
//
//           // Util.mensagemErro(getNpjDAO().getMensagem());
//        }
//
//    }
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

        
        try {
            
      
        atualizarObjetoComDadosFormulario();
      
        //setCalculo(getCalculo());
        if (getCalculo().getMetodologia().getId() == 2) {
            getCalculo().setDiaBase(br.com.intranet.cenopservicoscwb.model.util.Utils.getDia(getCalculo().getListaPeriodoCalculo().get(0).getDataInicioCalculo()));
        }

        if (getCalculo().getMora().getDataInicio().after(getCalculo().getListaPeriodoCalculo().get(getCalculo().getListaPeriodoCalculo().size() - 1).getDataFinalCalculo())) {
            Utils.alertaGeralInformacao(null, null, "Data de mora não pode ser superior à data de atualização do cálculo.");
            return;
        }

        GerarPdf gerarPdf = new GerarPdf();

        if (!getNpj().equals(getNpjDAO().localizar(getCalculo().getProtocoloGsv().getNpj().getNrPrc()))) {
            Utils.alertaGeralInformacao(null, null, "O protocolo " + getCalculo().getProtocoloGsv().getCdPrc().toString() + " " + "já está associado a outro NPJ:  " + getProtocoloGsvDAO().localizar(getProtocoloGsv().getCdPrc()).getNpj().getNrPrc().toString());
            return;
        }

        if (getCalculo().getId() == null) {
            complementarDadosCalculo(getCalculo());
            MotorCalculoPoupanca motorCalculoPoupanca = new MotorCalculoPoupanca();

             switch (getCalculo().getMetodologia().getId()) {
                case 1:
                    motorCalculoPoupanca.calcular(getCalculo(), getValorIndiceMap(), getValorIndiceUmMap(), getValorIndiceQuatroMap());
                    break;
                case 2:
                    motorCalculoPoupanca.calcularPj(getCalculo(), getValorIndiceMap());
                    break;
                case 3:
                    motorCalculoPoupanca.calcularDiferencaApadecoCumprimentoSentenca(getCalculo(), getValorIndiceMap());
                    break;
                case 4:
                    motorCalculoPoupanca.calcular(getCalculo(), getValorIndiceMap(), getValorIndiceUmMap(), getValorIndiceQuatroMap());
                    break;
                
                default:
                    Utils.alertaGeralInformacao(null, null, getCalculo().getMetodologia().getNomeMetodologia() + "não é uma metodologia válida");
                    return;
            }

            gerarPdf.gerarDocumentoResumo(getCalculo().getProtocoloGsv());
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
                motorCalculoPoupanca.calcularPcond(calculoPcond, getValorIndiceUmMap());
                //salvarCalculoPcond(calculoPcond);
                getCalculo().setCalculoPcond(calculoPcond);
                atualizarCalculo(getCalculo());
                gerarPdf.gerarDocumentoResumoPcond(getCalculoPcondDAO().localizarCalculoPcondPorProtocolo(getProtocoloGsv().getCdPrc()));
              
               
               

            } else {
                salvarCalculo(getCalculo());
               
              
            }

        } else {

            excluirPdfCalculo(getCalculo());
            excluirPdfCalculoPcond(getCalculo());
            complementarDadosCalculo(getCalculo());

            MotorCalculoPoupanca motorCalculoPoupanca = new MotorCalculoPoupanca();

             switch (getCalculo().getMetodologia().getId()) {
                case 1:
                    motorCalculoPoupanca.calcular(getCalculo(), getValorIndiceMap(), getValorIndiceUmMap(), getValorIndiceQuatroMap());
                    break;
                case 2:
                    motorCalculoPoupanca.calcularPj(getCalculo(), getValorIndiceMap());
                    break;
                case 3:
                    motorCalculoPoupanca.calcularDiferencaApadecoCumprimentoSentenca(getCalculo(), getValorIndiceMap());
                    break;
                case 4:
                    motorCalculoPoupanca.calcular(getCalculo(), getValorIndiceMap(), getValorIndiceUmMap(), getValorIndiceQuatroMap());
                    break;
                default:
                    Utils.alertaGeralInformacao(null, null, getCalculo().getMetodologia().getNomeMetodologia() + "não é uma metodologia válida");
                    return;
            }

            gerarPdf.gerarDocumentoResumo(getCalculo().getProtocoloGsv());

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

                motorCalculoPoupanca.calcularPcond(calculoPcond, getValorIndiceUmMap());
                // salvarCalculoPcond(calculoPcond);
                getCalculo().setCalculoPcond(calculoPcond);
                atualizarCalculo(getCalculo());

                gerarPdf.gerarDocumentoResumoPcond(getCalculoPcondDAO().localizarCalculoPcondPorProtocolo(getProtocoloGsv().getCdPrc()));
               
             
              

            } else {

                atualizarCalculo(getCalculo());
                gerarPdf.gerarDocumentoResumo(getCalculo().getProtocoloGsv());
               
               
               
            }
        }
         atualizaTabelaPrincipal();
          novo();
          atualizaFormularioCalculo();

         Utils.alertaGeralInformacao(null,null,getCalculoDAO().getMensagem());
       } catch (Exception e) {
           Utils.alertaGeral(null, null, "Erro - ao persistir este erro comunique a equipe responsável" + "\n" + e);
        }

    }

    public void excluirPdfCalculoPcond(Calculo calculo) {

        if (calculo.getCalculoPcond() == null) {
            return;
        }

        File file = new File("C:\\Users\\f5078775\\Desktop\\GCPDESKTOP\\" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + calculo.getProtocoloGsv().getCdPrc().toString() + "\\" + "CALCULO INTERNO (PCOND)" + " - " + calculo.getCalculoPcond().getCliente().getNomeCliente() + " - " + br.com.intranet.cenopservicoscwb.model.util.Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getCalculoPcond().getPlanoEconomico().getNomePlanoEconomico() + " - " + br.com.intranet.cenopservicoscwb.model.util.Utils.converterToMoney(calculo.getCalculoPcond().getValorFinal().toString()) + ".pdf");
        if (file.exists()) {
            file.delete();

        }

    }

    public void excluirPdfCalculo(Calculo calculo) {

        File file = new File("C:\\Users\\f5078775\\Desktop\\GCPDESKTOP\\" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + calculo.getProtocoloGsv().getCdPrc().toString() + "\\" + "CALCULO DEFESA" + " - " + calculo.getCliente().getNomeCliente() + " - " + br.com.intranet.cenopservicoscwb.model.util.Utils.tratarConta(calculo.getNumeroConta().toString()) + " - " + calculo.getPlanoEconomico().getNomePlanoEconomico() + " - " + br.com.intranet.cenopservicoscwb.model.util.Utils.converterToMoney(calculo.getValorFinal().toString()) + ".pdf");
        if (file.exists()) {
            file.delete();

        }

    }

    public void atualizaTabelaPrincipal() {
        //  TelaPrincipalController telaPrincipalController = new TelaPrincipalController();
        this.getTp().popularTabelacalculoEdicao();
    }

    
    
    
    
    public void atualizaFormularioCalculo() {

       

        if(getCalculo().getMetodologia()!=null){
             getCmbMetodologia().setValue(getCalculo().getMetodologia());
        }
           
        if(getCalculo().getCliente()!=null){
             getTxtCpfCnpj().setText(getCalculo().getCliente().getCpf());
        }
        
        if(getCalculo().getCliente()!=null){
            getTxtNome().setText(getCalculo().getCliente().getNomeCliente());
        }
        
        if(getCalculo().getNomeBanco()!=null){
             getCmbBanco().setValue(getCalculo().getNomeBanco());
        }
           
        if(getCalculo().getNumeroAgencia()!=null){
           getTxtAgencia().setText(getCalculo().getNumeroAgencia().toString()); 
        }
            
           
        if(getCalculo().getNumeroConta()!=null){
           getTxtConta().setText(getCalculo().getNumeroConta());
        }
            
        if(getCalculo().getPlanoEconomico()!=null){
             getCmbPlanoEconomico().setValue(getCalculo().getPlanoEconomico());
        }
           
        if(getCalculo().getSaldoBase()!=null){
             getTxtSaldoBase().setText(Utils.converterToMoneySaldoBase(getCalculo().getSaldoBase().toString()));
        }
           
        if(getCalculo().getDiaBase()!=null){
             getTxtDiaBase().setText(getCalculo().getDiaBase().toString());
        }
        
        if(getCalculo().getMora().getDataInicio()!=null){
              getTxtJurMora().setText(Utils.converteData(getCalculo().getMora().getDataInicio()));
        }
           
        if(getCalculo().getJuroRemuneratorio().getDataInicio()!=null){
             getTxtInitJurRem().setText(Utils.converteData(getCalculo().getJuroRemuneratorio().getDataInicio()));
        }
           
        if(getCalculo().getJuroRemuneratorio().getDataFinal()!=null){
          getTxtFimJurRem().setText(Utils.converteData(getCalculo().getJuroRemuneratorio().getDataFinal()));   
        }
          
        if(getCalculo().getExpurgo()!=null){
           getCmbExpurgo().setValue(getCalculo().getExpurgo());   
        }
           
           
        if(getCalculo().getListaPeriodoCalculo().get(0).getIndice()!=null){
            getCmbIndice().setValue(getCalculo().getListaPeriodoCalculo().get(0).getIndice());
        }
          
        if(getCalculo().getListaPeriodoCalculo().get(0).getDataInicioCalculo()!=null){
             getTxtDataInicioCorrecao().setText(Utils.formatDataTexto(getCalculo().getListaPeriodoCalculo().get(0).getDataInicioCalculo().toString()));
        }
            
        if(getCalculo().getListaPeriodoCalculo().get(0).getDataFinalCalculo()!=null){
              getTxtDataFinalCorrecao().setText(Utils.formatDataTexto(getCalculo().getListaPeriodoCalculo().get(0).getDataFinalCalculo().toString()));
        }   
        
        if(getCalculo().getValorFinal()!=null){
             getLblValorFinal().setText(Utils.converterToMoneySaldoBase(getCalculo().getValorFinal().toString()));
        }
          
    
        
            getCkbPcond().setSelected(getCalculo().isPcond());

    }

    void passarNpjProtocolo(TelaPrincipalController telaPrincipalController, Npj npj, ProtocoloGsv protocoloGSV, Map<String,BigDecimal> valorIndiceUmMap,Map<String, BigDecimal> valorIndiceDoisMap,Map<String, BigDecimal> valorIndiceTresMap, Map<String, BigDecimal> valorIndiceQuatroMap, Map<String, BigDecimal> valorIndiceCincoMap) {

        
        setValorIndiceUmMap(valorIndiceUmMap);
        setValorIndiceDoisMap(valorIndiceDoisMap);
        setValorIndiceTresMap(valorIndiceTresMap);
        setValorIndiceQuatroMap(valorIndiceQuatroMap);
        setValorIndiceCincoMap(valorIndiceCincoMap);
        
        
        
        
        this.setTp(telaPrincipalController);
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

    /**
     * @return the txtDataInicioCorrecao
     */
    public JFXTextField getTxtDataInicioCorrecao() {
        return txtDataInicioCorrecao;
    }

    /**
     * @param txtDataInicioCorrecao the txtDataInicioCorrecao to set
     */
    public void setTxtDataInicioCorrecao(JFXTextField txtDataInicioCorrecao) {
        this.txtDataInicioCorrecao = txtDataInicioCorrecao;
    }

    /**
     * @return the txtDataFinalCorrecao
     */
    public JFXTextField getTxtDataFinalCorrecao() {
        return txtDataFinalCorrecao;
    }

    /**
     * @param txtDataFinalCorrecao the txtDataFinalCorrecao to set
     */
    public void setTxtDataFinalCorrecao(JFXTextField txtDataFinalCorrecao) {
        this.txtDataFinalCorrecao = txtDataFinalCorrecao;
    }

    /**
     * @return the cmbIndice
     */
    public ComboBox<Indice> getCmbIndice() {
        return cmbIndice;
    }

    /**
     * @param cmbIndice the cmbIndice to set
     */
    public void setCmbIndice(ComboBox<Indice> cmbIndice) {
        this.cmbIndice = cmbIndice;
    }

    /**
     * @return the valorIndiceMap
     */
    public Map<String, BigDecimal> getValorIndiceMap() {
        return valorIndiceMap;
    }

    /**
     * @param valorIndiceMap the valorIndiceMap to set
     */
    public void setValorIndiceMap(Map<String, BigDecimal> valorIndiceMap) {
        this.valorIndiceMap = valorIndiceMap;
    }

    /**
     * @return the tp
     */
    public TelaPrincipalController getTp() {
        return tp;
    }

    /**
     * @param tp the tp to set
     */
    public void setTp(TelaPrincipalController tp) {
        this.tp = tp;
    }

    /**
     * @return the valorIndiceUmMap
     */
    public Map<String, BigDecimal> getValorIndiceUmMap() {
        return valorIndiceUmMap;
    }

    /**
     * @param valorIndiceUmMap the valorIndiceUmMap to set
     */
    public void setValorIndiceUmMap(Map<String, BigDecimal> valorIndiceUmMap) {
        this.valorIndiceUmMap = valorIndiceUmMap;
    }

    /**
     * @return the valorIndiceDoisMap
     */
    public Map<String, BigDecimal> getValorIndiceDoisMap() {
        return valorIndiceDoisMap;
    }

    /**
     * @param valorIndiceDoisMap the valorIndiceDoisMap to set
     */
    public void setValorIndiceDoisMap(Map<String, BigDecimal> valorIndiceDoisMap) {
        this.valorIndiceDoisMap = valorIndiceDoisMap;
    }

    /**
     * @return the valorIndiceTresMap
     */
    public Map<String, BigDecimal> getValorIndiceTresMap() {
        return valorIndiceTresMap;
    }

    /**
     * @param valorIndiceTresMap the valorIndiceTresMap to set
     */
    public void setValorIndiceTresMap(Map<String, BigDecimal> valorIndiceTresMap) {
        this.valorIndiceTresMap = valorIndiceTresMap;
    }

    /**
     * @return the valorIndiceQuatroMap
     */
    public Map<String, BigDecimal> getValorIndiceQuatroMap() {
        return valorIndiceQuatroMap;
    }

    /**
     * @param valorIndiceQuatroMap the valorIndiceQuatroMap to set
     */
    public void setValorIndiceQuatroMap(Map<String, BigDecimal> valorIndiceQuatroMap) {
        this.valorIndiceQuatroMap = valorIndiceQuatroMap;
    }

    /**
     * @return the valorIndiceCincoMap
     */
    public Map<String, BigDecimal> getValorIndiceCincoMap() {
        return valorIndiceCincoMap;
    }

    /**
     * @param valorIndiceCincoMap the valorIndiceCincoMap to set
     */
    public void setValorIndiceCincoMap(Map<String, BigDecimal> valorIndiceCincoMap) {
        this.valorIndiceCincoMap = valorIndiceCincoMap;
    }

    
    
    public void inputDataKeyTypedCPF(KeyEvent event) {

        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("###.###.###-##");

        tff.setCaracteresValidos("0123456789");
        tff.setTf(getTxtCpfCnpj());
        tff.formatter();

    }
    
//     @FXML
//    public void inputDataKeyTypedNPJ(KeyEvent event) {
//
//        TextFieldFormatter tff = new TextFieldFormatter();
//        tff.setMask("####/#######-##");
//
//        tff.setCaracteresValidos("0123456789");
//        tff.setTf(txtNPJ);
//        tff.formatter();
//
//    }

    /**
     * @return the txtCpfCnpj
     */
    public JFXTextField getTxtCpfCnpj() {
        return txtCpfCnpj;
    }

    /**
     * @param txtCpfCnpj the txtCpfCnpj to set
     */
    public void setTxtCpfCnpj(JFXTextField txtCpfCnpj) {
        this.txtCpfCnpj = txtCpfCnpj;
    }

    void passarNpjProtocoloAtualizado(TelaPrincipalController tp, Npj npj, ProtocoloGsv protocoloGSV) {
        setTp(tp) ;
        setNpj(npj);
        setProtocoloGsv(protocoloGSV);

    }
    
    
    @FXML
      public void calcularParaConferencia() {

          try {
          atualizarObjetoComDadosFormulario();
        if (getCalculo().getMetodologia().getId() == 2 && getCalculo().getListaPeriodoCalculo().get(0).getDataInicioCalculo() == null) {
            return;
        }

       

        MotorCalculoPoupanca motorCalculoPoupanca = new MotorCalculoPoupanca();
        motorCalculoPoupanca.calcularParaConferencia(getCalculo());
        setSaldoNaDataBase(getCalculo().getSaldoBase().add(getCalculo().getRemuneracaoBasica().add(getCalculo().getJurosCreditado())));

       

          getTp().passarCalculo(this, calculo);

        
             
        } catch (Exception e) {
            
            Utils.alertaGeral(null, null, "Erro no método calcularParaConferencia,  caso persista informe à equipe responsável \n" + e);
        }
          
    }

    /**
     * @return the saldoNaDataBase
     */
    public BigDecimal getSaldoNaDataBase() {
        return saldoNaDataBase;
    }

    /**
     * @param saldoNaDataBase the saldoNaDataBase to set
     */
    public void setSaldoNaDataBase(BigDecimal saldoNaDataBase) {
        this.saldoNaDataBase = saldoNaDataBase;
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

    private void atualizarObjetoComDadosFormulario() throws Exception {

        
        if(getCmbMetodologia().getValue()!=null){
              getCalculo().setMetodologia(getCmbMetodologia().getValue());
        }
        
        if(!getTxtCpfCnpj().getText().equals("")){
             getCalculo().getCliente().setCpf(getTxtCpfCnpj().getText());
        }
        
      if(!getTxtNome().getText().equals("")){
          getCalculo().getCliente().setNomeCliente(getTxtNome().getText());
      }
      
      if(getCmbBanco().getValue() !=null){
          getCalculo().setNomeBanco(getCmbBanco().getValue());
      }
       
        
      if(!getTxtConta().getText().equals("")){
            getCalculo().setNumeroConta(getTxtConta().getText());
      }
        
      if(!getTxtAgencia().getText().equals("")){
           getCalculo().setNumeroAgencia(Integer.parseInt(getTxtAgencia().getText()));
      }
      
      
      if(!getTxtSaldoBase().getText().equals("")){
           getCalculo().setSaldoBase(new BigDecimal(Utils.tratarVariavel(getTxtSaldoBase().getText())));
      }
      
       
      if(!getTxtDiaBase().getText().equals("")){
            getCalculo().setDiaBase(Integer.parseInt(getTxtDiaBase().getText()));
      }
      
      if(!getTxtJurMora().getText().equals("")){
           getCalculo().getMora().setDataInicio(Utils.formataData(getTxtJurMora().getText()));
      }
      
      if(!getTxtInitJurRem().getText().equals("")){
           getCalculo().getJuroRemuneratorio().setDataInicio(Utils.formataData(getTxtInitJurRem().getText()));
      }
       
      
      if(!getTxtFimJurRem().getText().equals('"')){
           getCalculo().getJuroRemuneratorio().setDataFinal(Utils.formataData(getTxtFimJurRem().getText()));
      }
      
       
       if(getCmbExpurgo().getValue()!=null){
           getCalculo().setExpurgo(getCmbExpurgo().getValue());
       }
       
       
        
        getCalculo().setPcond(getCkbPcond().isSelected());
        
        if(!getTxtDataInicioCorrecao().getText().equals("")){
             getCalculo().getListaPeriodoCalculo().get(0).setDataInicioCalculo(Utils.formataData(getTxtDataInicioCorrecao().getText()));
        }
        
        if(!getTxtDataFinalCorrecao().getText().equals("")){
            getCalculo().getListaPeriodoCalculo().get(0).setDataFinalCalculo(Utils.formataData(getTxtDataFinalCorrecao().getText()));
        }
       
        if(getCmbIndice().getValue()!=null){
              getCalculo().getListaPeriodoCalculo().get(0).setIndice(getCmbIndice().getValue());
        }
        
        
        if(getCmbPlanoEconomico().getValue()!=null){
             getCalculo().setPlanoEconomico(getCmbPlanoEconomico().getValue());
        }
      
       


    }
    
}
