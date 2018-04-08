package com.ehb.testing.instrument.builder;

import com.ehb.testing.config.TypeConstant;
import com.ehb.testing.util.ObjectUtil;
import soot.*;
import soot.jimple.*;
import soot.util.Chain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangxingqian on 2017/12/29.
 */
public abstract class AbstractMethodBuilder extends StatementBuilder {

    protected Chain<Unit> units;
    protected SootClass sc;
    protected String subSignature;
    protected SootMethod method;
    protected Body body;

    protected String returnType;
    protected String methodName;
    protected List<String> params;
    private boolean isStatic;
    public RefType sootClassType;

    public AbstractMethodBuilder(SootClass sc, String returnType, String methodName, List<String> params) {
        this(sc, returnType, methodName, params, false);
    }

    public AbstractMethodBuilder(SootClass sc, String returnType, String methodName, List<String> params, boolean isStatic) {
        this.sc = sc;
        this.sootClassType = RefType.v(sc);
        this.returnType = returnType;
        this.methodName = methodName;
        this.params = ObjectUtil.notEmpty(params) ? params : new ArrayList<>();
        String paramSig = ObjectUtil.notEmpty(params) ? params.toString().substring(1, params.toString().length() - 1) : "";
        paramSig = paramSig.replace(" ", "");
        subSignature = String.format("%s %s(%s)", returnType, methodName, paramSig);
        this.isStatic = isStatic;
    }

    public void build() {
        createMethodName();
        addMethodBody();
        if (!sc.declaresMethod(subSignature)) {
            sc.addMethod(method);
        } else { //remove the original method
            SootMethod oldMethod = sc.getMethod(subSignature);
            System.out.println("build Line " + oldMethod.getSignature());
            sc.removeMethod(oldMethod);
            sc.addMethod(method);
        }
    }

    protected void addMethodBody() {
        body = Jimple.v().newBody(method);
        body.getLocals();
        method.setActiveBody(body);
        units = body.getUnits();
        addUnits();
        body.validate();
    }

    protected abstract void addUnits();

    protected void createMethodName() {
        List<Type> types = paramTypes();
        params.forEach(s -> types.add(RefType.v(s)));

        Type rType;
        if (returnType.equals(TypeConstant.BOOL)) {
            rType = BooleanType.v();
        } else if (returnType.equals(TypeConstant.VOID)) {
            rType = VoidType.v();
        } else {
            rType = RefType.v(returnType);
        }
        method = new SootMethod(methodName, types, rType, isStatic ? Modifier.PUBLIC | Modifier.STATIC : Modifier.PUBLIC);
    }

    public Local addLocal(String name, Type refType) {
        return addLocal(body, name, refType);
    }

    public Local addLocalArray(String name, Type refType) {
        return addLocalArray(body, name, refType);
    }

    protected IdentityStmt addIdentityStmt(Value local, Value identityRef) {
        return addIdentityStmt(units, local, identityRef);
    }

    protected AssignStmt addAssignStmt(Value leftOp, Value rightOp) {
        return addAssignStmt(units, leftOp, rightOp);
    }

    protected InvokeStmt addInvokeStmt(Value op) {
        return addInvokeStmt(units, op);
    }

    protected ReturnVoidStmt addReturnVoidStmt() {
        return addReturnVoidStmt(units);
    }

    protected ReturnStmt addReturnTypeStmt(Value v) {
        return addReturnTypeStmt(units, v);
    }

    protected IfStmt addIfStmt(Expr expr, Unit target) {
        return addIfStmt(units, expr, target);
    }

    protected GotoStmt addGotoStmt(Unit target) {
        return addGotoStmt(units, target);
    }

    public List<Value> paramValues() {
        return new ArrayList<Value>();
    }

    public List<Type> paramTypes() {
        return new ArrayList<Type>();
    }

    public SootMethod getMethod() {
        return method;
    }

}
