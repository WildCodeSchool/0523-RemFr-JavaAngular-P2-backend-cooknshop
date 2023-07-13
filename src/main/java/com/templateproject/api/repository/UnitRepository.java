package com.templateproject.api.repository;

import com.templateproject.api.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    Optional<Unit> findUnitByName(String name);

    List<Unit> findUnitsByNameContaining(String name);

    List<Unit> findByNameContaining(String name);
}
