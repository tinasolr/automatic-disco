/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import DAO.*;
import Model.*;
import java.util.*;

/**
 *
 * @author tinar
 */
public class MediosCtrl {

    List<Medios> medSw = new ArrayList<>();

    public void buscarMedios(List<MediosDB> m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Medios> getMedSw() {        return medSw;    }
    public void setMedSw(List<Medios> medSw) {        this.medSw = medSw;    }

}
