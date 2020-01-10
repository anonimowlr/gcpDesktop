/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.model.entidade.Indice;
import java.util.List;

/**
 *
 * @author f5078775
 */
public class IndiceDAO<T, E> extends DAOGenerico<Indice, Object>{
    
    private List<Indice> listaIndiceSemTr;
    

    public IndiceDAO() {
        super();
        classePersistente = Indice.class;
        ordem = "id";
        maximoObjeto = 50; 
    }
    
    
    
     public List<Indice> getListaIndiceSemTr() {

            String jpql = "From  Indice i where i.id <> 4  order by " + ordem;

        return em.createQuery(jpql).getResultList();
    }
    
    
    
        
}
