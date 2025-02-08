package com.tiji.noweakness;


public class getAlertSound {
    public static Float getSoundPitch(int tick){
        if (tick % 4 == 0) return 0.529732f;
        else if ((tick + 2) % 4 == 0) return 0.594604f;
        return 0f;
    }
}
