package br.com.sneacker.sneackerjava.repository;

import br.com.sneacker.sneackerjava.model.Sneaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SneakerRepository extends JpaRepository<Sneaker, Long> {
}
