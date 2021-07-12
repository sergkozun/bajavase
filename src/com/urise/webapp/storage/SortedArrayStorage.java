package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void fillDeletedElement(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) System.arraycopy(storage, index + 1, storage, index, numMoved);
    }

    @Override
    public Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "???????????");
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertElement(Resume resume) {
        storage[size] = resume;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

}
