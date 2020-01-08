/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.intranet.cenopservicoscwb.controller;

import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
import br.intranet.cenopservicoscwb.dao.CalculoDAO;
import br.intranet.cenopservicoscwb.dao.PessoaDAO;
import br.intranet.cenopservicoscwb.model.entidades.Pessoa;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import testejavafx.MainApp;

/**
 * FXML Controller class
 *
 * @author f5078775
 */
public class TelaPrincipalController implements Initializable {

    private MainApp mainApp;
    
    
     private List<Calculo> listaCalculo = new ArrayList<>();
   
    private TableView<Pessoa> tblPessoa;
    private TableColumn<Pessoa, String> colNome;
    private TableColumn<Pessoa, Integer> colIdade;
    @FXML
    private TableView<Calculo> tblCalculoPoupanca;
    @FXML
    private TableColumn<Calculo, Integer> colId;
    @FXML
    private TableColumn<Calculo, Long> colNpj;
    
    private CalculoDAO<Calculo, Object> calculoDAO;
   
    @FXML
    private Label lbMensagemNavegacao;
    @FXML
    private JFXTextField txtFiltroQuantidadeReg;
    @FXML
    private AnchorPane anchorCalcEdit;
    @FXML
    private Button btnConsultar;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        
        getTblCalculoPoupanca().refresh();
        
        setCalculoDAO(new CalculoDAO<>());
        
        
        txtFiltroQuantidadeReg.setText(getCalculoDAO().getMaximoObjeto().toString());
        
        
      //  getCmbEscolha().getItems().addAll("M", "F");
        // TODO
    }
    
     public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void popularTabela(ActionEvent event) {
        
        
          List<Pessoa> listaPessoa = new ArrayList<>();
        
        PessoaDAO pessoaDao = new PessoaDAO();
        ObservableList<Pessoa> observableListPessoa = FXCollections.observableArrayList();
        

        getColNome().setCellValueFactory(new PropertyValueFactory<>("nome"));// atributo da entidade
        getColIdade().setCellValueFactory(new PropertyValueFactory<>("idade")); // atributo da entidade
       
        observableListPessoa = FXCollections.observableList(listaPessoa);
        getTblPessoa().setItems(observableListPessoa);
        getLbMensagemNavegacao().setText(getCalculoDAO().mensagemNavegacao());
        
        
        
        
        
        
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
     * @return the tblPessoa
     */
    public TableView<Pessoa> getTblPessoa() {
        return tblPessoa;
    }

    /**
     * @param tblPessoa the tblPessoa to set
     */
    public void setTblPessoa(TableView<Pessoa> tblPessoa) {
        this.tblPessoa = tblPessoa;
    }

    /**
     * @return the colNome
     */
    public TableColumn<Pessoa, String> getColNome() {
        return colNome;
    }

    /**
     * @param colNome the colNome to set
     */
    public void setColNome(TableColumn<Pessoa, String> colNome) {
        this.colNome = colNome;
    }

    /**
     * @return the colIdade
     */
    public TableColumn<Pessoa, Integer> getColIdade() {
        return colIdade;
    }

    /**
     * @param colIdade the colIdade to set
     */
    public void setColIdade(TableColumn<Pessoa, Integer> colIdade) {
        this.colIdade = colIdade;
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

    @FXML
    private void chamaFxml(ActionEvent event) {
        
        String path = "/br/intranet/cenopservicoscwb/views/EdicaoCalculo.fxml";
        
        EdicaoCalculoController adicaoCalculoController = new EdicaoCalculoController();
        adicaoCalculoController = (EdicaoCalculoController) mainApp.showCenterAnchorPaneWithReturn(path, adicaoCalculoController, anchorCalcEdit);
        
        
    }



    /**
     * @param mainApp the mainApp to set
     */
    
}
