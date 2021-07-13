package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private static Map<String, Resume> map = new HashMap<>();

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected void doDelete(Resume resume) {
        map.remove((resume).getUuid());
    }

    @Override
    protected void doSave(Resume r) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdate(Resume r, Resume resume) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }
}
