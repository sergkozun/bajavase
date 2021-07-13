package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    private static Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> doGetAll(){
        return new ArrayList<>(map.values());
    }

    @Override
    public void doSave(Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(String searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    public Resume doGet(String searchKey) {
        return map.get(searchKey);
    }

    @Override
    public void doDelete(String searchKey) {
        map.remove(searchKey);
    }

    @Override
    public void doUpdate(Resume resume, String searchKey) {
        map.put(searchKey, resume);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
            return uuid;
    }

}
