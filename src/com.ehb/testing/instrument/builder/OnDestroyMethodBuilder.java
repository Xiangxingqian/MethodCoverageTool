package com.ehb.testing.instrument.builder;

import com.ehb.testing.config.MethodConstant;
import com.ehb.testing.config.TypeConstant;
import com.ehb.testing.util.SignatureUtil;
import soot.Local;
import soot.SootClass;
import soot.jimple.Jimple;
import soot.jimple.ThisRef;

import java.util.List;

/**
 * Created by xiangxingqian on 2018/1/19.
 */
public class OnDestroyMethodBuilder extends AbstractMethodBuilder{

    public OnDestroyMethodBuilder(SootClass sc, String returnType, String methodName, List<String> params) {
        super(sc, returnType, methodName, params);
    }

    public OnDestroyMethodBuilder(SootClass sc, String returnType, String methodName, List<String> params, boolean isStatic) {
        super(sc, returnType, methodName, params, isStatic);
    }

    public static final String METHOD_NAME = "onDestroy";

    public static OnDestroyMethodBuilder v(SootClass sc) {
        return new OnDestroyMethodBuilder(sc, TypeConstant.VOID, METHOD_NAME, null);
    }

    @Override
    protected void addUnits() {
        Local activity;
        activity = addLocal("activity", sootClassType);

        addIdentityStmt(activity, new ThisRef(sootClassType));
        addInvokeStmt(Jimple.v().newSpecialInvokeExpr(activity, MethodConstant.onDestroy.makeRef()));
        addInvokeStmt(Jimple.v().newStaticInvokeExpr(SignatureUtil.getSootMethodSignature(TestUtil.class.getName(), "printVisitedMethods").makeRef()));
        addReturnVoidStmt();
    }
}
