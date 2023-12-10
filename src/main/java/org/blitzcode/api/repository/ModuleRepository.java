package org.blitzcode.api.repository;

import org.blitzcode.api.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface to interact with the Module table. findModuleByName() is used to fetch a module by name.
 */
public interface ModuleRepository extends JpaRepository<Module, Long> {
    Optional<Module> findModuleByName(String name);
}