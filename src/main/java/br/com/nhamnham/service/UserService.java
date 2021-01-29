package br.com.nhamnham.service;

import br.com.nhamnham.model.Enterprise;
import br.com.nhamnham.model.User;
import br.com.nhamnham.resource.enumeration.Roles;
import br.com.nhamnham.service.exception.DuplicatedException;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Transactional
    public void insert(User user){
        if(User.exists(user.getUsername())){
            throw new DuplicatedException("Usuário já cadastrado");
        }
        if(!Enterprise.findByIdOptional(new ObjectId(user.getEnterpriseId())).isPresent()){
            throw new NotFoundException("Empresa do usuário não localizada");
        }
        User.insert(user);
    }

    @Transactional
    public void configureRoles(List<Roles> roles, String id) {
        User user = (User) User.findByIdOptional(new ObjectId(id)).orElseThrow(()-> new NotFoundException("Usuário não localizado"));
        user.setRoles(roles.stream().map(r-> r.getName()).collect(Collectors.toSet()));
        User.persistOrUpdate(user);
    }
}
