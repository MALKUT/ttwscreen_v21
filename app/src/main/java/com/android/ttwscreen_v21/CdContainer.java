package com.android.ttwscreen_v21;
public class CdContainer {
    public boolean isContainerNumberValid(String pCid){
        if(pCid == null || pCid.length() != 11){
            return false;
        }
        String char2num = "0123456789A?BCDEFGHIJK?LMNOPQRSTU?VWXYZ";
        int sum = 0;
        for (int i = 0; i < 10; i++){
            int n = (char2num.indexOf(pCid.charAt(i)));
            n *= Math.pow(2, i);
            sum += n;
        }
        int rem = (sum % 11) % 10;
        return char2num.indexOf(pCid.charAt(10))== rem;
    }
}
