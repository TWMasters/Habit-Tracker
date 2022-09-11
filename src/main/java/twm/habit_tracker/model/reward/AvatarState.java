package twm.habit_tracker.model.reward;

import java.util.HashMap;

class AvatarState {
    private static final String FILE_NAME = "AvatarState.txt";

    public void createAvatarStateFile() {
        HashMap<String,String> map = new HashMap<>();
        map.put("Under-layer", "None");
        map.put("Over-layer", "None");
        map.put("Bottoms", "None");
        map.put("Footwear", "None");
        map.put("Eyewear", "None");
        map.put("Headwear", "None");
        FileHelper.writeToFile(map, FILE_NAME);
    }

    public HashMap<String,String> getAvatarState() {
        return FileHelper.readFromFile(FILE_NAME);
    }

}
