/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vista;

/**
 *
 * @author tinar
 */
public class CopiasPOJO {
//copia_id, m.medio_id, medio_nom, cp_obs, form_nom, u.ubi_id, ubi_obs, medio_manual, medio_caja, medio_imagen
    private String codigo;
    private String medCodigo;
    private String medNomb;
    private String observ;
    private String formato;
    private String ubicacion;
    private String ubiObserv;
    private boolean manual;
    private boolean caja;
    private String imagen;

    public CopiasPOJO() {
    }

    public CopiasPOJO(String codigo, String medCodigo, String medNomb, String observ, String formato, String ubicacion, String ubiObserv, boolean manual, boolean caja, String imagen) {
        this.codigo = codigo;
        this.medCodigo = medCodigo;
        this.medNomb = medNomb;
        this.observ = observ;
        this.formato = formato;
        this.ubicacion = ubicacion;
        this.ubiObserv = ubiObserv;
        this.manual = manual;
        this.caja = caja;
        this.imagen = imagen;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMedCodigo() {
        return medCodigo;
    }

    public void setMedCodigo(String medCodigo) {
        this.medCodigo = medCodigo;
    }

    public String getMedNomb() {
        return medNomb;
    }

    public void setMedNomb(String medNomb) {
        this.medNomb = medNomb;
    }

    public String getObserv() {
        return observ;
    }

    public void setObserv(String observ) {
        this.observ = observ;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUbiObserv() {
        return ubiObserv;
    }

    public void setUbiObserv(String ubiObserv) {
        this.ubiObserv = ubiObserv;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public boolean isCaja() {
        return caja;
    }

    public void setCaja(boolean caja) {
        this.caja = caja;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    
}
