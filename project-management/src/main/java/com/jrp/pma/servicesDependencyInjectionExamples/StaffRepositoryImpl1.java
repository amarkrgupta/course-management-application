package com.jrp.pma.servicesDependencyInjectionExamples;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class StaffRepositoryImpl1 implements IStaffRepository{

}
