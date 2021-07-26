package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(),  new ObjectStreamSerializer()));
    }

}