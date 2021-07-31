package com.urise.webapp.storage;

import com.urise.webapp.storage.AbstractStorageTest;
import com.urise.webapp.storage.serializer.JsonStreamSerializer;

import static org.junit.Assert.*;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }
}