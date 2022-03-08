package com.company.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    //can be understood by spring data jpa and create own sql statement
    //for count by id, automatically generated
    public Long countById(Integer id);
}
