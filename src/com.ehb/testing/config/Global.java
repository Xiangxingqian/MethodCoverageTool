package com.ehb.testing.config;

import com.ehb.manifest.AndroidIntentFilter;
import com.ehb.testing.util.Tuple;
import soot.Scene;
import soot.SootClass;
import soot.jimple.toolkits.callgraph.CallGraph;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class Global {

    public static Global g = null;

    private Global() {
    }

    public static Global v() {
        if (g == null) {
            g = new Global();
        }
        return g;
    }

    public static int totalMethod;
    public static int totalClass;
    public static int totalLine;
    public static int totalAct;

    private String apk;
    private String mainActivity;
    private Set<String> activities;
    private Map<String, List<AndroidIntentFilter>> activityToFilters;
    private Map<String, List<AndroidIntentFilter>> serviceToFilters;
    private Map<String, List<AndroidIntentFilter>> receiverToFilters;
    private String pkg;

    public static Map<Integer, String> indexToMethod = new ConcurrentHashMap<>();

    private Tuple<String, Integer> baseActivityCandidate;
    private Tuple<String, Integer> baseActivity;

    public Tuple<String, Integer> getBaseActivityCandidate() {
        return baseActivityCandidate;
    }

    public void setBaseActivityCandidate(Tuple<String, Integer> baseActivityCandidate) {
        this.baseActivityCandidate = baseActivityCandidate;
    }

    public Tuple<String, Integer> getBaseActivity() {
        return baseActivity;
    }

    public void setBaseActivity(Tuple<String, Integer> baseActivity) {
        this.baseActivity = baseActivity;
    }

    private Map<Integer, String> idToCallBack = new HashMap<Integer, String>();

    private CallGraph callGraph;

    public CallGraph getCallGraph() {
        return callGraph;
    }

    public void setCallGraph(CallGraph callGraph) {
        this.callGraph = callGraph;
    }

    public void setApk(String apk) {
        this.apk = apk;
    }

    public void setMainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setActivities(Set<String> activities) {
        this.activities = activities;
    }

    public void setActivityToFilters(
            Map<String, List<AndroidIntentFilter>> activityToFilters) {
        this.activityToFilters = activityToFilters;
    }

    public void setServiceToFilters(
            Map<String, List<AndroidIntentFilter>> serviceToFilters) {
        this.serviceToFilters = serviceToFilters;
    }

    public void setReceiverToFilters(
            Map<String, List<AndroidIntentFilter>> receiverToFilters) {
        this.receiverToFilters = receiverToFilters;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getApk() {
        return apk;
    }

    public String getMainActivity() {
        return mainActivity;
    }

    public Set<String> getActivities() {
        return activities;
    }

    public Map<String, List<AndroidIntentFilter>> getActivityToFilters() {
        return activityToFilters;
    }

    public Map<String, List<AndroidIntentFilter>> getServiceToFilters() {
        return serviceToFilters;
    }

    public Map<String, List<AndroidIntentFilter>> getReceiverToFilters() {
        return receiverToFilters;
    }

    public String getPkg() {
        return pkg;
    }

    public SootClass getMainActivityClass() {
        return Scene.v().getSootClass(mainActivity);
    }

    List<SootClass> visitedClasses = new ArrayList<SootClass>();

    public List<SootClass> getVisitedClasses() {
        return visitedClasses;
    }

    public Map<Integer, String> getIdToCallBack() {
        return idToCallBack;
    }

    Set<String> ehbEvent = new HashSet<>();

    public Set<String> getEHBEventSet() {
        return ehbEvent;
    }

    @Override
    public String toString() {
        return "Global{" +
                "apk='" + apk + '\'' +
                ", mainActivity='" + mainActivity + '\'' +
                ", activities=" + activities +
                ", activityToFilters=" + activityToFilters +
                ", serviceToFilters=" + serviceToFilters +
                ", receiverToFilters=" + receiverToFilters +
                ", pkg='" + pkg + '\'' +
                ", baseActivityCandidate=" + baseActivityCandidate +
                ", baseActivity=" + baseActivity +
                ", idToCallBack=" + idToCallBack +
                ", callGraph=" + callGraph +
                ", visitedClasses=" + visitedClasses +
                ", ehbEvent=" + ehbEvent +
                '}';
    }
}
