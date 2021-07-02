package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > 0) {      // Проверка наличия
            System.out.println("Resume " + resume.getUuid() + " is already exist");
        } else if (size == STORAGE_LIMIT) {      // Защита от переполнения
            System.out.println("Storage overflow");
        } else {
            insertElement(resume, index);
            storage[size] = resume;
            size++;
        }
    }


    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {      // Проверка наличия
            System.out.println("Resume " + uuid + " is not exist");
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {      // Проверка наличия
            System.out.println("Resume " + uuid + " is not exist");
        } else {
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0 ) {      // Проверка наличия
            System.out.println("Resume " + resume.getUuid() + " is not exist");
        } else {
            storage[index] = resume;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract int getIndex(String uuid);

    protected abstract void insertElement(Resume resume, int index);
}
