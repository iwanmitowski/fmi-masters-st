package org.example.services;

import org.example.stereotypes.Autowired;
import org.example.stereotypes.Injectable;

@Injectable
public class NestedLevel1Service {
    @Autowired
    private NestedLevel2Service nestedService;
    public String nested(){
        return "Nested Level 1, " + this.nestedService.nested();
    }
}
