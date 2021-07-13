package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SearchKey> implements Storage {

    public Resume get(String uuid) {
        SearchKey searchKey = getExistedKey(uuid);
        return doGet(searchKey);
    }

    public void update(Resume resume) {
        SearchKey searchKey = getExistedKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    public void save(Resume resume) {
        SearchKey searchKey = getNotExistedKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    public void delete(String uuid) {
        SearchKey searchKey = getExistedKey(uuid);
        doDelete(searchKey);
    }

    public List<Resume> getAllSorted(){
        List<Resume> allResumes = doGetAll();
        Collections.sort(allResumes);
        return allResumes;
    }

    protected abstract List<Resume> doGetAll();

    protected abstract Resume doGet(SearchKey searchKey);

    protected abstract void doDelete(SearchKey searchKey);

    protected abstract void doSave(Resume resume, SearchKey searchKey);

    protected abstract void doUpdate(Resume resume, SearchKey searchKey);

    protected abstract boolean isExist(SearchKey searchKey);

    private SearchKey getExistedKey(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) throw new NotExistStorageException(uuid);
        return searchKey;
    }

    private SearchKey getNotExistedKey(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) throw new ExistStorageException(uuid);
        return searchKey;
    }

    protected abstract SearchKey getSearchKey(String uuid);

}
