package com.castle.fortress.admin.check.entity;

import lombok.Data;

import java.util.Objects;

@Data
public class MapKey {
    private String key;
    private int sort;

    public MapKey(String key, int sort) {
        this.key = key;
        this.sort = sort;
    }

    public MapKey() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapKey mapKey = (MapKey) o;
        return Objects.equals(key, mapKey.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
