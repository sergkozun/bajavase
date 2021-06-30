package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public boolean isResumeExist(Resume resume){
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return true;
            }
        }
        return false;
    }
    public boolean isResumeExist(String uuid){
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume resume) {
        if(!isResumeExist(resume)) {
            storage[size] = resume;
            size++;
        } else{
            System.out.println("ERROR");
        }
    }

    public Resume get(String uuid) {
        if(isResumeExist(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) return storage[i];
            }
        }else{
            System.out.println("ERROR");
        }
        return null;
    }

    public void delete(String uuid) {
        if(isResumeExist(uuid)){
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                        for (int j = i; j < size - 1; j++) {
                            storage[j] = storage[j + 1];
                        }
                        size--;
                        break;
                    }
            }
        }else{
            System.out.println("ERROR");
        }
    }

    public void update(Resume resume){
        if(isResumeExist(resume)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(resume.getUuid())) {
                    storage[i] = resume;
                }
            }
        }else{
            System.out.println("ERROR");
        }
    }
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
