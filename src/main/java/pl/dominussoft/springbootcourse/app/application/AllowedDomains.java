package pl.dominussoft.springbootcourse.app.application;

import java.util.List;

//@Service
public class AllowedDomains {

    //    @Value("")
    List<String> accessList;

    public AllowedDomains(List<String> accessList) {
        this.accessList = accessList;
    }

    public List<String> getAccessList() {
        return accessList;
    }
}
