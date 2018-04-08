package com.ehb.testing.util;

import soot.*;
import soot.jimple.AbstractStmtSwitch;
import soot.jimple.IdentityStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.Stmt;

import java.util.Iterator;

/**
 * Created by xiangxingqian on 2018/1/13.
 */
public class StmtUtil {

    public static void insertStmt(final Body body, final boolean isBefore, Tuple<String, StmtCallBack>... tuples) {
        PatchingChain<Unit> units = body.getUnits();
        if (tuples.length == 0)
            return;
        for (Iterator<Unit> iter = units.snapshotIterator(); iter.hasNext(); ) {
            final Unit u = iter.next();
            u.apply(new AbstractStmtSwitch() {
                @Override
                public void caseInvokeStmt(InvokeStmt stmt) {
                    for (Tuple<String, StmtCallBack> tuple : tuples) {
                        if (stmt.getInvokeExpr().getMethod().getSignature().equals(tuple.getFirst())) {
                            System.out.println("caseInvokeStmt Line26 " + stmt);
                            Stmt insertedStmt = tuple.getSecond().callback(stmt);
                            if (isBefore) {
                                units.insertBefore(insertedStmt, u);
                            } else {
                                units.insertAfter(insertedStmt, u);
                            }
                            body.validate();
                        }
                    }
                }
            });
        }
    }

    public static void insertStmt(final Body body, final boolean isBefore, final String signature, StmtCallBack stmtCallBack) {
        insertStmt(body, isBefore, new Tuple<>(signature, stmtCallBack));
    }

    public static void insertAfterIdentity(Body b, Stmt stmt) {
        PatchingChain<Unit> units = b.getUnits();
        Unit firstNonIdentityStmt = units.getFirst();
        for (Unit unit : units) {
            if (unit instanceof IdentityStmt)
                continue;
            firstNonIdentityStmt = unit;
            break;
        }
        units.insertBefore(stmt, firstNonIdentityStmt);
    }

    public interface StmtCallBack {
        Stmt callback(Stmt stmt);
    }
}
