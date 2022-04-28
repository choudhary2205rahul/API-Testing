package com.swisscom.junit.one.petclinic.repositories;


import com.swisscom.junit.one.petclinic.model.Owner;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByLastName(String lastName);

    List<Owner> findAllByLastNameLike(String lastName);
}
