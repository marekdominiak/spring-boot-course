package pl.dominussoft.springbootcourse.app.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllowedDomains {

    List<String> accessList;

    public AllowedDomains(@Value("#{'${aplication.allowedDomains:@example.com,@example1.com,@example2.com}'.split(',')}")
                          List<String> accessList) {
        this.accessList = accessList;
    }

    public List<String> getAccessList() {
        return accessList;
    }
}
