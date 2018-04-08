package com.ehb.testing.instrument.instrumenter;

import com.ehb.testing.config.Global;
import com.ehb.testing.instrument.builder.OnDestroyMethodBuilder;
import com.ehb.testing.instrument.builder.TestUtil;
import com.ehb.testing.util.CheckUtil;
import com.ehb.testing.util.ObjectUtil;
import com.ehb.testing.util.SignatureUtil;
import com.ehb.testing.util.StmtUtil;
import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.IntConstant;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;

import java.util.HashSet;
import java.util.Set;

import static com.ehb.testing.config.TypeConstant.ON_DESTROY_SIGNATURE;
import static com.ehb.testing.util.StmtUtil.insertAfterIdentity;

/**
 * Created by xiangxingqian on 2018/1/19.
 */
public class MethodCoverageInstrumenter {

    /**
     * In Jimple method, the identity statements must be at the first line.
     * <p>
     * Thus, the method coverage statistics statement must be inserted after the identity statements
     *
     * @param b
     */
    public static void instrumentMethodCoverage(Body b) {
        if (CheckUtil.checkValidMethod(b)) {
            TestUtil.METHOD++;
//            indexToMethod.put(TestUtil.METHOD, b.getMethod().getSignature()+"\n");
            SootMethod addMethodCount = Scene.v().getMethod(SignatureUtil.getMethodSignature(TestUtil.class.getName(), "addMethodCount"));
            InvokeStmt invokeStmt = Jimple.v().newInvokeStmt(Jimple.v().newStaticInvokeExpr(addMethodCount.makeRef(), IntConstant.v(TestUtil.METHOD)));
            insertAfterIdentity(b, invokeStmt);

        }
    }

    public static void modifyAddOnDestroy(String baseActivity) {
        if (ObjectUtil.notEmpty(baseActivity)) {
            HashSet<String> set = new HashSet<String>();
            set.add(baseActivity);
            modifyActivity(set);
        } else {
            modifyActivity(Global.v().getActivities());
        }
    }

    private static void modifyActivity(Set<String> activities) {
        for (String activity : activities) {
            SootClass sootClass = Scene.v().getSootClass(activity);
            if (sootClass.declaresMethod(ON_DESTROY_SIGNATURE)) {
                SootMethod method = Scene.v().getMethod(ON_DESTROY_SIGNATURE);
                InvokeStmt printVisitedMethods = Jimple.v().newInvokeStmt(Jimple.v().newStaticInvokeExpr(SignatureUtil.getSootMethodSignature(TestUtil.class.getName(), "printVisitedMethods").makeRef()));
                StmtUtil.insertAfterIdentity(method.getActiveBody(), printVisitedMethods);
            } else {
                OnDestroyMethodBuilder.v(Scene.v().getSootClass(activity)).build();
            }
        }
    }
}
