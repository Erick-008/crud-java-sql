package dao;

import model.UsuarioModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    private Connection connection;

    public UsuarioDao(Connection connection) {
        this.connection = connection;
    }

    public void create(UsuarioModel usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, senha, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW())";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.executeUpdate();
        }
    }
    public void update(UsuarioModel usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ?, updated_at = NOW() WHERE id = ? AND deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getId());
            stmt.executeUpdate();
        }
    }
    public void softDelete(int id) throws SQLException {
        String sql = "UPDATE usuarios SET deleted_at = NOW() WHERE id = ? AND deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public UsuarioModel findById(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ? AND deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToModel(rs);
                }
            }
        }
        return null;
    }

    public List<UsuarioModel> findAll() throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE deleted_at IS NULL ORDER BY id DESC";
        List<UsuarioModel> usuarios = new ArrayList<>();

        try (var stmt = connection.prepareStatement(sql);
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapRowToModel(rs));
            }
        }
        return usuarios;
    }

    private UsuarioModel mapRowToModel(ResultSet rs) throws SQLException {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        usuario.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        usuario.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        return usuario;
    }
}
