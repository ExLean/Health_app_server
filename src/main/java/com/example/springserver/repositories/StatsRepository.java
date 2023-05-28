package com.example.springserver.repositories;

import com.example.springserver.models.Stats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends CrudRepository<Stats, Integer> {
}
