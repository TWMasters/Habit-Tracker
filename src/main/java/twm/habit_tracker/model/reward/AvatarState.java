package twm.habit_tracker.model.reward;

import java.util.HashMap;

class AvatarState {
    private static final String FILE_NAME = "AvatarState.txt";

    /**
     * Change current avatar state
     * @param key Selected Avatar Component
     * @param value new Avatar Reward for Component
     */
    public void changeAvatarState(String key, String value) {
        HashMap<String,String> oldState = FileHelper.readFromFile(FILE_NAME);
        oldState.replace(key, value);
        FileHelper.writeToFile(oldState, FILE_NAME);
    }

    /**
     * Create avatar state file
     */
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

    /**
     * Retrieve current avatar state
     * @return Mapping of Avatar Components to current part
     */
    public HashMap<String,String> getAvatarState() {
        return FileHelper.readFromFile(FILE_NAME);
    }


}
