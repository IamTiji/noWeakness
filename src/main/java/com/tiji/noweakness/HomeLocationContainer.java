package com.tiji.noweakness;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.math.Vec3d;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

public class HomeLocationContainer {
    private static final HashMap<UUID, Vec3d> homeLocations = new HashMap<>();

    public static void createOrLoad() throws IOException {
        Path saveFile = FabricLoader.getInstance().getConfigDir().resolve("home_locations.json");
        if (Files.exists(saveFile)) {
            String rawString = Files.readString(saveFile);
            JsonObject locations = new Gson().fromJson(rawString, JsonObject.class);

            for (String item : locations.asMap().keySet()){
                UUID uuid = UUID.fromString(item);
                JsonArray posElement = locations.get(item).getAsJsonArray();
                double x = posElement.get(0).getAsDouble();
                double y = posElement.get(1).getAsDouble();
                double z = posElement.get(2).getAsDouble();
                homeLocations.put(uuid, new Vec3d(x, y, z));
            }
        } else {
            Files.createFile(saveFile);
        }
    }

    private static void save() throws IOException {
        JsonObject locations = new JsonObject();
        for (UUID uuid : homeLocations.keySet()) {
            Vec3d location = homeLocations.get(uuid);
            JsonArray posArray = new JsonArray();
            posArray.add(location.x);
            posArray.add(location.y);
            posArray.add(location.z);
            locations.add(uuid.toString(), posArray);
        }
        Files.write(FabricLoader.getInstance().getConfigDir().resolve("home_locations.json"), new Gson().toJson(locations).getBytes());
    }

    public static void setHomeLocation(UUID uuid, Vec3d location) {
        homeLocations.put(uuid, location);
        try {
            save();
        } catch (IOException e) {
            No.LOGGER.error("Home 명령어 집 지정 실패: ", e);
        }
    }

    public static Vec3d getHomeLocation(UUID uuid) {
        return homeLocations.get(uuid);
    }
    public static boolean hasHomeLocation(UUID uuid) {
        return homeLocations.containsKey(uuid);
    }
}
