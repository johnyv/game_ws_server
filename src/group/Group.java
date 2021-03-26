package group;

import java.util.HashSet;
import java.util.Set;

public enum Group {
    INSTANCE;
    private final Set<String> COMMON_GROUP = new HashSet<>();

    public void add(String groupName) {
        if (COMMON_GROUP.contains(groupName)) {
            return;
        }
        COMMON_GROUP.add(groupName);
    }

    public Set<String> getAllGroup() {
        Set<String> set = new HashSet<>();
        set.addAll(COMMON_GROUP);
        return set;
    }

    public Set<String> getCommonGroup() {
        return COMMON_GROUP;
    }
}
