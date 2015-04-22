package ru.novotelnovad.view.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * ���������� �������� / ��������� ������, ������� ���������� URL ��� ���� ������ ��� ��������...
 * @author ����������� �.�.
 * @version 1.0
 */
@ApplicationPath("rest")
public class RestConfiguration extends Application {
	  public Set<Class<?>> getClasses() {
	  Set<Class<?>> classes = new HashSet<>();
	  	classes.add(ru.adnovotelnov.ejb.UserEJBLocal.class);
	  	return classes;
	  }
}