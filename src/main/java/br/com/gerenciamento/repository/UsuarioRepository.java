package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select e from Usuario e where e.email = :email")
    public Usuario findByEmail(@Param("email") String email);
    
    @Query("select u from Usuario u where u.user = :user")
    public Usuario findByUser(@Param("user") String user);

    @Query("select l from Usuario l where (l.user = :login or l.email = :login) and l.senha = :senha")
    public Usuario buscarLogin(@Param("login") String login, @Param("senha") String senha);

}