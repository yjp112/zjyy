package com.supconit.ywgl.patrol.services.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.ywgl.patrol.entities.PatrolPerson;
import com.supconit.ywgl.patrol.services.PatrolPersonService;
@Service
public class PatrolPersonServiceImp extends AbstractBaseBusinessService<PatrolPerson, Long> implements PatrolPersonService{

	@Override
	public PatrolPerson getById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(PatrolPerson entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(PatrolPerson entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PatrolPerson> findAll() {
		
		return null;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}
}
