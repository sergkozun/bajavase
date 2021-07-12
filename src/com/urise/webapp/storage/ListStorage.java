package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private static List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public List<Resume> doGetAll(){
        return new ArrayList<>(list);
    }

    @Override
    public void doSave(Resume resume) {
        list.add(resume);
    }

    @Override
    public Resume doGet(Object index) {
        return list.get((Integer) index);
    }

    @Override
    public void doDelete(Object index) {
        list.remove(((Integer) index).intValue());
    }

    @Override
    public void doUpdate(Resume resume, Object index) {
        list.set((Integer) index, resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) return i;
        }
        return null;
    }

}
