package com.courierandlogistics.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.courierandlogistics.entity.WareHouse;

public interface WareHouseRepository extends JpaRepository<WareHouse, Integer> 
{
	@Query("select w from WareHouse w where w.location=?1")
	Optional<WareHouse> findWareHouseByLocation(@Param("location") String l); //findByWareHouse(String wareHouse);
	
	@Query("select w from WareHouse w where w.capacity>?1")
	List<WareHouse> findWareHousesByCapacityGreaterThan( int capacity); //findByCapacityGreaterThan(Integer capacity);

}
