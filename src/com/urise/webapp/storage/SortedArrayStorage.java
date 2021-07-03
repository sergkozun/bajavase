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
    public int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertElement(Resume resume, int index) {
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

}
