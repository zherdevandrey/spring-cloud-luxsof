package com.example.clientservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ClientRest {
    @Autowired
    private ClientDAO dao;

    @Autowired
    private ClientRepository repo;

    @RequestMapping("/create")
    public Client create(@RequestParam String name) {
        log.info("create client request");
        return dao.create(name);
    }

    @RequestMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestParam String name) {
        log.info("update client request");
        if (dao.update(id, name)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        log.info("delete client request");
        repo.deleteById(id);
    }

    @RequestMapping("/get")
    public List<? extends Client> get() {
        log.info("get all clients request");
        return repo.findAll();
    }

    @RequestMapping("/get/{id}")
    public Client get(@PathVariable Integer id) {
        log.info("get client request");
        return repo.findById(id).orElseThrow(RuntimeException::new);
    }
}
