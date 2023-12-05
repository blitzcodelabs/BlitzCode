package org.blitzcode.api.repository;

import org.blitzcode.api.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}