package com.apirestdocker.course.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.apirestdocker.course.controller.PersonController;
import com.apirestdocker.course.data.vo.v1.PersonVO;
import com.apirestdocker.course.data.vo.v2.PersonVOV2;
import com.apirestdocker.course.exceptions.RequiredObjectIsNullException;
import com.apirestdocker.course.exceptions.ResourceNotFoundException;
import com.apirestdocker.course.mapper.DozerMapper;
import com.apirestdocker.course.mapper.custom.PersonMapper;
import com.apirestdocker.course.model.Person;
import com.apirestdocker.course.repository.PersonRepository;

@Service // indicate to Springboot knows that is an injectable object.
public class PersonService {

	private Logger logger = Logger.getLogger(PersonService.class.getName());

	@Autowired // dependency injection of repository
	PersonRepository repository;
	PersonMapper mapper;

	public PersonVO findById(Long id) {
		logger.info("Finding one PersonVO");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("no records for this Id"));
		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).getById(id)).withSelfRel());
		return vo;
	}
	

	public PersonVO create(PersonVO person) {

		if(person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one person!");
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).getById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public PersonVOV2 createV2(PersonVOV2 person) {

		logger.info("Creating one person!");
		var entity = mapper.convertVoToEntity(person);
		var vo =  mapper.convertEntityToVo(repository.save(entity));
		return vo;
	}

	public void delete(Long id) {
		
		logger.info("Deleting one person!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(entity);
	}

	public PersonVO update(PersonVO person) {
		if(person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating one person!");
		
		var entity = repository.findById(person.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).getById(vo.getKey())).withSelfRel());
		return vo;
	}

	public List<PersonVO> findAll() {
		logger.info("Find All");
		var people = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		
		people.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).getById(p.getKey())).withSelfRel()));
		return people;
	}
}
