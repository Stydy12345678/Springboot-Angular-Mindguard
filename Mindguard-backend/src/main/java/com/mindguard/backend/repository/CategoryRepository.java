//package com.mindguard.backend.repository;
//
//public interface CategoryRepository {
//
//}
package com.mindguard.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindguard.backend.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}