package model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
// ho dovuto importare tutto jakarta persistence altrimenti non funzionava join column
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
@Table(name="utente")
public class Utente implements Serializable {
	/**
	 * @return the idUtente
	 */
	public Long getIdUtente() {
		return idUtente;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @return the libriPresiInPrestito
	 */
	public int getLibriPresiInPrestito() {
		return libriPresiInPrestito;
	}
	/**
	 * @return the libri
	 */
	public List<Libro> getLibri() {
		return libri;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_utente")
	private Long idUtente;
	private String nome;
	private String cognome;
	private String email;
	private String telefono;
	@Column(name="libri_presi_in_prestito")
	private int libriPresiInPrestito;
	@ManyToMany
	@JoinTable(
			name="libro_utente",
			joinColumns=@JoinColumn(name="id_utente"),
			inverseJoinColumns=@JoinColumn(name="id_libro")
	)
	@JsonFormat
	private List<Libro> libri;

}
