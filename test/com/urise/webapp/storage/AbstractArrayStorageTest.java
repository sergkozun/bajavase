package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() {
        Resume r4 = new Resume("uuid4", "name4");
        storage.save(r4);
        assertSize(4);
        assertGet(r4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid1");
        assertSize(2);
        storage.delete("uuid2");
        assertSize(1);
        storage.delete("uuid3");
        assertSize(0);
        assertGet(RESUME_1);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        StringBuilder newFullname = new StringBuilder("uuid");
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(newFullname.append(i).toString()));
            }
        } catch (StorageException | CloneNotSupportedException e) {
            Assert.fail("unexpected storageOverflow!");
        }
        storage.save(new Resume("Uuid10001", "name10001"));
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}