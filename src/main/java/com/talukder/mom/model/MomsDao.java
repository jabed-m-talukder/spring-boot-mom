package com.talukder.mom.model;

import java.util.List;

import com.talukder.mom.domain.Moms;

public interface MomsDao {

	public void save(Moms m);

	public List<Moms> list();

}
