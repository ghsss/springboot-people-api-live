package com.digitalinnovationone.personapi.controller;

import com.digitalinnovationone.personapi.dto.MessageResponseDTO;
import com.digitalinnovationone.personapi.dto.request.PersonDTO;
import com.digitalinnovationone.personapi.exception.PersonNotFound;
import com.digitalinnovationone.personapi.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/api/v1/people")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private PersonService personService;
    //TROCADO POR @AllArgsConstructor(onConstr...
    /*@Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }*/

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createPerson(@RequestBody @Valid PersonDTO personDTO){
        return personService.createPerson(personDTO);
    }
    @GetMapping
    public List<PersonDTO> findAll(){
        return personService.findAll();
    }
    @GetMapping("/{id}")
    public PersonDTO findByID(@PathVariable Long id) throws PersonNotFound {
        return personService.findByID(id);
    }
    @PutMapping("/{id}")
    public MessageResponseDTO putByID(@PathVariable Long id,@RequestBody @Valid PersonDTO personDTO) throws PersonNotFound{
        return personService.updateByID(id, personDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByID(@PathVariable Long id) throws PersonNotFound {
        personService.delete(id);
    }
}
