package twm.habit_tracker.model.reward;

import java.util.HashMap;

class AvatarState {
    private static final String FILE_NAME = "AvatarState.txt";

    public void createAvatarStateFile() {
        HashMap<String,String> map = new HashMap<>();
        map.put("Under-layer", "none");
        map.put("Over-layer", "none");
        map.put("Bottoms", "none");
        map.put("Footwear", "none");
        map.put("Eyewear", "none");
        map.put("Headwear", "none");
        FileHelper.writeToFile(map, FILE_NAME);
    }

}
