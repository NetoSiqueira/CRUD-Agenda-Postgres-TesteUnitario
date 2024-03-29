package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.ConnectionFactory;
import model.Pessoa;



public class ContatoRepository {
	// a conex com o banco de dados
	private Connection connection;

	public ContatoRepository() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void addContato(Pessoa contato) {
		String sql = "INSERT INTO Pessoa(nivelDeAmizade ,nome, numero, conta) VALUES (?, ?, ?, ?)";

		try {
			// prepared statement para inserir
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setInt(1, contato.getNivelDeAmizade());
			stmt.setString(2, contato.getNome());
			stmt.setLong(3, contato.getNumero());
			stmt.setFloat(4, contato.getConta());

			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public void deleteContato(Pessoa contato) {
		String sql = "DELETE FROM Pessoa WHERE numero = ?";

		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setFloat(1, contato.getNumero());

			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public ArrayList<Pessoa> getListContatos() {
		String sql = "SELECT * FROM Pessoa;";
		ArrayList<Pessoa> listaContato = new ArrayList<Pessoa>();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int nivelDeAmizade = Integer.parseInt(rs.getString("nivelDeAmizade"));
				String nome = rs.getString("nome");
				long numero = Long.parseLong(rs.getString("numero"));
				float conta = Float.parseFloat(rs.getString("conta"));

				Pessoa contato = new Pessoa(nivelDeAmizade, nome, numero, conta);
				

				listaContato.add(contato);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return listaContato;
	}

	public Pessoa getaluById(long numero) {
		String sql = "SELECT * FROM Pessoa WHERE matricula = ?;";

		Pessoa contato = new Pessoa();
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setLong(1, numero);

			// executa
			// stmt.execute();

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				contato.setNumero(numero);
				contato.setConta(Float.parseFloat(rs.getString("conta")));
				contato.setNome(rs.getString("nome"));
				contato.setNivelDeAmizade(Integer.parseInt(rs.getString("nivelDeAmizade")));
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return contato;
	}

}
