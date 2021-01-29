package br.com.nhamnham.service;

import br.com.nhamnham.model.Enterprise;
import br.com.nhamnham.model.User;
import br.com.nhamnham.resource.enumeration.Roles;
import br.com.nhamnham.service.exception.DuplicatedException;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@ApplicationScoped
public class UserService {

    @Transactional
    public void insert(User user, String enterprise){
        if(User.exists(user.getUsername())){
            throw new DuplicatedException("Usuário já cadastrado");
        }
        if(nonNull(enterprise) && !Enterprise.findByIdOptional(new ObjectId(enterprise)).isPresent()){
            throw new NotFoundException("Empresa do usuário não localizada");
        }
        user.setEnterpriseId(enterprise);
        User.insert(user);
    }

    @Transactional
    public void configureRoles(List<Roles> roles, String id) {
        User user = (User) User.findByIdOptional(new ObjectId(id)).orElseThrow(()-> new NotFoundException("Usuário não localizado"));
        user.setRoles(roles.stream().map(Roles::getName).collect(Collectors.toSet()));
        User.persistOrUpdate(user);
    }

    public List<User> listByEnterprise(String enterprise) {
        if(nonNull(enterprise)){
            return User.findByEnterprise(enterprise).collect(Collectors.toList());
        }

        return User.findAll().list();
    }
}
