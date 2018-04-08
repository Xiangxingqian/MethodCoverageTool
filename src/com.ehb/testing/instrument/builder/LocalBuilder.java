package com.ehb.testing.instrument.builder;

import soot.ArrayType;
import soot.Body;
import soot.Local;
import soot.Type;
import soot.jimple.Jimple;

public class LocalBuilder{
	
	protected Local addLocal(Body body, String name, Type refType){
		Local newLocal = Jimple.v().newLocal(name, refType);
		body.getLocals().add(newLocal);
		return newLocal;
	}
	
	protected Local addLocalArray(Body body, String name, Type refType){
		Local newLocal = Jimple.v().newLocal(name, ArrayType.v(refType, 1));
		body.getLocals().add(newLocal);
		return newLocal;
	}
}
