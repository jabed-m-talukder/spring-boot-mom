package com.talukder.mom.model;

import java.util.List;

import com.talukder.mom.domain.Moms;

public interface MomsDao {

	public void addNew(Moms m);

	public Moms geMomsById(int id);

	public List<Moms> list();

	void update(Moms moms);

	void delete(Moms mom);

}
