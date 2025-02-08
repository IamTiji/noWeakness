package com.tiji.noweakness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class TpaRequestsContainer {
    public static HashMap<UUID, ArrayList<UUID>> requests = new HashMap<>();
    public static void addRequest(UUID sender, UUID receiver){
        if (requests.containsKey(sender)) {
            try {
                requests.get(receiver).add(sender);
            } catch (NullPointerException e) {
                requests.put(receiver, new ArrayList<>(Collections.singleton(sender)));
            }
        }else{
            requests.put(receiver, new ArrayList<>(Collections.singleton(sender)));
        }
    }
    public static ArrayList<UUID> getRequests(UUID receiver){
        return requests.getOrDefault(receiver, new ArrayList<>());
    }
    public static void removeRequest(UUID sender, UUID receiver){
        if (requests.containsKey(receiver)) {
            requests.get(receiver).remove(sender);
        }
    }
    public static boolean isThereRequest(UUID sender, UUID receiver){
        return getRequests(receiver).contains(sender);
    }
    public static String ToString() {
        StringBuilder sb = new StringBuilder();
        for (UUID receiver : requests.keySet()) {
            sb.append(receiver).append(" -> ").append(requests.get(receiver)).append("\n");
        }
        return sb.toString();
    }
}
