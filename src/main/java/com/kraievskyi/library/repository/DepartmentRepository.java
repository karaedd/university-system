package com.kraievskyi.library.repository;

import com.kraievskyi.library.model.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
