package org.example.services;

import org.example.stereotypes.Autowired;
import org.example.stereotypes.Injectable;

@Injectable
public class CustomerService {
    @Autowired
    private NestedLevel1Service nestedService;
    public String hello() {
        return "Customer Service, " + this.nestedService.nested();
    }
}
