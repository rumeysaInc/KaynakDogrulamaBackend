package org.dogrula.kaynakdogrulamabackend.repository;


import org.dogrula.kaynakdogrulamabackend.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Long> {
}

