package br.com.nhamnham.service;

import br.com.nhamnham.model.Enterprise;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class EnterpriseService {

    @Transactional
    public void insert(Enterprise enterprise){
        Enterprise.persist(enterprise);
    }

    @Transactional
    public void delete(String id) {
        Enterprise.deleteById(new ObjectId(id));
    }

    @Transactional
    public void update(Enterprise enterprise) {
        Enterprise.update(enterprise);
    }

    public List<Enterprise> listAll() {
        return Enterprise.listAll();
    }
}
