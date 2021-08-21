package com.digitalinnovationone.personapi.service;

import com.digitalinnovationone.personapi.dto.request.PersonDTO;
import com.digitalinnovationone.personapi.dto.MessageResponseDTO;
import com.digitalinnovationone.personapi.entity.Person;
import com.digitalinnovationone.personapi.exception.PersonNotFound;
import com.digitalinnovationone.personapi.mapper.PersonMapper;
import com.digitalinnovationone.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    private final PersonRepository personRepository;


    @Autowired
    public PersonService(PersonRepository personRepository){

        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO){
        Person personToSave = personMapper.toModel(personDTO);

        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created person with ID : ");
    }

    public List<PersonDTO> findAll () {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findByID (Long id) throws PersonNotFound {
        Person person = verifyIfExists(id);
        return personMapper.toDTO(person);
    }

    public void delete (Long id) throws PersonNotFound {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateByID (Long id, PersonDTO personDTO) throws PersonNotFound {
        verifyIfExists(id);
        Person personToUpdate = personMapper.toModel(personDTO);

        Person updatedPerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Updated person with ID : ");
    }

    private Person verifyIfExists (Long id) throws PersonNotFound{
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFound(id));
    }

    private MessageResponseDTO createMessageResponse (Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }

}
