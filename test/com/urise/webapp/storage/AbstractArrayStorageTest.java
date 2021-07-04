package com.urise.webapp.storage;

import com.sun.source.tree.AssertTree;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp(){
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
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
        Assert.assertEquals(storage.size(), 4);
        Assert.assertEquals(storage.get("uuid4"), r4);
    }

    @Test
    public void get() {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");
        Assert.assertEquals(r1, storage.get("uuid1"));
        Assert.assertEquals(r2, storage.get("uuid2"));
        Assert.assertEquals(r3, storage.get("uuid3"));
    }

    @Test
    public void delete() {
        storage.delete("uuid1");
        Assert.assertEquals(storage.size(), 2);
        storage.delete("uuid2");
        Assert.assertEquals(storage.size(), 1);
        storage.delete("uuid3");
        Assert.assertEquals(storage.size(), 0);
    }

    @Test
    public void update() {
        Resume updatedResumeR2 = new Resume("uuid2");
        storage.update(updatedResumeR2);
        Assert.assertEquals(storage.get("uuid2"), updatedResumeR2);
    }

    @Test
    public void getAll() {
        Resume[] allResumes = storage.getAll();
        Assert.assertEquals(allResumes[0], storage.get("uuid1"));
        Assert.assertEquals(allResumes[1], storage.get("uuid2"));
        Assert.assertEquals(allResumes[2], storage.get("uuid3"));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = StorageException.class)
    public void storageOverflow(){
        StringBuilder newUuid = new StringBuilder("uuid");
        try {
            for(int i = 4; i <= 10000; i++){
                storage.save(new Resume(newUuid.append(i).toString()));
            }
        } catch (StorageException e) {
            Assert.fail("unexpected storageOverflow!");
        }
        storage.save(new Resume("overflowUuid10001"));
    }
}