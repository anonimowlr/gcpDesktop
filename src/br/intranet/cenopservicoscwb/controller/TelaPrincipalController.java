/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.intranet.cenopservicoscwb.controller;

import CurrencyField.CurrencyField;
import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.layout.AnchorPane;
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
    
     

    public TelaPrincipalController() {

    }

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
        setProtocoloGsvDAO(new ProtocoloGsvDAO<>());
        setNpjDAO(new NpjDAO<>());

        getTblCalculoPoupanca().refresh();

        setCalculoDAO(new CalculoDAO<>());

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
        getTvTabelaCalculoEdicao().refresh();
        setNpj(new Npj());
        setProtocoloGSV(new ProtocoloGsv());

        getNpj().setNrPrc(new Long(getTxtNPJ().getText()));
        getProtocoloGSV().setCdPrc(Integer.parseInt(getTxtGSV().getText()));

        ProtocoloGsv protocoloGsv = getProtocoloGsvDAO().localizar(getProtocoloGSV().getCdPrc());
        Npj npj = getNpjDAO().localizar(getNpj().getNrPrc());

        if (npj != null) {
            setNpj(npj);
        }

        if (protocoloGsv != null) {
            setProtocoloGSV(protocoloGsv);

        }

        getNpj().adicionarProtocolo(getProtocoloGSV());

        if (protocoloGsv != null && protocoloGsv.getMulta() != null) {

            setMulta(protocoloGsv.getMulta());
            getProtocoloGSV().setMulta(getMulta());
            setHonorario(protocoloGsv.getHonorario());
            getProtocoloGSV().setHonorario(getHonorario());

        } else {
            getProtocoloGSV().setHonorario(new Honorario());
            getProtocoloGSV().setMulta(new Multa());
        }

        if (getProtocoloGSV().getListaCalculo().size() > 0) {
            popularTabelacalculoEdicao();

            chamaFormEdicao();
        } else {

            salvar();
            popularTabelacalculoEdicao();
            chamaFormEdicao();
        }
        getTvTabelaCalculoEdicao().refresh();

    }

    public void salvar() {

        if (getNpjDAO().atualizar(getNpj())) {
            //Util.mensagemInformacao(getNpjDAO().getMensagem());

        } else {

            Utils.alertaGeral(null, null, getNpjDAO().getMensagem());
        }

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

        edicaoCalculoController.passarCalculo(this, getCalculo());

        System.out.println(getTvTabelaCalculoEdicao().getSelectionModel().getSelectedItem().getValorFinal());

    }

    public void chamaFormEdicao() {
        String path = "/br/intranet/cenopservicoscwb/views/EdicaoCalculo.fxml";

        EdicaoCalculoController edicaoCalculoController = new EdicaoCalculoController();
        edicaoCalculoController = (EdicaoCalculoController) getMainApp().showCenterAnchorPaneWithReturn(path, edicaoCalculoController, getAnchorCalcEdit());

        edicaoCalculoController.passarNpjProtocolo(this, getNpj(), getProtocoloGSV());
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

}
