package com.ehb.testing.instrument.transformer;

import com.ehb.testing.instrument.instrumenter.MethodCoverageInstrumenter;
import soot.Body;
import soot.BodyTransformer;

import java.util.Map;

public class ApkBodyTransformer extends BodyTransformer {

    public ApkBodyTransformer() {
    }

    @Override
    protected void internalTransform(Body b, String phaseName, Map<String, String> options) {
        MethodCoverageInstrumenter.instrumentMethodCoverage(b);
    }
}
