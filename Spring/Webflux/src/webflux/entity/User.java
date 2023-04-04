package webflux.entity;

import org.springframework.lang.Nullable;

/**
 @author Alex
 @create 2023-02-28-11:18
 */
public class User {
    @Nullable
    private String name;

    @Nullable
    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getName() {
        return name;
    }
}
