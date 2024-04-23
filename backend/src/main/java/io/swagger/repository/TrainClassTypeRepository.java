package io.swagger.repository;

import io.swagger.model.TrainClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainClassTypeRepository extends JpaRepository<TrainClassType, Integer> {

    @Query(value = "SELECT MAX(class_type_id) FROM TrainClassType", nativeQuery = true)
    Integer findMaxClassTypeId();

    boolean existsByClassTypeId(Integer classTypeId);

    TrainClassType findByClassTypeName(String name);

}
