package model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data 
@Table(name="libro")
public class Libro implements Serializable {
	/**
	 * @param quantita the quantita to set
	 */
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	/**
	 * @return the idLibro
	 */
	public Long getIdLibro() {
		return idLibro;
	}
	/**
	 * @return the titolo
	 */
	public String getTitolo() {
		return titolo;
	}
	/**
	 * @return the autore
	 */
	public String getAutore() {
		return autore;
	}
	/**
	 * @return the editore
	 */
	public String getEditore() {
		return editore;
	}
	/**
	 * @return the quantita
	 */
	public int getQuantita() {
		return quantita;
	}
	/**
	 * @return the seriale
	 */
	public Long getSeriale() {
		return seriale;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_libro")
	private Long idLibro;
	private String titolo;
	private String autore;
	private String editore;
	private int quantita;
	private Long seriale;

}
