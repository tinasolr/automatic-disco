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
public class MediosTableFormat {

       private String codigo;
       private  String nombre;
       private String formato;
       private String original;
       private String ubiDepo;
       private String enDepo;
       private Integer copias;

    public MediosTableFormat(String codigo, String nombre, String formato, String original, String ubiDepo, String enDepo, Integer copias) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.formato = formato;
        this.original = original;
        this.ubiDepo = ubiDepo;
        this.enDepo = enDepo;
        this.copias = copias;
    }

    public MediosTableFormat() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String isOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getUbiDepo() {
        return ubiDepo;
    }

    public void setUbiDepo(String ubiDepo) {
        this.ubiDepo = ubiDepo;
    }

    public String isEnDepo() {
        return enDepo;
    }

    public void setEnDepo(String enDepo) {
        this.enDepo = enDepo;
    }

    public Integer getCopias() {
        return copias;
    }

    public void setCopias(Integer copias) {
        this.copias = copias;
    }


}
