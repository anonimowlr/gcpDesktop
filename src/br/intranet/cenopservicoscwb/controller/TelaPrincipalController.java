/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.intranet.cenopservicoscwb.controller;

import CurrencyField.CurrencyField;
import CurrencyField.TextFieldFormatter;
import br.com.intranet.cenopservicoscwb.model.dao.Dao;
import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
import br.com.intranet.cenopservicoscwb.model.entidade.Cliente;
import br.com.intranet.cenopservicoscwb.model.entidade.Honorario;
import br.com.intranet.cenopservicoscwb.model.entidade.Metodologia;
import br.com.intranet.cenopservicoscwb.model.entidade.Multa;
import br.com.intranet.cenopservicoscwb.model.entidade.Npj;
import br.com.intranet.cenopservicoscwb.model.entidade.ProtocoloGsv;
import br.intranet.cenopservicoscwb.dao.CalculoDAO;
import br.intranet.cenopservicoscwb.dao.NpjDAO;
import br.intranet.cenopservicoscwb.dao.ProtocoloGsvDAO;
import br.intranet.cenopservicoscwb.util.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import main.MainApp;

/**
 * FXML Controller class
 *
 * @author f5078775
 */
public class TelaPrincipalController extends AbstractController implements Initializable {

    private MainApp mainApp;

    private List<Calculo> listaCalculo = new ArrayList<>();
    private Calculo calculo;
    private Npj npj;
    private ProtocoloGsv protocoloGSV;
    private ProtocoloGsvDAO<ProtocoloGsv, Object> protocoloGsvDAO;
    private NpjDAO<Npj, Object> npjDAO;
    private Multa multa;
    private Honorario honorario;
    private EdicaoCalculoController edicaoCalculoController;
    private Map<String, BigDecimal> valorIndiceUmMap;
    private Map<String, BigDecimal> valorIndiceDoisMap;
    private Map<String, BigDecimal> valorIndiceTresMap;
    private Map<String, BigDecimal> valorIndiceQuatroMap;
    private Map<String, BigDecimal> valorIndiceCincoMap;
    private EdicaoCalculoController  ec;
    private Dao dao;

    @FXML
    private TableView<Calculo> tblCalculoPoupanca;

    @FXML
    private TableColumn<Calculo, Long> colNpj;

    private CalculoDAO<Calculo, Object> calculoDAO;

    @FXML
    private Label lbMensagemNavegacao;
    @FXML
    private JFXTextField txtFiltroQuantidadeReg;
    @FXML
    private AnchorPane anchorCalcEdit;
    private Button btnConsultar;
    @FXML
    private JFXTextField txtNPJ;
    @FXML
    private JFXTextField txtGSV;
    @FXML
    private TableView<Calculo> tvTabelaCalculoEdicao;
    @FXML
    private Button btnConsultaGsv;
    private JFXButton btnEditarSelecionado;
    @FXML
    private TableColumn<Calculo, Integer> colId;
    private TableColumn<Calculo, Integer> colIdTbEdicao;
    @FXML
    private Label lblChaveFunci;
    @FXML
    private TableColumn<Calculo, Metodologia> colMetodologia;
    @FXML
    private TableColumn<Calculo, String> colValorFinal;
    @FXML
    private TableColumn<Calculo, Cliente> colCpfCnpj;
    @FXML
    private Label lblChave;
    @FXML
    private JFXTextField txtPercentHonor;
    @FXML
    private JFXTextField txtPercentMulta;
    private JFXButton btnAplicarParametros;
    @FXML
    private HBox hbAreaParaDadosConferencia;
    @FXML
    private Label lblIdConferencia;
    @FXML
    private Label lblSaldoConferencia;
    @FXML
    private Label lblRemBaseConferencia;
    @FXML
    private Label lblJurCredConferencia;
    @FXML
    private Label lblSaldoNaDataBaseConferencia;
    @FXML
    private AnchorPane ancorSecaoParametros;

    public TelaPrincipalController() {

    }
    private Thread t1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        CurrencyField cur = new CurrencyField();
        cur.amountProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue.doubleValue());
            }
        });

        getTvTabelaCalculoEdicao().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> editaLinha(newValue));

        setCalculo(new Calculo());
        setDao(new Dao());
        setValorIndiceUmMap((Map<String, BigDecimal>) new HashMap());
        setValorIndiceDoisMap((Map<String, BigDecimal>) new HashMap());
        setValorIndiceTresMap((Map<String, BigDecimal>) new HashMap());
        setValorIndiceQuatroMap((Map<String, BigDecimal>) new HashMap());
        setValorIndiceCincoMap((Map<String, BigDecimal>) new HashMap());
        carregarMapsIndices();

        setProtocoloGsvDAO(new ProtocoloGsvDAO<>());
        setNpjDAO(new NpjDAO<>());
        getTblCalculoPoupanca().refresh();

        setCalculoDAO(new CalculoDAO<>());
        getLblChave().setText(MainApp.getFunci().getChaveFunci());

        getTxtFiltroQuantidadeReg().setText(getCalculoDAO().getMaximoObjeto().toString());

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void popularTabelaCalculo(ActionEvent event) {

        getCalculoDAO().setMaximoObjeto(Integer.parseInt(getTxtFiltroQuantidadeReg().getText()));

        ObservableList<Calculo> observableListCalculo = FXCollections.observableArrayList();
        setListaCalculo(getCalculoDAO().getListaObjetos());

        getColNpj().setCellValueFactory(new PropertyValueFactory<>("numeroConta"));// atributo da entidade
        getColId().setCellValueFactory(new PropertyValueFactory<>("numeroAgencia")); // atributo da entidade

        observableListCalculo = FXCollections.observableList(getListaCalculo());
        getTblCalculoPoupanca().setItems(observableListCalculo);

        getLbMensagemNavegacao().setText(getCalculoDAO().mensagemNavegacao());

    }

    @FXML
    private void limparListaCalculo(ActionEvent event) {

        getListaCalculo().clear();
        getTblCalculoPoupanca().refresh();

    }

    /**
     * @return the tblCalculoPoupanca
     */
    public TableView<Calculo> getTblCalculoPoupanca() {
        return tblCalculoPoupanca;
    }

    /**
     * @param tblCalculoPoupanca the tblCalculoPoupanca to set
     */
    public void setTblCalculoPoupanca(TableView<Calculo> tblCalculoPoupanca) {
        this.tblCalculoPoupanca = tblCalculoPoupanca;
    }

    /**
     * @return the colId
     */
    public TableColumn<Calculo, Integer> getColId() {
        return colId;
    }

    /**
     * @param colId the colId to set
     */
    public void setColId(TableColumn<Calculo, Integer> colId) {
        this.colId = colId;
    }

    /**
     * @return the colNpj
     */
    public TableColumn<Calculo, Long> getColNpj() {
        return colNpj;
    }

    /**
     * @param colNpj the colNpj to set
     */
    public void setColNpj(TableColumn<Calculo, Long> colNpj) {
        this.colNpj = colNpj;
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
     * @return the mainApp
     */
    public MainApp getMainApp() {
        return mainApp;
    }

    /**
     * @return the lbMensagemNavegacao
     */
    public Label getLbMensagemNavegacao() {
        return lbMensagemNavegacao;
    }

    /**
     * @param lbMensagemNavegacao the lbMensagemNavegacao to set
     */
    public void setLbMensagemNavegacao(Label lbMensagemNavegacao) {
        this.lbMensagemNavegacao = lbMensagemNavegacao;
    }

    /**
     * @return the listaCalculo
     */
    public List<Calculo> getListaCalculo() {
        return listaCalculo;
    }

    /**
     * @param listaCalculo the listaCalculo to set
     */
    public void setListaCalculo(List<Calculo> listaCalculo) {
        this.listaCalculo = listaCalculo;
    }

    /**
     * @return the txtFiltroQuantidadeReg
     */
    public JFXTextField getTxtFiltroQuantidadeReg() {
        return txtFiltroQuantidadeReg;
    }

    /**
     * @param txtFiltroQuantidadeReg the txtFiltroQuantidadeReg to set
     */
    public void setTxtFiltroQuantidadeReg(JFXTextField txtFiltroQuantidadeReg) {
        this.txtFiltroQuantidadeReg = txtFiltroQuantidadeReg;
    }

//    
//    private void chamaFxml(ActionEvent event) {
//
//        String path = "/br/intranet/cenopservicoscwb/views/EdicaoCalculo.fxml";
//
//        EdicaoCalculoController adicaoCalculoController = new EdicaoCalculoController();
//        adicaoCalculoController = (EdicaoCalculoController) getMainApp().showCenterAnchorPaneWithReturn(path, adicaoCalculoController, getAnchorCalcEdit());
//
//    }
    /**
     * @return the anchorCalcEdit
     */
    public AnchorPane getAnchorCalcEdit() {
        return anchorCalcEdit;
    }

    /**
     * @param anchorCalcEdit the anchorCalcEdit to set
     */
    public void setAnchorCalcEdit(AnchorPane anchorCalcEdit) {
        this.anchorCalcEdit = anchorCalcEdit;
    }

    /**
     * @return the btnConsultar
     */
    public Button getBtnConsultar() {
        return btnConsultar;
    }

    /**
     * @param btnConsultar the btnConsultar to set
     */
    public void setBtnConsultar(Button btnConsultar) {
        this.btnConsultar = btnConsultar;
    }

    /**
     * @return the txtNPJ
     */
    public JFXTextField getTxtNPJ() {
        return txtNPJ;
    }

    /**
     * @param txtNPJ the txtNPJ to set
     */
    public void setTxtNPJ(JFXTextField txtNPJ) {
        this.txtNPJ = txtNPJ;
    }

    /**
     * @return the txtGSV
     */
    public JFXTextField getTxtGSV() {
        return txtGSV;
    }

    /**
     * @param txtGSV the txtGSV to set
     */
    public void setTxtGSV(JFXTextField txtGSV) {
        this.txtGSV = txtGSV;
    }

    /**
     * @return the tvTabelaCalculoEdicao
     */
    public TableView<Calculo> getTvTabelaCalculoEdicao() {
        return tvTabelaCalculoEdicao;
    }

    /**
     * @param tvTabelaCalculoEdicao the tvTabelaCalculoEdicao to set
     */
    public void setTvTabelaCalculoEdicao(TableView<Calculo> tvTabelaCalculoEdicao) {
        this.tvTabelaCalculoEdicao = tvTabelaCalculoEdicao;
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
     * @return the protocoloGSV
     */
    public ProtocoloGsv getProtocoloGSV() {
        return protocoloGSV;
    }

    /**
     * @param protocoloGSV the protocoloGSV to set
     */
    public void setProtocoloGSV(ProtocoloGsv protocoloGSV) {
        this.protocoloGSV = protocoloGSV;
    }

    @FXML
    public void consultaGsv(ActionEvent event) {

        // t1 = new Thread(() -> {
        try {
            limparComponentes();

            getTvTabelaCalculoEdicao().refresh();
            setNpj(new Npj());
            setProtocoloGSV(new ProtocoloGsv());

            getNpj().setNrPrc(new Long(getTxtNPJ().getText().replace("/", "").replace("-", "")));
            getProtocoloGSV().setCdPrc(Integer.parseInt(getTxtGSV().getText()));

            ProtocoloGsv protocoloGsv = getProtocoloGsvDAO().localizar(getProtocoloGSV().getCdPrc());
            Npj npj = getNpjDAO().localizar(getNpj().getNrPrc());

            if (npj != null) {
                setNpj(npj);
            }

            if (protocoloGsv != null) {
                setProtocoloGSV(protocoloGsv);
                if (!getNpj().equals(getNpjDAO().localizar(getProtocoloGSV().getNpj().getNrPrc()))) {
                    Utils.alertaGeralInformacao(null, null, "O protocolo " + getProtocoloGSV().getCdPrc().toString() + " " + "já está associado a outro NPJ:  " + getProtocoloGsvDAO().localizar(getProtocoloGSV().getCdPrc()).getNpj().getNrPrc().toString());
                    return;
                }

            }

            getNpj().adicionarProtocolo(getProtocoloGSV());

            if (protocoloGsv != null && protocoloGsv.getMulta() != null) {

                setMulta(protocoloGsv.getMulta());
                getProtocoloGSV().setMulta(getMulta());
                setHonorario(protocoloGsv.getHonorario());
                getProtocoloGSV().setHonorario(getHonorario());
                
                atualizaComponentesParametros();

            } else {
                getProtocoloGSV().setHonorario(new Honorario());
                getProtocoloGSV().setMulta(new Multa());
                
                atualizaComponentesParametros();
                
            }

            

            getProtocoloGSV().getMulta().setTaxaMulta(Utils.converterStringParaBigDecimal(Utils.tratarVariavel(getTxtPercentMulta().getText())));
            getProtocoloGSV().getHonorario().setTaxaHonorario(Utils.converterStringParaBigDecimal(Utils.tratarVariavel(getTxtPercentHonor().getText())));

            if (getProtocoloGSV().getListaCalculo().size() > 0) {
                popularTabelacalculoEdicao();

                chamaFormEdicao();
            } else {

                salvar();
                popularTabelacalculoEdicao();
                chamaFormEdicao();
            }
            getTvTabelaCalculoEdicao().refresh();

        } catch (Exception e) {
            Utils.alertaGeral(null, null, "Erro ao iniciar consulta de protocolo \n" + e);
           
        }
        // });

        //  t1.start();
    }

    
    
    
    
    public void salvar() {

        if (getNpjDAO().atualizar(getNpj())) {
            //Util.mensagemInformacao(getNpjDAO().getMensagem());

        } else {

            Utils.alertaGeral(null, null, getNpjDAO().getMensagem());
        }

    }

    private void carregarMapsIndices() {
        setValorIndiceUmMap(getDao().carregaIndiceMap(1));
        setValorIndiceDoisMap(getDao().carregaIndiceMap(2));
        setValorIndiceTresMap(getDao().carregaIndiceMap(3));
        setValorIndiceQuatroMap(getDao().carregaIndiceMap(4));
        setValorIndiceCincoMap(getDao().carregaIndiceMap(5));
    }

    public final void popularTabelacalculoEdicao() {

        setProtocoloGSV(getProtocoloGsvDAO().localizar(getProtocoloGSV().getCdPrc()));

        getTvTabelaCalculoEdicao().getItems().clear();
        getListaCalculo().clear();

        setListaCalculo(getProtocoloGSV().getListaCalculo());

        getCalculoDAO().setMaximoObjeto(Integer.parseInt(getTxtFiltroQuantidadeReg().getText()));
        ObservableList<Calculo> observableListCalculo = FXCollections.observableArrayList();

        observableListCalculo = FXCollections.observableArrayList();

        //getColIdTbEdicao().setCellValueFactory(new PropertyValueFactory<>("id")); // atributo da entidade
        getColMetodologia().setCellValueFactory(new PropertyValueFactory<>("metodologia")); // atributo da entidade
        getColValorFinal().setCellValueFactory((new PropertyValueFactory<>("valorFinal"))); // atributo da entidade
        getColCpfCnpj().setCellValueFactory((new PropertyValueFactory<>("cliente"))); // atributo da entidade

        observableListCalculo = FXCollections.observableList(getListaCalculo());
        getTvTabelaCalculoEdicao().setItems(observableListCalculo);
        
       

        //getLbMensagemNavegacao().setText(getCalculoDAO().mensagemNavegacao());
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

    /**
     * @return the btnConsultaGsv
     */
    public Button getBtnConsultaGsv() {
        return btnConsultaGsv;
    }

    /**
     * @param btnConsultaGsv the btnConsultaGsv to set
     */
    public void setBtnConsultaGsv(Button btnConsultaGsv) {
        this.btnConsultaGsv = btnConsultaGsv;
    }

    private void editarSelecionado(ActionEvent event) {

        Calculo calculo = getTvTabelaCalculoEdicao().getSelectionModel().getSelectedItem();

        System.out.println(calculo);

    }

    /**
     * @return the btnEditarSelecionado
     */
    public JFXButton getBtnEditarSelecionado() {
        return btnEditarSelecionado;
    }

    /**
     * @param btnEditarSelecionado the btnEditarSelecionado to set
     */
    public void setBtnEditarSelecionado(JFXButton btnEditarSelecionado) {
        this.btnEditarSelecionado = btnEditarSelecionado;
    }

    /**
     * @return the colIdTbEdicao
     */
    public TableColumn<Calculo, Integer> getColIdTbEdicao() {
        return colIdTbEdicao;
    }

    /**
     * @param colIdTbEdicao the colIdTbEdicao to set
     */
    public void setColIdTbEdicao(TableColumn<Calculo, Integer> colIdTbEdicao) {
        this.colIdTbEdicao = colIdTbEdicao;
    }

    private void editaLinha(Calculo c) {

        String path = "/br/intranet/cenopservicoscwb/views/EdicaoCalculo.fxml";

        EdicaoCalculoController edicaoCalculoController = new EdicaoCalculoController();
        edicaoCalculoController = (EdicaoCalculoController) getMainApp().showCenterAnchorPaneWithReturn(path, edicaoCalculoController, getAnchorCalcEdit());

        Calculo calculo = getTvTabelaCalculoEdicao().getSelectionModel().getSelectedItem();
        setCalculo(calculo);

        atualizaParametrosGerais();

        edicaoCalculoController.passarCalculo(this, getCalculo());

        //System.out.println(getTvTabelaCalculoEdicao().getSelectionModel().getSelectedItem().getValorFinal());
    }

    public void chamaFormEdicao() {
        String path = "/br/intranet/cenopservicoscwb/views/EdicaoCalculo.fxml";

        setEdicaoCalculoController(new EdicaoCalculoController());
        
        this.setEdicaoCalculoController((EdicaoCalculoController) getMainApp().showCenterAnchorPaneWithReturn(path, getEdicaoCalculoController(), getAnchorCalcEdit()));

        this.getEdicaoCalculoController().passarNpjProtocolo(this, getNpj(), getProtocoloGSV(), getValorIndiceUmMap(), getValorIndiceDoisMap(), getValorIndiceTresMap(), getValorIndiceQuatroMap(), getValorIndiceCincoMap());
    }

    /**
     * @return the lblChaveFunci
     */
    public Label getLblChaveFunci() {
        return lblChaveFunci;
    }

    /**
     * @param lblChaveFunci the lblChaveFunci to set
     */
    public void setLblChaveFunci(Label lblChaveFunci) {
        this.lblChaveFunci = lblChaveFunci;
    }

    /**
     * @return the colMetodologia
     */
    public TableColumn<Calculo, Metodologia> getColMetodologia() {
        return colMetodologia;
    }

    /**
     * @param colMetodologia the colMetodologia to set
     */
    public void setColMetodologia(TableColumn<Calculo, Metodologia> colMetodologia) {
        this.colMetodologia = colMetodologia;
    }

    /**
     * @return the colValorFinal
     */
    public TableColumn<Calculo, String> getColValorFinal() {
        return colValorFinal;
    }

    /**
     * @param colValorFinal the colValorFinal to set
     */
    public void setColValorFinal(TableColumn<Calculo, String> colValorFinal) {
        this.colValorFinal = colValorFinal;
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

    /**
     * @return the dao
     */
    public Dao getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(Dao dao) {
        this.dao = dao;
    }

    @FXML
    public void inputDataKeyTypedNPJ(KeyEvent event) {

        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("####/#######-##");

        tff.setCaracteresValidos("0123456789");
        tff.setTf(getTxtNPJ());
        tff.formatter();

    }

    /**
     * @return the t1
     */
    public Thread getT1() {
        return t1;
    }

    /**
     * @param t1 the t1 to set
     */
    public void setT1(Thread t1) {
        this.t1 = t1;
    }

    /**
     * @return the colCpfCnpj
     */
    public TableColumn<Calculo, Cliente> getColCpfCnpj() {
        return colCpfCnpj;
    }

    /**
     * @param colCpfCnpj the colCpfCnpj to set
     */
    public void setColCpfCnpj(TableColumn<Calculo, Cliente> colCpfCnpj) {
        this.colCpfCnpj = colCpfCnpj;
    }

    /**
     * @return the lblChave
     */
    public Label getLblChave() {
        return lblChave;
    }

    /**
     * @param lblChave the lblChave to set
     */
    public void setLblChave(Label lblChave) {
        this.lblChave = lblChave;
    }

    /**
     * @return the txtPercentHonor
     */
    public JFXTextField getTxtPercentHonor() {
        return txtPercentHonor;
    }

    /**
     * @param txtPercentHonor the txtPercentHonor to set
     */
    public void setTxtPercentHonor(JFXTextField txtPercentHonor) {
        this.txtPercentHonor = txtPercentHonor;
    }

    /**
     * @return the txtPercentMulta
     */
    public JFXTextField getTxtPercentMulta() {
        return txtPercentMulta;
    }

    /**
     * @param txtPercentMulta the txtPercentMulta to set
     */
    public void setTxtPercentMulta(JFXTextField txtPercentMulta) {
        this.txtPercentMulta = txtPercentMulta;
    }

    private void atualizaParametrosGerais() {

        getProtocoloGSV().getMulta().setTaxaMulta(Utils.converterStringParaBigDecimal(Utils.tratarVariavel(getTxtPercentMulta().getText())));
        getProtocoloGSV().getHonorario().setTaxaHonorario(Utils.converterStringParaBigDecimal(Utils.tratarVariavel(getTxtPercentHonor().getText())));
        
       

        getEdicaoCalculoController().passarNpjProtocoloAtualizado(this, getNpj(), getProtocoloGSV());
        
        
        
    }

    /**
     * @return the edicaoCalculoController
     */
    public EdicaoCalculoController getEdicaoCalculoController() {
        return edicaoCalculoController;
    }

    /**
     * @param edicaoCalculoController the edicaoCalculoController to set
     */
    public void setEdicaoCalculoController(EdicaoCalculoController edicaoCalculoController) {
        this.edicaoCalculoController = edicaoCalculoController;
    }

    /**
     * @return the btnAplicarParametros
     */
    public JFXButton getBtnAplicarParametros() {
        return btnAplicarParametros;
    }

    /**
     * @param btnAplicarParametros the btnAplicarParametros to set
     */
    public void setBtnAplicarParametros(JFXButton btnAplicarParametros) {
        this.btnAplicarParametros = btnAplicarParametros;
    }

    private void atualizaComponentesParametros() {

        if(getTxtPercentHonor().getText().equals("") || getTxtPercentMulta().getText().equals("") ){
          getTxtPercentHonor().setText(getProtocoloGSV().getHonorario().getTaxaHonorario().toString());
          getTxtPercentMulta().setText(getProtocoloGSV().getMulta().getTaxaMulta().toString());  
        }    
        


    }
    
    
      public void passarCalculo(EdicaoCalculoController  ec, Calculo calculo) {

        this.setEc(ec);
        
        if(calculo==null){
            return;
        }
        setCalculo(calculo);
        atualizaComponentes();
    }

    /**
     * @return the ec
     */
    public EdicaoCalculoController getEc() {
        return ec;
    }

    /**
     * @param ec the ec to set
     */
    public void setEc(EdicaoCalculoController ec) {
        this.ec = ec;
    }

    /**
     * @return the hbAreaParaDadosConferencia
     */
    public HBox getHbAreaParaDadosConferencia() {
        return hbAreaParaDadosConferencia;
    }

    /**
     * @param hbAreaParaDadosConferencia the hbAreaParaDadosConferencia to set
     */
    public void setHbAreaParaDadosConferencia(HBox hbAreaParaDadosConferencia) {
        this.hbAreaParaDadosConferencia = hbAreaParaDadosConferencia;
    }

    /**
     * @return the lblIdConferencia
     */
    public Label getLblIdConferencia() {
        return lblIdConferencia;
    }

    /**
     * @param lblIdConferencia the lblIdConferencia to set
     */
    public void setLblIdConferencia(Label lblIdConferencia) {
        this.lblIdConferencia = lblIdConferencia;
    }

    /**
     * @return the lblSaldoConferencia
     */
    public Label getLblSaldoConferencia() {
        return lblSaldoConferencia;
    }

    /**
     * @param lblSaldoConferencia the lblSaldoConferencia to set
     */
    public void setLblSaldoConferencia(Label lblSaldoConferencia) {
        this.lblSaldoConferencia = lblSaldoConferencia;
    }

    /**
     * @return the lblRemBaseConferencia
     */
    public Label getLblRemBaseConferencia() {
        return lblRemBaseConferencia;
    }

    /**
     * @param lblRemBaseConferencia the lblRemBaseConferencia to set
     */
    public void setLblRemBaseConferencia(Label lblRemBaseConferencia) {
        this.lblRemBaseConferencia = lblRemBaseConferencia;
    }

    /**
     * @return the lblJurCredConferencia
     */
    public Label getLblJurCredConferencia() {
        return lblJurCredConferencia;
    }

    /**
     * @param lblJurCredConferencia the lblJurCredConferencia to set
     */
    public void setLblJurCredConferencia(Label lblJurCredConferencia) {
        this.lblJurCredConferencia = lblJurCredConferencia;
    }

    /**
     * @return the lblSaldoNaDataBaseConferencia
     */
    public Label getLblSaldoNaDataBaseConferencia() {
        return lblSaldoNaDataBaseConferencia;
    }

    /**
     * @param lblSaldoNaDataBaseConferencia the lblSaldoNaDataBaseConferencia to set
     */
    public void setLblSaldoNaDataBaseConferencia(Label lblSaldoNaDataBaseConferencia) {
        this.lblSaldoNaDataBaseConferencia = lblSaldoNaDataBaseConferencia;
    }

    private void atualizaComponentes() {
        
        getLblSaldoConferencia().setText(Utils.converterToMoneySaldoBase(getCalculo().getSaldoBase().toString()));
        getLblRemBaseConferencia().setText(Utils.converterToMoneySaldoBase(getCalculo().getRemuneracaoBasica().toString()));
        getLblJurCredConferencia().setText(Utils.converterToMoneySaldoBase(getCalculo().getJurosCreditado().toString()));
        getLblSaldoNaDataBaseConferencia().setText(Utils.converterToMoneySaldoBase(getCalculo().getSaldoBase().add(getCalculo().getRemuneracaoBasica().add(getCalculo().getJurosCreditado())).toString()));

    }
    
    
    private void limparComponentes(){
          getLblSaldoConferencia().setText("");
        getLblRemBaseConferencia().setText("");
        getLblJurCredConferencia().setText("");
        getLblSaldoNaDataBaseConferencia().setText("");

    }


}
