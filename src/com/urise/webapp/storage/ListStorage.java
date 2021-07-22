package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private static List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public List<Resume> doGetAll() {
        List<Resume> list123 = new ArrayList<>(list);
        return list123;
    }

    @Override
    public void doSave(Resume resume, Integer searchKey) {
        list.add(resume);
    }

    @Override
    public Resume doGet(Integer index) {
        return list.get(index);
    }

    @Override
    public void doDelete(Integer index) {
        list.remove(index.intValue());
    }

    @Override
    public void doUpdate(Resume resume, Integer index) {
        list.set(index, resume);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
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
