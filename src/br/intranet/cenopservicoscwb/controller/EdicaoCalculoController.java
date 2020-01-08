/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.intranet.cenopservicoscwb.controller;

import br.com.intranet.cenopservicoscwb.model.entidade.Metodologia;
import br.com.intranet.cenopservicoscwb.model.entidade.PlanoEconomico;
import br.intranet.cenopservicoscwb.dao.MetodologiaDAO;
import br.intranet.cenopservicoscwb.dao.PlanoEconomicoDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author f5078775
 */
public class EdicaoCalculoController extends  AbstractController implements Initializable {
    
    private Metodologia metodologia;
    private MetodologiaDAO<Metodologia, Object> metodologiaDAO;
    private PlanoEconomico planoEconomico;
    private PlanoEconomicoDAO<PlanoEconomico, Object> planoEconomicoDAO;
    

  
    
    
    
    
    @FXML
    private JFXButton btn_buscarTable;
    @FXML
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
       
         setPlanoEconomicoDAO(new PlanoEconomicoDAO<>());
        
         setMetodologiaDAO(new MetodologiaDAO<>());
         setMetodologia(getCmbMetodologia().getValue());
        
         List<Metodologia> listaMetodologia = new ArrayList<>();
         listaMetodologia = getMetodologiaDAO().getListaTodos();
         
        
        
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
    
}
