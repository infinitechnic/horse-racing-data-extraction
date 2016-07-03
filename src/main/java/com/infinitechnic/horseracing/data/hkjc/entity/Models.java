package com.infinitechnic.horseracing.data.hkjc.entity;

import com.sun.javafx.collections.ImmutableObservableList;

import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Models<E> {
    private Collection<E> models;

    public Models(Collection<E> models) {
        super();
        this.models = models;
    }

    public E add(E model) {
        models.add(model);
        return model;
    }

    public boolean addAll(Collection<E> models) {
        return this.models.addAll(models);
    }

    public boolean remove(E model) {
        return models.remove(model);
    }

    public void clear() {
        models.clear();
    }

    public Stream<E> stream() {
        return models.stream();
    }

    public List<E> getModels() {
        return Collections.unmodifiableList(new ArrayList<E>(models));
    }

    public int size() {
        return models == null ? 0 : models.size();
    }
}
