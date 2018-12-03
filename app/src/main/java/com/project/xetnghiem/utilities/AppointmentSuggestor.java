package com.project.xetnghiem.utilities;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.project.xetnghiem.models.SampleDto;
import com.project.xetnghiem.models.Slot;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duc
 */
public class AppointmentSuggestor {

    private List<Slot> availableSlots;
    private String comingDate;
    private int comingTime;
    private List<SampleDto> chosedSamples;

    private final int MAX = 24 * 60 * 60; // total wait time is never exceed 24 hours
    private List<List<Slot>> A = new ArrayList<>();
    private List<List<Slot>> tmpA;
    private boolean[] dA; // dA[i] = true/false;
    private int n;
    private final int minWait = MAX; // MAX
    List<List<Integer>> trace;

    List<Slot> tmpResult;
    private List<Slot> result;

    private void prepareDataStructure() {
        List<List<Slot>> arr = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            // new row
            arr.add(new ArrayList<>());
            SampleDto sample = chosedSamples.get(i);
            for (int j = 0; j < availableSlots.size(); j++) {
                Slot slot = availableSlots.get(j);
                if (slot.getDate().equals(comingDate)
                        && slot.getSampleGroupId() == sample.getSampleGroupId()
                        && slot.getStartTime() > comingTime) {
                    // clone objects
                    Slot item = new Slot();
                    item.setSlotId(slot.getSlotId());
                    item.setDate(slot.getDate());
                    item.setQuantity(slot.getQuantity());
                    item.setSampleGroupId(slot.getSampleGroupId());
                    item.setStartTime(slot.getStartTime());
                    item.setFinishTime(slot.getFinishTime());
                    // arr: add cell
                    int last = arr.size() - 1;
                    arr.get(last).add(item);
                }
            }
        }
        tmpA = arr;
        dA = new boolean[n];
        for (int i = 0; i < n; i++) {
            dA[i] = false;
            A.add(new ArrayList<>());
        }
    }

    private void permute(int i) {
        if (i == n) {
            dynamicProgram();
            return;
        }
        for (int j = 0; j < n; j++) {
            if (dA[j] == false) {
                A.set(i, tmpA.get(j));
                dA[j] = true;
                permute(i + 1);
                dA[j] = false;
            }
        }
    }

    private void dynamicProgram() {
        List<List<Integer>> dp = new ArrayList<>();
        trace = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            dp.add(new ArrayList<>());
            trace.add(new ArrayList<>());
            for (int j = 0; j < A.get(i).size(); j++) {
                int dpItem = MAX;
                int traceItem = -1;
                if (i == 0) {
                    dpItem = A.get(i).get(j).getStartTime() - comingTime;
                } else {
                    dpItem = MAX;
                    for (int k = 0; k < dp.get(i - 1).size(); k++) {
                        if (A.get(i).get(j).getStartTime() > A.get(i - 1).get(k).getFinishTime()) {
                            int waitTime = A.get(i).get(j).getStartTime() - A.get(i - 1).get(k).getFinishTime();
                            int tmpValue = dp.get(i - 1).get(k) + waitTime;
                            if (tmpValue < dpItem) {
                                dpItem = tmpValue;
                                traceItem = k;
                            }
                        }
                    } // end for k
                }
                dp.get(i).add(dpItem);
                trace.get(i).add(traceItem);
            } // end for j 
        } // end for i

        // CALC RES
        int tmpMinWait = MAX;
        int jMinWait = -1;
        for (int j = 0; j < dp.get(n-1).size(); j++) {
            if (dp.get(n-1).get(j) < tmpMinWait) {
                tmpMinWait = dp.get(n-1).get(j);
                jMinWait = j;
            }
        }

        // UPDATE RESULT
        if (tmpMinWait < minWait) {
            tmpResult = new ArrayList<>();
            doTrace(n-1, jMinWait);
            result = tmpResult;
        }
    }

    private void doTrace(int i, int j) {
        if (i < 0) {
            return;
        }
        doTrace(i-1, trace.get(i).get(j));
        tmpResult.add(A.get(i).get(j));
    }

    private void setSampleIdsIntoResult() {
        if (result == null) {
            return;
        }
        for (int i = 0; i < n; i++) {
            chosedSamples.get(i).setSampleId(-1);
        }
        for (int i = 0; i < chosedSamples.size(); i++) {
            int sampleId = chosedSamples.get(i).getSampleId();
            for (int j = 0; j < result.size(); j++) {
                if (sampleId == -1) {
                    result.get(j).setSampleId(sampleId);
                    break;
                }
            }
        }
    }

    private void solve() {
        prepareDataStructure();
        permute(0);
        setSampleIdsIntoResult();
    }

    public List<Slot> CalcTheBestTour(List<Slot> availableSlots, String comingDate,
                                        int comingTime, List<SampleDto> chosedSamples) {
        // init fields
        this.availableSlots = availableSlots;
        this.comingDate = comingDate;
        this.comingTime = comingTime;
        this.chosedSamples = chosedSamples;
        this.n = chosedSamples.size();

        solve();

        return result;
    }

}
