package com.talukder.mom.model;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.talukder.mom.domain.Moms;

@Repository
@Transactional
public class MomsDaoImpl implements MomsDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void addNew(Moms m) {
		getSession().save(m);

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Moms> list() {
		return getSession().createQuery("from Moms").list();

	}

	@Override
	public void delete(Moms mom) {
		getSession().delete(mom);
	}

	@Override
	public void update(Moms mom) {
		getSession().clear();
		getSession().update(mom);

	}

	@Override
	public Moms geMomsById(int id) {
		Moms objMom = (Moms) getSession().createQuery("from Moms where id=:id")
				.setParameter("id", id)
				.uniqueResult();
		return objMom;
	}

}
