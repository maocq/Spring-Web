package co.com.maocq.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="usuario")
public class Usuario {

	@Id
	@Column(name = "id")
	private int id;

    @NotNull
    @Size(min = 3, max = 50) 
	@Column(name = "nombre")
	private String name;

	@Column(name = "email")
	private String email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", name=" + name + ", email=" + email + "]";
	}

}
