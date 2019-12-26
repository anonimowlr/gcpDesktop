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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import testejavafx.MainApp;

/**
 * FXML Controller class
 *
 * @author f5078775
 */
public class TelaPrincipalController implements Initializable {

    private MainApp mainApp;
    
    
    
   
    @FXML
    private TableView<Pessoa> tblPessoa;
    @FXML
    private TableColumn<Pessoa, String> colNome;
    @FXML
    private TableColumn<Pessoa, Integer> colIdade;
    @FXML
    private TableView<Calculo> tblCalculoPoupanca;
    @FXML
    private TableColumn<Calculo, Integer> colId;
    @FXML
    private TableColumn<Calculo, Long> colNpj;
    
    private CalculoDAO<Calculo, Object> calculoDAO;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
       // setCalculoDAO(new CalculoDAO<>());
        
        
      //  getCmbEscolha().getItems().addAll("M", "F");
        // TODO
    }
    
     public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void popularTabela(ActionEvent event) {
        
        
          List<Pessoa> listaPessoa = new ArrayList<>();
        
        PessoaDAO pessoaDao = new PessoaDAO();
        ObservableList<Pessoa> observableListPessoa = FXCollections.observableArrayList();
        listaPessoa = pessoaDao.buscar();

        getColNome().setCellValueFactory(new PropertyValueFactory<>("nome"));// atributo da entidade
        getColIdade().setCellValueFactory(new PropertyValueFactory<>("idade")); // atributo da entidade
       
        observableListPessoa = FXCollections.observableList(listaPessoa);
        getTblPessoa().setItems(observableListPessoa);
        
        
        
    }
    
    
    private void popularTabelaCalculo(ActionEvent event) {
        
        
          List<Calculo> listaPessoa = new ArrayList<>();
        
        
        
        
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
     * @param mainApp the mainApp to set
     */
    
}
