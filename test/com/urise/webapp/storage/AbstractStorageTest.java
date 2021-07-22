package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorageTest {
    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    protected static final Resume R1;
    private static final String UUID_2 = "uuid2";
    protected static final Resume R2;
    private static final String UUID_3 = "uuid3";
    protected static final Resume R3;

    static {
        R1 = new Resume(UUID_1, "Kozun Sergey");
        R2 = new Resume(UUID_2, "Fokin Nikolay");
        R3 = new Resume(UUID_3, "Loktionov Vitaly");
        R1.addContact(ContactType.EMAIL, "kozunsv@gmail.com");
        R1.addContact(ContactType.PHONE, "+79136797316");
        R1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListSection("ACHIEVEMENT1", "ACHIEVEMENT2", "ACHIEVEMENT3"));
        R1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(new Organization("Organization11", "http://Organization11.ru",
                        new Organization.Position(2006, Month.AUGUST, "position1", "content1"),
                        new Organization.Position(2007, Month.SEPTEMBER, "position2", "content2"))));
        R1.addSection(SectionType.EDUCATION, new OrganizationSection(new Organization("Institute1", "http://Organization44.ru",
                new Organization.Position(2006, Month.AUGUST, "student", "fakultet1"),
                new Organization.Position(2008, Month.JANUARY, "student", "fakultet2")),
                new Organization("Organization12", "http://Organization22.ru",
                        new Organization.Position(2008, Month.FEBRUARY, "student", "fakultet1"))));
        R2.addContact(ContactType.EMAIL, "fokinnikol@inbox.ru");
        R2.addContact(ContactType.PHONE, "+79223451234");
        R2.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(new Organization("Organization2", "http://Organization2.ru",
                        new Organization.Position(2012, Month.JULY, "position2", "content2    "))));
    }
    @Before
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
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
        Resume r4 = new Resume("uuid4", "name4");
        storage.save(r4);
        assertGet(r4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(R2);
    }

    @Test
    public void get() {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("uuid7");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid1");
        storage.delete("uuid2");
        storage.delete("uuid3");
        assertGet(R1);
    }

    @Test
    public void update() {
        Resume updatedResumeR2 = new Resume("uuid2", "name2");
        storage.update(updatedResumeR2);
        assertGet(updatedResumeR2);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("uuid7", "name7"));
    }

    @Test
    public void getAllSorted() {
        List<Resume> allResumes = storage.getAllSorted();
        Assert.assertEquals(3, allResumes.size());
        List<Resume> allResumes2 = Arrays.asList(R1, R2, R3);
        Collections.sort(allResumes2);
        Assert.assertEquals(allResumes, allResumes2);
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }
}

