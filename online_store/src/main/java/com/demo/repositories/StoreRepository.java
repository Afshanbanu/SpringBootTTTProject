package com.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entities.Store;

public interface StoreRepository extends JpaRepository<Store, Integer>{

}
