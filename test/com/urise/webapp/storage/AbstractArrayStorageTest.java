package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("uuid7");
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(storage.size(), 0);
    }

    @Test
    public void save() {
        Resume r4 = new Resume("uuid4");
        storage.save(r4);
        assertSize(4);
        assertGet(r4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_2);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("uuid7");
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
    public void update() {
        Resume updatedResumeR2 = new Resume("uuid2");
        storage.update(updatedResumeR2);
        assertGet(updatedResumeR2);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("uuid7"));
    }

    @Test
    public void getAll() {
        Resume[] allResumes = storage.getAll();
        Assert.assertEquals(allResumes[0], RESUME_1);
        Assert.assertEquals(allResumes[1], RESUME_2);
        Assert.assertEquals(allResumes[2], RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        StringBuilder newUuid = new StringBuilder("uuid");
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(newUuid.append(i).toString()));
            }
        } catch (StorageException e) {
            Assert.fail("unexpected storageOverflow!");
        }
        storage.save(new Resume("Uuid10001"));
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size){
        Assert.assertEquals(size, storage.size());
    }
}